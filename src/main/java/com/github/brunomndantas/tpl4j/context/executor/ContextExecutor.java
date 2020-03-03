/*-
 * Copyright (c) 2019, Bruno Dantas <bmndantas@gmail.com>
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.  */
package com.github.brunomndantas.tpl4j.context.executor;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.status.State;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class ContextExecutor implements IContextExecutor {

    protected volatile IContextManager contextManager;
    public IContextManager getContextManager() { return this.contextManager; }



    public ContextExecutor(IContextManager contextManager) {
        this.contextManager = contextManager;
    }



    @Override
    public synchronized <T> void execute(Context<T> context) {
        if(context.getStatus().getScheduledEvent().hasFired())
            throw new RuntimeException("Task:" + context.getTaskId() + " already scheduled!");

        context.getStatus().setState(State.SCHEDULED);

        if(!this.verifyCancel(context))
            this.schedule(context);
    }

    protected <T> boolean verifyCancel(Context<T> context) {
        try {
            if(!context.getOptions().notCancelable())
                context.getCancellationToken().abortIfCancelRequested();
        } catch (CancelledException e) {
            this.endExecution(context, null, e);
            return true;
        }

        return false;
    }

    protected <T> void schedule(Context<T> context) {
        context.getScheduler().schedule(() -> run(context));
    }

    protected <T> void run(Context<T> context) {
        this.contextManager.registerCurrentThreadAsExecutorOfContext(context);

        try {
            if(!context.getOptions().notCancelable())
                context.getCancellationToken().abortIfCancelRequested();

            context.getStatus().setState(State.RUNNING);

            T value = context.getAction().run(context.getCancellationToken());

            declareSuccess(context, value);
        } catch(Exception e) {
            if(e instanceof CancelledException)
                declareCancel(context, (CancelledException) e);
            else
                declareFail(context, e);
        }

        this.contextManager.registerCurrentThreadEndExecutionOfContext(context);
    }

    protected <T> void declareSuccess(Context<T> context, T value) {
        Collection<Context<?>> children = getAttachedChildrenContext(context);

        if(children.isEmpty())
            this.endExecution(context, value, null);
        else
            this.endExecutionAfterChildrenFinish(context, children, value, null);
    }

    protected <T> void declareCancel(Context<T> context, CancelledException exception) {
        Collection<Context<?>> children = getAttachedChildrenContext(context);

        if(children.isEmpty())
            this.endExecution(context, null, exception);
        else
            this.endExecutionAfterChildrenFinish(context, children, null, exception);
    }

    protected <T> void declareFail(Context<T> context, Exception exception) {
        Collection<Context<?>> children = getAttachedChildrenContext(context);

        if(children.isEmpty())
            this.endExecution(context, null, exception);
        else
            this.endExecutionAfterChildrenFinish(context, children, null, exception);
    }

    protected Collection<Context<?>> getAttachedChildrenContext(Context<?> context) {
        if(context.getOptions().rejectChildren())
            return new LinkedList<>();

        return context.getChildrenContexts()
                .stream()
                .filter(ctx -> ctx.getOptions().attachToParent())
                .collect(Collectors.toList());
    }

    protected <T> void endExecutionAfterChildrenFinish(Context<T> context, Collection<Context<?>> childrenContexts, T value, Exception exception) {
        context.getStatus().setState(State.WAITING_CHILDREN);

        for(Context<?> childContext : childrenContexts) {
            childContext.getStatus().getFinishedEvent().addListener(() -> {
                context.getScheduler().schedule(() -> {
                    synchronized (childrenContexts) {
                        if(!context.getStatus().getFinishedEvent().hasFired() && allFinished(childrenContexts))
                            endExecution(context, childrenContexts, value, exception);
                    }
                });
            });
        }
    }

    protected boolean allFinished(Collection<Context<?>> contexts) {
        return contexts
                .stream()
                .allMatch((context) -> context.getStatus().getFinishedEvent().hasFired());
    }

    protected <T> void endExecution(Context<T> context, Collection<Context<?>> childrenContexts, T value, Exception exception) {
        Exception childException;
        for(Context<?> childContext : childrenContexts) {
            if(childContext.getStatus().getState().equals(State.FAILED)) {
                childException = childContext.getResultException();

                if(exception == null || exception instanceof CancelledException)
                    exception = childException;
                else if(exception != childException)
                    exception.addSuppressed(childException);
            }
        }

        this.endExecution(context, value, exception);
    }

    protected <T> void endExecution(Context<T> context, T value, Exception exception) {
        if(exception != null && exception instanceof CancelledException) {
            this.contextManager.setContextResult(context, null, exception);
            context.getStatus().setState(State.CANCELED);
        } else if(exception != null) {
            this.contextManager.setContextResult(context, null, exception);
            context.getStatus().setState(State.FAILED);
        } else {
            this.contextManager.setContextResult(context, value, null);
            context.getStatus().setState(State.SUCCEEDED);
        }

        this.contextManager.unregisterContext(context);
    }

}
