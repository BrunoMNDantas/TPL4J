package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.status.State;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class StateExecutor implements IStateExecutor {

    private static final Logger LOGGER = LogManager.getLogger(StateExecutor.class);



    protected volatile State state;
    @Override public State getState() { return this.state; }



    public StateExecutor(State state) {
        this.state = state;
    }



    @Override
    public <T> void execute(IContext<T> context) {
        LOGGER.trace("Executing state:" + this.state);
        context.getStatus().setState(this.state);
    }

}
