package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class WhenAllActionTest {

    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);

        assertSame(tasks, action.getTasks());
    }

    @Test
    public void runSuccessTest() throws Exception {
        Task<String> taskA = new Task<>((t) -> "A", new CancellationToken(), TestUtils.SCHEDULER);
        Task<String> taskB = new Task<>((t) -> "B", new CancellationToken(), TestUtils.SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        taskA.getFinishedEvent().await();
        taskB.getFinishedEvent().await();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);
        Collection<String> results = action.run(new CancellationToken());

        assertEquals(tasks.size(), results.size());
        assertTrue(results.contains(taskA.getResultValue()));
        assertTrue(results.contains(taskB.getResultValue()));
    }

    @Test
    public void runFailTest() throws Exception {
        Exception exceptionB = new Exception();
        Exception exceptionC = new Exception();
        Task<String> taskA = new Task<>((t) -> "A", new CancellationToken(), TestUtils.SCHEDULER);
        Task<String> taskB = new Task<>((IAction<String>) (t) -> { throw exceptionB; }, new CancellationToken(), TestUtils.SCHEDULER);
        Task<String> taskC = new Task<>((IAction<String>) (t) -> { throw exceptionC; }, new CancellationToken(), TestUtils.SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB, taskC);

        taskA.start();
        taskB.start();
        taskC.start();

        taskA.getFinishedEvent().await();
        taskB.getFinishedEvent().await();
        taskC.getFinishedEvent().await();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);
        try{
            action.run(new CancellationToken());
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(exceptionB, e);
            assertEquals(1, e.getSuppressed().length);
            assertEquals(exceptionC, e.getSuppressed()[0]);
        }
    }

    @Test(expected = CancelledException.class)
    public void runCancelTest() throws Exception {
        Task<String> taskA = new Task<>((t) -> "A", new CancellationToken(), TestUtils.SCHEDULER);
        Task<String> taskB = new Task<>((t) -> { t.cancel(); t.abortIfCancelRequested(); return null; }, new CancellationToken(), TestUtils.SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        taskA.getFinishedEvent().await();
        taskB.getFinishedEvent().await();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);

        action.run(new CancellationToken());
    }

    @Test(expected = CancelledException.class)
    public void runCancelTokenTest() throws Exception {
        Task<String> taskA = new Task<>((t) -> "A", new CancellationToken(), TestUtils.SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA);

        taskA.start();

        taskA.getFinishedEvent().await();

        CancellationToken cancellationToken = new CancellationToken();

        cancellationToken.cancel();

        WhenAllAction<String> action = new WhenAllAction<>(tasks);

        action.run(cancellationToken);
    }

}
