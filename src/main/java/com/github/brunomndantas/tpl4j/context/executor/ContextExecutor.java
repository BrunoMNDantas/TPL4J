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
package com.github.brunomndantas.tpl4j.context.executor;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.executor.state.*;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextExecutor implements IContextExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextExecutor.class);



    protected volatile IStateExecutor scheduledStateExecutor;
    public IStateExecutor getScheduledStateExecutor() { return this.scheduledStateExecutor; }

    protected IContextManager contextManager;
    public IContextManager getContextManager() { return this.contextManager; }



    public ContextExecutor(IContextManager contextManager, IStateExecutor scheduledStateExecutor) {
        this.contextManager = contextManager;
        this.scheduledStateExecutor = scheduledStateExecutor;
    }

    public ContextExecutor(IContextManager contextManager) {
        this(
            contextManager,
            new ScheduledStateExecutor(
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
                )
            )
        );
    }



    @Override
    public synchronized <T> void execute(IContext<T> context) {
        LOGGER.trace("Executing Task with id:" + context.getTaskId());
        this.scheduledStateExecutor.execute(context);
    }

}
