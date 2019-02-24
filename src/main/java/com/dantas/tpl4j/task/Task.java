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
package com.dantas.tpl4j.task;

import com.dantas.tpl4j.task.action.link.*;
import com.dantas.tpl4j.task.action.retry.RetryAction;
import com.dantas.tpl4j.task.core.BaseTask;
import com.dantas.tpl4j.task.core.TaskOption;
import com.dantas.tpl4j.task.core.action.*;
import com.dantas.tpl4j.task.core.job.Job;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Task<T> extends BaseTask<T> {

    public static final Consumer<Runnable> DEFAULT_SCHEDULER = (r) -> new Thread(r).start();
    public static final TaskOption[] DEFAULT_OPTIONS = new TaskOption[]{ TaskOption.ACCEPT_CHILDREN, TaskOption.DETACH_FROM_PARENT };



    public Task(Job<T> job) {
        super(job);
    }


    public Task(IAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(new Job<>(UUID.randomUUID().toString(), action, scheduler, Arrays.asList(options)));
    }

    public Task(IEmptyAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(new EmptyAction<>(action), scheduler, options);
    }

    public Task(IVoidAction action, Consumer<Runnable> scheduler, TaskOption... options) {
        this((IAction<T>)(new VoidAction(action)), scheduler, options);
    }

    public Task(IEmptyVoidAction action, Consumer<Runnable> scheduler, TaskOption... options) {
        this((IAction<T>) (new EmptyVoidAction(action)), scheduler, options);
    }


    public Task(IAction<T> action, TaskOption... options) {
        this(action, DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyAction<T> action, TaskOption... options) {
        this(new EmptyAction<>(action), DEFAULT_SCHEDULER, options);
    }

    public Task(IVoidAction action, TaskOption... options) {
        this((IAction<T>)(new VoidAction(action)), DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyVoidAction action, TaskOption... options) {
        this((IAction<T>) (new EmptyVoidAction(action)), DEFAULT_SCHEDULER, options);
    }


    public Task(IAction<T> action, Consumer<Runnable> scheduler) {
        this(action, scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action, Consumer<Runnable> scheduler) {
        this(new EmptyAction<>(action), scheduler, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action, Consumer<Runnable> scheduler) {
        this((IAction<T>)(new VoidAction(action)), scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action, Consumer<Runnable> scheduler) {
        this((IAction<T>) (new EmptyVoidAction(action)), scheduler, DEFAULT_OPTIONS);
    }


    public Task(IAction<T> action) {
        this(action, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action) {
        this(new EmptyAction<>(action), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action) {
        this((IAction<T>)(new VoidAction(action)), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action) {
        this((IAction<T>) (new EmptyVoidAction(action)), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }



    public <K> Task<K> then(Task<K> task) {
        super.getStatus().finishedEvent.addListener(task::start);
        return task;
    }

    public <K> Task<K> then(ILinkAction<K, T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        IAction<K> taskAction = new LinkAction<>(this, action);
        Task<K> task = new Task<>(taskAction, scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(ILinkVoidAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        IAction<Void> taskAction = new LinkVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskAction, scheduler, options);

        return this.then(task);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, Consumer<Runnable> scheduler, TaskOption... options) {
        IAction<K> taskAction = new LinkEmptyAction<>(this, action);
        Task<K> task = new Task<>(taskAction, scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, Consumer<Runnable> scheduler, TaskOption... options) {
        IAction<Void> taskAction = new LinkEmptyVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskAction, scheduler, options);

        return this.then(task);
    }


    public <K> Task<K> then(ILinkAction<K, T> action, Consumer<Runnable> scheduler) {
        return this.then(action, scheduler, this.getOptions().toArray(new TaskOption[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action, Consumer<Runnable> scheduler) {
        return this.then(action, scheduler, this.getOptions().toArray(new TaskOption[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, Consumer<Runnable> scheduler) {
        return this.then(action, scheduler, this.getOptions().toArray(new TaskOption[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action, Consumer<Runnable> scheduler) {
        return this.then(action, scheduler, this.getOptions().toArray(new TaskOption[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, TaskOption... options) {
        return this.then(action, this.getScheduler(), options);
    }

    public Task<Void> then(ILinkVoidAction<T> action, TaskOption... options) {
        return this.then(action, this.getScheduler(), options);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, TaskOption... options) {
        return this.then(action, this.getScheduler(), options);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, TaskOption... options) {
        return this.then(action, this.getScheduler(), options);
    }


    public <K> Task<K> then(ILinkAction<K, T> action) {
        return this.then(action, this.getScheduler(), this.getOptions().toArray(new TaskOption[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action) {
        return this.then(action, this.getScheduler(), this.getOptions().toArray(new TaskOption[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action) {
        return this.then(action, this.getScheduler(), this.getOptions().toArray(new TaskOption[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action) {
        return this.then(action, this.getScheduler(), this.getOptions().toArray(new TaskOption[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, Consumer<Runnable> scheduler, TaskOption... options) {
        IAction<T> taskAction = new RetryAction<>(this, retrySupplier);
        Task<T> task = new Task<>(taskAction, scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(int numberOfTries, Consumer<Runnable> scheduler, TaskOption... options) {
        IAction<T> taskAction = new RetryAction<>(this, numberOfTries);
        Task<T> task = new Task<>(taskAction, scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(Consumer<Runnable> scheduler, TaskOption... options) {
        IAction<T> taskAction = new RetryAction<>(this);
        Task<T> task = new Task<>(taskAction, scheduler, options);

        return this.then(task);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, Consumer<Runnable> scheduler) {
        return this.retry(retrySupplier, scheduler, this.getOptions().toArray(new TaskOption[0]));
    }

    public Task<T> retry(int numberOfTries, Consumer<Runnable> scheduler) {
        return this.retry(numberOfTries, scheduler, this.getOptions().toArray(new TaskOption[0]));
    }

    public Task<T> retry(Consumer<Runnable> scheduler) {
        return this.retry(scheduler, this.getOptions().toArray(new TaskOption[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, TaskOption... options) {
        return this.retry(retrySupplier, this.getScheduler(), options);
    }

    public Task<T> retry(int numberOfTries, TaskOption... options) {
        return this.retry(numberOfTries, this.getScheduler(), options);
    }

    public Task<T> retry(TaskOption... options) {
        return this.retry(this.getScheduler(), options);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier) {
        return this.retry(retrySupplier, this.getScheduler(), this.getOptions().toArray(new TaskOption[0]));
    }

    public Task<T> retry(int numberOfTries) {
        return this.retry(numberOfTries, this.getScheduler(), this.getOptions().toArray(new TaskOption[0]));
    }

    public Task<T> retry() {
        return this.retry(this.getScheduler(), this.getOptions().toArray(new TaskOption[0]));
    }

}
