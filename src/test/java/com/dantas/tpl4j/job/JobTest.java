package com.dantas.tpl4j.job;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class JobTest {

    private static final Consumer<Runnable> SCHEDULER = (r) -> new Thread(r).start();



    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test
    public void createdStatusTest() {
        Job<?> job = new Job<>(() -> null, SCHEDULER);
        assertEquals(JobStatus.CREATED, job.getStatus());
    }

    @Test
    public void scheduledStatusTest() {
        Job<?> job = new Job<>(() -> null, (t) -> {});

        job.schedule();

        assertEquals(JobStatus.SCHEDULED, job.getStatus());
    }

    @Test
    public void runningStatusTest() {
        Job<?> job = new Job<>(() -> {
            sleep(2000);
            return null;
        }, SCHEDULER);

        job.schedule();

        sleep(1000);

        assertEquals(JobStatus.RUNNING, job.getStatus());
    }

    @Test
    public void cancelledStatusTest() throws Exception {
        Job<?> job = new Job<>(() -> "", (t) -> new Thread(() -> {
            sleep(2000);
            t.run();
        }).start());

        job.schedule();

        job.cancel();

        job.getResult();

        assertEquals(JobStatus.CANCELED, job.getStatus());
    }

    @Test
    public void failedStatusTest() {
        Job<?> job = new Job<>(() -> { throw new RuntimeException(); }, SCHEDULER);

        job.schedule();

        try {
            job.getResult();
        } catch (Exception e) {
            //Catching RuntimeException thrown during the action
        }

        assertEquals(JobStatus.FAILED, job.getStatus());
    }

    @Test
    public void succeededStatusTest() throws Exception {
        Job<?> job = new Job<>(() -> null, SCHEDULER);

        job.schedule();

        job.getResult();

        assertEquals(JobStatus.SUCCEEDED, job.getStatus());
    }

    @Test
    public void scheduledEventTest() {
        boolean[] executed = new boolean[1];

        Job<?> job = new Job<>(() -> null, SCHEDULER);

        job.events.scheduledEvent.addListener(() -> executed[0] = true);

        job.schedule();

        sleep(1000);

        assertTrue(executed[0]);
    }

    @Test
    public void runningEventTest() {
        boolean[] executed = new boolean[1];

        Job<?> job = new Job<>(() -> null, SCHEDULER);

        job.events.runningEvent.addListener(() -> executed[0] = true);

        job.schedule();

        sleep(1000);

        assertTrue(executed[0]);
    }

    @Test
    public void cancelledEventTest() {
        boolean[] executed = new boolean[1];

        Job<?> job = new Job<>(() -> null, SCHEDULER);

        job.events.cancelledEvent.addListener(() -> executed[0] = true);

        job.cancel();

        job.schedule();

        sleep(1000);

        assertTrue(executed[0]);
    }

    @Test
    public void failedEventTest() {
        boolean[] executed = new boolean[1];

        Job<?> job = new Job<>(() -> { throw new RuntimeException(); }, SCHEDULER);

        job.events.failedEvent.addListener(() -> executed[0] = true);

        job.schedule();

        sleep(1000);

        assertTrue(executed[0]);
    }

    @Test
    public void succeededEventTest() {
        boolean[] executed = new boolean[1];

        Job<?> job = new Job<>(() -> null, SCHEDULER);

        job.events.succeededEvent.addListener(() -> executed[0] = true);

        job.schedule();

        sleep(1000);

        assertTrue(executed[0]);
    }

    @Test
    public void finishedEventTest() {
        boolean[] executed = new boolean[1];
        Job<?> job = new Job<>(() -> null, SCHEDULER);
        job.events.finishedEvent.addListener(() -> executed[0] = true);
        job.schedule();
        sleep(1000);
        assertTrue(executed[0]);

        executed[0] = false;
        job = new Job<>(() -> { throw new RuntimeException(); }, SCHEDULER);
        job.events.finishedEvent.addListener(() -> executed[0] = true);
        job.schedule();
        sleep(1000);
        assertTrue(executed[0]);

        executed[0] = false;
        job = new Job<>(() -> null, SCHEDULER);
        job.events.finishedEvent.addListener(() -> executed[0] = true);
        job.cancel();
        job.schedule();
        sleep(1000);
        assertTrue(executed[0]);
    }

    @Test
    public void valuePropertyDuringExecutionTest() {
        String str = "";
        Job<String> job = new Job<>(() -> {
            sleep(2000);
            return str;
        }, SCHEDULER);

        job.schedule();

        sleep(1000);

        assertNull(job.getValue());
    }

    @Test
    public void valuePropertyAfterExecutionTest() throws Exception {
        String str = "";
        Job<String> job = new Job<>(() -> str, SCHEDULER);

        job.schedule();

        job.getResult();

        assertSame(str, job.getValue());
    }

    @Test
    public void exceptionPropertyDuringExecutionTest() {
        Exception exception = new RuntimeException();
        Job<String> job = new Job<>(() -> {
            sleep(2000);
            throw exception;
        }, SCHEDULER);

        job.schedule();

        sleep(1000);

        assertNull(job.getException());
    }

    @Test
    public void exceptionPropertyAfterExecutionTest() {
        Exception exception = new RuntimeException();
        Job<String> job = new Job<>(() -> { throw exception; }, SCHEDULER);

        job.schedule();

        try {
            job.getResult();
        } catch (Exception e) {
            //Catching RuntimeException throw during action
        }

        assertSame(exception, job.getException());
    }

    @Test
    public void defaultSchedulerTest() {
        boolean[] running = new boolean[Runtime.getRuntime().availableProcessors()*4];

        for(int i=0; i<running.length; ++i) {
            int idx = i;
            new Job<>(() -> {
                running[idx] = true;
                sleep(5000);
                return null;
            }, SCHEDULER).schedule();
        }

        sleep(1000);

        for(Boolean bool : running)
            assertTrue(bool);
    }

    @Test
    public void suppliedSchedulerTest() {
        boolean[] running = new boolean[8];

        ExecutorService pool = Executors.newFixedThreadPool(2);
        try {
            for(int i=0; i<running.length; ++i) {
                int idx = i;
                new Job<>(() -> {
                    running[idx] = true;
                    sleep(5000);
                    return null;
                }, pool::submit).schedule();
            }

            sleep(1000);

            int count = 0;
            for(boolean b : running)
                if(b)
                    count++;

            assertEquals(2, count);
        } finally {
            pool.shutdown();
        }
    }

}
