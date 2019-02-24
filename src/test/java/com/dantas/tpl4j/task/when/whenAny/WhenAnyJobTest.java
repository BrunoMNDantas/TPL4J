package com.dantas.tpl4j.task.when.whenAny;

import com.dantas.tpl4j.task.Task;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAnyJobTest {

    private static final ExecutorService POOL = Executors.newSingleThreadExecutor();
    private static final Consumer<Runnable> SCHEDULER = POOL::submit;



    @AfterClass
    public static void close() {
        POOL.shutdown();
    }



    @Test
    public void getJobsTest() {
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAnyJob<String> job = new WhenAnyJob<>("", SCHEDULER, new LinkedList<>(), tasks);

        assertSame(tasks, job.getTasks());
    }

    @Test
    public void runWithSuccessTaskTest() throws Exception {
        WhenAnyJob<String> job = new WhenAnyJob<>("", SCHEDULER, new LinkedList<>(), new LinkedList<>());

        job.schedule();

        assertNull(job.getResult());

        Task<String> taskA = new Task<>(() -> "A");
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        job = new WhenAnyJob<>("", SCHEDULER, new LinkedList<>(), tasks);

        job.schedule();

        Task<String> result = job.getResult();

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().finishedEvent.hasFired());
    }

    @Test
    public void runWithFailTaskTest() throws Exception {
        Task<String> taskA = new Task<>(() -> { throw new Exception(); });
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        WhenAnyJob<String> job = new WhenAnyJob<>("", SCHEDULER, new LinkedList<>(), tasks);

        job.schedule();

        Task<String> result = job.getResult();

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().finishedEvent.hasFired());
    }

    @Test
    public void runWthCancelTaskTest() throws Exception {
        Task<String> taskA = new Task<>((t) -> { if(true) throw t.abort(); return "A"; });
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; });
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        WhenAnyJob<String> job = new WhenAnyJob<>("", SCHEDULER, new LinkedList<>(), tasks);

        job.schedule();

        Task<String> result = job.getResult();

        assertSame(taskA, result);
        assertFalse(taskB.getStatus().finishedEvent.hasFired());
    }

    @Test
    public void lateFinishRegisterOnSchedulerTest() throws InterruptedException {
        int[] counter = new int[1];
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Consumer<Runnable> scheduler = (action) -> { counter[0]++; pool.submit(action); };

        Task<String> taskA = new Task<>(() -> "A", scheduler);
        Task<String> taskB = new Task<>(() -> { Thread.sleep(3000); return "B"; }, scheduler);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        WhenAnyJob<String> job = new WhenAnyJob<>("", scheduler, new LinkedList<>(), tasks);

        job.schedule();

        job.getStatus().finishedEvent.await();

        assertEquals(5, counter[0]);
        //Why 5?
        //taskA registers the action
        //taskB registers the action
        //job registers the action
        //job register an action for callback when taskA finishes
        //job register an action for callback when taskB finishes

        pool.shutdown();
    }
    
}
