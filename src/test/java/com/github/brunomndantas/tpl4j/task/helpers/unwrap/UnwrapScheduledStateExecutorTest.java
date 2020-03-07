package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.builder.ContextBuilder;
import com.github.brunomndantas.tpl4j.context.builder.IContextBuilder;
import com.github.brunomndantas.tpl4j.context.executor.state.IStateExecutor;
import com.github.brunomndantas.tpl4j.context.executor.state.StateExecutor;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.*;

public class UnwrapScheduledStateExecutorTest {

    @Test
    public void getTaskTest() {
        Task<String> t = new Task<>(() -> null);
        Task<Task<String>> task = new Task<>((token) -> t);
        UnwrapScheduledStateExecutor<?> executor = new UnwrapScheduledStateExecutor<>(null, null, task);
        assertSame(task, executor.getTask());
    }

    @Test
    public void constructorTest() {
        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        Task<String> t = new Task<>(() -> null);
        Task<Task<String>> task = new Task<>((token) -> t);

        UnwrapScheduledStateExecutor<?> executor = new UnwrapScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, task);

        assertSame(canceledStateExecutor, executor.getCancelStateExecutor());
        assertSame(runningStateExecutor, executor.getRunningStateExecutor());
        assertSame(task, executor.getTask());
    }

    @Test
    public void executeTest() throws Exception {
        Task<String> innerTask = new Task<>((t) -> { Thread.sleep(3000); return "A"; });
        Task<Task<String>> outerTask = new Task<>((t) -> { Thread.sleep(3000); innerTask.start(); return innerTask; });

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        UnwrapScheduledStateExecutor<?> executor = new UnwrapScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, outerTask);

        IContextManager contextManager = new ContextManager();
        IContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> null,
                new CancellationToken(),
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        outerTask.start();

        context.getStatus().getRunningEvent().await();
        assertEquals(State.RUNNING, context.getStatus().getState());
        assertTrue(outerTask.getFinishedEvent().hasFired());
        assertTrue(innerTask.getFinishedEvent().hasFired());
    }

    @Test
    public void executeWithInnerNullTaskTest() throws Exception {
        Task<Task<String>> outerTask = new Task<>((t) -> { Thread.sleep(3000); return null; });

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        UnwrapScheduledStateExecutor<?> executor = new UnwrapScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, outerTask);

        IContextManager contextManager = new ContextManager();
        IContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> outerTask.getFinishedEvent().hasFired(),
                new CancellationToken(),
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        outerTask.start();

        context.getStatus().getRunningEvent().await();
        assertEquals(State.RUNNING, context.getStatus().getState());
        assertTrue(outerTask.getFinishedEvent().hasFired());
    }

    @Test
    public void executeDetectsCancelAfterOuterTaskFinishesTest() throws Exception {
        CancellationToken token = new CancellationToken();
        Task<Task<String>> outer = new Task<>((ct) -> { token.cancel(); return null; });

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        UnwrapScheduledStateExecutor<?> executor = new UnwrapScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, outer);

        IContextManager contextManager = new ContextManager();
        IContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> null,
                token,
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        outer.start();

        context.getStatus().getCancelledEvent().await();
        assertEquals(State.CANCELED, context.getStatus().getState());
    }

    @Test
    public void executeDetectsCancelAfterInnerTaskFinishesTest() throws Exception {
        CancellationToken token = new CancellationToken();
        Task<String> innerTask = new Task<>((ct) -> { token.cancel(); return "A"; });
        Task<Task<String>> outerTask = new Task<>((ct) -> { innerTask.start(); return innerTask; });

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        UnwrapScheduledStateExecutor<?> executor = new UnwrapScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, outerTask);

        IContextManager contextManager = new ContextManager();
        IContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> null,
                token,
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        outerTask.start();

        context.getStatus().getCancelledEvent().await();
        assertEquals(State.CANCELED, context.getStatus().getState());
    }

    @Test
    public void executeWhenOuterTaskFailsTest() throws Exception {
        Task<Task<String>> outerTask = new Task<>((IAction<Task<String>>) (t) -> { throw TestUtils.FAIL_RESULT; });

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        UnwrapScheduledStateExecutor<?> executor = new UnwrapScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, outerTask);

        IContextManager contextManager = new ContextManager();
        IContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> outerTask.getFinishedEvent().hasFired(),
                new CancellationToken(),
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        outerTask.start();

        context.getStatus().getRunningEvent().await();
        assertEquals(State.RUNNING, context.getStatus().getState());
        assertTrue(outerTask.getFinishedEvent().hasFired());
    }

}
