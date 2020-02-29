package com.github.brunomndantas.tpl4j.context.manager;

import com.github.brunomndantas.tpl4j.context.Context;

public interface IContextManager {

    void registerContext(Context<?> context);

    void unregisterContext(Context<?> context);


    void registerCurrentThreadAsCreatorOfContext(Context<?> context);

    void registerCurrentThreadAsExecutorOfContext(Context<?> context);

    void registerCurrentThreadEndExecutionOfContext(Context<?> context);


    void registerTaskParenting(Context<?> parentContext, Context<?> childContext);


    Context<?> getContextRunningOnCurrentThread();


    <T> void setContextResult(Context<T> context, T value, Exception exception);
    
}
