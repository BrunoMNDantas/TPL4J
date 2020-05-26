package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.status.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ScheduledStateExecutor extends StateExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduledStateExecutor.class);



    protected volatile IStateExecutor cancelStateExecutor;
    public IStateExecutor getCancelStateExecutor() { return this.cancelStateExecutor; }

    protected volatile IStateExecutor runningStateExecutor;
    public IStateExecutor getRunningStateExecutor() { return this.runningStateExecutor; }



    public ScheduledStateExecutor(IStateExecutor cancelStateExecutor, IStateExecutor runningStateExecutor) {
        super(State.SCHEDULED);
        this.cancelStateExecutor = cancelStateExecutor;
        this.runningStateExecutor = runningStateExecutor;
    }



    @Override
    public <T> void execute(IContext<T> context) {
        if(context.getStatus().getScheduledEvent().hasFired())
            throw new RuntimeException("Task:" + context.getTaskId() + " already scheduled!");

        super.execute(context);

        if(!this.verifyCancel(context))
            this.schedule(context);
    }

    protected <T> boolean verifyCancel(IContext<T> context) {
        LOGGER.trace("Verifying cancel for Task with id:" + context.getTaskId());

        try {
            if(!context.getOptions().notCancelable())
                context.getCancellationToken().abortIfCancelRequested();
        } catch (CancelledException e) {
            context.setResultException(e);
            this.cancelStateExecutor.execute(context);
            return true;
        }

        return false;
    }

    protected <T> void schedule(IContext<T> context) {
        LOGGER.trace("Scheduling Task with id:" + context.getTaskId());

        context.getScheduler().schedule(() -> this.runningStateExecutor.execute(context));
    }

}
