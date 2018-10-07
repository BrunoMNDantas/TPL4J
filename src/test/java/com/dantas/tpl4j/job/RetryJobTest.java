package com.dantas.tpl4j.job;

import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RetryJobTest {

    private static final Consumer<Runnable> SCHEDULER = (r) -> new Thread(r).start();



    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test(expected = RuntimeException.class)
    public void oneRetryTest() throws Exception {
        int[] counter = new int[1];

        Job<Void> job = new VoidJob(() -> {
            counter[0]++;
            throw new RuntimeException();
        }, SCHEDULER);

        Job<Void> retryJob = new RetryJob<>(job, SCHEDULER);

        job.schedule();
        retryJob.schedule();

        try {
            retryJob.getResult();
        } catch (Exception e) {
            assertEquals(2, counter[0]);
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void multipleRetriesTest() throws Exception {
        int[] counter = new int[1];

        Job<Void> job = new VoidJob(() -> {
            counter[0]++;
            throw new RuntimeException();
        }, SCHEDULER);

        Job<Void> retryJob = new RetryJob<>(job, 2, SCHEDULER);

        job.schedule();
        retryJob.schedule();

        try {
            retryJob.getResult();
        } catch (Exception e) {
            assertEquals(3, counter[0]);
            throw e;
        }
    }

    @Test
    public void cancelRetryForeverJobTest() throws Exception {
        int[] counter = new int[1];

        Job<Void> job = new VoidJob(() -> {
            counter[0]++;
            throw new RuntimeException();
        }, SCHEDULER);

        Job<Void> retryJob = new RetryJob<>(job, RetryJob.RETRY_UNTIL_SUCCESS, SCHEDULER);

        job.schedule();
        retryJob.schedule();

        sleep(2000);

        job.cancel();

        retryJob.getResult();

        assertEquals(JobStatus.CANCELED, retryJob.getStatus());
        assertTrue(retryJob.events.cancelledEvent.hasFired());
        assertTrue(counter[0] > 1);
    }

    @Test(expected = RuntimeException.class)
    public void retrySupplierTest() throws Exception {
        int[] counter = new int[1];

        Job<Void> job = new VoidJob(() -> {
            counter[0]++;
            throw new RuntimeException();
        }, SCHEDULER);

        Job<?> retryJob = new RetryJob<>(job, () -> counter[0] <= 3, SCHEDULER);

        job.schedule();
        retryJob.schedule();

        try {
            retryJob.getResult();
        } catch (Exception e) {
            assertEquals(4, counter[0]);
            throw e;
        }
    }

}
