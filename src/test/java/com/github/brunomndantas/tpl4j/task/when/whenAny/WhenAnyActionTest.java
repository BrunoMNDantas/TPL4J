package com.github.brunomndantas.tpl4j.task.when.whenAny;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAnyActionTest {

    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();



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

        taskA.getStatus().finishedEvent.await();

        WhenAnyAction<String> action = new WhenAnyAction<>(tasks);
        Task<?> result = action.run(new CancellationToken());

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().finishedEvent.hasFired());
    }

    @Test
    public void runWithFailTaskTest() throws Exception {
        Task<String> taskA = new Task<>(() -> { throw new Exception(); } );
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        taskA.getStatus().finishedEvent.await();

        WhenAnyAction<String> action = new WhenAnyAction<>(tasks);
        Task<?> result = action.run(new CancellationToken());

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().finishedEvent.hasFired());
    }

    @Test
    public void runWithCancelTaskTest() throws Exception {
        Task<String> taskA = new Task<>(() -> "A" );
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.cancel();
        taskA.start();
        taskB.start();

        taskA.getStatus().finishedEvent.await();

        WhenAnyAction<String> action = new WhenAnyAction<>(tasks);
        Task<?> result = action.run(new CancellationToken());

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().finishedEvent.hasFired());
    }

}
