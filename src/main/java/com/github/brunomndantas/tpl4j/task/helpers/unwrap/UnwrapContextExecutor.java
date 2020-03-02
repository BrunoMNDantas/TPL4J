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
package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.executor.ContextExecutor;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.Task;

public class UnwrapContextExecutor<K> extends ContextExecutor {

    protected Task<Task<K>> task;
    public Task<Task<K>> getTask() { return this.task; }



    public UnwrapContextExecutor(IContextManager contextManager, Task<Task<K>> task) {
        super(contextManager);
        this.task = task;
    }



    @Override
    protected <T> void schedule(Context<T> context) {
        this.task.getFinishedEvent().addListener(() -> {
            if(super.verifyCancel(context))
                return;

            if(this.task.getState().equals(State.SUCCEEDED)) {
                Task<K> task = this.task.getResultValue();
                this.scheduleExecutionOnFinished(context, task);
            } else {
                this.scheduleExecution(context);
            }
        });
    }

    protected <T> void scheduleExecutionOnFinished(Context<T> context, Task<?> task) {
        if(task == null)
            this.scheduleExecution(context);
        else
            task.getFinishedEvent().addListener(() -> {
                if(!super.verifyCancel(context))
                    this.scheduleExecution(context);
            });
    }

    protected <T> void scheduleExecution(Context<T> context) {
        context.getScheduler().schedule(() -> super.run(context));
    }

}
