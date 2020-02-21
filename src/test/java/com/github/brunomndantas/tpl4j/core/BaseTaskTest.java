package com.github.brunomndantas.tpl4j.core;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.job.Job;
import org.junit.Test;

import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class BaseTaskTest {

    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();
    private static final String SUCCESS_RESULT = "";
    private static final IAction<String> SUCCESS_ACTION = (token) -> { Thread.sleep(3000); return SUCCESS_RESULT; };
    private static final Exception FAIL_RESULT = new Exception();
    private static final IAction<String> FAIL_ACTION = (token) -> { Thread.sleep(3000); throw FAIL_RESULT; };
    private static final IAction<String> CANCEL_ACTION = (token) -> { Thread.sleep(3000); token.abortIfCancelRequested(); return SUCCESS_RESULT; };



    @Test
    public void getJobTest() {
        Job<?> job = new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());
        BaseTask<?> task = new BaseTask<>(job);

        assertNotNull(task.getJob());
        assertSame(SUCCESS_ACTION, task.getJob().getAction());
    }

    @Test
    public void getIdTest() {
        String id = "";
        BaseTask<?> task = new BaseTask<>(new Job<>(id, SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        assertSame(id, task.getId());
    }

    @Test
    public void getActionTest() {
        BaseTask<?> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        assertNotNull(task.getAction());
        assertSame(SUCCESS_ACTION, task.getAction());
    }

    @Test
    public void getSchedulerTest() {
        BaseTask<?> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        assertSame(task.getJob().getScheduler(), task.getScheduler());
    }

    @Test
    public void getOptionsTest() {
        BaseTask<?> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        assertSame(task.getJob().getOptions(), task.getOptions());
    }

    @Test
    public void getValueTest() throws InterruptedException {
        BaseTask<?> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        task.start();

        task.getStatus().finishedEvent.await();

        assertSame(SUCCESS_RESULT, task.getValue());
    }

    @Test
    public void getExceptionTest() throws InterruptedException {
        BaseTask<?> task = new BaseTask<>(new Job<>("", FAIL_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        task.start();

        task.getStatus().finishedEvent.await();

        assertSame(FAIL_RESULT, task.getException());
    }

    @Test
    public void getCancellationTokenTest() {
        BaseTask<?> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        assertSame(CANCELLATION_TOKEN, task.getCancellationToken());
        assertSame(task.getJob().getCancellationToken(), task.getCancellationToken());
    }

    @Test
    public void getStatusTest() {
        BaseTask<?> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        assertNotNull(task.getStatus());
        assertSame(task.getJob().getStatus(), task.getStatus());
    }

    @Test
    public void constructorsTest() {
        Job<?> job = new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());
        BaseTask<?> task = new BaseTask<>(job);

        assertNotNull(task.getJob());
        assertSame(SUCCESS_ACTION, task.getJob().getAction());
    }

    @Test
    public void startTest() throws InterruptedException {
        BaseTask<?> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        task.start();

        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task.getStatus().succeededEvent.await();

        assertSame(SUCCESS_RESULT, task.getValue());
    }

    @Test
    public void cancelTest() throws InterruptedException {
        BaseTask<?> task = new BaseTask<>(new Job<>("", CANCEL_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        task.start();

        task.cancel();

        assertTrue(task.hasCancelRequest());

        task.getStatus().cancelledEvent.await();
    }

    @Test
    public void getSuccessResultTest() throws Exception {
        BaseTask<?> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        task.start();

        assertSame(SUCCESS_RESULT, task.getResult());
    }

    @Test
    public void getFailResultTest() {
        BaseTask<?> task = new BaseTask<>(new Job<>("", FAIL_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        task.start();

        try {
            task.getResult();
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(FAIL_RESULT, e);
        }
    }

    @Test
    public void getCancelResultTest() throws Exception {
        BaseTask<?> task = new BaseTask<>(new Job<>("", CANCEL_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        task.start();

        task.cancel();

        assertNull(task.getResult());
    }

    @Test
    public void hasCancelRequestTest() {
        BaseTask<?> task = new BaseTask<>(new Job<>("", CANCEL_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        assertFalse(task.hasCancelRequest());

        task.cancel();

        assertTrue(task.hasCancelRequest());
    }

    @Test
    public void startTwiceTest() {
        BaseTask<String> task = new BaseTask<>(new Job<>("", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>()));

        task.start();

        try {
            task.start();
            fail("Exception should be thrown!");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("already scheduled"));
        }
    }

}
