package com.github.brunomndantas.tpl4j.task.helpers.parallel.context;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.executor.ContextExecutor;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.status.State;

import java.util.Collection;

public class ParallelContextExecutor extends ContextExecutor {

    public ParallelContextExecutor(IContextManager contextManager) {
        super(contextManager);
    }



    @Override
    protected <T> void endExecution(Context<T> context, Collection<Context<?>> childrenContexts, T value, Exception exception) {
        Exception childException = null;
        for(Context<?> childContext : childrenContexts) {
            if(childContext.getStatus().getState().equals(State.CANCELED)) {
                if(childException == null)
                    childException = childContext.getResultException();
                else if(childException != childContext.getResultException())
                    childException.addSuppressed(childContext.getResultException());
            }
        }

        if(exception == null)
            exception = childException;

        super.endExecution(context, childrenContexts, value, exception);
    }

}
