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
package com.github.brunomndantas.tpl4j.context.builder;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;

public class ContextBuilder implements IContextBuilder {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContextBuilder.class);



    protected volatile IContextManager contextManager;
    public IContextManager getContextManager() { return this.contextManager; }



    public ContextBuilder(IContextManager contextManager) {
        this.contextManager = contextManager;
    }



    public <T> IContext<T> build(String taskId, IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, IOptions options) {
        Context<T> context = new Context<>(taskId, action, cancellationToken, scheduler, options, new Status(taskId), null, new LinkedList<>(), Thread.currentThread().getId(), 0, null, null);

        this.contextManager.registerContext(context);

        LOGGER.info("Context for Task with id:" + taskId + " created on Thread ith id:" + context.getCreatorThreadId());
        LOGGER.info("Task with id:" + taskId + " monitoring CancellationToken with id:" + cancellationToken.getId() + "!");
        LOGGER.info("Task with id:" + taskId + " associated to Scheduler with id:" + scheduler.getId() + "!");
        LOGGER.info("Task with id:" + taskId + " created with options:[" + optionsToString(options) + "]");
        LOGGER.info("Task with id:" + taskId + (context.getParentContext() == null ? " has no parent!" : (" is child of Task with id:" + context.getParentContext().getTaskId())));

        return context;
    }

    protected String optionsToString(IOptions options) {
        StringBuilder sb = new StringBuilder();

        for(Option option : options.getOptions())
            sb.append(option).append(";");

        String str = sb.toString();
        if(!str.isEmpty())
            str = str.substring(0, str.length()-";".length());

        return str;
    }

}

