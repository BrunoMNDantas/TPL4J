package com.github.brunomndantas.tpl4j.core.status;

public interface IStatus {

    State getState();

    void setState(State state);

    IEvent getScheduledEvent();

    IEvent getRunningEvent();

    IEvent getWaitingForChildrenEvent();

    IEvent getCancelledEvent();

    IEvent getFailedEvent();

    IEvent getSucceededEvent();

    IEvent getFinishedEvent();

}
