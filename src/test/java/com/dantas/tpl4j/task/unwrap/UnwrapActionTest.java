package com.dantas.tpl4j.task.unwrap;

import com.dantas.tpl4j.task.Task;
import com.dantas.tpl4j.task.core.cancel.CancellationToken;
import com.dantas.tpl4j.task.core.cancel.CancelledException;
import com.dantas.tpl4j.task.core.job.Job;
import org.junit.Test;

import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class UnwrapActionTest {

    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();



    @Test
    public void getJobTest() {
        Task<String> task = new Task<>(() -> null);
        Job<Task<String>> job = new Job<>("", (token) -> task, SCHEDULER, new LinkedList<>());

        UnwrapAction<String> action = new UnwrapAction<>(job);

        assertSame(job, action.getJob());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String result = "";
        Task<String> task = new Task<>(() -> result);
        Job<Task<String>> job = new Job<>("", (token) -> task, SCHEDULER, new LinkedList<>());

        task.start();
        task.getStatus().finishedEvent.await();

        job.schedule();
        job.getStatus().finishedEvent.await();

        UnwrapAction<String> action = new UnwrapAction<>(job);

        assertSame(result, action.run(new CancellationToken("")));
    }

    @Test
    public void runFailJobTest() throws InterruptedException {
        Exception result = new Exception();
        Job<Task<String>> job = new Job<>("", (token) -> { throw result; }, SCHEDULER, new LinkedList<>());

        job.schedule();
        job.getStatus().finishedEvent.await();

        UnwrapAction<String> action = new UnwrapAction<>(job);

        try {
            action.run(new CancellationToken(""));
            fail("Exception should be thrown!");
        }catch(Exception e) {
            assertSame(result, e);
        }
    }

    @Test
    public void runFailTaskTest() throws InterruptedException {
        Exception result = new Exception();
        Task<String> task = new Task<>(() -> { throw result; });
        Job<Task<String>> job = new Job<>("", (token) -> task, SCHEDULER, new LinkedList<>());

        task.start();
        task.getStatus().finishedEvent.await();

        job.schedule();
        job.getStatus().finishedEvent.await();

        UnwrapAction<String> action = new UnwrapAction<>(job);

        try {
            action.run(new CancellationToken(""));
            fail("Exception should be thrown!");
        }catch(Exception e) {
            assertSame(result, e);
        }
    }

    @Test(expected = CancelledException.class)
    public void runCancelJobTest() throws Exception {
        Exception result = new Exception();
        Job<Task<String>> job = new Job<>("", (token) -> { throw result; }, SCHEDULER, new LinkedList<>());

        job.cancel();
        job.schedule();
        job.getStatus().finishedEvent.await();

        UnwrapAction<String> action = new UnwrapAction<>(job);

        action.run(new CancellationToken(""));
    }

    @Test(expected = CancelledException.class)
    public void runCancelTaskTest() throws Exception {
        String result = "";
        Task<String> task = new Task<>(() -> result);
        Job<Task<String>> job = new Job<>("", (token) -> task, SCHEDULER, new LinkedList<>());

        task.cancel();
        task.start();
        task.getStatus().finishedEvent.await();

        job.schedule();
        job.getStatus().finishedEvent.await();

        UnwrapAction<String> action = new UnwrapAction<>(job);

        action.run(new CancellationToken(""));
    }

}
