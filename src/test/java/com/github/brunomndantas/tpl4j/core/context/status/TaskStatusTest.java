package com.github.brunomndantas.tpl4j.core.context.status;

import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class TaskStatusTest {

    @Test
    public void getTaskIdTest() {
        String taskId = "Id";
        TaskStatus status = new TaskStatus(taskId);
        assertSame(taskId, status.getTaskId());
    }

    @Test
    public void getInitialValueTest() {
        TaskStatus status = new TaskStatus("");
        assertSame(Status.CREATED, status.getValue());
    }

    @Test
    public void declareScheduleTest() {
        TaskStatus status = new TaskStatus("");

        status.declareSchedule();

        assertSame(Status.SCHEDULED, status.getValue());
        assertTrue(status.scheduledEvent.hasFired());
    }

    @Test
    public void declareRunTest() {
        TaskStatus status = new TaskStatus("");

        status.declareRun();

        assertSame(Status.RUNNING, status.getValue());
        assertTrue(status.runningEvent.hasFired());
    }

    @Test
    public void declareWaitChildrenTest() {
        TaskStatus status = new TaskStatus("");

        status.declareWaitChildren();

        assertSame(Status.WAITING_CHILDREN, status.getValue());
        assertTrue(status.waitingForChildrenEvent.hasFired());
    }

    @Test
    public void declareCancelTest() {
        TaskStatus status = new TaskStatus("");

        status.declareCancel();

        assertSame(Status.CANCELED, status.getValue());
        assertTrue(status.cancelledEvent.hasFired());
        assertTrue(status.finishedEvent.hasFired());
    }

    @Test
    public void declareFailTest() {
        TaskStatus status = new TaskStatus("");

        status.declareFail();

        assertSame(Status.FAILED, status.getValue());
        assertTrue(status.failedEvent.hasFired());
        assertTrue(status.finishedEvent.hasFired());
    }

    @Test
    public void declareSuccessTest() {
        TaskStatus status = new TaskStatus("");

        status.declareSuccess();

        assertSame(Status.SUCCEEDED, status.getValue());
        assertTrue(status.succeededEvent.hasFired());
        assertTrue(status.finishedEvent.hasFired());
    }

    @Test
    public void declareFinishTest() {
        TaskStatus status = new TaskStatus("");

        status.declareFinish();

        assertTrue(status.finishedEvent.hasFired());
    }

}

