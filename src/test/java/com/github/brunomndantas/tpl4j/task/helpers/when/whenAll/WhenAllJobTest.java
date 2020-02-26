package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAllJobTest {

    private static final ExecutorService POOL = Executors.newSingleThreadExecutor();
    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = POOL::submit;



    @AfterClass
    public static void close() {
        POOL.shutdown();
    }



    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAllJob<String> job = new WhenAllJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), tasks);

        assertSame(tasks, job.getTasks());
    }

     @Test
    public void runSuccessTest() throws Exception {
         WhenAllJob<String> job = new WhenAllJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), new LinkedList<>());

         job.schedule();

         assertTrue(job.getResult().isEmpty());

         Task<String> taskA = new Task<>("", (t) -> { Thread.sleep(3000); return "A"; }, new CancellationToken(), SCHEDULER);
         Task<String> taskB = new Task<>("", (t) -> { Thread.sleep(3000); return "B"; }, new CancellationToken(), SCHEDULER);
         Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

         taskA.start();
         taskB.start();

         job = new WhenAllJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), tasks);

         job.schedule();

         Collection<String> results = job.getResult();

         assertEquals(tasks.size(), results.size());
         assertTrue(results.contains(taskA.getValue()));
         assertTrue(results.contains(taskB.getValue()));
     }

    @Test
    public void runFailTest() {
        Exception exception = new Exception();
        Task<String> taskA = new Task<>("", (t) -> "A", new CancellationToken(), SCHEDULER);
        Task<String> taskB = new Task<>("", (IAction<String>) (t) -> { Thread.sleep(3000); throw exception; }, new CancellationToken(), SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        WhenAllJob<String> job = new WhenAllJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), tasks);

        job.schedule();

        try{
            job.getResult();
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(exception, e.getCause());
        }
    }

    @Test
    public void runCancelTest() throws InterruptedException {
        Task<String> taskA = new Task<>("", (t) -> { Thread.sleep(3000); return "A"; }, CANCELLATION_TOKEN, SCHEDULER);
        Task<String> taskB = new Task<>("", (t) -> { Thread.sleep(3000); return "B"; }, CANCELLATION_TOKEN, SCHEDULER);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        WhenAllJob<String> job = new WhenAllJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), tasks);
        job.cancel();

        job.schedule();

        job.getStatus().getCancelledEvent().await();
    }

    @Test
    public void lateFinishRegisterOnSchedulerTest() throws InterruptedException {
        int[] counter = new int[1];
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Consumer<Runnable> scheduler = (action) -> { counter[0]++; pool.submit(action); };

        Task<String> taskA = new Task<>("", (t) -> { Thread.sleep(3000); return "A"; }, CANCELLATION_TOKEN, scheduler);
        Task<String> taskB = new Task<>("", (t) -> { Thread.sleep(3000); return "B"; }, CANCELLATION_TOKEN, scheduler);
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        taskA.start();
        taskB.start();

        WhenAllJob<String> job = new WhenAllJob<>("", CANCELLATION_TOKEN, scheduler, new LinkedList<>(), tasks);

        job.schedule();

        job.getStatus().getFinishedEvent().await();

        assertEquals(5, counter[0]);
        //Why 5?
        //jobA registers the action
        //jobB registers the action
        //job registers the action
        //job register an action for callback when jobA finishes
        //job register an action for callback when jobB finishes

        pool.shutdown();
    }

}
