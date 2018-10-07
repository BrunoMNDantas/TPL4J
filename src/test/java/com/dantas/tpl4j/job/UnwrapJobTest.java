package com.dantas.tpl4j.job;

import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.*;

public class UnwrapJobTest {

    private static final Consumer<Runnable> SCHEDULER = (r) -> new Thread(r).start();



    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test
    public void unwrapWithNormalSchedulerTest() throws Exception {
        String str = "";

        boolean[] firstLevelRunning = new boolean[1];
        boolean[] secondLevelRunning = new boolean[1];
        Job<String> firstLevelJob = new Job<>(() -> {
            firstLevelRunning[0] = true;
            sleep(5000);

            firstLevelRunning[0] = false;

            return str;
        }, SCHEDULER);

        Job<Job<String>> secondLevelJob = new Job<>(() -> {
            secondLevelRunning[0] = true;
            sleep(5000);

            secondLevelRunning[0] = false;

            firstLevelJob.schedule();

            return firstLevelJob;
        }, SCHEDULER);

        Job<String> job = new UnwrapJob<>(secondLevelJob, SCHEDULER);

        secondLevelJob.schedule();
        job.schedule();

        sleep(1000);
        assertFalse(firstLevelRunning[0]);
        assertTrue(secondLevelRunning[0]);

        sleep(1000);
        assertFalse(firstLevelRunning[0]);
        assertTrue(secondLevelRunning[0]);
        assertSame(str, firstLevelJob.getResult());

        assertSame(str, job.getResult());
        assertFalse(firstLevelRunning[0]);
        assertFalse(secondLevelRunning[0]);
    }

    @Test
    public void unwrapWithDummySchedulerTest() throws Exception {
        String str = "";

        boolean[] firstLevelRunning = new boolean[1];
        boolean[] secondLevelRunning = new boolean[1];
        Job<String> firstLevelJob = new Job<>(() -> {
            firstLevelRunning[0] = true;
            sleep(5000);

            firstLevelRunning[0] = false;

            return str;
        }, SCHEDULER);

        Job<Job<String>> secondLevelJob = new Job<>(() -> {
            secondLevelRunning[0] = true;
            sleep(5000);

            secondLevelRunning[0] = false;

            firstLevelJob.schedule();

            return firstLevelJob;
        }, SCHEDULER);

        Job<String> job = new UnwrapJob<>(secondLevelJob, (r) -> {});

        secondLevelJob.schedule();
        job.schedule();

        sleep(1000);
        assertFalse(firstLevelRunning[0]);
        assertTrue(secondLevelRunning[0]);

        sleep(1000);
        assertFalse(firstLevelRunning[0]);
        assertTrue(secondLevelRunning[0]);
        assertSame(str, firstLevelJob.getResult());

        sleep(6000);
        assertFalse(job.events.runningEvent.hasFired());
        assertFalse(firstLevelRunning[0]);
        assertFalse(secondLevelRunning[0]);
    }

    @Test(expected = RuntimeException.class)
    public void unwrapWithFailJobTest() throws Exception {
        Job<String> innerJob = new Job<>(() -> { throw new RuntimeException(); }, SCHEDULER);

        Job<Job<String>> outerJob = new Job<>(() -> {
            innerJob.schedule();
            return innerJob;
        }, SCHEDULER);

        Job<String> job = new UnwrapJob<>(outerJob, SCHEDULER);

        outerJob.schedule();
        job.schedule();

        job.getResult();
    }

}
