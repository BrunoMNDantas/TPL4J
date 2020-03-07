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

public class SucceededStateExecutorTest {

    @Test
    public void getContextManagerTest() {
        IContextManager contextManager = new ContextManager();
        SucceededStateExecutor executor = new SucceededStateExecutor(contextManager);
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void constructorTest() {
        IContextManager contextManager = new ContextManager();

        SucceededStateExecutor executor = new SucceededStateExecutor(contextManager);

        assertEquals(State.SUCCEEDED, executor.getState());
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
        SucceededStateExecutor executor = new SucceededStateExecutor(contextManager);

        executor.execute(context);

        assertEquals(State.SUCCEEDED, context.getStatus().getState());
        assertTrue(passed.get());
    }

}
