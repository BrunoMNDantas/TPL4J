package com.github.brunomndantas.tpl4j.context.manager;

import com.github.brunomndantas.tpl4j.context.Context;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class ContextManager implements IContextManager {

    private static final Logger LOGGER = LogManager.getLogger(ContextManager.class);



    protected final Map<String, Context<?>> contexts = new HashMap<>();
    protected final Map<Long, Context<?>> contextByExecutorThread = new HashMap<>();



    @Override
    public synchronized void registerContext(Context<?> context) {
        String taskId = context.getTaskId();

        if(this.contexts.containsKey(taskId))
            throw new IllegalArgumentException("There is already a context registered for Task with id:" + taskId + "!");

        this.contexts.put(taskId, context);

        LOGGER.info("Registered Context for Task with id:" + taskId);
    }

    @Override
    public synchronized void unregisterContext(String taskId) {
        Context<?> context = this.getContext(taskId);

        if(context == null)
            throw new IllegalArgumentException("There is no Context associated with id:" + taskId + "!");

        this.contexts.remove(taskId);

        if(this.contextByExecutorThread.get(context.getExecutorThreadId()) == null || !this.contextByExecutorThread.get(context.getExecutorThreadId()).getTaskId().equals(taskId))
            throw new IllegalArgumentException("Context associated with id:" + taskId + " is not executing on the specified thread!");

        this.contextByExecutorThread.put(context.getExecutorThreadId(), null);

        LOGGER.info("Unregistered Context for Task with id:" + taskId);
    }

    @Override
    public synchronized Context<?> getContext(String taskId) {
        return this.contexts.get(taskId);
    }

    @Override
    public synchronized void registerCurrentThreadAsCreatorOfContext(String taskId) {
        Context<?> context = this.getContext(taskId);

        if(context == null)
            throw new IllegalArgumentException("There is no Context associated with id:" + taskId + "!");

        if(context.getCreatorThreadId() != 0)
            throw new IllegalArgumentException("Task with id:" + taskId + " has already a creator thread registered!");

        long threadId = Thread.currentThread().getId();
        context.setCreatorThreadId(threadId);

        LOGGER.info("Thread with id:" + threadId + " registered as creator of Task with id:" + taskId);
    }

    @Override
    public synchronized void registerCurrentThreadAsExecutorOfContext(String taskId) {
        Context<?> context = this.getContext(taskId);

        if(context == null)
            throw new IllegalArgumentException("There is no Context associated with id:" + taskId + "!");

        if(context.getExecutorThreadId() != 0)
            throw new IllegalArgumentException("Task with id:" + taskId + " has already a executor thread registered!");

        long threadId = Thread.currentThread().getId();
        context.setExecutorThreadId(threadId);
        this.contextByExecutorThread.put(threadId, context);

        LOGGER.info("Thread with id:" + threadId + " registered as executor of Task with id:" + taskId);
    }

    @Override
    public synchronized void registerTaskParenting(String parentTaskId, String childTaskId) {
        Context<?> parentContext = this.getContext(parentTaskId);
        Context<?> childContext = this.getContext(childTaskId);

        if(childContext == null)
            throw new IllegalArgumentException("There is no Context associated with id:" + childTaskId + "!");

        if(parentContext == null)
            throw new IllegalArgumentException("There is no Context associated with id:" + parentTaskId + "!");

        if(parentContext.hasChild(childTaskId))
            throw new IllegalArgumentException("Task with id:" + childTaskId + " is already registered as child of Task with id:" + parentTaskId + "!");

        if(childContext.getParentTaskId() != null && !childContext.getParentTaskId().equals(parentTaskId))
            throw new IllegalArgumentException("Task with id:" + childTaskId + " has another parent which is not Task with id:" + parentTaskId + "!");

        parentContext.addChild(childTaskId);
        LOGGER.info("Task with id:" + parentTaskId + " registered as parent of Task with id:" + childTaskId);

        childContext.setParentTaskId(parentTaskId);
        LOGGER.info("Task with id:" + childTaskId + " registered as child of Task with id:" + parentTaskId);
    }

    @Override
    public synchronized String getIdOfTaskRunningOnCurrentThread() {
        long currentThreadId = Thread.currentThread().getId();

        Context<?> context = this.contextByExecutorThread.get(currentThreadId);

        return context == null ? null : context.getTaskId();
    }

    @Override
    public synchronized <T> void setContextResult(String taskId, T value, Exception exception) {
        Context<T> context = (Context<T>) this.getContext(taskId);

        if(context == null)
            throw new IllegalArgumentException("There is no Context associated with id:" + taskId + "!");

        if(context.getResultValue() != null || context.getResultException() != null)
            throw new IllegalArgumentException("Context of Task with id:" + context.getTaskId() + " already has its value set!");

        context.setResultValue(value);
        context.setResultException(exception);
    }

}
