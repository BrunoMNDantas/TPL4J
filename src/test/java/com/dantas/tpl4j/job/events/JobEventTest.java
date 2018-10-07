package com.dantas.tpl4j.job.events;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class JobEventTest {

    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test
    public void addListenerTest() {
        JobEvent event = new JobEvent();

        boolean[] executed = new boolean[1];
        event.addListener(() -> executed[0] = true);

        event.fire();

        sleep(2000);

        assertTrue(executed[0]);
    }

    @Test
    public void addListenerAfterFireTest() {
        JobEvent event = new JobEvent();

        event.fire();

        boolean[] executed = new boolean[1];
        event.addListener(() -> executed[0] = true);

        assertTrue(executed[0]);
    }

    @Test
    public void removeEventTest() {
        JobEvent event = new JobEvent();

        boolean[] executed = new boolean[1];
        Runnable listener = () -> executed[0] = true;
        event.addListener(listener);
        event.removeListener(listener);

        event.fire();

        sleep(2000);

        assertFalse(executed[0]);
    }

    @Test
    public void hasFiredBeforeFireTest() {
        JobEvent event = new JobEvent();
        assertFalse(event.hasFired());
    }

    @Test
    public void hasFiredAfterFireTest() {
        JobEvent event = new JobEvent();
        event.fire();
        assertTrue(event.hasFired());
    }

    @Test
    public void awayWithTimeoutTest() throws Exception {
        JobEvent event = new JobEvent();
        assertFalse(event.await(1000));
    }

    @Test
    public void awayWithoutTimeoutTest() throws Exception {
        JobEvent event = new JobEvent();

        new Thread(() -> {
            sleep(1000);
            event.fire();
        }).start();

        assertTrue(event.await(2000));
    }

    @Test
    public void awayForeverTest() throws Exception {
        long sleepTime = 3000;
        long exceptedFinish = System.currentTimeMillis() + sleepTime;

        JobEvent event = new JobEvent();

        new Thread(() -> {
            sleep(sleepTime);
            event.fire();
        }).start();

        assertTrue(event.await());
        assertTrue(System.currentTimeMillis() > exceptedFinish);
    }

}
