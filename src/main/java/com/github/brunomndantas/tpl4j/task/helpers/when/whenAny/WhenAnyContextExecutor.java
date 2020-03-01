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
package com.github.brunomndantas.tpl4j.task.helpers.when.whenAny;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.executor.ContextExecutor;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.task.Task;

import java.util.Collection;

public class WhenAnyContextExecutor<K> extends ContextExecutor {

    protected volatile Collection<Task<K>> tasks;
    public Collection<Task<K>> getTasks() { return this.tasks; }

    protected boolean finished;



    public WhenAnyContextExecutor(IContextManager contextManager, Collection<Task<K>> tasks) {
        super(contextManager);
        this.tasks = tasks;
    }



    @Override
    protected <T> void schedule(Context<T> context) {
        if(this.tasks.isEmpty())
            this.scheduleExecution(context);
        else
            this.tasks.forEach(task -> this.scheduleExecutionOnFinished(context, task));
    }

    protected <T> void scheduleExecutionOnFinished(Context<T> context, Task<?> task) {
        task.getContext().getStatus().getFinishedEvent().addListener(() -> {
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
                .anyMatch(t->t.getContext().getStatus().getFinishedEvent().hasFired());
    }

    protected <T> void scheduleExecution(Context<T> context) {
        context.getScheduler().schedule(() -> super.run(context));
        this.finished = true;
    }

}
