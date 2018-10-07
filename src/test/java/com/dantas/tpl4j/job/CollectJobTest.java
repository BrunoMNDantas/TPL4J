package com.dantas.tpl4j.job;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.assertTrue;

public class CollectJobTest {

    private static final Consumer<Runnable> SCHEDULER = (r) -> new Thread(r).start();



    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test
    public void whenAllWithEmptyJobsTest() throws Exception {
        Collection<Job<Void>> jobs = new LinkedList<>();

        CollectJob<Void> collectJob = new CollectJob<>(jobs, SCHEDULER);

        long finishedTime = System.currentTimeMillis() + 100;

        collectJob.schedule();
        collectJob.getResult();

        assertTrue(finishedTime >= System.currentTimeMillis());
    }

    @Test
    public void whenAllWithNonEmptyJobsTest() throws Exception {
        Collection<Job<Void>> jobs = new LinkedList<>();

        Job<Void> job;
        for(int i=0; i<4; ++i) {
            job = new VoidJob(() -> sleep(3000), SCHEDULER);
            jobs.add(job);
        }

        CollectJob<Void> collectJob = new CollectJob<>(jobs, SCHEDULER);

        for(Job j : jobs)
            j.schedule();

        long finishedTime = System.currentTimeMillis() + 3000;

        collectJob.schedule();
        collectJob.getResult();

        assertTrue(finishedTime <= System.currentTimeMillis());
    }

    @Test(expected = RuntimeException.class)
    public void whenAllWithFailingJobTest() throws Exception {
        Collection<Job<Void>> jobs = new LinkedList<>();

        Job<Void> job;
        for(int i=0; i<4; ++i) {
            job = new VoidJob(() -> sleep(3000), SCHEDULER);

            jobs.add(job);

            job.schedule();
        }

        job = new VoidJob(() -> { throw new RuntimeException(); }, SCHEDULER);
        jobs.add(job);
        job.schedule();

        CollectJob<Void> collectJob = new CollectJob<>(jobs, SCHEDULER);
        collectJob.schedule();
        collectJob.getResult();
    }

}
