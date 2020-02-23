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
package com.github.brunomndantas.tpl4j.core.job;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.core.status.Status;

import java.util.function.Consumer;

public class SimpleJob<T> {

    protected final Object lock = new Object();

    protected volatile String taskId;
    public String getTaskId() { return this.taskId; }

    protected volatile IAction<T> action;
    public IAction<T> getAction() { return this.action; }

    protected volatile Consumer<Runnable> scheduler;
    public Consumer<Runnable> getScheduler() { return this.scheduler; }

    protected volatile Status status;
    public Status getStatus() { return this.status; }

    protected volatile CancellationToken cancellationToken;
    public CancellationToken getCancellationToken() { return this.cancellationToken; }

    protected T value;
    public T getValue() { synchronized (lock) { return this.value; } }
    protected void setValue(T value) { synchronized (lock) { this.value = value; } }

    protected Exception exception;
    public Exception getException() { synchronized (lock) { return this.exception; } }
    protected void setException(Exception exception) { synchronized (lock) { this.exception = exception; } }



    public SimpleJob(String taskId, IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this.taskId = taskId;
        this.action = action;
        this.cancellationToken = cancellationToken;
        this.scheduler = scheduler;
        this.status = new Status(taskId);
    }



    public T getResult() throws Exception {
        this.getStatus().finishedEvent.await();

        if(this.getStatus().getValue() == State.FAILED)
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
            if(this.status.scheduledEvent.hasFired())
                throw new RuntimeException("Task:" + this.taskId + " already scheduled!");

            this.status.declareSchedule();
            this.scheduler.accept(this::run);
        }
    }

    protected void run() {
        try {
            if(hasCancelRequest()) {
                declareCancel();
                return;
            }

            this.status.declareRun();

            if(hasCancelRequest()) {
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
    }

    protected void declareCancel() {
        this.status.declareCancel();
    }

    protected void declareSuccess() {
        this.status.declareSuccess();
    }

    protected void declareFail() {
        this.status.declareFail();
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

}
