package com.github.brunomndantas.tpl4j.core.scheduler;

import java.util.UUID;

public class SingleThreadScheduler extends PoolScheduler {

    public SingleThreadScheduler(String id) {
        super(id, 1);
    }

    public SingleThreadScheduler() {
        this(UUID.randomUUID().toString());
    }

}
