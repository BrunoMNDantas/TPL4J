package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class UnwrapJobTest {

    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();



    @Test
    public void getTaskTest() {
        Task<String> t = new Task<>(() -> null);
        Task<Task<String>> task = new Task<>("", (token) -> t, CANCELLATION_TOKEN, SCHEDULER);

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), task);

        assertSame(task, unwrapJob.getTask());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String result = "";
        Task<String> t = new Task<>(() -> result);
        Task<Task<String>> task = new Task<>("", (token) -> t, new CancellationToken(), SCHEDULER);

        t.start();
        t.getStatus().finishedEvent.await();
        task.start();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), task);

        unwrapJob.schedule();
        task.getStatus().finishedEvent.await();

        assertSame(result, unwrapJob.getResult());
    }

    @Test
    public void runFailTaskTest() throws InterruptedException {
        Exception result = new Exception();
        Task<Task<String>> task = new Task<>("", (IAction<Task<String>>)(token) -> { throw result; }, new CancellationToken(), SCHEDULER);

        task.start();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), task);
        unwrapJob.schedule();

        try {
            unwrapJob.getResult();
            fail("Exception should be thrown!");
        }catch(Exception e) {
            assertSame(result, e);
        }
    }

    @Test
    public void runFailRootTaskTest() throws InterruptedException {
        Exception result = new Exception();
        Task<String> t = new Task<>(() -> { throw result; });
        Task<Task<String>> task = new Task<>("", (token) -> t, new CancellationToken(), SCHEDULER);

        t.start();
        t.getStatus().finishedEvent.await();
        task.start();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", new CancellationToken(), SCHEDULER, new LinkedList<>(), task);
        unwrapJob.schedule();

        try {
            unwrapJob.getResult();
            fail("Exception should be thrown!");
        }catch(Exception e) {
            assertSame(result, e);
        }
    }

    @Test
    public void runCancelTaskTest() throws Exception {
        Exception result = new Exception();
        Task<Task<String>> task = new Task<>("", (IAction<Task<String>>)(token) -> { throw result; }, CANCELLATION_TOKEN, SCHEDULER);

        task.cancel();
        task.start();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), task);

        unwrapJob.schedule();
        unwrapJob.getStatus().cancelledEvent.await();
    }

    @Test
    public void runCancelRootTaskTest() throws Exception {
        String result = "";
        Task<String> t = new Task<>(() -> result);
        Task<Task<String>> task = new Task<>("", (token) -> t, CANCELLATION_TOKEN, SCHEDULER);

        t.cancel();
        t.start();
        t.getStatus().finishedEvent.await();
        task.start();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>(), task);
        unwrapJob.schedule();

        unwrapJob.getStatus().cancelledEvent.await();
    }

    @Test
    public void lateFinishRegisterOnSchedulerTest() throws InterruptedException {
        int[] counter = new int[1];
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Consumer<Runnable> scheduler = (action) -> { counter[0]++; pool.submit(action); };

        Task<String> t = new Task<>(() -> "", scheduler);
        Task<Task<String>> task = new Task<>("", (token) -> t, CANCELLATION_TOKEN, scheduler);

        t.start();
        task.start();

        UnwrapJob<String> unwrapJob = new UnwrapJob<>("", CANCELLATION_TOKEN, scheduler, new LinkedList<>(), task);

        unwrapJob.schedule();
        unwrapJob.getStatus().finishedEvent.await();

        assertEquals(5, counter[0]);
        //Why 5?
        //task registers the action
        //job registers the action
        //unwrapJob registers the action
        //unwrapJob register and action for callback when job finishes
        //unwrapJob register an action for callback when task finishes

        pool.shutdown();
    }

}
