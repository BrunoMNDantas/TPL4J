package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.core.status.Status;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class ScheduledStateExecutorTest {

    @Test
    public void getCancelStateExecutorTest() {
        IStateExecutor cancelStateExecutor = new StateExecutor(State.CANCELED);
        ScheduledStateExecutor executor = new ScheduledStateExecutor(cancelStateExecutor, null);
        assertSame(cancelStateExecutor, executor.getCancelStateExecutor());
    }

    @Test
    public void getRunningStateExecutorTest() {
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        ScheduledStateExecutor executor = new ScheduledStateExecutor(null, runningStateExecutor);
        assertSame(runningStateExecutor, executor.getRunningStateExecutor());
    }

    @Test
    public void constructorTest() {
        IStateExecutor cancelStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);

        ScheduledStateExecutor executor = new ScheduledStateExecutor(cancelStateExecutor, runningStateExecutor);

        assertEquals(State.SCHEDULED, executor.getState());
        assertSame(cancelStateExecutor, executor.getCancelStateExecutor());
        assertSame(runningStateExecutor, executor.getRunningStateExecutor());
    }

    @Test
    public void executeTest() throws Exception {
        AtomicBoolean runningStateExecutorInvoked = new AtomicBoolean();
        IContext<?> context = new Context<>("", (ct)->"", new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, null, 0, 0, null, null);
        IStateExecutor canceledExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningExecutor = new StateExecutor(State.RUNNING) {
            @Override public <T> void execute(IContext<T> c) { runningStateExecutorInvoked.set(c==context); }
        };
        ScheduledStateExecutor executor = new ScheduledStateExecutor(canceledExecutor, runningExecutor);

        executor.execute(context);

        Thread.sleep(1000);

        assertEquals(State.SCHEDULED, context.getStatus().getState());
        assertTrue(runningStateExecutorInvoked.get());
    }

    @Test
    public void executeDetectsCancelTest() {
        AtomicBoolean passed = new AtomicBoolean();
        IContext<?> context = new Context<>("", (ct)->"", new CancellationToken(), null, TestUtils.OPTIONS, new Status(""), null, null, 0, 0, null, null);
        IStateExecutor canceledExecutor = new StateExecutor(State.CANCELED) {
            @Override public <T> void execute(IContext<T> c) {
                passed.set(c == context);
            }
        };
        IStateExecutor runningExecutor = new StateExecutor(State.RUNNING);
        ScheduledStateExecutor executor = new ScheduledStateExecutor(canceledExecutor, runningExecutor);

        context.getCancellationToken().cancel();

        executor.execute(context);

        assertEquals(State.SCHEDULED, context.getStatus().getState());
        assertTrue(passed.get());
    }

    @Test
    public void executeDetectsCancelAndNotCancelableOptionTest() throws Exception {
        AtomicBoolean runningStateExecutorInvoked = new AtomicBoolean();
        IContext<?> context = new Context<>("", (ct)->"", new CancellationToken(), TestUtils.SCHEDULER, new Options(Arrays.asList(Option.NOT_CANCELABLE)), new Status(""), null, null, 0, 0, null, null);
        IStateExecutor canceledExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningExecutor = new StateExecutor(State.RUNNING) {
            @Override public <T> void execute(IContext<T> c) { runningStateExecutorInvoked.set(c==context); }
        };
        ScheduledStateExecutor executor = new ScheduledStateExecutor(canceledExecutor, runningExecutor);

        context.getCancellationToken().cancel();

        executor.execute(context);

        Thread.sleep(1000);

        assertEquals(State.SCHEDULED, context.getStatus().getState());
        assertTrue(runningStateExecutorInvoked.get());
    }

    @Test
    public void executeTwiceThrownExceptionTest() {
        AtomicBoolean runningStateExecutorInvoked = new AtomicBoolean();
        IContext<?> context = new Context<>("", (ct)->"", new CancellationToken(), TestUtils.SCHEDULER, TestUtils.OPTIONS, new Status(""), null, null, 0, 0, null, null);
        IStateExecutor canceledExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningExecutor = new StateExecutor(State.RUNNING) {
            @Override public <T> void execute(IContext<T> c) { runningStateExecutorInvoked.set(c==context); }
        };
        ScheduledStateExecutor executor = new ScheduledStateExecutor(canceledExecutor, runningExecutor);

        executor.execute(context);

        try {
            executor.execute(context);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("already"));
        }
    }
}
