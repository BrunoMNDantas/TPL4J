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

        assertSame(context, contextManager.getContext(context.getTaskId()));
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
        contextManager.registerCurrentThreadAsCreatorOfContext(context.getTaskId());
        contextManager.registerCurrentThreadAsExecutorOfContext(context.getTaskId());
        contextManager.unregisterContext(context.getTaskId());

        assertNull(contextManager.getContext(context.getTaskId()));
        assertNull(contextManager.getIdOfTaskRunningOnCurrentThread());
    }

    @Test
    public void unregisterContextWithNotRegisteredContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.unregisterContext(context.getTaskId());
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("There is no Context"));
            assertTrue(e.getMessage().contains(context.getTaskId()));
        }
    }

    @Test
    public void unregisterContextWithInvalidExecutorThreadTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(context);
        contextManager.registerCurrentThreadAsCreatorOfContext(context.getTaskId());
        contextManager.registerCurrentThreadAsExecutorOfContext(context.getTaskId());

        context.setExecutorThreadId(1);

        try {
            contextManager.unregisterContext(context.getTaskId());
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("not executing on the specified thread"));
        }
    }

    @Test
    public void getContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        contextManager.registerContext(context);

        assertSame(context, contextManager.getContext(context.getTaskId()));
    }

    @Test
    public void getContextWithNonExistentIdReturnsNullTest() {
        ContextManager contextManager = new ContextManager();
        assertNull(contextManager.getContext("non existent id"));
    }

    @Test
    public void registerCurrentThreadAsCreatorOfContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);
        contextManager.registerContext(context);

        contextManager.registerCurrentThreadAsCreatorOfContext(context.getTaskId());

        assertEquals(Thread.currentThread().getId(), context.getCreatorThreadId());
    }

    @Test
    public void registerCurrentThreadAsCreatorOfContextWithoutRegisterContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.registerCurrentThreadAsCreatorOfContext(context.getTaskId());
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("There is no Context"));
        }
    }

    @Test
    public void registerCurrentThreadAsCreatorOfContextWithCreatorAlreadyRegisteredTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);
        contextManager.registerContext(context);
        contextManager.registerCurrentThreadAsCreatorOfContext(context.getTaskId());

        try {
            contextManager.registerCurrentThreadAsCreatorOfContext(context.getTaskId());
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

        contextManager.registerCurrentThreadAsExecutorOfContext(context.getTaskId());

        assertEquals(Thread.currentThread().getId(), context.getExecutorThreadId());
    }

    @Test
    public void registerCurrentThreadAsExecutorOfContextWithoutRegisterContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.registerCurrentThreadAsExecutorOfContext(context.getTaskId());
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("There is no Context"));
        }
    }

    @Test
    public void registerCurrentThreadAsExecutorOfContextWithExecutorAlreadyRegisteredTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);
        contextManager.registerContext(context);
        contextManager.registerCurrentThreadAsExecutorOfContext(context.getTaskId());

        try {
            contextManager.registerCurrentThreadAsExecutorOfContext(context.getTaskId());
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("has already a executor"));
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

        contextManager.registerTaskParenting(parentTaskId, childTaskId);

        assertSame(parentTaskId, childContext.getParentTaskId());
        assertTrue(parentContext.hasChild(childTaskId));
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
            contextManager.registerTaskParenting(childTaskId, parentTaskId);
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("There is no Context"));
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
            contextManager.registerTaskParenting(childTaskId, parentTaskId);
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("There is no Context"));
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
        contextManager.registerTaskParenting(childTaskId, parentTaskId);

        try {
            contextManager.registerTaskParenting(childTaskId, parentTaskId);
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

        contextManager.registerTaskParenting(parentTaskAId, childTaskId);

        try {
            contextManager.registerTaskParenting(parentTaskBId, childTaskId);
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("another parent"));
        }
    }

    @Test
    public void getTaskRunningOnCurrentThreadTest() {
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

        contextManager.registerCurrentThreadAsExecutorOfContext(taskAId);
        contextManager.registerCurrentThreadAsExecutorOfContext(taskBId);
        contextManager.registerCurrentThreadAsExecutorOfContext(taskCId);

        taskAContext.getStatus().setState(State.SUCCEEDED);
        taskBContext.getStatus().setState(State.SUCCEEDED);

        assertEquals(taskCId, contextManager.getIdOfTaskRunningOnCurrentThread());
    }

    @Test
    public void setContextResultWithNonRegisteredContextTest() {
        ContextManager contextManager = new ContextManager();
        Context<String> context = new Context<>("id", null, null, null, null, null, null, null, 0, 0, null, null);

        try {
            contextManager.setContextResult(context.getTaskId(), null, null);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("There is no Context"));
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

        contextManager.setContextResult(taskContext.getTaskId(), result, null);

        assertSame(result, taskContext.getResultValue());
    }

    @Test
    public void setContextResultWithExceptionTest() {
        ContextManager contextManager = new ContextManager();
        String taskId = "task";
        Context<String> taskContext = new Context<>(taskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        Exception result = new Exception();

        contextManager.registerContext(taskContext);

        contextManager.setContextResult(taskContext.getTaskId(), null, result);

        assertSame(result, taskContext.getResultException());
    }

    @Test
    public void setContextResultTwiceTest() {
        ContextManager contextManager = new ContextManager();
        String taskId = "task";
        Context<String> taskContext = new Context<>(taskId, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);
        String result = "";

        contextManager.registerContext(taskContext);

        contextManager.setContextResult(taskContext.getTaskId(), result, null);

        try {
            contextManager.setContextResult(taskContext.getTaskId(), result, null);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("already has its value"));
        }
    }

}
