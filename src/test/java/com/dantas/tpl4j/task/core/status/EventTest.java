package com.dantas.tpl4j.task.core.status;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EventTest {

    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test
    public void addListenerTest() {
        Event event = new Event();

        boolean[] executed = new boolean[1];
        event.addListener(() -> executed[0] = true);

        event.fire();

        sleep(2000);

        assertTrue(executed[0]);
    }

    @Test
    public void addListenerAfterFireTest() {
        Event event = new Event();

        event.fire();

        boolean[] executed = new boolean[1];
        event.addListener(() -> executed[0] = true);

        assertTrue(executed[0]);
    }

    @Test
    public void removeEventTest() {
        Event event = new Event();

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
        Event event = new Event();
        assertFalse(event.hasFired());
    }

    @Test
    public void hasFiredAfterFireTest() {
        Event event = new Event();
        event.fire();
        assertTrue(event.hasFired());
    }

    @Test
    public void awayWithTimeoutTest() throws Exception {
        Event event = new Event();
        assertFalse(event.await(1000));
    }

    @Test
    public void awayWithoutTimeoutTest() throws Exception {
        Event event = new Event();

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

        Event event = new Event();

        new Thread(() -> {
            sleep(sleepTime);
            event.fire();
        }).start();

        assertTrue(event.await());
        assertTrue(System.currentTimeMillis() > exceptedFinish);
    }

}
