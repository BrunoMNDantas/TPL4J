package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.executor.state.IStateExecutor;
import com.github.brunomndantas.tpl4j.context.executor.state.ScheduledStateExecutor;
import com.github.brunomndantas.tpl4j.task.Task;

import java.util.Collection;

public class WhenAllScheduledStateExecutor<K> extends ScheduledStateExecutor {

    protected volatile Collection<Task<K>> tasks;
    public Collection<Task<K>> getTasks() { return this.tasks; }

    protected boolean finished;



    public WhenAllScheduledStateExecutor(IStateExecutor cancelStateExecutor, IStateExecutor runningStateExecutor, Collection<Task<K>> tasks) {
        super(cancelStateExecutor, runningStateExecutor);
        this.tasks = tasks;
    }



    @Override
    protected <T> void schedule(IContext<T> context) {
        if(this.tasks.isEmpty())
            this.scheduleExecution(context);
        else
            this.tasks.forEach(task -> scheduleExecutionOnFinished(context, task));
    }

    protected <T> void scheduleExecutionOnFinished(IContext<T> context, Task<?> task) {
        task.getFinishedEvent().addListener(() -> {
            synchronized (context) {
                if(!this.finished) {
                    if(super.verifyCancel(context))
                        this.finished = true;
                    else if(allFinished())
                        this.scheduleExecution(context);
                }
            }
        });
    }

    protected boolean allFinished() {
        return this.tasks
                .stream()
                .allMatch(t->t.getFinishedEvent().hasFired());
    }

    protected <T> void scheduleExecution(IContext<T> context) {
        super.schedule(context);
        this.finished = true;
    }

}
