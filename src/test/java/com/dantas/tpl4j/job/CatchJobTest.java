package com.dantas.tpl4j.job;

import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.assertSame;

public class CatchJobTest {

    private static final Consumer<Runnable> SCHEDULER = (r) -> new Thread(r).start();




    @Test
    public void catchWithoutExceptionTest() throws Exception {
        Object value = new Object();

        Job<Object> firstJob = new Job<>(() -> value, SCHEDULER);
        Job<Object> secondJob = new CatchJob<>(firstJob, (ex) -> { throw new Exception(); }, SCHEDULER);

        firstJob.schedule();
        secondJob.schedule();

        assertSame(value, secondJob.getResult());
    }

    @Test
    public void catchAndReturnValueTest() throws Exception {
        Object value = new Object();

        Job<Object> firstJob = new Job<>(() -> { throw new Exception(); }, SCHEDULER);
        Job<Object> secondJob = new CatchJob<>(firstJob, (ex) -> value, SCHEDULER);

        firstJob.schedule();
        secondJob.schedule();

        assertSame(value, secondJob.getResult());
    }

    @Test
    public void catchAndReturnSameExceptionTest() throws Exception {
        Exception firstException = new Exception();

        Job<Void> firstJob = new VoidJob(() -> { throw firstException; }, SCHEDULER);
        Job<Void> secondJob = new CatchJob<>(firstJob, (ex) -> { throw ex; }, SCHEDULER);

        firstJob.schedule();
        secondJob.schedule();

        secondJob.events.finishedEvent.await();

        assertSame(firstException, secondJob.getException());
    }

    @Test
    public void catchAndReturnDifferentExceptionTest() throws Exception {
        Exception firstException = new Exception();
        Exception secondException = new Exception();

        Job<Void> firstJob = new VoidJob(() -> { throw firstException; }, SCHEDULER);
        Job<Void> secondJob = new CatchJob<>(firstJob, (ex) -> { throw secondException; }, SCHEDULER);

        firstJob.schedule();
        secondJob.schedule();

        secondJob.events.finishedEvent.await();

        assertSame(secondException, secondJob.getException());
    }

}
