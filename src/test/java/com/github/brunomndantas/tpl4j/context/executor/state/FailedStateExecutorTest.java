package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.core.status.Status;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class FailedStateExecutorTest {

    @Test
    public void getContextManagerTest() {
        IContextManager contextManager = new ContextManager();
        FailedStateExecutor executor = new FailedStateExecutor(contextManager);
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void constructorTest() {
        IContextManager contextManager = new ContextManager();

        FailedStateExecutor executor = new FailedStateExecutor(contextManager);

        assertEquals(State.FAILED, executor.getState());
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void executeTest() {
        AtomicBoolean passed = new AtomicBoolean();
        IContext<?> context = new Context<>("", (ct)->"", null, null, null, new Status(""), null, null, 0, 0, null, null);
        IContextManager contextManager = new ContextManager() {
            @Override public synchronized void unregisterContext(IContext<?> c) {
                passed.set(c == context);
            }
        };
        contextManager.registerContext(context);
        FailedStateExecutor executor = new FailedStateExecutor(contextManager);

        executor.execute(context);

        assertEquals(State.FAILED, context.getStatus().getState());
        assertTrue(passed.get());
    }

}
