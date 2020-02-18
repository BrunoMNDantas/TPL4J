package com.github.brunomndantas.tpl4j.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.task.context.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.job.Job;
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
    public void getJobsTest() {
        Collection<Job<String>> jobs = new LinkedList<>();

        WhenAllJob<String> job = new WhenAllJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), jobs);

        assertSame(jobs, job.getJobs());
    }

     @Test
    public void runSuccessTest() throws Exception {
         WhenAllJob<String> job = new WhenAllJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), new LinkedList<>());

         job.schedule();

         assertTrue(job.getResult().isEmpty());

         Job<String> jobA = new Job<>("", (t) -> { Thread.sleep(3000); return "A"; }, new CancellationToken(), SCHEDULER, new LinkedList<>());
         Job<String> jobB = new Job<>("", (t) -> { Thread.sleep(3000); return "B"; }, new CancellationToken(), SCHEDULER, new LinkedList<>());
         Collection<Job<String>> jobs = Arrays.asList(jobA, jobB);

         jobA.schedule();
         jobB.schedule();

         job = new WhenAllJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), jobs);

         job.schedule();

         Collection<String> results = job.getResult();

         assertEquals(jobs.size(), results.size());
         assertTrue(results.contains(jobA.getValue()));
         assertTrue(results.contains(jobB.getValue()));
     }

    @Test
    public void runFailTest() {
        Exception exception = new Exception();
        Job<String> jobA = new Job<>("", (t) -> "A", new CancellationToken(), SCHEDULER, new LinkedList<>());
        Job<String> jobB = new Job<>("", (t) -> { Thread.sleep(3000); throw exception; }, new CancellationToken(), SCHEDULER, new LinkedList<>());
        Collection<Job<String>> jobs = Arrays.asList(jobA, jobB);

        jobA.schedule();
        jobB.schedule();

        WhenAllJob<String> job = new WhenAllJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), jobs);

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
        Job<String> jobA = new Job<>("", (t) -> { Thread.sleep(3000); return "A"; }, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());
        Job<String> jobB = new Job<>("", (t) -> { Thread.sleep(3000); return "B"; }, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());
        Collection<Job<String>> jobs = Arrays.asList(jobA, jobB);

        jobA.schedule();
        jobB.schedule();

        WhenAllJob<String> job = new WhenAllJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), jobs);
        job.cancel();

        job.schedule();

        job.getStatus().cancelledEvent.await();
    }

    @Test
    public void lateFinishRegisterOnSchedulerTest() throws InterruptedException {
        int[] counter = new int[1];
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Consumer<Runnable> scheduler = (action) -> { counter[0]++; pool.submit(action); };

        Job<String> jobA = new Job<>("", (t) -> { Thread.sleep(3000); return "A"; }, CANCELLATION_TOKEN, scheduler, new LinkedList<>());
        Job<String> jobB = new Job<>("", (t) -> { Thread.sleep(3000); return "B"; }, CANCELLATION_TOKEN, scheduler, new LinkedList<>());
        Collection<Job<String>> jobs = Arrays.asList(jobA, jobB);

        jobA.schedule();
        jobB.schedule();

        WhenAllJob<String> job = new WhenAllJob<>("", CANCELLATION_TOKEN, scheduler, new LinkedList<>(), jobs);

        job.schedule();

        job.getStatus().finishedEvent.await();

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
