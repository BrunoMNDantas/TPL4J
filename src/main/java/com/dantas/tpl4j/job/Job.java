package com.dantas.tpl4j.job;

import com.dantas.tpl4j.job.actions.Action;
import com.dantas.tpl4j.job.events.JobEvents;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Consumer;

public class Job<T> implements Runnable, Future<T> {

    protected Object lock = new Object();

    public JobEvents events = new JobEvents();

    protected JobStatus status = JobStatus.CREATED;
    public JobStatus getStatus() { synchronized (lock) { return status; } }
    protected void setStatus(JobStatus status) { synchronized (lock) { this.status = status; } }

    protected T value;
    public T getValue() { synchronized (lock) { return value; } }
    protected void setValue(T value) { synchronized (lock) { this.value = value; } }

    protected Exception exception;
    public Exception getException(){ synchronized (lock) { return exception; } }
    protected void setException(Exception exception) { synchronized (lock) { this.exception = exception; } }

    protected boolean hasCancelRequest;

    protected Action<T> action;
    protected Consumer<Runnable> scheduler;



    public Job(Action<T> action, Consumer<Runnable> scheduler) {
        this.action = action;
        this.scheduler = scheduler;
    }



    public void schedule() {
        declareSchedule();
        scheduler.accept(this);
    }

    @Override
    public void run() {
        try {
            if(hasCancelRequest()) {
                declareCancel();
                return;
            }

            declareRun();

            if(hasCancelRequest()) {
                declareCancel();
                return;
            }

            T value = execute();
            this.setValue(value);

            if(hasCancelRequest()) {
                declareCancel();
                return;
            }

            declareSuccess();
        } catch(Exception e) {
            this.setException(e);
            declareFail();
        } finally {
            declareFinish();
        }
    }

    protected boolean hasCancelRequest() {
        synchronized (lock) {
            return hasCancelRequest;
        }
    }

    protected T execute() throws Exception {
        return action.run();
    }


    protected void declareSchedule() {
        synchronized (lock) {
            this.setStatus(JobStatus.SCHEDULED);
            this.events.scheduledEvent.fire();
        }
    }

    protected void declareRun() {
        synchronized (lock) {
            this.setStatus(JobStatus.RUNNING);
            this.events.runningEvent.fire();
        }
    }

    protected void declareCancel() {
        synchronized (lock) {
            this.setStatus(JobStatus.CANCELED);
            this.events.cancelledEvent.fire();
        }
    }

    protected void declareFail() {
        synchronized (lock) {
            this.setStatus(JobStatus.FAILED);
            this.events.failedEvent.fire();
        }
    }

    protected void declareSuccess() {
        synchronized (lock) {
            this.setStatus(JobStatus.SUCCEEDED);
            this.events.succeededEvent.fire();
        }
    }

    protected void declareFinish() {
        synchronized (lock) {
            this.events.finishedEvent.fire();
        }
    }


    public T getResult() throws Exception {
        events.finishedEvent.await();

        if(this.getException() != null)
            throw this.getException();
        else
            return this.getValue();
    }

    public T getResult(long timeout) throws Exception {
        if(!events.finishedEvent.await(timeout))
            throw new TimeoutException();

        if(this.getException() != null)
            throw this.getException();
        else
            return this.getValue();
    }

    public void cancel(){
        synchronized (lock) {
            this.hasCancelRequest = true;
        }
    }


    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        synchronized (lock) {
            this.hasCancelRequest = true;
        }

        return true;
    }

    @Override
    public boolean isCancelled() {
        return this.events.cancelledEvent.hasFired();
    }

    @Override
    public boolean isDone() {
        return this.events.finishedEvent.hasFired();
    }

    @Override
    public T get() throws ExecutionException {
        try{
            return getResult();
        } catch(Exception e) {
            throw new ExecutionException(e);
        }
    }

    @Override
    public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        try{
            return getResult(TimeUnit.MICROSECONDS.convert(timeout, unit));
        } catch(Exception e) {
            if(e instanceof InterruptedException)
                throw (InterruptedException) e;

            if(e instanceof TimeoutException)
                throw (TimeoutException) e;

            throw new ExecutionException(e);
        }
    }

}
