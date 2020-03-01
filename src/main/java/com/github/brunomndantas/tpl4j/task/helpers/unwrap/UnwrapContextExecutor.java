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
    public synchronized <T> void execute(Context<T> context) {
        this.task.getContext().getStatus().getFinishedEvent().addListener(() -> {
            if(this.task.getContext().getStatus().getState().equals(State.SUCCEEDED)) {
                if(this.task.getContext().getResultValue() == null)
                    context.getScheduler().schedule(() -> run(context));
                else
                    this.task.getContext().getResultValue().getContext().getStatus().getFinishedEvent().addListener(() -> {
                        context.getScheduler().schedule(() -> run(context));
                    });
            } else {
                context.getScheduler().schedule(() -> run(context));
            }
        });
    }

}
