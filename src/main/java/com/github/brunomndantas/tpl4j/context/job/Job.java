/*-
* Copyright (c) 2019, Bruno Dantas <bmndantas@gmail.com>
* All rights reserved.
*
* This program is free software: you can redistribute it and/or modify it
* under the terms of the GNU General Public License as published by the
* Free Software Foundation, either version 3 of the License, or (at your
* option) any later version.
*
* This program is distributed in the hope that it will be useful, but
* WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
* or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
* for more details.
*
* You should have received a copy of the GNU General Public License along
* with this program.  If not, see <http://www.gnu.org/licenses/>.  */
package com.github.brunomndantas.tpl4j.context.job;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.core.status.Status;

import java.util.Collection;
import java.util.function.Consumer;

public class Job<T> {

    protected final Object lock = new Object();

    protected volatile String taskId;
    public String getTaskId() { return this.taskId; }

    protected volatile IAction<T> action;
    public IAction<T> getAction() { return this.action; }

    protected volatile Consumer<Runnable> scheduler;
    public Consumer<Runnable> getScheduler() { return this.scheduler; }

    protected volatile Status status;
    public Status getStatus() { return this.status; }

    protected volatile ICancellationToken cancellationToken;
    public ICancellationToken getCancellationToken() { return this.cancellationToken; }

    protected T value;
    public T getValue() { synchronized (lock) { return this.value; } }
    protected void setValue(T value) { synchronized (lock) { this.value = value; } }

    protected Exception exception;
    public Exception getException() { synchronized (lock) { return this.exception; } }
    protected void setException(Exception exception) { synchronized (lock) { this.exception = exception; } }

    protected volatile Options options;
    public Options getOptions() { return this.options; }



    public Job(String taskId, IAction<T> action, ICancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<Option> options) {
        this.taskId = taskId;
        this.action = action;
        this.cancellationToken = cancellationToken;
        this.scheduler = scheduler;
        this.status = new Status(taskId);
        this.options = new Options(options);

        JobManager.INSTANCE.registerJobCreationOnCurrentThread(this);
    }



    public T getResult() throws Exception {
        this.getStatus().getFinishedEvent().await();

        if(this.getStatus().getState() == State.FAILED)
            throw this.getException();
        else
            return this.getValue();
    }

    public void cancel() {
        cancellationToken.cancel();
    }

    public boolean hasCancelRequest() {
        return cancellationToken.hasCancelRequest();
    }

    public void schedule() {
        synchronized (lock) {
            if(this.status.getScheduledEvent().hasFired())
                throw new RuntimeException("Task:" + this.taskId + " already scheduled!");

            this.status.setState(State.SCHEDULED);
            this.scheduler.accept(this::run);
        }
    }

    protected void run() {
        JobManager.INSTANCE.registerJobExecutionOnCurrentThread(this);

        try {
            if(hasCancelRequest() && !this.options.contains(Option.NOT_CANCELABLE)) {
                declareCancel();
                return;
            }

            this.status.setState(State.RUNNING);

            if(hasCancelRequest() && !this.options.contains(Option.NOT_CANCELABLE)) {
                declareCancel();
                return;
            }

            executeAction();

            declareSuccess();
        } catch(Exception e) {
            if(e instanceof CancelledException)
                declareCancel();
            else
                declareFail();
        }

        JobManager.INSTANCE.unregisterJobExecution(this);
    }

    protected T executeAction() throws Exception {
        try {
            T value = this.action.run(cancellationToken);
            setValue(value);
            return value;
        } catch (Exception e) {
            if(!(e instanceof CancelledException))
                setException(e);

            throw e;
        }
    }

    protected void declareCancel() {
        Collection<JobContext> children = JobManager.INSTANCE.getAttachedChildrenContexts(this);

        if(children.isEmpty())
            this.status.setState(State.CANCELED);
        else
            registerCancelAfterChildren(children);
    }

    protected void registerCancelAfterChildren(Collection<JobContext> childrenContexts) {
        this.status.setState(State.WAITING_CHILDREN);

        for(JobContext childContext : childrenContexts) {
            childContext.getJob().cancel();

            childContext.getJob().status.getFinishedEvent().addListener(() -> {
                this.scheduler.accept(() -> {
                    synchronized (childContext) {
                        if(!this.status.getFinishedEvent().hasFired() && allFinished(childrenContexts))
                            declareCancelAfterChildren(childrenContexts);
                    }
                });
            });
        }
    }

    protected void declareCancelAfterChildren(Collection<JobContext> childrenContexts) {
        for(JobContext childContext : childrenContexts) {
            if(childContext.getJob().status.getFailedEvent().hasFired()) {
                Exception childException = childContext.getJob().getException();
                childException = new ChildException(childException.getMessage(), childException);
                this.setException(childException);
                this.status.setState(State.FAILED);
                return;
            }
        }

        this.status.setState(State.CANCELED);
    }

    protected void declareSuccess() {
        Collection<JobContext> children = JobManager.INSTANCE.getAttachedChildrenContexts(this);

        if(children.isEmpty())
            this.status.setState(State.SUCCEEDED);
        else
            registerSuccessAfterChildren(children);
    }

    protected void registerSuccessAfterChildren(Collection<JobContext> childrenContexts) {
        this.status.setState(State.WAITING_CHILDREN);

        synchronized (childrenContexts){
            for(JobContext childContext : childrenContexts) {
                childContext.getJob().status.getFinishedEvent().addListener(() -> {
                    this.scheduler.accept(() -> {
                        synchronized (childContext) {
                            if(!this.status.getFinishedEvent().hasFired() && allFinished(childrenContexts))
                                declareSuccessAfterChildren(childrenContexts);
                        }
                    });
                });
            }
        }
    }

    protected void declareSuccessAfterChildren(Collection<JobContext> childrenContexts) {
        for(JobContext childContext : childrenContexts) {
            if(childContext.getJob().status.getFailedEvent().hasFired()) {
                Exception childException = childContext.getJob().getException();
                childException = new ChildException(childException.getMessage(), childException);
                this.setException(childException);
                this.status.setState(State.FAILED);
                return;
            }
        }

        this.status.setState(State.SUCCEEDED);
    }

    protected void declareFail() {
        Collection<JobContext> children = JobManager.INSTANCE.getAttachedChildrenContexts(this);

        if(children.isEmpty())
            this.status.setState(State.FAILED);
        else
            registerFailAfterChildren(children);
    }

    protected void registerFailAfterChildren(Collection<JobContext> childrenContexts) {
        this.status.setState(State.WAITING_CHILDREN);

        for(JobContext childContext : childrenContexts) {
            childContext.getJob().status.getFinishedEvent().addListener(() -> {
                this.scheduler.accept(() -> {
                    synchronized (childContext) {
                        if(!this.status.getFinishedEvent().hasFired() && allFinished(childrenContexts))
                            declareFailAfterChildren(childrenContexts);
                    }
                });
            });
        }
    }

    protected void declareFailAfterChildren(Collection<JobContext> childrenContexts) {
        this.status.setState(State.FAILED);
    }

    private boolean allFinished(Collection<JobContext> contexts) {
        return contexts
                .stream()
                .allMatch((context) -> context.getJob().status.getFinishedEvent().hasFired());
    }

}
