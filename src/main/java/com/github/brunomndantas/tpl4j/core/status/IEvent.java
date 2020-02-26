package com.github.brunomndantas.tpl4j.core.status;

public interface IEvent {

    void addListener(Runnable listener);

    void removeListener(Runnable listener);

    boolean await() throws InterruptedException;

    boolean await(long timeout) throws InterruptedException;

    void fire();

    boolean hasFired();

}
