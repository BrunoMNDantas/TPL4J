package com.github.brunomndantas.tpl4j.core.scheduler;

import org.junit.Test;

import static org.junit.Assert.*;

public class PoolSchedulerTest {

    @Test
    public void constructorsTest() {
        String id = "Id";
        int numberOfThreads = 1;
        PoolScheduler scheduler;

        scheduler = new PoolScheduler(id, numberOfThreads);
        assertEquals(id, scheduler.getId());
        assertEquals(numberOfThreads, scheduler.getNumberOfThreads());
        scheduler.close();

        scheduler = new PoolScheduler(id);
        assertEquals(id, scheduler.getId());
        assertEquals(Runtime.getRuntime().availableProcessors(), scheduler.getNumberOfThreads());
        scheduler.close();

        scheduler = new PoolScheduler(numberOfThreads);
        assertNotNull(scheduler.getId());
        assertEquals(numberOfThreads, numberOfThreads);
        scheduler.close();

        scheduler = new PoolScheduler();
        assertNotNull(scheduler.getId());
        assertEquals(Runtime.getRuntime().availableProcessors(), scheduler.getNumberOfThreads());
        scheduler.close();
    }

    @Test
    public void getNumberOfThreadsTest() {
        int numberOfThreads = 1;
        PoolScheduler scheduler = new PoolScheduler("", numberOfThreads);
        assertEquals(numberOfThreads, scheduler.getNumberOfThreads());
        scheduler.close();
    }

    @Test
    public void getIdTest() {
        String id = "ID";
        PoolScheduler scheduler = new PoolScheduler(id, 1);
        assertSame(id, scheduler.getId());
        scheduler.close();
    }

    @Test
    public void scheduleTest() throws Exception {
        boolean[] passedA = new boolean[1];
        boolean[] passedB = new boolean[1];
        long[] threadA = new long[1];
        long[] threadB = new long[1];

        PoolScheduler scheduler = new PoolScheduler();

        scheduler.schedule(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
            passedA[0] = true;
            threadA[0] = Thread.currentThread().getId();
        });

        scheduler.schedule(() -> {
            try { Thread.sleep(1000); } catch (InterruptedException e) { }
            passedB[0] = true;
            threadB[0] = Thread.currentThread().getId();
        });

        Thread.sleep(2000);

        assertTrue(passedA[0]);
        assertTrue(passedB[0]);
        assertNotEquals(threadA[0], threadB[0]);

        scheduler.close();
    }

    @Test
    public void closeTest() {
        PoolScheduler scheduler = new PoolScheduler();
        scheduler.close();
    }

}

