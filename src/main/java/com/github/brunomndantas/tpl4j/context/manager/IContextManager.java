package com.github.brunomndantas.tpl4j.context.manager;

import com.github.brunomndantas.tpl4j.context.Context;

public interface IContextManager {

    void registerContext(Context<?> context);

    void unregisterContext(String taskId);

    Context<?> getContext(String taskId);


    void registerCurrentThreadAsCreatorOfContext(String taskId);

    void registerCurrentThreadAsExecutorOfContext(String taskId);


    void registerTaskParenting(String parentTaskId, String childTaskId);


    String getIdOfTaskRunningOnCurrentThread();


    <T> void setContextResult(String taskId, T value, Exception exception);
    
}
