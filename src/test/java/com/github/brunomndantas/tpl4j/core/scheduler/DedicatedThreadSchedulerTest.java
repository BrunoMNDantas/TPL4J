package com.github.brunomndantas.tpl4j.core.scheduler;

import org.junit.Test;

import static org.junit.Assert.*;

public class DedicatedThreadSchedulerTest {

    @Test
    public void constructorsTest() {
        String id = "";
        DedicatedThreadScheduler scheduler = new DedicatedThreadScheduler(id);
        assertSame(id, scheduler.getId());
        scheduler.close();

        scheduler = new DedicatedThreadScheduler();
        assertNotNull(scheduler.getId());
        assertFalse(scheduler.getId().isEmpty());
        scheduler.close();
    }

    @Test
    public void getIdTest() {
        String id = "";
        DedicatedThreadScheduler scheduler = new DedicatedThreadScheduler(id);
        assertSame(id, scheduler.getId());
        scheduler.close();
    }

    @Test
    public void scheduleTest() throws Exception {
        boolean[] passedA = new boolean[1];
        boolean[] passedB = new boolean[1];
        long[] threadA = new long[1];
        long[] threadB = new long[1];

        DedicatedThreadScheduler scheduler = new DedicatedThreadScheduler();

        scheduler.schedule(() -> {
            passedA[0] = true;
            threadA[0] = Thread.currentThread().getId();
        });

        scheduler.schedule(() -> {
            passedB[0] = true;
            threadB[0] = Thread.currentThread().getId();
        });

        Thread.sleep(1000);

        assertTrue(passedA[0]);
        assertTrue(passedB[0]);
        assertNotEquals(threadA[0], threadB[0]);

        scheduler.close();
    }

    @Test
    public void closeTest() {
        DedicatedThreadScheduler scheduler = new DedicatedThreadScheduler();
        scheduler.close();
    }

}
