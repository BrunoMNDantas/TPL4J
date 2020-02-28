package com.github.brunomndantas.tpl4j.core.scheduler;

import java.util.UUID;

public class DedicatedThreadScheduler implements IScheduler {

    protected volatile String id;



    public DedicatedThreadScheduler(String id) {
        this.id = id;
    }

    public DedicatedThreadScheduler() {
        this(UUID.randomUUID().toString());
    }



    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void schedule(Runnable action) {
        new Thread(action).start();
    }

    @Override
    public void close() {

    }

}
