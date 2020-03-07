package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.status.State;

public class CanceledStateExecutor extends StateExecutor {

    protected volatile IContextManager contextManager;
    public IContextManager getContextManager() { return this.contextManager; }



    public CanceledStateExecutor(IContextManager contextManager) {
        super(State.CANCELED);
        this.contextManager = contextManager;
    }



    @Override
    public <T> void execute(IContext<T> context) {
        super.execute(context);
        this.contextManager.unregisterContext(context);
    }

}
