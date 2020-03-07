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
package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.context.executor.ContextExecutor;
import com.github.brunomndantas.tpl4j.context.executor.state.*;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.task.Task;

import java.util.Collection;

public class WhenAllContextExecutor<K> extends ContextExecutor {

    protected volatile Collection<Task<K>> tasks;
    public Collection<Task<K>> getTasks() { return this.tasks; }



    public WhenAllContextExecutor(IContextManager contextManager, Collection<Task<K>> tasks) {
        super(
            contextManager,
            new WhenAllScheduledStateExecutor<> (
                new CanceledStateExecutor(contextManager),
                new RunningStateExecutor(
                    new CanceledStateExecutor(contextManager),
                    new FailedStateExecutor(contextManager),
                    new SucceededStateExecutor(contextManager),
                    new WaitingChildrenStateExecutor(
                        new CanceledStateExecutor(contextManager),
                        new FailedStateExecutor(contextManager),
                        new SucceededStateExecutor(contextManager)
                    ),
                    contextManager
                ),
                tasks
            )
        );
        this.tasks = tasks;
    }

}
