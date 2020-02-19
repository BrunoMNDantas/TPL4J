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
package com.github.brunomndantas.tpl4j.core.job;

import com.github.brunomndantas.tpl4j.core.context.TaskOption;
import com.github.brunomndantas.tpl4j.core.action.action.IAction;
import com.github.brunomndantas.tpl4j.core.context.cancel.CancellationToken;

import java.util.Collection;
import java.util.function.Consumer;

public class Job<T> extends SimpleJob<T> {

    protected volatile Collection<TaskOption> options;
    public Collection<TaskOption> getOptions() { return this.options; }



    public Job(String taskId, IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<TaskOption> options) {
        super(taskId, action, cancellationToken, scheduler);
        this.options = options;

        JobManager.INSTANCE.registerJobCreationOnCurrentThread(this);
    }



    @Override
    protected void run() {
        JobManager.INSTANCE.registerJobExecutionOnCurrentThread(this);
        super.run();
        JobManager.INSTANCE.unregisterJobExecution(this);
    }

    @Override
    protected void declareCancel() {
        Collection<JobContext> children = JobManager.INSTANCE.getAttachedChildrenContexts(this);

        if(children.isEmpty())
            super.declareCancel();
        else
            registerCancelAfterChildren(children);
    }

    protected void registerCancelAfterChildren(Collection<JobContext> childrenContexts) {
        super.status.declareWaitChildren();

        for(JobContext childContext : childrenContexts) {
            childContext.getJob().cancel();

            childContext.getJob().status.finishedEvent.addListener(() -> {
                super.scheduler.accept(() -> {
                    synchronized (childContext) {
                        if(!super.status.finishedEvent.hasFired() && allFinished(childrenContexts))
                            declareCancelAfterChildren(childrenContexts);
                    }
                });
            });
        }
    }

    protected void declareCancelAfterChildren(Collection<JobContext> childrenContexts) {
        for(JobContext childContext : childrenContexts) {
            if(childContext.getJob().status.failedEvent.hasFired()) {
                Exception childException = childContext.getJob().getException();
                childException = new ChildException(childException.getMessage(), childException);
                super.setException(childException);
                super.declareFail();
                return;
            }
        }

        super.declareCancel();
    }

    @Override
    protected void declareSuccess() {
        Collection<JobContext> children = JobManager.INSTANCE.getAttachedChildrenContexts(this);

        if(children.isEmpty())
            super.declareSuccess();
        else
            registerSuccessAfterChildren(children);
    }

    protected void registerSuccessAfterChildren(Collection<JobContext> childrenContexts) {
        super.status.declareWaitChildren();

        synchronized (childrenContexts){
            for(JobContext childContext : childrenContexts) {
                childContext.getJob().status.finishedEvent.addListener(() -> {
                    super.scheduler.accept(() -> {
                        synchronized (childContext) {
                            if(!super.status.finishedEvent.hasFired() && allFinished(childrenContexts))
                                declareSuccessAfterChildren(childrenContexts);
                        }
                    });
                });
            }
        }
    }

    protected void declareSuccessAfterChildren(Collection<JobContext> childrenContexts) {
        for(JobContext childContext : childrenContexts) {
            if(childContext.getJob().status.failedEvent.hasFired()) {
                Exception childException = childContext.getJob().getException();
                childException = new ChildException(childException.getMessage(), childException);
                super.setException(childException);
                super.declareFail();
                return;
            }
        }

        super.declareSuccess();
    }

    @Override
    protected void declareFail() {
        Collection<JobContext> children = JobManager.INSTANCE.getAttachedChildrenContexts(this);

        if(children.isEmpty())
            super.declareFail();
        else
            registerFailAfterChildren(children);
    }

    protected void registerFailAfterChildren(Collection<JobContext> childrenContexts) {
        super.status.declareWaitChildren();

        for(JobContext childContext : childrenContexts) {
            childContext.getJob().status.finishedEvent.addListener(() -> {
                super.scheduler.accept(() -> {
                    synchronized (childContext) {
                        if(!super.status.finishedEvent.hasFired() && allFinished(childrenContexts))
                            declareFailAfterChildren(childrenContexts);
                    }
                });
            });
        }
    }

    protected void declareFailAfterChildren(Collection<JobContext> childrenContexts) {
        super.declareFail();
    }

    private boolean allFinished(Collection<JobContext> contexts) {
        return contexts
                .stream()
                .allMatch((context) -> context.getJob().status.finishedEvent.hasFired());
    }

}
