package com.github.brunomndantas.tpl4j.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.task.context.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.context.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.task.job.Job;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAllActionTest {

    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();



    @Test
    public void getJobsTest() {
        Collection<Job<String>> jobs = new LinkedList<>();

        WhenAllAction<String> action = new WhenAllAction<>(jobs);

        assertSame(jobs, action.getJobs());
    }

    @Test
    public void runSuccessTest() throws Exception {
        Job<String> jobA = new Job<>("", (t) -> "A", new CancellationToken(), SCHEDULER, new LinkedList<>());
        Job<String> jobB = new Job<>("", (t) -> "B", new CancellationToken(), SCHEDULER, new LinkedList<>());
        Collection<Job<String>> jobs = Arrays.asList(jobA, jobB);

        jobA.schedule();
        jobB.schedule();

        jobA.getStatus().finishedEvent.await();
        jobB.getStatus().finishedEvent.await();

        WhenAllAction<String> action = new WhenAllAction<>(jobs);
        Collection<String> results = action.run(new CancellationToken());

        assertEquals(jobs.size(), results.size());
        assertTrue(results.contains(jobA.getValue()));
        assertTrue(results.contains(jobB.getValue()));
    }

    @Test
    public void runFailTest() throws Exception {
        Exception exceptionB = new Exception();
        Exception exceptionC = new Exception();
        Job<String> jobA = new Job<>("", (t) -> "A", new CancellationToken(), SCHEDULER, new LinkedList<>());
        Job<String> jobB = new Job<>("", (t) -> { throw exceptionB; }, new CancellationToken(), SCHEDULER, new LinkedList<>());
        Job<String> jobC = new Job<>("", (t) -> { throw exceptionC; }, new CancellationToken(), SCHEDULER, new LinkedList<>());
        Collection<Job<String>> jobs = Arrays.asList(jobA, jobB, jobC);

        jobA.schedule();
        jobB.schedule();
        jobC.schedule();

        jobA.getStatus().finishedEvent.await();
        jobB.getStatus().finishedEvent.await();
        jobC.getStatus().finishedEvent.await();

        WhenAllAction<String> action = new WhenAllAction<>(jobs);
        try{
            action.run(new CancellationToken());
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(exceptionB, e.getCause());
            assertEquals(1, e.getSuppressed().length);
            assertEquals(exceptionC, e.getSuppressed()[0]);
        }
    }

    @Test(expected = CancelledException.class)
    public void runCancelTest() throws Exception {
        Job<String> jobA = new Job<>("", (t) -> "A", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());
        Job<String> jobB = new Job<>("", (t) -> { t.cancel(); t.abortIfCancelRequested(); return null; }, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());
        Collection<Job<String>> jobs = Arrays.asList(jobA, jobB);

        jobA.schedule();
        jobB.schedule();

        jobA.getStatus().finishedEvent.await();
        jobB.getStatus().finishedEvent.await();

        WhenAllAction<String> action = new WhenAllAction<>(jobs);

        action.run(new CancellationToken());
    }

    @Test(expected = CancelledException.class)
    public void runCancelTokenTest() throws Exception {
        Job<String> jobA = new Job<>("", (t) -> "A", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());
        Collection<Job<String>> jobs = Arrays.asList(jobA);

        jobA.schedule();

        jobA.getStatus().finishedEvent.await();

        CancellationToken cancellationToken = new CancellationToken();

        cancellationToken.cancel();

        WhenAllAction<String> action = new WhenAllAction<>(jobs);

        action.run(cancellationToken);
    }

}