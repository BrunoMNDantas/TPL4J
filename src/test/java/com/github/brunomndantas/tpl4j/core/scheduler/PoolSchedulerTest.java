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
        assertEquals(numberOfThreads, Runtime.getRuntime().availableProcessors());
        scheduler.close();

        scheduler = new PoolScheduler(numberOfThreads);
        assertNotNull(scheduler.getId());
        assertEquals(numberOfThreads, numberOfThreads);
        scheduler.close();

        scheduler = new PoolScheduler();
        assertNotNull(scheduler.getId());
        assertEquals(numberOfThreads, Runtime.getRuntime().availableProcessors());
        scheduler.close();
    }

    @Test
    public void getNumberOfThreads() {
        int numberOfThreads = 1;
        PoolScheduler scheduler = new PoolScheduler("", numberOfThreads);
        assertEquals(numberOfThreads, scheduler.getNumberOfThreads());
        scheduler.close();
    }

    @Test
    public void getIdTest() {
        String id = "ID";
        PoolScheduler scheduler = new PoolScheduler(id, 0);
        assertSame(id, scheduler.getId());
        scheduler.close();
    }

    @Test
    public void scheduleTest() throws Exception {
        boolean[] passed = new boolean[1];
        PoolScheduler scheduler = new PoolScheduler();

        scheduler.schedule(() -> passed[0] = true);

        Thread.sleep(1000);

        scheduler.close();
    }

    @Test
    public void closeTest() {
        PoolScheduler scheduler = new PoolScheduler();
        scheduler.close();
    }

}

