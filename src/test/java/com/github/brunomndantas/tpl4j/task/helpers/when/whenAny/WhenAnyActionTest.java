package com.github.brunomndantas.tpl4j.task.helpers.when.whenAny;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class WhenAnyActionTest {

    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAnyAction<String> action = new WhenAnyAction<>(tasks);

        assertSame(tasks, action.getTasks());
    }

    @Test
    public void runEmptyTasksTest() throws Exception {
        WhenAnyAction<String> action = new WhenAnyAction<>(new LinkedList<>());
        Task<?> result = action.run(new CancellationToken());

        assertNull(result);
    }

    @Test
    public void runWithSuccessTaskTest() throws Exception {
        Task<String> taskA = new Task<>(() -> "A");
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        taskA.getStatus().getFinishedEvent().await();

        WhenAnyAction<String> action = new WhenAnyAction<>(tasks);
        Task<?> result = action.run(new CancellationToken());

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().getFinishedEvent().hasFired());
    }

    @Test
    public void runWithFailTaskTest() throws Exception {
        Task<String> taskA = new Task<>(() -> { throw new Exception(); } );
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        taskA.getStatus().getFinishedEvent().await();

        WhenAnyAction<String> action = new WhenAnyAction<>(tasks);
        Task<?> result = action.run(new CancellationToken());

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().getFinishedEvent().hasFired());
    }

    @Test
    public void runWithCancelTaskTest() throws Exception {
        Task<String> taskA = new Task<>(() -> "A" );
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.cancel();
        taskA.start();
        taskB.start();

        taskA.getStatus().getFinishedEvent().await();

        WhenAnyAction<String> action = new WhenAnyAction<>(tasks);
        Task<?> result = action.run(new CancellationToken());

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().getFinishedEvent().hasFired());
    }

}
