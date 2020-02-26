package com.github.brunomndantas.tpl4j.core.job;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class JobTest {

    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();
    private static final Collection<Option> OPTIONS = new LinkedList<>();
    private static final String SUCCESS_RESULT = "";
    private static final IAction<String> SUCCESS_ACTION = (token) -> { Thread.sleep(3000); return SUCCESS_RESULT; };
    private static final Exception FAIL_RESULT = new Exception();
    private static final IAction<String> FAIL_ACTION = (token) -> { Thread.sleep(3000); throw FAIL_RESULT; };
    private static final IAction<String> CANCEL_ACTION = (token) -> { Thread.sleep(3000); token.abortIfCancelRequested(); return SUCCESS_RESULT; };
    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();



    @Test
    public void getTaskIdTest() {
        String taskId = "ID";

        Job<String> job = new Job<>(taskId, SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        assertEquals(taskId, job.getTaskId());
    }

    @Test
    public void getActionTest() {
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        assertSame(SUCCESS_ACTION, job.getAction());
    }

    @Test
    public void getScheduledTest() {
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        assertSame(SCHEDULER, job.getScheduler());
    }

    @Test
    public void getStatusTest() {
        String taskId = "ID";
        Job<String> job = new Job<>(taskId, SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        assertNotNull(job.getStatus());
        assertEquals(taskId, job.getStatus().getTaskId());
    }

    @Test
    public void getCancellationToken() {
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        assertSame(CANCELLATION_TOKEN, job.getCancellationToken());
    }

    @Test
    public void getValueBeforeFinishTest() {
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        String value = job.getValue();
        assertNull(value);

        job.schedule();

        value = job.getValue();
        assertNull(value);
    }

    @Test
    public void getValueAfterFinishTest() throws InterruptedException {
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, OPTIONS);

        job.schedule();

        job.status.finishedEvent.await();

        String value = job.getValue();
        assertSame(SUCCESS_RESULT, value);
    }

    @Test
    public void getExceptionBeforeFinishTest() {
        Job<String> job = new Job<>("ID", FAIL_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        Exception exception = job.getException();
        assertNull(exception);

        job.schedule();

        exception = job.getException();
        assertNull(exception);
    }

    @Test
    public void getExceptionAfterFinishTest() throws InterruptedException {
        Job<String> job = new Job<>("ID", FAIL_ACTION, new CancellationToken(), SCHEDULER, OPTIONS);

        job.schedule();

        job.status.finishedEvent.await();

        Exception exception = job.getException();
        assertSame(FAIL_RESULT, exception);
    }

    @Test
    public void hasCancelRequestTest() {
        Job<String> job = new Job<>("ID", FAIL_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        job.cancel();
        assertTrue(job.hasCancelRequest());
    }

    @Test
    public void scheduleTest() {
        boolean[] scheduled = new boolean[]{false};
        Consumer<Runnable> scheduler = (action) -> { scheduled[0] = true; new Thread(action).start(); };
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, scheduler, OPTIONS);

        job.schedule();

        assertTrue(scheduled[0]);
        assertTrue(job.status.scheduledEvent.hasFired());
    }

    @Test
    public void cancelBeforeScheduleTest() throws InterruptedException {
        CancellationToken cancellationToken = new CancellationToken();
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, cancellationToken, SCHEDULER, OPTIONS);

        job.cancel();

        job.schedule();

        job.status.finishedEvent.await();

        assertFalse(job.status.runningEvent.hasFired());
        assertTrue(job.status.cancelledEvent.hasFired());
    }

    @Test
    public void cancelWhileScheduledTest() throws InterruptedException {
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (action) -> new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch(Exception e){}
            new Thread(action).start();
        }).start();
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, cancellationToken, scheduler, OPTIONS);

        job.schedule();

        job.cancel();

        assertTrue(job.hasCancelRequest());

        job.status.finishedEvent.await();

        assertFalse(job.status.runningEvent.hasFired());
        assertTrue(job.status.cancelledEvent.hasFired());
    }

    @Test
    public void cancelWhileRunningTest() throws InterruptedException {
        CancellationToken cancellationToken = new CancellationToken();
        Job<String> job = new Job<>("ID", (token) -> {
            token.cancel();
            token.abortIfCancelRequested();
            return null;
        }, cancellationToken, SCHEDULER, OPTIONS);

        job.schedule();

        job.status.finishedEvent.await();

        assertTrue(job.status.cancelledEvent.hasFired());
    }

    @Test
    public void cancelAfterFinishedTest() throws InterruptedException {
        CancellationToken cancellationToken = new CancellationToken();
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, cancellationToken, SCHEDULER, OPTIONS);

        job.schedule();

        job.status.finishedEvent.await();

        job.cancel();

        Thread.sleep(3000);

        assertFalse(job.status.cancelledEvent.hasFired());
    }

    @Test
    public void scheduleTwiceTest() {
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

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
        Job<String> job = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        job.schedule();

        assertSame(SUCCESS_RESULT, job.getResult());
    }

    @Test
    public void getFailResultTest() {
        Job<String> job = new Job<>("ID", FAIL_ACTION, new CancellationToken(), SCHEDULER, OPTIONS);

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
        CancellationToken cancellationToken = new CancellationToken();
        Job<String> job = new Job<>("ID", (cancelToken) -> {
            cancelToken.cancel();
            cancelToken.abortIfCancelRequested();
            return null;
        }, cancellationToken, SCHEDULER, OPTIONS);

        job.schedule();

        assertNull(job.getResult());
    }

    @Test
    public void getOptionsTest() {
        Collection<Option> options = new LinkedList<>();

        Job<?> job = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, options);

        assertSame(options, job.getOptions());
    }

    @Test
    public void runCheckNotCancelableOption() throws Exception {
        Job<?> job = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.NOT_CANCELABLE));

        job.cancel();
        job.schedule();

        job.status.succeededEvent.await();

        assertSame(SUCCESS_RESULT, job.getValue());
        assertNull(job.getException());

        job = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, new LinkedList<>());

        job.cancel();
        job.schedule();

        job.status.cancelledEvent.await();

        assertNull(job.getValue());
        assertNull(job.getException());
    }


    @Test
    public void successWithoutChildrenTest() throws InterruptedException {
        Job<?> job = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, new LinkedList<>());

        job.schedule();

        job.status.succeededEvent.await();

        assertSame(SUCCESS_RESULT, job.getValue());
        assertNull(job.getException());
    }

    @Test
    public void failWithoutChildrenTest() throws InterruptedException {
        Job<?> job = new Job<>("ID", FAIL_ACTION, new CancellationToken(), SCHEDULER, new LinkedList<>());

        job.schedule();

        job.status.failedEvent.await();

        assertSame(FAIL_RESULT, job.getException());
        assertNull(job.getValue());
    }

    @Test
    public void cancelWithoutChildrenTest() throws InterruptedException {
        Job<?> job = new Job<>("ID", CANCEL_ACTION, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());

        job.schedule();

        Thread.sleep(1000);

        job.cancel();

        job.status.cancelledEvent.await();

        assertNull(job.getValue());
        assertNull(job.getException());
    }


    @Test
    public void successRejectingChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            return SUCCESS_RESULT;
        }, new CancellationToken(), SCHEDULER, Arrays.asList(Option.REJECT_CHILDREN));

        parentJob.schedule();

        parentJob.status.succeededEvent.await();

        assertSame(SUCCESS_RESULT, parentJob.getValue());
        assertFalse(children[0].status.finishedEvent.hasFired());
        assertFalse(children[1].status.finishedEvent.hasFired());
    }

    @Test
    public void failRejectingChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            throw FAIL_RESULT;
        }, new CancellationToken(), SCHEDULER, Arrays.asList(Option.REJECT_CHILDREN));

        parentJob.schedule();

        parentJob.status.failedEvent.await();

        assertSame(FAIL_RESULT, parentJob.getException());
        assertFalse(children[0].status.finishedEvent.hasFired());
        assertFalse(children[1].status.finishedEvent.hasFired());
    }

    @Test
    public void cancelRejectingChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            token.cancel();
            token.abortIfCancelRequested();

            return null;
        }, new CancellationToken(), SCHEDULER, Arrays.asList(Option.REJECT_CHILDREN));

        parentJob.schedule();

        parentJob.status.cancelledEvent.await();

        assertFalse(children[0].status.finishedEvent.hasFired());
        assertFalse(children[1].status.finishedEvent.hasFired());
    }


    @Test
    public void successWithSuccessfulChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            return SUCCESS_RESULT;
        }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());

        parentJob.status.succeededEvent.await();

        assertSame(SUCCESS_RESULT, parentJob.getValue());
        assertTrue(children[0].status.succeededEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }

    @Test
    public void successWithFailedChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", FAIL_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            return SUCCESS_RESULT;
        }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());

        parentJob.status.failedEvent.await();

        assertSame(FAIL_RESULT, parentJob.getException().getCause());
        assertTrue(children[0].status.failedEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }

    @Test
    public void successWithCancelChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", CANCEL_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            return SUCCESS_RESULT;
        }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());
        children[0].cancel();

        parentJob.status.succeededEvent.await();

        assertSame(SUCCESS_RESULT, parentJob.getValue());
        assertTrue(children[0].status.cancelledEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }


    @Test
    public void failWithSuccessfulChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            throw FAIL_RESULT;
        }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());

        parentJob.status.failedEvent.await();

        assertSame(FAIL_RESULT, parentJob.getException());
        assertTrue(children[0].status.succeededEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }

    @Test
    public void failWithFailedChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", FAIL_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            throw FAIL_RESULT;
        }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());

        parentJob.status.failedEvent.await();

        assertSame(FAIL_RESULT, parentJob.getException());
        assertTrue(children[0].status.failedEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }

    @Test
    public void failWithCancelledChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", CANCEL_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            throw FAIL_RESULT;
        }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        children[0].cancel();
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());

        parentJob.status.failedEvent.await();

        assertSame(FAIL_RESULT, parentJob.getException());
        assertTrue(children[0].status.cancelledEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }


    @Test
    public void cancelWithSuccessfulChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, CANCELLATION_TOKEN, SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            Thread.sleep(500);

            token.cancel();
            token.abortIfCancelRequested();

            return null;
        }, CANCELLATION_TOKEN, SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());

        parentJob.status.cancelledEvent.await();

        assertTrue(children[0].status.succeededEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }

    @Test
    public void cancelWithFailedChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", FAIL_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            Thread.sleep(500);

            token.cancel();
            token.abortIfCancelRequested();

            return null;
        }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());

        parentJob.status.failedEvent.await();

        assertSame(FAIL_RESULT, parentJob.getException().getCause());
        assertTrue(children[0].status.failedEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }

    @Test
    public void cancelWithCancelledChildrenTest() throws InterruptedException {
        Job[] children = new Job[2];
        Job<?> parentJob = new Job<>("ID", (token) -> {
            Job<?> childJobA = new Job<>("ID", CANCEL_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));
            Job<?> childJobB = new Job<>("ID", SUCCESS_ACTION, new CancellationToken(), SCHEDULER, Arrays.asList(Option.ATTACH_TO_PARENT));

            children[0] = childJobA;
            children[1] = childJobB;

            childJobA.schedule();
            childJobB.schedule();

            Thread.sleep(500);
            token.cancel();
            token.abortIfCancelRequested();

            return null;
        }, new CancellationToken(), SCHEDULER, new LinkedList<>());

        parentJob.schedule();

        Thread.sleep(1000);
        children[0].cancel();
        assertTrue(parentJob.status.waitingForChildrenEvent.hasFired());

        parentJob.status.cancelledEvent.await();

        assertTrue(children[0].status.cancelledEvent.hasFired());
        assertTrue(children[1].status.succeededEvent.hasFired());
    }

    @Test
    public void lateFinishRegisterOnSchedulerTest() throws InterruptedException {
        int[] counter = new int[1];
        ExecutorService pool = Executors.newSingleThreadExecutor();
        Consumer<Runnable> scheduler = (action) -> { counter[0]++; pool.submit(action); };

        Job<?> job = new Job<>("Parent", (ct) -> {
            new Job<>("ChildA", (ctA) -> null, new CancellationToken(), scheduler, Arrays.asList(Option.ATTACH_TO_PARENT)).schedule();
            new Job<>("ChildB", (ctA) -> null, new CancellationToken(), scheduler, Arrays.asList(Option.ATTACH_TO_PARENT)).schedule();

            return null;
        }, new CancellationToken(), scheduler, new LinkedList<>());

        job.schedule();
        job.getStatus().finishedEvent.await();

        assertEquals(5, counter[0]);
        //Why 5?
        //parent registers the action
        //childA registers the action
        //childB registers the action
        //parent register an action for callback when childA finishes
        //parent register an action for callback when childB finishes

        pool.shutdown();
    }

}
