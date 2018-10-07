package com.dantas.tpl4j.task;

import com.dantas.tpl4j.job.Job;
import com.dantas.tpl4j.job.JobStatus;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BaseTask<T> implements Future<T>, Runnable {

    protected Job<T> job;



    public BaseTask(Job<T> job) {
        this.job = job;
    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return job.cancel(mayInterruptIfRunning);
    }

    @Override
    public boolean isCancelled() {
        return job.isCancelled();
    }

    @Override
    public boolean isDone() {
        return job.isDone();
    }

    @Override
    public T get() throws InterruptedException, ExecutionException {
        return job.get();
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return job.get(timeout, unit);
    }


    public JobStatus getStatus() {
        return job.getStatus();
    }

    public T getValue() {
        return job.getValue();
    }

    public Exception getException(){
        return job.getException();
    }

    public void cancel(){
        job.cancel();
    }

    public T getResult() throws Exception {
        return job.getResult();
    }

    public T getResult(long timeout) throws Exception {
        return job.getResult(timeout);
    }


    @Override
    public void run() {
        job.schedule();
    }

}
