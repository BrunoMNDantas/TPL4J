package com.github.brunomndantas.tpl4j.core.scheduler;

public interface IScheduler {

    String getId();

    void schedule(Runnable action);

    void close();

}
