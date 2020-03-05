package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.builder.ContextBuilder;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class UnwrapContextExecutorTest {

    @Test
    public void getTaskTest() {
        Task<String> t = new Task<>(() -> null);
        Task<Task<String>> task = new Task<>((token) -> t);
        UnwrapContextExecutor<?> executor = new UnwrapContextExecutor<>(null, task);
        assertSame(task, executor.getTask());
    }

    @Test
    public void constructorTest() {
        ContextManager contextManager = new ContextManager();
        Task<String> t = new Task<>(() -> null);
        Task<Task<String>> task = new Task<>((token) -> t);

        UnwrapContextExecutor<?> executor = new UnwrapContextExecutor<>(contextManager, task);

        assertSame(contextManager, executor.getContextManager());
        assertSame(task, executor.getTask());
    }

    @Test
    public void scheduleTest() throws Exception {
        Task<String> taskA = new Task<>((t) -> { Thread.sleep(3000); return "A"; }, TestUtils.SCHEDULER);
        Task<Task<String>> taskB = new Task<>((t) -> { Thread.sleep(3000); taskA.start(); return taskA; }, TestUtils.SCHEDULER);

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        UnwrapContextExecutor<?> executor = new UnwrapContextExecutor<>(contextManager, taskB);

        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> taskB.getFinishedEvent().hasFired() && taskA.getFinishedEvent().hasFired(),
                new CancellationToken(),
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);
        Thread.sleep(1000);
        taskB.start();

        context.getStatus().getFinishedEvent().await();
        assertTrue(context.getResultValue());

        Task<Task<String>> taskC = new Task<>((t) -> { Thread.sleep(3000); return null; }, TestUtils.SCHEDULER);

        executor = new UnwrapContextExecutor<>(contextManager, taskC);

        context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> taskC.getFinishedEvent().hasFired(),
                new CancellationToken(),
                TestUtils.SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);
        Thread.sleep(1000);
        taskC.start();

        context.getStatus().getFinishedEvent().await();
        assertTrue(context.getResultValue());
    }

}
