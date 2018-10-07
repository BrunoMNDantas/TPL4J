/*-
 * Copyright (c) 2016, NGSPipes Team <ngspipes@gmail.com>
 * All rights reserved.
 *
 * This file is part of NGSPipes <http://ngspipes.github.io/>.
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
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dantas.tpl4j.task;

import com.dantas.tpl4j.job.*;
import com.dantas.tpl4j.job.actions.CatchAction;
import com.dantas.tpl4j.job.events.JobEvents;
import com.dantas.tpl4j.task.actions.LinkTaskAction;
import com.dantas.tpl4j.task.actions.TaskLinkTaskAction;
import com.dantas.tpl4j.task.actions.VoidLinkTaskAction;

import java.util.function.Consumer;
import java.util.function.Supplier;

public class Task<T>  extends BaseTask<T> implements Runnable {

    public static final Consumer<Runnable> DEFAULT_SCHEDULER = (r) -> new Thread(r).start();



    public final Job<T> job;
    public final JobEvents events;
    public final Consumer<Runnable> scheduler;



    public Task(Job<T> job, Consumer<Runnable> scheduler) {
        super(job);

        this.job = job;
        this.events = job.events;
        this.scheduler = scheduler;
    }

    public Task(Job<T> job) {
        this(job, DEFAULT_SCHEDULER);
    }



    public <K> Task<K> then(Task<K> task) {
        this.events.finishedEvent.addListener(task::run);
        return task;
    }


    public <K> Task<K> then(LinkTaskAction<K, T> action, Consumer<Runnable> scheduler) {
        Job<K> job = new LinkJob<>(this.job, (res) -> action.run(this), scheduler);

        Task<K> task = new Task<>(job, scheduler);

        return this.then(task);
    }

    public <K> Task<K> then(LinkTaskAction<K, T> action) {
        return then(action, this.scheduler);
    }


    public Task<Void> then(VoidLinkTaskAction<T> action, Consumer<Runnable> scheduler) {
        Job<Void> job = new LinkVoidJob<>(this.job, (res) -> action.run(this), scheduler);

        Task<Void> task = new Task<>(job, scheduler);

        return this.then(task);
    }

    public Task<Void> then(VoidLinkTaskAction<T> action) {
        return then(action, this.scheduler);
    }


    public <K> Task<K> unwrap(TaskLinkTaskAction<K, T> action, Consumer<Runnable> scheduler) {
        Job<Task<K>> job = new LinkJob<>(this.job, (res) -> action.run(this), scheduler);
        Task<Task<K>> task = new Task<>(job, scheduler);
        then(task);

        Job<Job<K>> wrapJob = new Job<>(() -> job.getResult().job, scheduler);
        Task<K> unwrapTask = new Task<>(new UnwrapJob<>(wrapJob, scheduler), scheduler);
        return unwrapTask.then(unwrapTask);
    }

    public <K> Task<K> unwrap(TaskLinkTaskAction<K, T> action) {
        return unwrap(action, this.scheduler);
    }


    public Task<T> repeat(Supplier<Boolean> retrySupplier, Consumer<Runnable> scheduler) {
        RetryJob<T> job = new RetryJob<>(this.job, retrySupplier, scheduler);

        Task<T> task = new Task<>(job, scheduler);

        return then(task);
    }

    public Task<T> repeat(int numberOfTries, Consumer<Runnable> scheduler) {
        RetryJob<T> job = new RetryJob<>(this.job, numberOfTries, scheduler);

        Task<T> task = new Task<>(job, scheduler);

        return then(task);
    }

    public Task<T> repeat(Consumer<Runnable> scheduler) {
        RetryJob<T> job = new RetryJob<>(this.job, scheduler);

        Task<T> task = new Task<>(job, scheduler);

        return then(task);
    }

    public Task<T> repeat(Supplier<Boolean> retrySupplier) {
        return repeat(retrySupplier, this.scheduler);
    }

    public Task<T> repeat(int numberOfRetries) {
        return repeat(numberOfRetries, this.scheduler);
    }

    public Task<T> repeat() {
        return repeat(this.scheduler);
    }


    public Task<T> _catch(CatchAction<T> action, Consumer<Runnable> scheduler) {
        Job<T> job = new CatchJob<>(this.job, action, scheduler);

        Task<T> task = new Task<>(job, scheduler);

        return then(task);
    }

    public Task<T> _catch(CatchAction<T> action) {
        return _catch(action, this.scheduler);
    }

}