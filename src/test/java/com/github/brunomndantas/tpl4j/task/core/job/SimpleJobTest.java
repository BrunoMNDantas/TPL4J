package com.github.brunomndantas.tpl4j.task.core.job;

import com.github.brunomndantas.tpl4j.task.core.action.IAction;
import org.junit.Test;

import java.util.function.Consumer;

import static org.junit.Assert.*;

public class SimpleJobTest {

    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();
    private static final String SUCCESS_RESULT = "";
    private static final IAction<String> SUCCESS_ACTION = (token) -> { Thread.sleep(3000); return SUCCESS_RESULT; };
    private static final Exception FAIL_RESULT = new Exception();
    private static final IAction<String> FAIL_ACTION = (token) -> { Thread.sleep(3000); throw FAIL_RESULT; };


    @Test
    public void getTaskIdTest() {
        String taskId = "ID";

        SimpleJob<String> job = new SimpleJob<>(taskId, SUCCESS_ACTION, SCHEDULER);

        assertEquals(taskId, job.getTaskId());
    }

    @Test
    public void getActionTest() {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);

        assertSame(SUCCESS_ACTION, job.getAction());
    }

    @Test
    public void getScheduledTest() {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);

        assertSame(SCHEDULER, job.getScheduler());
    }

    @Test
    public void getStatusTest() {
        String taskId = "ID";
        SimpleJob<String> job = new SimpleJob<>(taskId, SUCCESS_ACTION, SCHEDULER);

        assertNotNull(job.getStatus());
        assertEquals(taskId, job.getStatus().getTaskId());
    }

    @Test
    public void getCancellationToken() {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);
        assertNotNull(job.getCancellationToken());
    }

    @Test
    public void getValueBeforeFinishTest() {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);

        String value = job.getValue();
        assertNull(value);

        job.schedule();

        value = job.getValue();
        assertNull(value);
    }

    @Test
    public void getValueAfterFinishTest() throws InterruptedException {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);

        job.schedule();

        job.status.finishedEvent.await();

        String value = job.getValue();
        assertSame(SUCCESS_RESULT, value);
    }

    @Test
    public void getExceptionBeforeFinishTest() {
        SimpleJob<String> job = new SimpleJob<>("ID", FAIL_ACTION, SCHEDULER);

        Exception exception = job.getException();
        assertNull(exception);

        job.schedule();

        exception = job.getException();
        assertNull(exception);
    }

    @Test
    public void getExceptionAfterFinishTest() throws InterruptedException {
        SimpleJob<String> job = new SimpleJob<>("ID", FAIL_ACTION, SCHEDULER);

        job.schedule();

        job.status.finishedEvent.await();

        Exception exception = job.getException();
        assertSame(FAIL_RESULT, exception);
    }

    @Test
    public void hasCancelRequestTest() {
        SimpleJob<String> job = new SimpleJob<>("ID", FAIL_ACTION, SCHEDULER);

        job.cancel();
        assertTrue(job.hasCancelRequest());
    }

    @Test
    public void scheduleTest() {
        boolean[] scheduled = new boolean[]{false};
        Consumer<Runnable> scheduler = (action) -> { scheduled[0] = true; new Thread(action).start(); };
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, scheduler);

        job.schedule();

        assertTrue(scheduled[0]);
        assertTrue(job.status.scheduledEvent.hasFired());
    }

    @Test
    public void cancelBeforeScheduleTest() throws InterruptedException {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);

        job.cancel();

        job.schedule();

        job.status.finishedEvent.await();

        assertFalse(job.status.runningEvent.hasFired());
        assertTrue(job.status.cancelledEvent.hasFired());
    }

    @Test
    public void cancelWhileScheduledTest() throws InterruptedException {
        Consumer<Runnable> scheduler = (action) -> new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch(Exception e){}
            new Thread(action).start();
        }).start();
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, scheduler);

        job.schedule();

        job.cancel();

        assertTrue(job.hasCancelRequest());

        job.status.finishedEvent.await();

        assertFalse(job.status.runningEvent.hasFired());
        assertTrue(job.status.cancelledEvent.hasFired());
    }

    @Test
    public void cancelWhileRunningTest() throws InterruptedException {
        SimpleJob<String> job = new SimpleJob<>("ID", (token) -> {
            token.cancel();
            token.abortIfCancelRequested();
            return null;
        }, SCHEDULER);

        job.schedule();

        job.status.finishedEvent.await();

        assertTrue(job.status.cancelledEvent.hasFired());
    }

    @Test
    public void cancelAfterFinishedTest() throws InterruptedException {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);

        job.schedule();

        job.status.finishedEvent.await();

        job.cancel();

        Thread.sleep(3000);

        assertFalse(job.status.cancelledEvent.hasFired());
    }

    @Test
    public void scheduleTwiceTest() {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);

        job.schedule();

        try {
            job.schedule();
            fail("Exception should be thrown!");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("already scheduled"));
        }
    }

    @Test
    public void getSuccessResultTest() throws Exception {
        SimpleJob<String> job = new SimpleJob<>("ID", SUCCESS_ACTION, SCHEDULER);

        job.schedule();

        assertSame(SUCCESS_RESULT, job.getResult());
    }

    @Test
    public void getFailResultTest() {
        SimpleJob<String> job = new SimpleJob<>("ID", FAIL_ACTION, SCHEDULER);

        job.schedule();

        try {
            job.getResult();
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(FAIL_RESULT, e);
        }
    }

    @Test
    public void getCancelResultTest() throws Exception {
        SimpleJob<String> job = new SimpleJob<>("ID", (cancelToken) -> {
            cancelToken.cancel();
            cancelToken.abortIfCancelRequested();
            return null;
        }, SCHEDULER);

        job.schedule();

        assertNull(job.getResult());
    }

}
