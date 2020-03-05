package com.github.brunomndantas.tpl4j.context.manager;

import com.github.brunomndantas.tpl4j.context.Context;
import org.junit.Test;

import java.util.LinkedList;

import static org.junit.Assert.*;

public class ContextManagerTest {

    @Test
    public void registerContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(context);

        assertTrue(contextManager.contexts.contains(context));
    }

    @Test
    public void registerContextTwiceThrowsExceptionTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);
        contextManager.registerContext(context);

        try {
            contextManager.registerContext(context);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("already"));
            assertTrue(e.getMessage().contains("registered"));
        }
    }

    @Test
    public void registerContextWithChildTask() {
        ContextManager contextManager = new ContextManager();
        Context<String> childContext = new Context<>("id", null, null, null, null, null, null, new LinkedList<>(), Thread.currentThread().getId(), 0, null, null);
        Context<String> parentContext = new Context<>("id", null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);

        contextManager.registerContext(parentContext);
        contextManager.registerCurrentThreadAsExecutorOfContext(parentContext);
        contextManager.registerContext(childContext);

        assertSame(parentContext, childContext.getParentContext());
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
    }

    @Test
    public void unregisterContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(context);
        contextManager.registerCurrentThreadAsExecutorOfContext(context);
        contextManager.unregisterContext(context);

        assertFalse(contextManager.contexts.contains(context));
    }

    @Test
    public void unregisterContextWithNotRegisteredContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.unregisterContext(context);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("Context not registered"));
            assertTrue(e.getMessage().contains(context.getTaskId()));
        }
    }

    @Test
    public void registerCurrentThreadAsExecutorOfContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(context);

        contextManager.registerCurrentThreadAsExecutorOfContext(context);

        assertEquals(Thread.currentThread().getId(), context.getExecutorThreadId());
    }

    @Test
    public void registerCurrentThreadAsExecutorOfContextWithoutRegisterContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.registerCurrentThreadAsExecutorOfContext(context);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("Context not registered"));
        }
    }

    @Test
    public void registerCurrentThreadAsExecutorOfContextWithExecutorAlreadyRegisteredTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);
        contextManager.registerContext(context);
        contextManager.registerCurrentThreadAsExecutorOfContext(context);

        try {
            contextManager.registerCurrentThreadAsExecutorOfContext(context);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("has already a executor"));
        }
    }

    @Test
    public void registerCurrentEndExecutionOfContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(context);
        contextManager.registerCurrentThreadAsExecutorOfContext(context);

        contextManager.registerCurrentThreadEndExecutionOfContext(context);

        assertNull(contextManager.contextByExecutorThread.get(Thread.currentThread().getId()));
    }

    @Test
    public void registerCurrentEndExecutionOfContextWithoutRegisterContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.registerCurrentThreadEndExecutionOfContext(context);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("Context not registered"));
        }
    }

    @Test
    public void registerCurrentEndExecutionOfContextOnDifferentThreadTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(context);

        try {
            contextManager.registerCurrentThreadEndExecutionOfContext(context);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("different Thread"));
        }
    }

}
