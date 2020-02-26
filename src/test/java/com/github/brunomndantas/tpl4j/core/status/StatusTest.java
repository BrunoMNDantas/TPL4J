package com.github.brunomndantas.tpl4j.core.status;

import org.junit.Test;

import static org.junit.Assert.*;

public class StatusTest {

    @Test
    public void getTaskIdTest() {
        String taskId = "Id";
        Status status = new Status(taskId);
        assertSame(taskId, status.getTaskId());
    }

    @Test
    public void constructorStateTest() {
        String id = "ID";
        Status status = new Status(id);
        assertSame(id, status.getTaskId());
        assertSame(State.CREATED, status.getState());
    }

    @Test
    public void getState() {
        Status status = new Status("");
        assertSame(State.CREATED, status.getState());
    }

    @Test
    public void setState() {
        Status status = new Status("Id");
        assertFalse(status.getScheduledEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());
        status.setState(State.SCHEDULED);
        assertTrue(status.getScheduledEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());

        status = new Status("Id");
        assertFalse(status.getRunningEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());
        status.setState(State.RUNNING);
        assertTrue(status.getRunningEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());

        status = new Status("Id");
        assertFalse(status.getWaitingForChildrenEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());
        status.setState(State.WAITING_CHILDREN);
        assertTrue(status.getWaitingForChildrenEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());

        status = new Status("Id");
        assertFalse(status.getSucceededEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());
        status.setState(State.SUCCEEDED);
        assertTrue(status.getSucceededEvent().hasFired());
        assertTrue(status.getFinishedEvent().hasFired());

        status = new Status("Id");
        assertFalse(status.getCancelledEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());
        status.setState(State.CANCELED);
        assertTrue(status.getCancelledEvent().hasFired());
        assertTrue(status.getFinishedEvent().hasFired());

        status = new Status("Id");
        assertFalse(status.getFailedEvent().hasFired());
        assertFalse(status.getFinishedEvent().hasFired());
        status.setState(State.FAILED);
        assertTrue(status.getFailedEvent().hasFired());
        assertTrue(status.getFinishedEvent().hasFired());

        try {
            status = new Status("Id");
            status.setState(State.CREATED);
        } catch (Exception e) {
            assertNotNull(e.getMessage());
            assertTrue(e.getMessage().contains("Invalid"));
            assertTrue(e.getMessage().contains("CREATED"));
        }
    }

    @Test
    public void getScheduledEventTest() {
        Status status = new Status("Id");
        IEvent event = status.getScheduledEvent();
        assertNotNull(event);
        assertFalse(event.hasFired());
    }

    @Test
    public void getRunningEventTest() {
        Status status = new Status("Id");
        IEvent event = status.getRunningEvent();
        assertNotNull(event);
        assertFalse(event.hasFired());
    }

    @Test
    public void getWaitingForChildrenEventTest() {
        Status status = new Status("Id");
        IEvent event = status.getWaitingForChildrenEvent();
        assertNotNull(event);
        assertFalse(event.hasFired());
    }

    @Test
    public void getCancelledEventTest() {
        Status status = new Status("Id");
        IEvent event = status.getCancelledEvent();
        assertNotNull(event);
        assertFalse(event.hasFired());
    }

    @Test
    public void getFailedEventTest() {
        Status status = new Status("Id");
        IEvent event = status.getFailedEvent();
        assertNotNull(event);
        assertFalse(event.hasFired());
    }

    @Test
    public void getSucceededEventTest() {
        Status status = new Status("Id");
        IEvent event = status.getSucceededEvent();
        assertNotNull(event);
        assertFalse(event.hasFired());
    }

    @Test
    public void getFinishedEventTest() {
        Status status = new Status("Id");
        IEvent event = status.getFinishedEvent();
        assertNotNull(event);
        assertFalse(event.hasFired());
    }

}

