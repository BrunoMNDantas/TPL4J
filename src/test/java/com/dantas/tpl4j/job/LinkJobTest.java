package com.dantas.tpl4j.job;

import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertEquals;

public class LinkJobTest {

    private static final Consumer<Runnable> SCHEDULER = (r) -> new Thread(r).start();



    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test
    public void linkWithNormalJobTest() throws Exception {
        Job<Integer> firstJob = new Job<>(() -> 1, SCHEDULER);
        Job<Integer> secondJob = new LinkJob<>(firstJob, (number) -> number+1, SCHEDULER);

        firstJob.schedule();
        secondJob.schedule();

        assertEquals(new Integer(2), secondJob.getResult());
    }

    @Test(expected = RuntimeException.class)
    public void linkWithFailJobTest() throws Exception {
        Job<Integer> firstJob = new Job<>(() -> { throw new RuntimeException(); }, SCHEDULER);
        Job<Integer> secondJob = new LinkJob<>(firstJob, (number) -> number+1, SCHEDULER);

        firstJob.schedule();
        secondJob.schedule();

        secondJob.getResult();
    }

}
