package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.status.State;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class WaitingChildrenStateExecutor extends StateExecutor {

    protected volatile IStateExecutor cancelStateExecutor;
    public IStateExecutor getCancelStateExecutor() { return this.cancelStateExecutor; }

    protected volatile IStateExecutor failedStateExecutor;
    public IStateExecutor getFailedStateExecutor() { return this.failedStateExecutor; }

    protected volatile IStateExecutor succeededStateExecutor;
    public IStateExecutor getSucceededStateExecutor() { return this.succeededStateExecutor; }



    public WaitingChildrenStateExecutor(IStateExecutor cancelStateExecutor, IStateExecutor failedStateExecutor, IStateExecutor succeededStateExecutor) {
        super(State.WAITING_CHILDREN);
        this.cancelStateExecutor = cancelStateExecutor;
        this.failedStateExecutor = failedStateExecutor;
        this.succeededStateExecutor = succeededStateExecutor;
    }



    @Override
    public <T> void execute(IContext<T> context) {
        super.execute(context);

        Collection<IContext<?>> childrenContexts = StateUtils.getAttachedChildrenContext(context);

        for(IContext<?> childContext : childrenContexts) {
            childContext.getStatus().getFinishedEvent().addListener(() -> {
                synchronized (childrenContexts) {
                    if(!context.getStatus().getFinishedEvent().hasFired() && allFinished(childrenContexts)) {
                        mergeResults(context, childrenContexts);
                        invokeNextState(context);
                    }
                }
            });
        }
    }

    protected boolean allFinished(Collection<IContext<?>> contexts) {
        return contexts
                .stream()
                .allMatch((context) -> context.getStatus().getFinishedEvent().hasFired());
    }

    protected <T> void mergeResults(IContext<T> context, Collection<IContext<?>> childrenContexts) {
        if(context.getResultException() instanceof CancelledException)
            mergeChildrenResultWithParentCancellation(context, childrenContexts);
        else if(context.getResultException() != null)
            mergeChildrenResultWithParentFailure(context, childrenContexts);
        else
            mergeChildrenResultWithParentSuccess(context, childrenContexts);
    }

    protected <T> void mergeChildrenResultWithParentSuccess(IContext<T> context, Collection<IContext<?>> childrenContexts) {
        Collection<Exception> childrenFails = this.collectChildrenFails(childrenContexts);
        Collection<Exception> childrenCancels = this.collectChildrenCancels(childrenContexts);

        if(!childrenFails.isEmpty() && !context.getOptions().notPropagateFailure())
            context.setResultException(combineExceptions(childrenFails));
        else if(!childrenCancels.isEmpty() && !context.getOptions().notPropagateCancellation())
            context.setResultException(combineExceptions(childrenCancels));
    }

    protected <T> void mergeChildrenResultWithParentCancellation(IContext<T> context, Collection<IContext<?>> childrenContexts) {
        Collection<Exception> childrenFails = this.collectChildrenFails(childrenContexts);
        Collection<Exception> childrenCancels = this.collectChildrenCancels(childrenContexts);

        if(!childrenFails.isEmpty() && !context.getOptions().notPropagateFailure())
            context.setResultException(combineExceptions(childrenFails));
        else if(!childrenCancels.isEmpty() && !context.getOptions().notPropagateCancellation())
            context.setResultException(combineExceptions(context.getResultException(), childrenCancels));
    }

    protected <T> void mergeChildrenResultWithParentFailure(IContext<T> context, Collection<IContext<?>> childrenContexts) {
        Collection<Exception> childrenFails = this.collectChildrenFails(childrenContexts);

        if(!childrenFails.isEmpty() && !context.getOptions().notPropagateFailure())
            context.setResultException(combineExceptions(context.getResultException(), childrenFails));
    }

    protected Collection<Exception> collectChildrenFails(Collection<IContext<?>> childrenContexts) {
        return childrenContexts
                .stream()
                .filter(childContext -> childContext.getStatus().getState().equals(State.FAILED))
                .map(IContext::getResultException)
                .collect(Collectors.toList());
    }

    protected Collection<Exception> collectChildrenCancels(Collection<IContext<?>> childrenContexts) {
        return childrenContexts
                .stream()
                .filter(childContext -> childContext.getStatus().getState().equals(State.CANCELED))
                .map(IContext::getResultException)
                .collect(Collectors.toList());
    }

    protected Exception combineExceptions(Collection<Exception> exceptions) {
        Exception exception = null;

        for(Exception e : exceptions) {
            if(exception == null)
                exception = e;
            else if(e != exception)
                exception.addSuppressed(e);
        }

        return exception;
    }

    protected Exception combineExceptions(Exception exception, Collection<Exception> exceptions) {
        Collection<Exception> list = new LinkedList<>();
        list.add(exception);
        list.addAll(exceptions);

        return combineExceptions(list);
    }

    protected <T> void invokeNextState(IContext<T> context) {
        if(context.getResultException() instanceof CancelledException)
            this.cancelStateExecutor.execute(context);
        else if(context.getResultException() != null)
            this.failedStateExecutor.execute(context);
        else
            this.succeededStateExecutor.execute(context);
    }

}
