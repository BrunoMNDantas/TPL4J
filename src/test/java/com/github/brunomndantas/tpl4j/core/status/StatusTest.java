package com.github.brunomndantas.tpl4j.core.status;

import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class StatusTest {

    @Test
    public void getTaskIdTest() {
        String taskId = "Id";
        Status status = new Status(taskId);
        assertSame(taskId, status.getTaskId());
    }

    @Test
    public void getInitialValueTest() {
        Status status = new Status("");
        assertSame(State.CREATED, status.getValue());
    }

    @Test
    public void declareScheduleTest() {
        Status status = new Status("");

        status.declareSchedule();

        assertSame(State.SCHEDULED, status.getValue());
        assertTrue(status.scheduledEvent.hasFired());
    }

    @Test
    public void declareRunTest() {
        Status status = new Status("");

        status.declareRun();

        assertSame(State.RUNNING, status.getValue());
        assertTrue(status.runningEvent.hasFired());
    }

    @Test
    public void declareWaitChildrenTest() {
        Status status = new Status("");

        status.declareWaitChildren();

        assertSame(State.WAITING_CHILDREN, status.getValue());
        assertTrue(status.waitingForChildrenEvent.hasFired());
    }

    @Test
    public void declareCancelTest() {
        Status status = new Status("");

        status.declareCancel();

        assertSame(State.CANCELED, status.getValue());
        assertTrue(status.cancelledEvent.hasFired());
        assertTrue(status.finishedEvent.hasFired());
    }

    @Test
    public void declareFailTest() {
        Status status = new Status("");

        status.declareFail();

        assertSame(State.FAILED, status.getValue());
        assertTrue(status.failedEvent.hasFired());
        assertTrue(status.finishedEvent.hasFired());
    }

    @Test
    public void declareSuccessTest() {
        Status status = new Status("");

        status.declareSuccess();

        assertSame(State.SUCCEEDED, status.getValue());
        assertTrue(status.succeededEvent.hasFired());
        assertTrue(status.finishedEvent.hasFired());
    }

    @Test
    public void declareFinishTest() {
        Status status = new Status("");

        status.declareFinish();

        assertTrue(status.finishedEvent.hasFired());
    }

}

