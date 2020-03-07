package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.executor.state.IStateExecutor;
import com.github.brunomndantas.tpl4j.context.executor.state.ScheduledStateExecutor;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.Task;

public class UnwrapScheduledStateExecutor<K> extends ScheduledStateExecutor {

    protected volatile Task<Task<K>> task;
    public Task<Task<K>> getTask() { return this.task; }



    public UnwrapScheduledStateExecutor(IStateExecutor cancelStateExecutor, IStateExecutor runningStateExecutor, Task<Task<K>> task) {
        super(cancelStateExecutor, runningStateExecutor);
        this.task = task;
    }



    @Override
    protected <T> void schedule(IContext<T> context) {
        this.task.getFinishedEvent().addListener(() -> {
            if(super.verifyCancel(context))
                return;

            if(this.task.getState().equals(State.SUCCEEDED)) {
                Task<K> task = this.task.getResultValue();
                this.scheduleExecutionOnFinished(context, task);
            } else {
                super.schedule(context);
            }
        });
    }

    protected <T> void scheduleExecutionOnFinished(IContext<T> context, Task<?> task) {
        if(task == null)
            super.schedule(context);
        else
            task.getFinishedEvent().addListener(() -> {
                if(!super.verifyCancel(context))
                    super.schedule(context);
            });
    }

}
