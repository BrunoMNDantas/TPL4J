package com.github.brunomndantas.tpl4j.core.scheduler;

import org.junit.Test;

import static org.junit.Assert.*;

public class SingleThreadSchedulerTest {

    @Test
    public void constructorsTest() {
        String id = "Id";
        SingleThreadScheduler schedulerA = new SingleThreadScheduler(id);
        SingleThreadScheduler schedulerB = new SingleThreadScheduler();
        SingleThreadScheduler schedulerC = new SingleThreadScheduler();

        assertEquals(1, schedulerA.getNumberOfThreads());
        assertEquals(1, schedulerB.getNumberOfThreads());
        assertEquals(1, schedulerC.getNumberOfThreads());

        assertSame(id, schedulerA.getId());
        assertNotNull(schedulerB.getId());
        assertNotNull(schedulerC.getId());
        assertNotEquals(schedulerB.getId(), schedulerC.getId());

        schedulerA.close();
        schedulerB.close();
        schedulerC.close();
    }

}
