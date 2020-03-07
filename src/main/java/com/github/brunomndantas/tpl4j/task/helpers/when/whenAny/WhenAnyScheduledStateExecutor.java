package com.github.brunomndantas.tpl4j.task.helpers.when.whenAny;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.executor.state.IStateExecutor;
import com.github.brunomndantas.tpl4j.context.executor.state.ScheduledStateExecutor;
import com.github.brunomndantas.tpl4j.task.Task;

import java.util.Collection;

public class WhenAnyScheduledStateExecutor<K> extends ScheduledStateExecutor {

    protected volatile Collection<Task<K>> tasks;
    public Collection<Task<K>> getTasks() { return this.tasks; }

    protected boolean finished;



    public WhenAnyScheduledStateExecutor(IStateExecutor cancelStateExecutor, IStateExecutor runningStateExecutor, Collection<Task<K>> tasks) {
        super(cancelStateExecutor, runningStateExecutor);
        this.tasks = tasks;
    }



    @Override
    protected <T> void schedule(IContext<T> context) {
        if(this.tasks.isEmpty())
            this.scheduleExecution(context);
        else
            this.tasks.forEach(task -> this.scheduleExecutionOnFinished(context, task));
    }

    protected <T> void scheduleExecutionOnFinished(IContext<T> context, Task<?> task) {
        task.getFinishedEvent().addListener(() -> {
            synchronized (context) {
                if(!this.finished) {
                    if(super.verifyCancel(context))
                        this.finished = true;
                    else if(anyFinished())
                        this.scheduleExecution(context);
                }
            }
        });
    }

    protected boolean anyFinished() {
        return this.tasks
                .stream()
                .anyMatch(t->t.getFinishedEvent().hasFired());
    }

    protected <T> void scheduleExecution(IContext<T> context) {
        super.schedule(context);
        this.finished = true;
    }

}
