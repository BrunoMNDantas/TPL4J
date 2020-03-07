package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.builder.ContextBuilder;
import com.github.brunomndantas.tpl4j.context.executor.state.IStateExecutor;
import com.github.brunomndantas.tpl4j.context.executor.state.StateExecutor;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.*;

public class WhenAllScheduledStateExecutorTest {
    
    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();
        WhenAllScheduledStateExecutor<String> executor = new WhenAllScheduledStateExecutor<>(null, null, tasks);
        assertSame(tasks, executor.getTasks());
    }

    @Test
    public void constructorTest() {
        Collection<Task<String>> tasks = new LinkedList<>();
        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);

        WhenAllScheduledStateExecutor<String> executor = new WhenAllScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, tasks);

        assertSame(canceledStateExecutor, executor.getCancelStateExecutor());
        assertSame(runningStateExecutor, executor.getRunningStateExecutor());
        assertSame(tasks, executor.getTasks());
    }

    @Test
    public void executeTest() throws Exception {
        Task<String> taskA = new Task<>((t) -> { Thread.sleep(3000); return "A"; });
        Task<String> taskB = new Task<>((t) -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        WhenAllScheduledStateExecutor<?> executor = new WhenAllScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, tasks);

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> tasks.stream().allMatch(t->t.getFinishedEvent().hasFired()),
                new CancellationToken(),
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        taskA.start();
        taskB.start();

        context.getStatus().getRunningEvent().await();
        assertEquals(State.RUNNING, context.getStatus().getState());
        assertTrue(taskA.getFinishedEvent().hasFired());
        assertTrue(taskB.getFinishedEvent().hasFired());
    }

    @Test
    public void executeWithEmptyTasksTest() throws Exception {
        Collection<Task<String>> tasks = new LinkedList<>();

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        WhenAllScheduledStateExecutor<?> executor = new WhenAllScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, tasks);

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> tasks.stream().allMatch(t->t.getFinishedEvent().hasFired()),
                new CancellationToken(),
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        context.getStatus().getRunningEvent().await();
        assertEquals(State.RUNNING, context.getStatus().getState());
    }

    @Test
    public void executeDetectsCancelTest() throws Exception {
        CancellationToken token = new CancellationToken();
        Task<String> taskA = new Task<>((t) -> { token.cancel(); Thread.sleep(3000); return "A"; });
        Task<String> taskB = new Task<>((t) -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        IStateExecutor canceledStateExecutor = new StateExecutor(State.CANCELED);
        IStateExecutor runningStateExecutor = new StateExecutor(State.RUNNING);
        WhenAllScheduledStateExecutor<?> executor = new WhenAllScheduledStateExecutor<>(canceledStateExecutor, runningStateExecutor, tasks);

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> null,
                token,
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        taskA.start();

        context.getStatus().getCancelledEvent().await();
        assertEquals(State.CANCELED, context.getStatus().getState());
    }

}

