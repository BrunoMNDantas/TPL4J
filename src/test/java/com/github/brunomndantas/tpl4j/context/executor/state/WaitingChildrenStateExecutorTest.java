package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
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

public class WaitingChildrenStateExecutorTest {

    @Test
    public void getCanceledStateExecutorTest() {
        IStateExecutor cancelStateExecutor = new StateExecutor(State.CANCELED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(cancelStateExecutor, null, null);
        assertSame(cancelStateExecutor, executor.getCancelStateExecutor());
    }

    @Test
    public void getFailedStateExecutorTest() {
        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        assertSame(failedStateExecutor, executor.getFailedStateExecutor());
    }

    @Test
    public void getSucceededStateExecutorTest() {
        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, null, succeededStateExecutor);
        assertSame(succeededStateExecutor, executor.getSucceededStateExecutor());
    }

    @Test
    public void constructorTest() {
        IStateExecutor cancelStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED);
        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(cancelStateExecutor, failedStateExecutor, succeededStateExecutor);

        assertEquals(State.WAITING_CHILDREN, executor.getState());
        assertSame(cancelStateExecutor, executor.getCancelStateExecutor());
        assertSame(failedStateExecutor, executor.getFailedStateExecutor());
        assertSame(succeededStateExecutor, executor.getSucceededStateExecutor());
    }

    @Test
    public void executeWithSuccessAndChildrenSuccessTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.SUCCESS_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, TestUtils.SUCCESS_RESULT, null);
        IContext<?> parentContext = new Context<>("", TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, TestUtils.SUCCESS_RESULT, null);
        childContext.getStatus().setState(State.SUCCEEDED);

        AtomicBoolean succeededStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor succeededStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.SUCCEEDED; }
            @Override public <T> void execute(IContext<T> c) { succeededStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, null, succeededStateExecutor);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(succeededStateExecutorInvoked.get());
    }

    @Test
    public void executeWithSuccessAndChildrenCancelTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> parentContext = new Context<>("", TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, TestUtils.SUCCESS_RESULT, null);
        childContext.getStatus().setState(State.CANCELED);

        AtomicBoolean cancelledStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor cancelledStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.CANCELED; }
            @Override public <T> void execute(IContext<T> c) { cancelledStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(cancelledStateExecutor, null, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(cancelledStateExecutorInvoked.get());
        assertSame(childContext.getResultException(), parentContext.getResultException());
    }

    @Test
    public void executeWithSuccessAndChildrenCancelWithNotPropagateCancellationOptionTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> parentContext = new Context<>("", TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.NOT_PROPAGATE_CANCELLATION)), new Status(""), null, Arrays.asList(childContext), 0, 0, TestUtils.SUCCESS_RESULT, null);
        childContext.getStatus().setState(State.CANCELED);

        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, null, succeededStateExecutor);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.SUCCEEDED, parentContext.getStatus().getState());
        assertNull(parentContext.getResultException());
    }

    @Test
    public void executeWithSuccessAndChildrenFailTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> parentContext = new Context<>("", TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, TestUtils.SUCCESS_RESULT, null);
        childContext.getStatus().setState(State.FAILED);

        AtomicBoolean failedStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor failedStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.FAILED; }
            @Override public <T> void execute(IContext<T> c) { failedStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(failedStateExecutorInvoked.get());
        assertSame(childContext.getResultException(), parentContext.getResultException());
    }

    @Test
    public void executeWithSuccessAndChildrenFailWithNotPropagateFailureOptionTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> parentContext = new Context<>("", TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.NOT_PROPAGATE_FAILURE)), new Status(""), null, Arrays.asList(childContext), 0, 0, TestUtils.SUCCESS_RESULT, null);
        childContext.getStatus().setState(State.FAILED);

        IStateExecutor succeededStateExecutor = new StateExecutor(State.SUCCEEDED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, null, succeededStateExecutor);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.SUCCEEDED, parentContext.getStatus().getState());
        assertNull(parentContext.getResultException());
    }

    @Test
    public void executeWithCancelAndChildrenSuccessTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.SUCCESS_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, TestUtils.SUCCESS_RESULT, null);
        IContext<?> parentContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, null, new CancelledException(""));
        childContext.getStatus().setState(State.SUCCEEDED);

        AtomicBoolean cancelledStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor cancelledStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.CANCELED; }
            @Override public <T> void execute(IContext<T> c) { cancelledStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(cancelledStateExecutor, null, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(cancelledStateExecutorInvoked.get());
    }

    @Test
    public void executeWithCancelAndChildrenCancelTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> parentContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, null, new CancelledException(""));
        childContext.getStatus().setState(State.CANCELED);

        AtomicBoolean cancelledStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor cancelledStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.CANCELED; }
            @Override public <T> void execute(IContext<T> c) { cancelledStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(cancelledStateExecutor, null, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(cancelledStateExecutorInvoked.get());
        assertEquals(1, parentContext.getResultException().getSuppressed().length);
    }

    @Test
    public void executeWithCancelAndChildrenCancelWithNotPropagateCancellationOptionTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> parentContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.NOT_PROPAGATE_CANCELLATION)), new Status(""), null, Arrays.asList(childContext), 0, 0, null, new CancelledException(""));
        childContext.getStatus().setState(State.CANCELED);

        IStateExecutor cancelledStateExecutor = new StateExecutor(State.CANCELED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(cancelledStateExecutor, null, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.CANCELED, parentContext.getStatus().getState());
        assertEquals(0, parentContext.getResultException().getSuppressed().length);
    }

    @Test
    public void executeWithCancelAndChildrenFailTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> parentContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, null, new CancelledException(""));
        childContext.getStatus().setState(State.FAILED);

        AtomicBoolean failedStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor failedStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.FAILED; }
            @Override public <T> void execute(IContext<T> c) { failedStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(failedStateExecutorInvoked.get());
        assertSame(childContext.getResultException(), parentContext.getResultException());
    }

    @Test
    public void executeWithCancelAndChildrenFailWithNotPropagateFailureOptionTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> parentContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.NOT_PROPAGATE_FAILURE)), new Status(""), null, Arrays.asList(childContext), 0, 0, null, new CancelledException(""));
        childContext.getStatus().setState(State.FAILED);

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(canceledStateExecutor, null, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.CANCELED, parentContext.getStatus().getState());
        assertTrue(parentContext.getResultException() instanceof CancelledException);
    }

    @Test
    public void executeWithFailAndChildrenSuccessTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.SUCCESS_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, TestUtils.SUCCESS_RESULT, null);
        IContext<?> parentContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, null, new Exception());
        childContext.getStatus().setState(State.SUCCEEDED);

        AtomicBoolean failedStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor failedStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.FAILED; }
            @Override public <T> void execute(IContext<T> c) { failedStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(failedStateExecutorInvoked.get());
        assertNotNull(parentContext.getResultException());
    }

    @Test
    public void executeWithFailAndChildrenCancelTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> parentContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, null, new Exception());
        childContext.getStatus().setState(State.CANCELED);

        AtomicBoolean failedStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor failedStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.FAILED; }
            @Override public <T> void execute(IContext<T> c) { failedStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(failedStateExecutorInvoked.get());
        assertNotNull(parentContext.getResultException());
    }

    @Test
    public void executeWithFailAndChildrenCancelWithNotPropagateCancellationOptionTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> parentContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.NOT_PROPAGATE_CANCELLATION)), new Status(""), null, Arrays.asList(childContext), 0, 0, null, new Exception());
        childContext.getStatus().setState(State.CANCELED);

        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.FAILED, parentContext.getStatus().getState());
        assertNotNull(parentContext.getResultException());
        assertEquals(0, parentContext.getResultException().getSuppressed().length);
    }

    @Test
    public void executeWithFailAndChildrenFailTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> parentContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childContext), 0, 0, null, new Exception());
        childContext.getStatus().setState(State.FAILED);

        AtomicBoolean failedStateExecutorInvoked = new AtomicBoolean();
        IStateExecutor failedStateExecutor = new IStateExecutor() {
            @Override public State getState() { return State.FAILED; }
            @Override public <T> void execute(IContext<T> c) { failedStateExecutorInvoked.set(c==parentContext); }
        };

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.WAITING_CHILDREN, parentContext.getStatus().getState());
        assertTrue(failedStateExecutorInvoked.get());
        assertNotSame(childContext.getResultException(), parentContext.getResultException());
    }

    @Test
    public void executeWithFailAndChildrenFailWithNotPropagateFailureOptionTest() throws Exception {
        IContext<?> childContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> parentContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.NOT_PROPAGATE_FAILURE)), new Status(""), null, Arrays.asList(childContext), 0, 0, null, new Exception());
        childContext.getStatus().setState(State.FAILED);

        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED);
        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertEquals(State.FAILED, parentContext.getStatus().getState());
        assertNotSame(childContext.getResultException(), parentContext.getResultException());
        assertEquals(0, parentContext.getResultException().getSuppressed().length);
    }

    @Test
    public void executeMergesFailsTest() throws Exception {
        IContext<?> childAContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> childBContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> parentContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childAContext, childBContext), 0, 0, null, new Exception());
        childAContext.getStatus().setState(State.FAILED);
        childBContext.getStatus().setState(State.FAILED);

        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED);

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertNotSame(childAContext.getResultException(), parentContext.getResultException());
        assertNotSame(childBContext.getResultException(), parentContext.getResultException());
        assertEquals(2, parentContext.getResultException().getSuppressed().length);
        assertSame(childAContext.getResultException(), parentContext.getResultException().getSuppressed()[0]);
        assertSame(childBContext.getResultException(), parentContext.getResultException().getSuppressed()[1]);
    }

    @Test
    public void executeMergesChildrenFailsTest() throws Exception {
        IContext<?> childAContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> childBContext = new Context<>("", TestUtils.FAIL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new Exception());
        IContext<?> parentContext = new Context<>("", TestUtils.SUCCESS_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childAContext, childBContext), 0, 0, TestUtils.SUCCESS_RESULT, null);
        childAContext.getStatus().setState(State.FAILED);
        childBContext.getStatus().setState(State.FAILED);

        IStateExecutor failedStateExecutor = new StateExecutor(State.FAILED);

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(null, failedStateExecutor, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertSame(childAContext.getResultException(), parentContext.getResultException());
        assertEquals(1, parentContext.getResultException().getSuppressed().length);
        assertSame(childBContext.getResultException(), parentContext.getResultException().getSuppressed()[0]);
    }

    @Test
    public void executeMergesCancelsTest() throws Exception {
        IContext<?> childAContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> childBContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> parentContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childAContext, childBContext), 0, 0, null, new CancelledException(""));
        childAContext.getStatus().setState(State.CANCELED);
        childBContext.getStatus().setState(State.CANCELED);

        IStateExecutor cancelledStateExecutor = new StateExecutor(State.CANCELED);

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(cancelledStateExecutor, null, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertNotSame(childAContext.getResultException(), parentContext.getResultException());
        assertNotSame(childBContext.getResultException(), parentContext.getResultException());
        assertEquals(2, parentContext.getResultException().getSuppressed().length);
        assertSame(childAContext.getResultException(), parentContext.getResultException().getSuppressed()[0]);
        assertSame(childBContext.getResultException(), parentContext.getResultException().getSuppressed()[1]);
    }

    @Test
    public void executeMergesChildrenCancelsTest() throws Exception {
        IContext<?> childAContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> childBContext = new Context<>("", TestUtils.CANCEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), null, new LinkedList<>(), 0, 0, null, new CancelledException(""));
        IContext<?> parentContext = new Context<>("", TestUtils.SUCCESS_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, Arrays.asList(childAContext, childBContext), 0, 0, TestUtils.SUCCESS_RESULT, null);
        childAContext.getStatus().setState(State.CANCELED);
        childBContext.getStatus().setState(State.CANCELED);

        IStateExecutor cancelledStateExecutor = new StateExecutor(State.CANCELED);

        WaitingChildrenStateExecutor executor = new WaitingChildrenStateExecutor(cancelledStateExecutor, null, null);
        executor.execute(parentContext);

        Thread.sleep(1000);
        assertSame(childAContext.getResultException(), parentContext.getResultException());
        assertEquals(1, parentContext.getResultException().getSuppressed().length);
        assertSame(childBContext.getResultException(), parentContext.getResultException().getSuppressed()[0]);
    }

}
