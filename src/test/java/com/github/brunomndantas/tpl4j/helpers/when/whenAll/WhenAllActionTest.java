package com.github.brunomndantas.tpl4j.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAllActionTest {

    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();



    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);

        assertSame(tasks, action.getTasks());
    }

    @Test
    public void runSuccessTest() throws Exception {
        Task<String> taskA = new Task<>("", (t) -> "A", new CancellationToken(), SCHEDULER);
        Task<String> taskB = new Task<>("", (t) -> "B", new CancellationToken(), SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        taskA.getStatus().finishedEvent.await();
        taskB.getStatus().finishedEvent.await();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);
        Collection<String> results = action.run(new CancellationToken());

        assertEquals(tasks.size(), results.size());
        assertTrue(results.contains(taskA.getValue()));
        assertTrue(results.contains(taskB.getValue()));
    }

    @Test
    public void runFailTest() throws Exception {
        Exception exceptionB = new Exception();
        Exception exceptionC = new Exception();
        Task<String> taskA = new Task<>("", (t) -> "A", new CancellationToken(), SCHEDULER);
        Task<String> taskB = new Task<>("", (IAction<String>) (t) -> { throw exceptionB; }, new CancellationToken(), SCHEDULER);
        Task<String> taskC = new Task<>("", (IAction<String>) (t) -> { throw exceptionC; }, new CancellationToken(), SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB, taskC);

        taskA.start();
        taskB.start();
        taskC.start();

        taskA.getStatus().finishedEvent.await();
        taskB.getStatus().finishedEvent.await();
        taskC.getStatus().finishedEvent.await();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);
        try{
            action.run(new CancellationToken());
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(exceptionB, e.getCause());
            assertEquals(1, e.getSuppressed().length);
            assertEquals(exceptionC, e.getSuppressed()[0]);
        }
    }

    @Test(expected = CancelledException.class)
    public void runCancelTest() throws Exception {
        Task<String> taskA = new Task<>("", (t) -> "A", CANCELLATION_TOKEN, SCHEDULER);
        Task<String> taskB = new Task<>("", (t) -> { t.cancel(); t.abortIfCancelRequested(); return null; }, CANCELLATION_TOKEN, SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        taskA.getStatus().finishedEvent.await();
        taskB.getStatus().finishedEvent.await();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);

        action.run(new CancellationToken());
    }

    @Test(expected = CancelledException.class)
    public void runCancelTokenTest() throws Exception {
        Task<String> taskA = new Task<>("", (t) -> "A", CANCELLATION_TOKEN, SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA);

        taskA.start();

        taskA.getStatus().finishedEvent.await();

        CancellationToken cancellationToken = new CancellationToken();

        cancellationToken.cancel();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);

        action.run(cancellationToken);
    }

}
