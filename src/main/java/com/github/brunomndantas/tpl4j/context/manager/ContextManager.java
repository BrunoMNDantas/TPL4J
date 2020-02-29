package com.github.brunomndantas.tpl4j.context.manager;

import com.github.brunomndantas.tpl4j.context.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class ContextManager implements IContextManager {

    private static final Logger LOGGER = LogManager.getLogger(ContextManager.class);



    protected final Collection<Context<?>> contexts = new HashSet<>();
    protected final Map<Long, Context<?>> contextByExecutorThread = new HashMap<>();



    @Override
    public synchronized void registerContext(Context<?> context) {
        String taskId = context.getTaskId();

        if(this.contexts.contains(context))
            throw new IllegalArgumentException("There is already a context registered for Task with id:" + taskId + "!");

        this.contexts.add(context);

        LOGGER.info("Registered Context for Task with id:" + taskId);
    }

    @Override
    public synchronized void unregisterContext(Context<?> context) {
        String taskId = context.getTaskId();

        if(!this.contexts.contains(context))
            throw new IllegalArgumentException("Context not registered for Task with id:" + taskId + "!");

        this.contexts.remove(context);

        LOGGER.info("Unregistered Context for Task with id:" + taskId);
    }

    @Override
    public synchronized void registerCurrentThreadAsCreatorOfContext(Context<?> context) {
        String taskId = context.getTaskId();

        if(!this.contexts.contains(context))
            throw new IllegalArgumentException("Context not registered for Task with id:" + taskId + "!");

        if(context.getCreatorThreadId() != 0)
            throw new IllegalArgumentException("Task with id:" + taskId + " has already a creator thread registered!");

        long threadId = Thread.currentThread().getId();
        context.setCreatorThreadId(threadId);

        LOGGER.info("Thread with id:" + threadId + " registered as creator of Task with id:" + taskId);
    }

    @Override
    public synchronized void registerCurrentThreadAsExecutorOfContext(Context<?> context) {
        String taskId = context.getTaskId();

        if(!this.contexts.contains(context))
            throw new IllegalArgumentException("Context not registered for Task with id:" + taskId + "!");

        if(context.getExecutorThreadId() != 0)
            throw new IllegalArgumentException("Task with id:" + taskId + " has already a executor thread registered!");

        long threadId = Thread.currentThread().getId();
        context.setExecutorThreadId(threadId);
        this.contextByExecutorThread.put(threadId, context);

        LOGGER.info("Thread with id:" + threadId + " registered as executor of Task with id:" + taskId);
    }

    @Override
    public void registerCurrentThreadEndExecutionOfContext(Context<?> context) {
        String taskId = context.getTaskId();
        long currentThreadId = Thread.currentThread().getId();

        if(!this.contexts.contains(context))
            throw new IllegalArgumentException("Context not registered for Task with id:" + taskId + "!");

        if(context.getExecutorThreadId() != currentThreadId)
            throw new IllegalArgumentException("Trying to declare end of execution of Task with id:" + taskId + " on a different Thread!");

        this.contextByExecutorThread.put(currentThreadId, null);

        LOGGER.info("Thread with id:" + currentThreadId + " registered end of execution of Task with id:" + taskId);
    }

    @Override
    public synchronized void registerTaskParenting(Context<?> parentContext, Context<?> childContext) {
        String parentTaskId = parentContext.getTaskId();
        String childTaskId = childContext.getTaskId();

        if(!this.contexts.contains(parentContext))
            throw new IllegalArgumentException("Context not registered for Task with id:" + parentTaskId + "!");

        if(!this.contexts.contains(childContext))
            throw new IllegalArgumentException("Context not registered for Task with id:" + childTaskId + "!");

        if(parentContext.hasChild(childContext))
            throw new IllegalArgumentException("Task with id:" + childTaskId + " is already registered as child of Task with id:" + parentTaskId + "!");

        if(childContext.getParentContext() != null && !childContext.getParentContext().equals(parentContext))
            throw new IllegalArgumentException("Task with id:" + childTaskId + " has another parent which is not Task with id:" + parentTaskId + "!");

        parentContext.addChild(childContext);
        LOGGER.info("Task with id:" + parentTaskId + " registered as parent of Task with id:" + childTaskId);

        childContext.setParentContext(parentContext);
        LOGGER.info("Task with id:" + childTaskId + " registered as child of Task with id:" + parentTaskId);
    }

    @Override
    public synchronized Context<?> getContextRunningOnCurrentThread() {
        long currentThreadId = Thread.currentThread().getId();
        return this.contextByExecutorThread.get(currentThreadId);
    }

    @Override
    public synchronized <T> void setContextResult(Context<T> context, T value, Exception exception) {
        String taskId = context.getTaskId();

        if(!this.contexts.contains(context))
            throw new IllegalArgumentException("Context not registered for Task with id:" + taskId + "!");

        if(context.getResultValue() != null || context.getResultException() != null)
            throw new IllegalArgumentException("Context of Task with id:" + context.getTaskId() + " already has its value set!");

        context.setResultValue(value);
        context.setResultException(exception);
    }

}
