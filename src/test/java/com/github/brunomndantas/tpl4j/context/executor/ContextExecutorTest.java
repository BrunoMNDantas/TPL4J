package com.github.brunomndantas.tpl4j.context.executor;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.builder.ContextBuilder;
import com.github.brunomndantas.tpl4j.context.executor.state.IStateExecutor;
import com.github.brunomndantas.tpl4j.context.executor.state.ScheduledStateExecutor;
import com.github.brunomndantas.tpl4j.context.executor.state.StateExecutor;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class ContextExecutorTest {

    @Test
    public void getScheduledStateExecutorTest() {
        IStateExecutor scheduledStateExecutor = new StateExecutor(State.SCHEDULED);
        ContextExecutor executor = new ContextExecutor(null, scheduledStateExecutor);
        assertSame(scheduledStateExecutor, executor.getScheduledStateExecutor());
    }

    @Test
    public void getContextManager() {
        IContextManager contextManager = new ContextManager();
        ContextExecutor executor = new ContextExecutor(contextManager, null);
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void constructorsTest() {
        IContextManager contextManager = new ContextManager();
        IStateExecutor scheduledStateExecutor = new StateExecutor(State.SCHEDULED);

        ContextExecutor executor = new ContextExecutor(contextManager, scheduledStateExecutor);

        assertSame(contextManager, executor.getContextManager());
        assertSame(scheduledStateExecutor, executor.getScheduledStateExecutor());

        executor = new ContextExecutor(contextManager);

        assertSame(contextManager, executor.getContextManager());
        assertTrue(executor.getScheduledStateExecutor() instanceof ScheduledStateExecutor);
    }

    @Test
    public void executeTest() {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<String> context = contextBuilder.build("parent", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        ContextExecutor executor = new ContextExecutor(contextManager);

        executor.execute(context);
        assertTrue(context.getStatus().getScheduledEvent().hasFired());
    }

    @Test
    public void executeVerifyIfCancelledRequestedTest() {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<String> context = contextBuilder.build("parent", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        ContextExecutor executor = new ContextExecutor(contextManager);

        context.getCancellationToken().cancel();

        executor.execute(context);
        assertTrue(context.getStatus().getScheduledEvent().hasFired());
        assertTrue(context.getStatus().getCancelledEvent().hasFired());
    }

    @Test
    public void executeTwiceResultsInExceptionTest() {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<String> context = contextBuilder.build("parent", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
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
            IContext<String> context = contextBuilder.build("child", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
            executor.execute(context);
            return TestUtils.SUCCESS_RESULT;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.REJECT_CHILDREN)));

        long time = System.currentTimeMillis() + TestUtils.SLEEP_TIME;
        executor.execute(context);

        context.getStatus().getFinishedEvent().await();

        assertTrue(System.currentTimeMillis() < time);
    }

    @Test
    public void executeWaitsForAttachedChildrenTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            return TestUtils.SUCCESS_RESULT;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);

        long time = System.currentTimeMillis() + TestUtils.SLEEP_TIME;
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        assertTrue(System.currentTimeMillis() >= time);
    }

    @Test
    public void executeWithParentSuccessAndChildSuccessTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            return TestUtils.SUCCESS_RESULT;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);

        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getSucceededEvent().hasFired());
        assertSame(TestUtils.SUCCESS_RESULT, parentContext.getResultValue());
        assertNull(parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getSucceededEvent().hasFired());
        assertSame(TestUtils.SUCCESS_RESULT, childContext.getResultValue());
        assertNull(childContext.getResultException());
    }

    @Test
    public void executeWithParentSuccessAndChildCancelTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.CANCEL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            return TestUtils.SUCCESS_RESULT;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getCancelledEvent().hasFired());
        assertNotNull(parentContext.getResultValue());
        assertSame(childContext.getResultException(), parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getCancelledEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertTrue(childContext.getResultException() instanceof CancelledException);
    }

    @Test
    public void executeWithParentSuccessAndChildFailTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.FAIL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            return TestUtils.SUCCESS_RESULT;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNotNull(parentContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getFailedEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, childContext.getResultException());
    }

    @Test
    public void executeWithParentCancelAndChildSuccessTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            token.cancel();
            token.abortIfCancelRequested();
            return null;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getCancelledEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertTrue(parentContext.getResultException() instanceof CancelledException);

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getSucceededEvent().hasFired());
        assertSame(TestUtils.SUCCESS_RESULT, childContext.getResultValue());
        assertNull(childContext.getResultException());
    }

    @Test
    public void executeWithParentCancelAndChildCancelTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.CANCEL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            token.cancel();
            token.abortIfCancelRequested();
            return null;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getCancelledEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertTrue(parentContext.getResultException() instanceof CancelledException);

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getCancelledEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertTrue(childContext.getResultException() instanceof CancelledException);
    }

    @Test
    public void executeWithParentCancelAndChildFailTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.FAIL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            token.cancel();
            token.abortIfCancelRequested();
            return null;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getFailedEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, childContext.getResultException());
    }

    @Test
    public void executeWithParentCancelAndChildrenFailTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("childA", TestUtils.FAIL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            context = contextBuilder.build("childB", TestUtils.FAIL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            token.cancel();
            token.abortIfCancelRequested();

            return null;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childAContext = context.getChildrenContexts().stream().findFirst().get();
        IContext<?> childBContext = context.getChildrenContexts().stream().skip(1).findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childAContext));
        assertTrue(parentContext.getChildrenContexts().contains(childBContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, parentContext.getResultException());
        assertEquals(0, parentContext.getResultException().getSuppressed().length);//Both end with same exception so child exception is not suppressed

        assertNotNull(childAContext);
        assertEquals(parentContext, childAContext.getParentContext());
        assertTrue(childAContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childAContext.getStatus().getFailedEvent().hasFired());
        assertNull(childAContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, childAContext.getResultException());

        assertNotNull(childBContext);
        assertEquals(parentContext, childBContext.getParentContext());
        assertTrue(childBContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childBContext.getStatus().getFailedEvent().hasFired());
        assertNull(childBContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, childBContext.getResultException());
    }

    @Test
    public void executeWithParentFailAndChildSuccessTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            throw TestUtils.FAIL_RESULT;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getSucceededEvent().hasFired());
        assertSame(TestUtils.SUCCESS_RESULT, childContext.getResultValue());
        assertNull(childContext.getResultException());
    }

    @Test
    public void executeWithParentFailAndChildCancelTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.CANCEL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            throw TestUtils.FAIL_RESULT;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, parentContext.getResultException());

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getCancelledEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertTrue(childContext.getResultException() instanceof CancelledException);
    }

    @Test
    public void executeWithParentFailAndChildFailTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("child", TestUtils.FAIL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);
            throw TestUtils.FAIL_RESULT;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childContext = context.getChildrenContexts().stream().findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, parentContext.getResultException());
        assertEquals(0, parentContext.getResultException().getSuppressed().length); //Both end with same exception so child exception is not suppressed

        assertNotNull(childContext);
        assertEquals(parentContext, childContext.getParentContext());
        assertTrue(childContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childContext.getStatus().getFailedEvent().hasFired());
        assertNull(childContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, childContext.getResultException());
    }

    @Test
    public void executeWithParentFailAndChildrenFailTest() throws Exception {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        ContextExecutor executor = new ContextExecutor(contextManager);

        Exception exception = new Exception();
        IAction<String> action = (token) ->  {
            IContext<String> context = contextBuilder.build("childA", TestUtils.FAIL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            context = contextBuilder.build("childB", TestUtils.FAIL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)));
            executor.execute(context);

            throw exception;
        };

        IContext<String> context = contextBuilder.build("parent", action, new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS);
        executor.execute(context);
        context.getStatus().getFinishedEvent().await();

        IContext<?> parentContext = context;
        IContext<?> childAContext = context.getChildrenContexts().stream().findFirst().get();
        IContext<?> childBContext = context.getChildrenContexts().stream().skip(1).findFirst().get();

        assertNotNull(parentContext);
        assertTrue(parentContext.getChildrenContexts().contains(childAContext));
        assertTrue(parentContext.getChildrenContexts().contains(childBContext));
        assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(parentContext.getStatus().getFailedEvent().hasFired());
        assertNull(parentContext.getResultValue());
        assertSame(exception, parentContext.getResultException());
        assertSame(TestUtils.FAIL_RESULT, parentContext.getResultException().getSuppressed()[0]);
        assertSame(TestUtils.FAIL_RESULT, parentContext.getResultException().getSuppressed()[1]);

        assertNotNull(childAContext);
        assertEquals(parentContext, childAContext.getParentContext());
        assertTrue(childAContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childAContext.getStatus().getFailedEvent().hasFired());
        assertNull(childAContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, childAContext.getResultException());

        assertNotNull(childBContext);
        assertEquals(parentContext, childBContext.getParentContext());
        assertTrue(childBContext.getStatus().getFinishedEvent().hasFired());
        assertTrue(childBContext.getStatus().getFailedEvent().hasFired());
        assertNull(childBContext.getResultValue());
        assertSame(TestUtils.FAIL_RESULT, childBContext.getResultException());
    }

}

