package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.core.status.Status;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class RunningStateExecutorTest {

    @Test
    public void getCancelStateExecutorTest() {
        IStateExecutor cancelStateExecutor = new StateExecutor(State.CANCELED);
        RunningStateExecutor executor = new RunningStateExecutor(cancelStateExecutor, null, null, null, null);
        assertSame(cancelStateExecutor, executor.getCancelStateExecutor());
    }

    @Test
    public void getFailedStateExecutorTest() {
        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED);
        RunningStateExecutor executor = new RunningStateExecutor(null, failedStateExecutor, null, null, null);
        assertSame(failedStateExecutor, executor.getFailedStateExecutor());
    }

    @Test
    public void getSucceededStateExecutorTest() {
        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);
        RunningStateExecutor executor = new RunningStateExecutor(null, null, succeededStateExecutor, null, null);
        assertSame(succeededStateExecutor, executor.getSucceededStateExecutor());
    }

    @Test
    public void getWaitingChildrenStateExecutorTest() {
        IStateExecutor waitingChildrenStateExecutor = new StateExecutor(State.WAITING_CHILDREN);
        RunningStateExecutor executor = new RunningStateExecutor(null, null, null, waitingChildrenStateExecutor, null);
        assertSame(waitingChildrenStateExecutor, executor.getWaitingChildrenStateExecutor());
    }

    @Test
    public void getContextManagerTest() {
        IContextManager contextManager = new ContextManager();
        RunningStateExecutor executor = new RunningStateExecutor(null, null, null, null, contextManager);
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void constructorTest() {
        IStateExecutor cancelStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED);
        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);
        IStateExecutor waitingChildrenStateExecutor = new StateExecutor(State.WAITING_CHILDREN);
        IContextManager contextManager = new ContextManager();

        RunningStateExecutor executor = new RunningStateExecutor(cancelStateExecutor, failedStateExecutor, succeededStateExecutor, waitingChildrenStateExecutor, contextManager);

        assertEquals(State.RUNNING, executor.getState());
        assertSame(cancelStateExecutor, executor.getCancelStateExecutor());
        assertSame(failedStateExecutor, executor.getFailedStateExecutor());
        assertSame(succeededStateExecutor, executor.getSucceededStateExecutor());
        assertSame(waitingChildrenStateExecutor, executor.getWaitingChildrenStateExecutor());
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void executeDetectsCancelTest() {
        AtomicBoolean canceledStateExecutorInvoked = new AtomicBoolean();

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED) {
            @Override public <T> void execute(IContext<T> context) { canceledStateExecutorInvoked.set(true); }
        };
        IContext<?> context = new Context<>( "", TestUtils.SUCCESS_ACTION, new CancellationToken(), null, TestUtils.OPTIONS, new Status(""), null, new LinkedList<>(), 0, 0, null, null);
        context.getCancellationToken().cancel();

        IContextManager contextManager = new ContextManager();
        contextManager.registerContext(context);

        RunningStateExecutor executor = new RunningStateExecutor(canceledStateExecutor, null, null, null, contextManager);
        executor.execute(context);

        assertTrue(canceledStateExecutorInvoked.get());
    }

    @Test
    public void executeDetectsCancelWithNotCancelableOptionTest() {
        AtomicBoolean canceledStateExecutorInvoked = new AtomicBoolean();

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED) {
            @Override public <T> void execute(IContext<T> context) { canceledStateExecutorInvoked.set(true); }
        };
        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);
        IContext<?> context = new Context<>( "", TestUtils.SUCCESS_ACTION, new CancellationToken(), null, new Options(Arrays.asList(Option.NOT_CANCELABLE)), new Status(""), null, new LinkedList<>(), 0, 0, null, null);
        context.getCancellationToken().cancel();

        IContextManager contextManager = new ContextManager();
        contextManager.registerContext(context);

        RunningStateExecutor executor = new RunningStateExecutor(canceledStateExecutor, null, succeededStateExecutor, null, contextManager);
        executor.execute(context);

        assertFalse(canceledStateExecutorInvoked.get());
    }

    @Test
    public void executeRegistersExecutorTest() {
        Context<?> context = new Context<>("", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null , new LinkedList<>(), 0, 0, null, null);

        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);
        AtomicBoolean registered = new AtomicBoolean();
        ContextManager contextManager = new ContextManager() {
            @Override public synchronized void registerCurrentThreadEndExecutionOfContext(IContext<?> c) { registered.set(c==context); }
        };
        contextManager.registerContext(context);

        RunningStateExecutor executor = new RunningStateExecutor(null, null, succeededStateExecutor, null, contextManager);
        executor.execute(context);

        assertTrue(registered.get());
    }

    @Test
    public void executeUnregistersExecutorTest() {
        Context<?> context = new Context<>("", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null , new LinkedList<>(), 0, 0, null, null);

        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);
        AtomicBoolean unregister = new AtomicBoolean();
        ContextManager contextManager = new ContextManager() {
            @Override public synchronized void registerCurrentThreadEndExecutionOfContext(IContext<?> c) { unregister.set(c==context); }
        };
        contextManager.registerContext(context);

        RunningStateExecutor executor = new RunningStateExecutor(null, null, succeededStateExecutor, null, contextManager);
        executor.execute(context);

        assertTrue(unregister.get());
    }

    @Test
    public void executeWithCancelResultTest() {
        Context<?> context = new Context<>("", TestUtils.CANCEL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null , new LinkedList<>(), 0, 0, null, null);

        AtomicBoolean cancelledStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor cancelledStateExecutor = new StateExecutor(State.CANCELED) {
            @Override public <T> void execute(IContext<T> c) { cancelledStateExecutorInvoked.set(c==context); }
        };
        ContextManager contextManager = new ContextManager();
        contextManager.registerContext(context);

        RunningStateExecutor executor = new RunningStateExecutor(cancelledStateExecutor, null, null, null, contextManager);
        executor.execute(context);

        assertEquals(State.RUNNING, context.getStatus().getState());
        assertTrue(cancelledStateExecutorInvoked.get());
        assertNotNull(context.getResultException());
        assertTrue(context.getResultException() instanceof CancelledException);
    }

    @Test
    public void executeWithFailResultTest() {
        Context<?> context = new Context<>("", TestUtils.FAIL_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null , new LinkedList<>(), 0, 0, null, null);

        AtomicBoolean failedStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED) {
            @Override public <T> void execute(IContext<T> c) { failedStateExecutorInvoked.set(c==context); }
        };
        ContextManager contextManager = new ContextManager();
        contextManager.registerContext(context);

        RunningStateExecutor executor = new RunningStateExecutor(null, failedStateExecutor, null, null, contextManager);
        executor.execute(context);

        assertEquals(State.RUNNING, context.getStatus().getState());
        assertTrue(failedStateExecutorInvoked.get());
        assertSame(TestUtils.FAIL_RESULT, context.getResultException());
    }

    @Test
    public void executeWithSuccessResultTest() {
        Context<?> context = new Context<>("", TestUtils.SUCCESS_ACTION, new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null , new LinkedList<>(), 0, 0, null, null);

        AtomicBoolean succeededStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED) {
            @Override public <T> void execute(IContext<T> c) { succeededStateExecutorInvoked.set(c==context); }
        };
        ContextManager contextManager = new ContextManager();
        contextManager.registerContext(context);

        RunningStateExecutor executor = new RunningStateExecutor(null, null, succeededStateExecutor, null, contextManager);
        executor.execute(context);

        assertEquals(State.RUNNING, context.getStatus().getState());
        assertTrue(succeededStateExecutorInvoked.get());
        assertSame(TestUtils.SUCCESS_RESULT, context.getResultValue());
    }

    @Test
    public void executeWithChildrenTest() {
        Context<?> childAContext = new Context<>("", TestUtils.SUCCESS_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null , new LinkedList<>(), 0, 0, TestUtils.SUCCESS_RESULT, null);
        Context<?> childBContext = new Context<>("", TestUtils.SUCCESS_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null , new LinkedList<>(), 0, 0, TestUtils.SUCCESS_RESULT, null);
        Context<?> parentContext = new Context<>("", TestUtils.SUCCESS_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null , Arrays.asList(childAContext, childBContext), 0, 0, TestUtils.SUCCESS_RESULT, null);

        AtomicBoolean waitingChildrenStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor waitingChildrenStateExecutor = new StateExecutor(State.WAITING_CHILDREN) {
            @Override public <T> void execute(IContext<T> c) { waitingChildrenStateExecutorInvoked.set(c==parentContext); }
        };
        ContextManager contextManager = new ContextManager();
        contextManager.registerContext(childAContext);
        contextManager.registerContext(childBContext);
        contextManager.registerContext(parentContext);

        RunningStateExecutor executor = new RunningStateExecutor(null, null, null, waitingChildrenStateExecutor, contextManager);
        executor.execute(parentContext);

        assertEquals(State.RUNNING, parentContext.getStatus().getState());
        assertTrue(waitingChildrenStateExecutorInvoked.get());
    }

}
