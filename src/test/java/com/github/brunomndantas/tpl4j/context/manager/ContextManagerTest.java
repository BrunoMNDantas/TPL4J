package com.github.brunomndantas.tpl4j.context.manager;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.core.status.Status;
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
    public void unregisterContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(context);
        contextManager.registerCurrentThreadAsCreatorOfContext(context);
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
    public void registerCurrentThreadAsCreatorOfContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);
        contextManager.registerContext(context);

        contextManager.registerCurrentThreadAsCreatorOfContext(context);

        assertEquals(Thread.currentThread().getId(), context.getCreatorThreadId());
    }

    @Test
    public void registerCurrentThreadAsCreatorOfContextWithoutRegisterContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.registerCurrentThreadAsCreatorOfContext(context);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("Context not registered"));
        }
    }

    @Test
    public void registerCurrentThreadAsCreatorOfContextWithCreatorAlreadyRegisteredTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);
        contextManager.registerContext(context);
        contextManager.registerCurrentThreadAsCreatorOfContext(context);

        try {
            contextManager.registerCurrentThreadAsCreatorOfContext(context);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("has already a creator"));
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

    @Test
    public void registerTaskParentingTest() {
        ContextManager contextManager = new ContextManager();
        String childTaskId = "child";
        String parentTaskId = "parent";
        Context<String> childContext = new Context<>(childTaskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        Context<String> parentContext = new Context<>(parentTaskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);

        contextManager.registerContext(childContext);
        contextManager.registerContext(parentContext);

        contextManager.registerTaskParenting(parentContext, childContext);

        assertSame(parentContext, childContext.getParentContext());
        assertTrue(parentContext.hasChild(childContext));
    }

    @Test
    public void registerTaskParentingWithoutRegisterParentContextTest() {
        ContextManager contextManager = new ContextManager();
        String childTaskId = "child";
        String parentTaskId = "parent";
        Context<String> childContext = new Context<>(childTaskId, null, null, null, null, null, null, null, 0, 0, null, null);
        Context<String> parentContext = new Context<>(parentTaskId, null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(childContext);

        try {
            contextManager.registerTaskParenting(parentContext, childContext);
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("Context not registered"));
            assertTrue(e.getMessage().contains(parentTaskId));
        }
    }

    @Test
    public void registerTaskParentingWithoutRegisterChildContextTest() {
        ContextManager contextManager = new ContextManager();
        String childTaskId = "child";
        String parentTaskId = "parent";
        Context<String> childContext = new Context<>(childTaskId, null, null, null, null, null, null, null, 0, 0, null, null);
        Context<String> parentContext = new Context<>(parentTaskId, null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(parentContext);

        try {
            contextManager.registerTaskParenting(parentContext, childContext);
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("Context not registered"));
            assertTrue(e.getMessage().contains(childTaskId));
        }
    }

    @Test
    public void registerTaskParentingTwiceTest() {
        ContextManager contextManager = new ContextManager();
        String childTaskId = "child";
        String parentTaskId = "parent";
        Context<String> childContext = new Context<>(childTaskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        Context<String> parentContext = new Context<>(parentTaskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);

        contextManager.registerContext(childContext);
        contextManager.registerContext(parentContext);
        contextManager.registerTaskParenting(parentContext, childContext);

        try {
            contextManager.registerTaskParenting(parentContext, childContext);
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("already registered as child"));
        }
    }

    @Test
    public void registerTaskParentingWithDifferentParentsTest() {
        ContextManager contextManager = new ContextManager();
        String childTaskId = "child";
        String parentTaskAId = "parenA";
        String parentTaskBId = "parentB";
        Context<String> childContext = new Context<>(childTaskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        Context<String> parentAContext = new Context<>(parentTaskAId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        Context<String> parentBContext = new Context<>(parentTaskBId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);

        contextManager.registerContext(childContext);
        contextManager.registerContext(parentAContext);
        contextManager.registerContext(parentBContext);

        contextManager.registerTaskParenting(parentAContext, childContext);

        try {
            contextManager.registerTaskParenting(parentBContext, childContext);
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("another parent"));
        }
    }

    @Test
    public void getContextRunningOnCurrentThreadTest() {
        ContextManager contextManager = new ContextManager();
        String taskAId = "A";
        String taskBId = "B";
        String taskCId = "C";
        Context<String> taskAContext = new Context<>(taskAId, null, null, null, null, new Status(taskAId), null, new LinkedList<>(), 0, 0, null, null);
        Context<String> taskBContext = new Context<>(taskBId, null, null, null, null, new Status(taskBId), null, new LinkedList<>(), 0, 0, null, null);
        Context<String> taskCContext = new Context<>(taskCId, null, null, null, null, new Status(taskCId), null, new LinkedList<>(), 0, 0, null, null);

        contextManager.registerContext(taskAContext);
        contextManager.registerContext(taskBContext);
        contextManager.registerContext(taskCContext);

        contextManager.registerCurrentThreadAsExecutorOfContext(taskAContext);
        contextManager.registerCurrentThreadAsExecutorOfContext(taskBContext);
        contextManager.registerCurrentThreadAsExecutorOfContext(taskCContext);

        taskAContext.getStatus().setState(State.SUCCEEDED);
        taskBContext.getStatus().setState(State.SUCCEEDED);

        assertEquals(taskCContext, contextManager.getContextRunningOnCurrentThread());
    }

    @Test
    public void setContextResultWithNonRegisteredContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.setContextResult(context, null, null);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("Context not registered"));
            assertTrue(e.getMessage().contains(context.getTaskId()));
        }
    }

    @Test
    public void setContextResultWithValueTest() {
        ContextManager contextManager = new ContextManager();
        String taskId = "task";
        Context<String> taskContext = new Context<>(taskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        String result = "";

        contextManager.registerContext(taskContext);

        contextManager.setContextResult(taskContext, result, null);

        assertSame(result, taskContext.getResultValue());
    }

    @Test
    public void setContextResultWithExceptionTest() {
        ContextManager contextManager = new ContextManager();
        String taskId = "task";
        Context<String> taskContext = new Context<>(taskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        Exception result = new Exception();

        contextManager.registerContext(taskContext);

        contextManager.setContextResult(taskContext, null, result);

        assertSame(result, taskContext.getResultException());
    }

    @Test
    public void setContextResultTwiceTest() {
        ContextManager contextManager = new ContextManager();
        String taskId = "task";
        Context<String> taskContext = new Context<>(taskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        String result = "";

        contextManager.registerContext(taskContext);

        contextManager.setContextResult(taskContext, result, null);

        try {
            contextManager.setContextResult(taskContext, result, null);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("already has its value"));
        }
    }

}
