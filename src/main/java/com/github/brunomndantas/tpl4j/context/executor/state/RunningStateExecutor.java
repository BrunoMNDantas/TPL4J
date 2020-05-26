package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.status.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class RunningStateExecutor extends StateExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RunningStateExecutor.class);



    protected volatile IStateExecutor cancelStateExecutor;
    public IStateExecutor getCancelStateExecutor() { return this.cancelStateExecutor; }

    protected volatile IStateExecutor failedStateExecutor;
    public IStateExecutor getFailedStateExecutor() { return this.failedStateExecutor; }

    protected volatile IStateExecutor succeededStateExecutor;
    public IStateExecutor getSucceededStateExecutor() { return this.succeededStateExecutor; }

    protected volatile IStateExecutor waitingChildrenStateExecutor;
    public IStateExecutor getWaitingChildrenStateExecutor() { return  this.waitingChildrenStateExecutor; }

    protected volatile IContextManager contextManager;
    public IContextManager getContextManager() { return this.contextManager; }



    public RunningStateExecutor(IStateExecutor cancelStateExecutor, IStateExecutor failedStateExecutor, IStateExecutor succeededStateExecutor, IStateExecutor waitingChildrenStateExecutor, IContextManager contextManager) {
        super(State.RUNNING);
        this.cancelStateExecutor = cancelStateExecutor;
        this.failedStateExecutor = failedStateExecutor;
        this.succeededStateExecutor = succeededStateExecutor;
        this.waitingChildrenStateExecutor = waitingChildrenStateExecutor;
        this.contextManager = contextManager;
    }



    @Override
    public <T> void execute(IContext<T> context) {
        super.execute(context);

        this.contextManager.registerCurrentThreadAsExecutorOfContext(context);
        run(context);
        this.contextManager.registerCurrentThreadEndExecutionOfContext(context);

        cancelChildrenIfNeeded(context);
        cancelParentIfNeeded(context);
        invokeNextState(context);

        LOGGER.trace("Task with id:" + context.getTaskId() + " end run");
    }

    protected <T> void run(IContext<T> context) {
        try {
            if(!context.getOptions().notCancelable())
                context.getCancellationToken().abortIfCancelRequested();

            T value = context.getAction().run(context.getCancellationToken());
            context.setResultValue(value);
        } catch(Exception e) {
            context.setResultException(e);
        }
    }

    protected <T> void cancelChildrenIfNeeded(IContext<T> context) {
        Collection<IContext<?>> childrenContexts = StateUtils.getAttachedChildrenContext(context);
        if(!childrenContexts.isEmpty()) {
            if(context.getResultException() instanceof CancelledException && context.getOptions().cancelChildrenOnCancellation())
                childrenContexts.forEach(cc -> cc.getCancellationToken().cancel());
            else if(context.getResultException() != null && context.getOptions().cancelChildrenOnFailure())
                childrenContexts.forEach(cc -> cc.getCancellationToken().cancel());
        }
    }

    protected <T> void cancelParentIfNeeded(IContext<T> context) {
        IContext<?> parentContext = context.getParentContext();
        if( parentContext != null &&
            context.getOptions().attachToParent() &&
            !parentContext.getOptions().rejectChildren()) {
            if(context.getResultException() instanceof CancelledException && context.getOptions().cancelParentOnCancellation())
                parentContext.getCancellationToken().cancel();
            else if(context.getResultException() != null && context.getOptions().cancelParentOnFailure())
                parentContext.getCancellationToken().cancel();
        }
    }

    protected <T> void invokeNextState(IContext<T> context) {
        if(!StateUtils.getAttachedChildrenContext(context).isEmpty())
            this.waitingChildrenStateExecutor.execute(context);
        else if(context.getResultException() instanceof CancelledException)
            this.cancelStateExecutor.execute(context);
        else if(context.getResultException() != null)
            this.failedStateExecutor.execute(context);
        else
            this.succeededStateExecutor.execute(context);
    }

}
