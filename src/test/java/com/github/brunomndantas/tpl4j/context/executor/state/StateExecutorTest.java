package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.core.status.Status;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class StateExecutorTest {

    @Test
    public void constructorTest() {
        State state = State.CREATED;
        StateExecutor executor = new StateExecutor(state);
        assertSame(state, executor.getState());
    }

    @Test
    public void getStateTest() {
        State state = State.CREATED;
        StateExecutor executor = new StateExecutor(state);
        assertSame(state, executor.getState());
    }

    @Test
    public void executeTest() {
        IContext<?> context = new Context<>("", (ct)->null, null, null, null, new Status(""), null, null, 0, 0, null, null);
        StateExecutor executor = new StateExecutor(State.RUNNING);

        executor.execute(context);

        assertSame(executor.getState(), context.getStatus().getState());
    }

}
