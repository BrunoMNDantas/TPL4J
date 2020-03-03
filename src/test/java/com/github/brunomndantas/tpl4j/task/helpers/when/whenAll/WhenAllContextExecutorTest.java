package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.builder.ContextBuilder;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.SingleThreadScheduler;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class WhenAllContextExecutorTest {

    private static final IScheduler SCHEDULER = new SingleThreadScheduler();



    @AfterClass
    public static void close() {
        SCHEDULER.close();
    }



    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();
        WhenAllContextExecutor<String> executor = new WhenAllContextExecutor<>(null, tasks);
        assertSame(tasks, executor.getTasks());
    }

    @Test
    public void constructorTest() {
        ContextManager contextManager = new ContextManager();
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAllContextExecutor<String> executor = new WhenAllContextExecutor<>(contextManager, tasks);

        assertSame(contextManager, executor.getContextManager());
        assertSame(tasks, executor.getTasks());
    }

    @Test
    public void scheduleTest() throws Exception {
        Task<String> taskA = new Task<>((t) -> { Thread.sleep(3000); return "A"; }, SCHEDULER);
        Task<String> taskB = new Task<>((t) -> { Thread.sleep(3000); return "B"; }, SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        WhenAllContextExecutor<?> executor = new WhenAllContextExecutor<>(contextManager, tasks);

        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> tasks.stream().allMatch(t->t.getFinishedEvent().hasFired()),
                new CancellationToken(),
                SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);

        taskA.start();
        taskB.start();

        context.getStatus().getFinishedEvent().await();
        assertTrue(context.getResultValue());
    }

    @Test
    public void scheduleWithEmptyTasksTest() throws Exception {
        Collection<Task<String>> tasks = new LinkedList<>();

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        WhenAllContextExecutor<?> executor = new WhenAllContextExecutor<>(contextManager, tasks);

        IContext<Boolean> context = contextBuilder.build(
                UUID.randomUUID().toString(),
                (ct) -> tasks.stream().allMatch(t->t.getFinishedEvent().hasFired()),
                new CancellationToken(),
                SCHEDULER,
                new Options(new LinkedList<>()));

        executor.execute(context);
        context.getStatus().getFinishedEvent().await();
        assertTrue(context.getResultValue());
    }

}
