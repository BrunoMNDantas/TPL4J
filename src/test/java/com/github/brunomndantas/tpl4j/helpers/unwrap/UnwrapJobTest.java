package com.github.brunomndantas.tpl4j.helpers.unwrap;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.context.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.job.Job;
import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class UnwrapJobTest {

    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();



    @Test
    public void getJobTest() {
        Task<String> task = new Task<>(() -> null);
        Job<Task<String>> job = new Job<>("", (token) -> task, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), job);

        assertSame(job, unwrapJob.getJob());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String result = "";
        Task<String> task = new Task<>(() -> result);
        Job<Task<String>> job = new Job<>("", (token) -> task, new CancellationToken(), SCHEDULER, new LinkedList<>());

        task.start();
        task.getStatus().finishedEvent.await();
        job.schedule();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), job);

        unwrapJob.schedule();
        job.getStatus().finishedEvent.await();

        assertSame(result, unwrapJob.getResult());
    }

    @Test
    public void runFailJobTest() throws InterruptedException {
        Exception result = new Exception();
        Job<Task<String>> job = new Job<>("", (token) -> { throw result; }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        job.schedule();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), job);
        unwrapJob.schedule();

        try {
            unwrapJob.getResult();
            fail("Exception should be thrown!");
        }catch(Exception e) {
            assertSame(result, e);
        }
    }

    @Test
    public void runFailTaskTest() throws InterruptedException {
        Exception result = new Exception();
        Task<String> task = new Task<>(() -> { throw result; });
        Job<Task<String>> job = new Job<>("", (token) -> task, new CancellationToken(), SCHEDULER, new LinkedList<>());

        task.start();
        task.getStatus().finishedEvent.await();
        job.schedule();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), job);
        unwrapJob.schedule();

        try {
            unwrapJob.getResult();
            fail("Exception should be thrown!");
        }catch(Exception e) {
            assertSame(result, e);
        }
    }

    @Test
    public void runCancelJobTest() throws Exception {
        Exception result = new Exception();
        Job<Task<String>> job = new Job<>("", (token) -> { throw result; }, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());

        job.cancel();
        job.schedule();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), job);

        unwrapJob.schedule();
        unwrapJob.getStatus().cancelledEvent.await();
    }

    @Test
    public void runCancelTaskTest() throws Exception {
        String result = "";
        Task<String> task = new Task<>(() -> result);
        Job<Task<String>> job = new Job<>("", (token) -> task, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());

        task.cancel();
        task.start();
        task.getStatus().finishedEvent.await();
        job.schedule();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), job);
        unwrapJob.schedule();

        unwrapJob.getStatus().cancelledEvent.await();
    }

    @Test
    public void lateFinishRegisterOnSchedulerTest() throws InterruptedException {
        int[] counter = new int[1];
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Consumer<Runnable> scheduler = (action) -> { counter[0]++; pool.submit(action); };

        Task<String> task = new Task<>(() -> "", scheduler);
        Job<Task<String>> job = new Job<>("", (token) -> task, CANCELLATION_TOKEN, scheduler, new LinkedList<>());

        task.start();
        job.schedule();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", CANCELLATION_TOKEN, scheduler, new LinkedList<>(), job);

        unwrapJob.schedule();
        unwrapJob.getStatus().finishedEvent.await();

        assertEquals(5, counter[0]);
        //Why 5?
        //task registers the action
        //job registers the action
        //unwrapJob registers the action
        //unwrapJob register and action for callback when job finishes
        //unwrapJob register an action for callback when task finishes

        pool.shutdown();
    }

}
