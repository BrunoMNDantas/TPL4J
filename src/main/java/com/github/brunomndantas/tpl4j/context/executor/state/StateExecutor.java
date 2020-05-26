package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.status.State;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StateExecutor implements IStateExecutor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StateExecutor.class);



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
