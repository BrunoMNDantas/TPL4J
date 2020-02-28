package com.github.brunomndantas.tpl4j.core.scheduler;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolScheduler implements IScheduler {

    protected volatile ExecutorService pool;
    private volatile String id;

    protected volatile int numberOfThreads;
    public int getNumberOfThreads() { return this.numberOfThreads; }



    public PoolScheduler(String id, int numberOfThreads) {
        this.id = id;
        this.numberOfThreads = numberOfThreads;
        this.pool = Executors.newFixedThreadPool(numberOfThreads);
    }

    public PoolScheduler(int numberOfThreads) {
        this(UUID.randomUUID().toString(), numberOfThreads);
    }

    public PoolScheduler(String id) {
        this(id, Runtime.getRuntime().availableProcessors());
    }

    public PoolScheduler() {
        this(UUID.randomUUID().toString(), Runtime.getRuntime().availableProcessors());
    }



    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void schedule(Runnable action) {
        this.pool.submit(action);
    }

    @Override
    public void close() {
        this.pool.shutdown();
    }

}
