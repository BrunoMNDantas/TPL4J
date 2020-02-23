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
    public void getInitialStateTest() {
        Status status = new Status("");
        assertSame(State.CREATED, status.getState());
    }

    @Test
    public void declareScheduleTest() {
        Status status = new Status("");

        status.declareSchedule();

        assertSame(State.SCHEDULED, status.getState());
        assertTrue(status.scheduledEvent.hasFired());
    }

    @Test
    public void declareRunTest() {
        Status status = new Status("");

        status.declareRun();

        assertSame(State.RUNNING, status.getState());
        assertTrue(status.runningEvent.hasFired());
    }

    @Test
    public void declareWaitChildrenTest() {
        Status status = new Status("");

        status.declareWaitChildren();

        assertSame(State.WAITING_CHILDREN, status.getState());
        assertTrue(status.waitingForChildrenEvent.hasFired());
    }

    @Test
    public void declareCancelTest() {
        Status status = new Status("");

        status.declareCancel();

        assertSame(State.CANCELED, status.getState());
        assertTrue(status.cancelledEvent.hasFired());
        assertTrue(status.finishedEvent.hasFired());
    }

    @Test
    public void declareFailTest() {
        Status status = new Status("");

        status.declareFail();

        assertSame(State.FAILED, status.getState());
        assertTrue(status.failedEvent.hasFired());
        assertTrue(status.finishedEvent.hasFired());
    }

    @Test
    public void declareSuccessTest() {
        Status status = new Status("");

        status.declareSuccess();

        assertSame(State.SUCCEEDED, status.getState());
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

