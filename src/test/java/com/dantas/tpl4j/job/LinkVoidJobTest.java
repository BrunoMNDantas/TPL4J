package com.dantas.tpl4j.job;

import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertNull;

public class LinkVoidJobTest {

    private static final Consumer<Runnable> SCHEDULER = (r) -> new Thread(r).start();



    private static void sleep(long milliseconds) {
        try{
            Thread.sleep(milliseconds);
        } catch (Exception e) { }
    }



    @Test
    public void linkWithNormalJobTest() throws Exception {
        Job<Integer> firstJob = new Job<>(() -> 1, SCHEDULER);
        Job<Void> secondJob = new LinkVoidJob<>(firstJob, (number) -> {}, SCHEDULER);

        firstJob.schedule();
        secondJob.schedule();

        assertNull(secondJob.getResult());
    }

    @Test(expected = RuntimeException.class)
    public void linkWithFailJobTest() throws Exception {
        Job<Integer> firstJob = new Job<>(() -> { throw new RuntimeException(); }, SCHEDULER);
        Job<Void> secondJob = new LinkVoidJob<>(firstJob, (number) -> {}, SCHEDULER);

        firstJob.schedule();
        secondJob.schedule();

        secondJob.getResult();
    }

}
