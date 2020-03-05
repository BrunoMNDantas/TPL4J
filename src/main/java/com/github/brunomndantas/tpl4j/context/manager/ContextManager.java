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
package com.github.brunomndantas.tpl4j.context.manager;

import com.github.brunomndantas.tpl4j.context.IContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ContextManager implements IContextManager {

    private static final Logger LOGGER = LogManager.getLogger(ContextManager.class);



    protected final Collection<IContext<?>> contexts = new HashSet<>();
    protected final Map<Long, IContext<?>> contextByExecutorThread = new HashMap<>();



    @Override
    public synchronized void registerContext(IContext<?> context) {
        String taskId = context.getTaskId();

        if(this.contexts.contains(context))
            throw new IllegalArgumentException("There is already a context registered for Task with id:" + taskId + "!");

        this.contexts.add(context);
        LOGGER.trace("Registered IContext for Task with id:" + taskId);

        registerParenting(context);
    }

    protected void registerParenting(IContext<?> context) {
        long threadId = context.getCreatorThreadId();
        IContext<?> parentContext = this.contextByExecutorThread.get(threadId);

        if(parentContext != null) {
            String parentTaskId = parentContext.getTaskId();
            String childTaskId = context.getTaskId();

            parentContext.addChild(context);
            LOGGER.trace("Task with id:" + parentTaskId + " registered as parent of Task with id:" + childTaskId);

            context.setParentContext(parentContext);
            LOGGER.trace("Task with id:" + childTaskId + " registered as child of Task with id:" + parentTaskId);
        }
    }

    @Override
    public synchronized void unregisterContext(IContext<?> context) {
        String taskId = context.getTaskId();

        if(!this.contexts.contains(context))
            throw new IllegalArgumentException("IContext not registered for Task with id:" + taskId + "!");

        this.contexts.remove(context);

        LOGGER.trace("Unregistered IContext for Task with id:" + taskId);
    }

    @Override
    public synchronized void registerCurrentThreadAsExecutorOfContext(IContext<?> context) {
        String taskId = context.getTaskId();

        if(!this.contexts.contains(context))
            throw new IllegalArgumentException("IContext not registered for Task with id:" + taskId + "!");

        if(context.getExecutorThreadId() != 0)
            throw new IllegalArgumentException("Task with id:" + taskId + " has already a executor thread registered!");

        long threadId = Thread.currentThread().getId();
        context.setExecutorThreadId(threadId);
        this.contextByExecutorThread.put(threadId, context);

        LOGGER.trace("Thread with id:" + threadId + " registered as executor of Task with id:" + taskId);
    }

    @Override
    public synchronized void registerCurrentThreadEndExecutionOfContext(IContext<?> context) {
        String taskId = context.getTaskId();
        long currentThreadId = Thread.currentThread().getId();

        if(!this.contexts.contains(context))
            throw new IllegalArgumentException("IContext not registered for Task with id:" + taskId + "!");

        if(context.getExecutorThreadId() != currentThreadId)
            throw new IllegalArgumentException("Trying to declare end of execution of Task with id:" + taskId + " on a different Thread!");

        this.contextByExecutorThread.put(currentThreadId, null);

        LOGGER.trace("Thread with id:" + currentThreadId + " registered end of execution of Task with id:" + taskId);
    }

}
