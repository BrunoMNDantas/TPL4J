package com.github.brunomndantas.tpl4j.context.executor;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.builder.ContextBuilder;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.SingleThreadScheduler;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class ContextExecutorTest {

    private static final long SLEEP_TIME = 3000;
    private static final IScheduler SCHEDULER = new SingleThreadScheduler();
    private static final IOptions OPTIONS = new Options(new LinkedList<>());
    private static final String SUCCESS_RESULT = "";
    private static final IAction<String> SUCCESS_ACTION = (token) -> { Thread.sleep(SLEEP_TIME); return SUCCESS_RESULT; };
    private static final Exception FAIL_RESULT = new Exception();
    private static final IAction<String> FAIL_ACTION = (token) -> { Thread.sleep(SLEEP_TIME); throw FAIL_RESULT; };
    private static final IAction<String> CANCEL_ACTION = (token) -> { Thread.sleep(SLEEP_TIME); token.cancel(); token.abortIfCancelRequested(); return null; };



    @AfterClass
    public static void close() {
        SCHEDULER.close();
    }



    @Test
    public void getContextManagerTest() {
        ContextManager contextManager = new ContextManager();
        ContextExecutor executor = new ContextExecutor(contextManager);
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void constructorTest() {
        ContextManager contextManager = new ContextManager();
        ContextExecutor executor = new ContextExecutor(contextManager);
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void executeTest() {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        Context<String> context = contextBuilder.build("parent", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, OPTIONS);
        ContextExecutor executor = new ContextExecutor(contextManager);

        executor.execute(context);
        assertTrue(context.getStatus().getScheduledEvent().hasFired());
    }

    @Test
    public void executeTwiceResultsInExceptionTest() {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        Context<String> context = contextBuilder.build("parent", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, OPTIONS);
        ContextExecutor executor = new ContextExecutor(contextManager);

        executor.execute(context);

        try {
            executor.execute(context);
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("already scheduled"));
        }
    }

    @Test
    public void executeDoesntWaitForNonAttachedChildrenTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, OPTIONS);
            executor.execute(context);
            return SUCCESS_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.REJECT_CHILDREN)));

        long time = System.currentTimeMillis() + SLEEP_TIME;
        executor.execute(context);

        context.getStatus().getFinishedEvent().await();

        assertTrue(System.currentTimeMillis() < time);
    }

    @Test
    public void executeWaitsForAttachedChildrenTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            return SUCCESS_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);

        long time = System.currentTimeMillis() + SLEEP_TIME;
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        assertTrue(System.currentTimeMillis() >= time);
    }

    @Test
    public void executeWithParentSuccessAndChildSuccessTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            return SUCCESS_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);

        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getSucceededEvent().hasFired());
        assertSame(SUCCESS_RESULT, parentContext.getResultValue());
        assertNull(parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getSucceededEvent().hasFired());
        assertSame(SUCCESS_RESULT, childContext.getResultValue());
        assertNull(childContext.getResultException());
    }

    @Test
    public void executeWithParentSuccessAndChildCancelTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", CANCEL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            return SUCCESS_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getSucceededEvent().hasFired());
        assertSame(SUCCESS_RESULT, parentContext.getResultValue());
        assertNull(parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getCancelledEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertTrue(childContext.getResultException() instanceof CancelledException);
    }

    @Test
    public void executeWithParentSuccessAndChildFailTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", FAIL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            return SUCCESS_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(FAIL_RESULT, parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getFailedEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertSame(FAIL_RESULT, childContext.getResultException());
    }

    @Test
    public void executeWithParentSuccessAndChildrenFailTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("childA", FAIL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            context = contextBuilder.build("childB", FAIL_ACTION, new CancellationToken(), SCHEDULER, OPTIONS);
            executor.execute(context);

            return SUCCESS_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childAContext = contextManager.getContext("childA");
        Context<?> childBContext = contextManager.getContext("childB");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childAContext.getTaskId()));
        assertTrue(parentContext.getChildrenTasksIds().contains(childBContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(FAIL_RESULT, parentContext.getResultException());
        assertEquals(0, parentContext.getResultException().getSuppressed().length);//Both end with same exception so child exception is not suppressed

        assertNotNull(childAContext);
        assertEquals(parentContext.getTaskId(), childAContext.getParentTaskId());
        assertTrue(childAContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childAContext.getStatus().getFailedEvent().hasFired());
        assertNull(childAContext.getResultValue());
        assertSame(FAIL_RESULT, childAContext.getResultException());

        assertNotNull(childBContext);
        assertEquals(parentContext.getTaskId(), childBContext.getParentTaskId());
        assertTrue(childBContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childBContext.getStatus().getFailedEvent().hasFired());
        assertNull(childBContext.getResultValue());
        assertSame(FAIL_RESULT, childBContext.getResultException());
    }

    @Test
    public void executeWithParentCancelAndChildSuccessTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            token.cancel();
            token.abortIfCancelRequested();
            return null;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getCancelledEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertTrue(parentContext.getResultException() instanceof CancelledException);

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getSucceededEvent().hasFired());
        assertSame(SUCCESS_RESULT, childContext.getResultValue());
        assertNull(childContext.getResultException());
    }

    @Test
    public void executeWithParentCancelAndChildCancelTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", CANCEL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            token.cancel();
            token.abortIfCancelRequested();
            return null;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getCancelledEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertTrue(parentContext.getResultException() instanceof CancelledException);

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getCancelledEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertTrue(childContext.getResultException() instanceof CancelledException);
    }

    @Test
    public void executeWithParentCancelAndChildFailTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", FAIL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            token.cancel();
            token.abortIfCancelRequested();
            return null;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(FAIL_RESULT, parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getFailedEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertSame(FAIL_RESULT, childContext.getResultException());
    }

    @Test
    public void executeWithParentCancelAndChildrenFailTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("childA", FAIL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            context = contextBuilder.build("childB", FAIL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            token.cancel();
            token.abortIfCancelRequested();

            return null;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childAContext = contextManager.getContext("childA");
        Context<?> childBContext = contextManager.getContext("childB");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childAContext.getTaskId()));
        assertTrue(parentContext.getChildrenTasksIds().contains(childBContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(FAIL_RESULT, parentContext.getResultException());
        assertEquals(0, parentContext.getResultException().getSuppressed().length);//Both end with same exception so child exception is not suppressed

        assertNotNull(childAContext);
        assertEquals(parentContext.getTaskId(), childAContext.getParentTaskId());
        assertTrue(childAContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childAContext.getStatus().getFailedEvent().hasFired());
        assertNull(childAContext.getResultValue());
        assertSame(FAIL_RESULT, childAContext.getResultException());

        assertNotNull(childBContext);
        assertEquals(parentContext.getTaskId(), childBContext.getParentTaskId());
        assertTrue(childBContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childBContext.getStatus().getFailedEvent().hasFired());
        assertNull(childBContext.getResultValue());
        assertSame(FAIL_RESULT, childBContext.getResultException());
    }

    @Test
    public void executeWithParentFailAndChildSuccessTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            throw FAIL_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(FAIL_RESULT, parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getSucceededEvent().hasFired());
        assertSame(SUCCESS_RESULT, childContext.getResultValue());
        assertNull(childContext.getResultException());
    }

    @Test
    public void executeWithParentFailAndChildCancelTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", CANCEL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            throw FAIL_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(FAIL_RESULT, parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getCancelledEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertTrue(childContext.getResultException() instanceof CancelledException);
    }

    @Test
    public void executeWithParentFailAndChildFailTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("child", FAIL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            throw FAIL_RESULT;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childContext = contextManager.getContext("child");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(FAIL_RESULT, parentContext.getResultException());
        assertEquals(0, parentContext.getResultException().getSuppressed().length); //Both end with same exception so child exception is not suppressed

        assertNotNull(childContext);
        assertEquals(parentContext.getTaskId(), childContext.getParentTaskId());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getFailedEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertSame(FAIL_RESULT, childContext.getResultException());
    }

    @Test
    public void executeWithParentFailAndChildrenFailTest() throws Exception {
        ContextManager contextManager = new ContextManager(){
            @Override public synchronized void unregisterContext(String taskId) { }
        };
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        Exception exception = new Exception();
        IAction<String> action = (token) ->  {
            Context<String> context = contextBuilder.build("childA", FAIL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            context = contextBuilder.build("childB", FAIL_ACTION, new CancellationToken(), SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            throw exception;
        };

        Context<String> context = contextBuilder.build("parent", action, new CancellationToken(), SCHEDULER, OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        Context<?> parentContext = contextManager.getContext("parent");
        Context<?> childAContext = contextManager.getContext("childA");
        Context<?> childBContext = contextManager.getContext("childB");

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenTasksIds().contains(childAContext.getTaskId()));
        assertTrue(parentContext.getChildrenTasksIds().contains(childBContext.getTaskId()));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(exception, parentContext.getResultException());
        assertSame(FAIL_RESULT, parentContext.getResultException().getSuppressed()[0]);
        assertSame(FAIL_RESULT, parentContext.getResultException().getSuppressed()[1]);

        assertNotNull(childAContext);
        assertEquals(parentContext.getTaskId(), childAContext.getParentTaskId());
        assertTrue(childAContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childAContext.getStatus().getFailedEvent().hasFired());
        assertNull(childAContext.getResultValue());
        assertSame(FAIL_RESULT, childAContext.getResultException());

        assertNotNull(childBContext);
        assertEquals(parentContext.getTaskId(), childBContext.getParentTaskId());
        assertTrue(childBContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childBContext.getStatus().getFailedEvent().hasFired());
        assertNull(childBContext.getResultValue());
        assertSame(FAIL_RESULT, childBContext.getResultException());
    }

}

