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
package com.github.brunomndantas.tpl4j.task;

import com.github.brunomndantas.tpl4j.core.BaseTask;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.job.Job;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.action.action.*;
import com.github.brunomndantas.tpl4j.task.action.link.*;
import com.github.brunomndantas.tpl4j.task.action.retry.RetryAction;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Task<T> extends BaseTask<T> {

    public static final Consumer<Runnable> DEFAULT_SCHEDULER = (r) -> new Thread(r).start();
    public static final Option[] DEFAULT_OPTIONS = new Option[]{ Option.ACCEPT_CHILDREN, Option.DETACH_FROM_PARENT };



    public Task(Job<T> job) {
        super(job);
    }


    public Task(String taskId, IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        this(new Job<>(taskId, action, cancellationToken, scheduler, Arrays.asList(options)));
    }

    public Task(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        this(taskId, new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    public Task(String taskId, IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        this(taskId, (IAction<T>)(new VoidAction(action)), cancellationToken, scheduler, options);
    }

    public Task(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, scheduler, options);
    }


    public Task(String taskId, IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, action, cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, new EmptyAction<>(action), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, (IAction<T>)(new VoidAction(action)), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }


    public Task(String taskId, IAction<T> action, CancellationToken cancellationToken, Option... options) {
        this(taskId, action, cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        this(taskId, new EmptyAction<>(action), cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IVoidAction action, CancellationToken cancellationToken, Option... options) {
        this(taskId, (IAction<T>)(new VoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, options);
    }


    public Task(String taskId, IAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        this(new Job<>(taskId, action, new CancellationToken(), scheduler, Arrays.asList(options)));
    }

    public Task(String taskId, IEmptyAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        this(taskId, new EmptyAction<>(action), new CancellationToken(), scheduler, options);
    }

    public Task(String taskId, IVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        this(taskId, (IAction<T>)(new VoidAction(action)), new CancellationToken(), scheduler, options);
    }

    public Task(String taskId, IEmptyVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), scheduler, options);
    }


    public Task(String taskId, IAction<T> action, CancellationToken cancellationToken) {
        this(taskId, action, cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken) {
        this(taskId, new EmptyAction<>(action), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IVoidAction action, CancellationToken cancellationToken) {
        this(taskId, (IAction<T>)(new VoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }


    public Task(String taskId, IAction<T> action, Consumer<Runnable> scheduler) {
        this(taskId, action, new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyAction<T> action, Consumer<Runnable> scheduler) {
        this(taskId, new EmptyAction<>(action), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IVoidAction action, Consumer<Runnable> scheduler) {
        this(taskId, (IAction<T>)(new VoidAction(action)), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyVoidAction action, Consumer<Runnable> scheduler) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }


    public Task(String taskId, IAction<T> action, Option... options) {
        this(taskId, action, new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IEmptyAction<T> action, Option... options) {
        this(taskId, new EmptyAction<>(action), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IVoidAction action, Option... options) {
        this(taskId, (IAction<T>)(new VoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IEmptyVoidAction action, Option... options) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }


    public Task(String taskId, IAction<T> action) {
        this(taskId, action, new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyAction<T> action) {
        this(taskId, new EmptyAction<>(action), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IVoidAction action) {
        this(taskId, (IAction<T>)(new VoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyVoidAction action) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }


    public Task(IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        this(UUID.randomUUID().toString(), action, cancellationToken, scheduler, options);
    }

    public Task(IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        this(new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    public Task(IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        this((IAction<T>)(new VoidAction(action)), cancellationToken, scheduler, options);
    }

    public Task(IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        this((IAction<T>) (new EmptyVoidAction(action)), cancellationToken, scheduler, options);
    }


    public Task(IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(action, cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(new EmptyAction<>(action), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this((IAction<T>)(new VoidAction(action)), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this((IAction<T>) (new EmptyVoidAction(action)), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }


    public Task(IAction<T> action, CancellationToken cancellationToken, Option... options) {
        this(action, cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        this(new EmptyAction<>(action), cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(IVoidAction action, CancellationToken cancellationToken, Option... options) {
        this((IAction<T>)(new VoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        this((IAction<T>) (new EmptyVoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, options);
    }


    public Task(IAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        this(UUID.randomUUID().toString(), action, new CancellationToken(), scheduler, options);
    }

    public Task(IEmptyAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        this(new EmptyAction<>(action), new CancellationToken(), scheduler, options);
    }

    public Task(IVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        this((IAction<T>)(new VoidAction(action)), new CancellationToken(), scheduler, options);
    }

    public Task(IEmptyVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        this((IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), scheduler, options);
    }


    public Task(IAction<T> action, CancellationToken cancellationToken) {
        this(action, cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action, CancellationToken cancellationToken) {
        this(new EmptyAction<>(action), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action, CancellationToken cancellationToken) {
        this((IAction<T>)(new VoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action, CancellationToken cancellationToken) {
        this((IAction<T>) (new EmptyVoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }


    public Task(IAction<T> action, Consumer<Runnable> scheduler) {
        this(action, new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action, Consumer<Runnable> scheduler) {
        this(new EmptyAction<>(action), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action, Consumer<Runnable> scheduler) {
        this((IAction<T>)(new VoidAction(action)), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action, Consumer<Runnable> scheduler) {
        this((IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }


    public Task(IAction<T> action, Option... options) {
        this(action, new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyAction<T> action, Option... options) {
        this(new EmptyAction<>(action), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(IVoidAction action, Option... options) {
        this((IAction<T>)(new VoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyVoidAction action, Option... options) {
        this((IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }


    public Task(IAction<T> action) {
        this(action, new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action) {
        this(new EmptyAction<>(action), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action) {
        this((IAction<T>)(new VoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action) {
        this((IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }



    public <K> Task<K> then(Task<K> task) {
        super.getStatus().finishedEvent.addListener(task::start);
        return task;
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<K> taskAction = new LinkAction<>(this, action);
        Task<K> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<Void> taskAction = new LinkVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<K> taskAction = new LinkEmptyAction<>(this, action);
        Task<K> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<Void> taskAction = new LinkEmptyVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.then(taskId, action, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.then(taskId, action, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.then(taskId, action, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.then(taskId, action, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, CancellationToken cancellationToken, Option... options) {
        return this.then(taskId, action, cancellationToken, this.getScheduler(), options);
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.then(taskId, action, cancellationToken, this.getScheduler(), options);
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, CancellationToken cancellationToken, Option... options) {
        return this.then(taskId, action, cancellationToken, this.getScheduler(), options);
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return this.then(taskId, action, cancellationToken, this.getScheduler(), options);
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, Consumer<Runnable> scheduler, Option... options) {
        IAction<K> taskAction = new LinkAction<>(this, action);
        Task<K> task = new Task<>(taskId, taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        IAction<Void> taskAction = new LinkVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskId, taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, Consumer<Runnable> scheduler, Option... options) {
        IAction<K> taskAction = new LinkEmptyAction<>(this, action);
        Task<K> task = new Task<>(taskId, taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        IAction<Void> taskAction = new LinkEmptyVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskId, taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, CancellationToken cancellationToken) {
        return this.then(taskId, action, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, CancellationToken cancellationToken) {
        return this.then(taskId, action, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, CancellationToken cancellationToken) {
        return this.then(taskId, action, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, CancellationToken cancellationToken) {
        return this.then(taskId, action, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, Consumer<Runnable> scheduler) {
        return this.then(taskId, action, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, Consumer<Runnable> scheduler) {
        return this.then(taskId, action, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, Consumer<Runnable> scheduler) {
        return this.then(taskId, action, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, Consumer<Runnable> scheduler) {
        return this.then(taskId, action, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, Option... options) {
        return this.then(taskId, action, this.getCancellationToken(), this.getScheduler(), options);
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, Option... options) {
        return this.then(taskId, action, this.getCancellationToken(), this.getScheduler(), options);
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, Option... options) {
        return this.then(taskId, action, this.getCancellationToken(), this.getScheduler(), options);
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, Option... options) {
        return this.then(taskId, action, this.getCancellationToken(), this.getScheduler(), options);
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action) {
        return this.then(taskId, action, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action) {
        return this.then(taskId, action, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action) {
        return this.then(taskId, action, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action) {
        return this.then(taskId, action, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<K> taskAction = new LinkAction<>(this, action);
        Task<K> task = new Task<>(taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(ILinkVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<Void> taskAction = new LinkVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<K> taskAction = new LinkEmptyAction<>(this, action);
        Task<K> task = new Task<>(taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<Void> taskAction = new LinkEmptyVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }


    public <K> Task<K> then(ILinkAction<K, T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.then(action, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.then(action, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.then(action, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.then(action, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, CancellationToken cancellationToken, Option... options) {
        return this.then(action, cancellationToken, this.getScheduler(), options);
    }

    public Task<Void> then(ILinkVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.then(action, cancellationToken, this.getScheduler(), options);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, CancellationToken cancellationToken, Option... options) {
        return this.then(action, cancellationToken, this.getScheduler(), options);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return this.then(action, cancellationToken, this.getScheduler(), options);
    }


    public <K> Task<K> then(ILinkAction<K, T> action, Consumer<Runnable> scheduler, Option... options) {
        IAction<K> taskAction = new LinkAction<>(this, action);
        Task<K> task = new Task<>(taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(ILinkVoidAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        IAction<Void> taskAction = new LinkVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, Consumer<Runnable> scheduler, Option... options) {
        IAction<K> taskAction = new LinkEmptyAction<>(this, action);
        Task<K> task = new Task<>(taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        IAction<Void> taskAction = new LinkEmptyVoidAction<>(this, action);
        Task<Void> task = new Task<>(taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }


    public <K> Task<K> then(ILinkAction<K, T> action, CancellationToken cancellationToken) {
        return this.then(action, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action, CancellationToken cancellationToken) {
        return this.then(action, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, CancellationToken cancellationToken) {
        return this.then(action, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action, CancellationToken cancellationToken) {
        return this.then(action, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, Consumer<Runnable> scheduler) {
        return this.then(action, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action, Consumer<Runnable> scheduler) {
        return this.then(action, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, Consumer<Runnable> scheduler) {
        return this.then(action, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action, Consumer<Runnable> scheduler) {
        return this.then(action, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, Option... options) {
        return this.then(action, this.getCancellationToken(), this.getScheduler(), options);
    }

    public Task<Void> then(ILinkVoidAction<T> action, Option... options) {
        return this.then(action, this.getCancellationToken(), this.getScheduler(), options);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, Option... options) {
        return this.then(action, this.getCancellationToken(), this.getScheduler(), options);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, Option... options) {
        return this.then(action, this.getCancellationToken(), this.getScheduler(), options);
    }


    public <K> Task<K> then(ILinkAction<K, T> action) {
        return this.then(action, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action) {
        return this.then(action, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action) {
        return this.then(action, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action) {
        return this.then(action, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, retrySupplier);
        Task<T> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(String taskId, int numberOfTries, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, numberOfTries);
        Task<T> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(String taskId, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this);
        Task<T> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, CancellationToken cancellationToken,  Consumer<Runnable> scheduler) {
        return this.retry(taskId, retrySupplier, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, int numberOfTries, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.retry(taskId, numberOfTries, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.retry(taskId, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, CancellationToken cancellationToken, Option... options) {
        return this.retry(taskId, retrySupplier, cancellationToken, this.getScheduler(), options);
    }

    public Task<T> retry(String taskId, int numberOfTries, CancellationToken cancellationToken, Option... options) {
        return this.retry(taskId, numberOfTries, cancellationToken, this.getScheduler(), options);
    }

    public Task<T> retry(String taskId, CancellationToken cancellationToken, Option... options) {
        return this.retry(taskId, cancellationToken, this.getScheduler(), options);
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, retrySupplier);
        Task<T> task = new Task<>(taskId, taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(String taskId, int numberOfTries, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, numberOfTries);
        Task<T> task = new Task<>(taskId, taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(String taskId, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this);
        Task<T> task = new Task<>(taskId, taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, CancellationToken cancellationToken) {
        return this.retry(taskId, retrySupplier, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, int numberOfTries, CancellationToken cancellationToken) {
        return this.retry(taskId, numberOfTries, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, CancellationToken cancellationToken) {
        return this.retry(taskId, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, Consumer<Runnable> scheduler) {
        return this.retry(taskId, retrySupplier, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, int numberOfTries, Consumer<Runnable> scheduler) {
        return this.retry(taskId, numberOfTries, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, Consumer<Runnable> scheduler) {
        return this.retry(taskId, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, Option... options) {
        return this.retry(taskId, retrySupplier, this.getCancellationToken(), this.getScheduler(), options);
    }

    public Task<T> retry(String taskId, int numberOfTries, Option... options) {
        return this.retry(taskId, numberOfTries, this.getCancellationToken(), this.getScheduler(), options);
    }

    public Task<T> retry(String taskId, Option... options) {
        return this.retry(taskId, this.getCancellationToken(), this.getScheduler(), options);
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier) {
        return this.retry(taskId, retrySupplier, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, int numberOfTries) {
        return this.retry(taskId, numberOfTries, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId) {
        return this.retry(taskId, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, retrySupplier);
        Task<T> task = new Task<>(taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(int numberOfTries, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, numberOfTries);
        Task<T> task = new Task<>(taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this);
        Task<T> task = new Task<>(taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.retry(retrySupplier, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(int numberOfTries, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.retry(numberOfTries, cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return this.retry(cancellationToken, scheduler, this.getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, CancellationToken cancellationToken, Option... options) {
        return this.retry(retrySupplier, cancellationToken, this.getScheduler(), options);
    }

    public Task<T> retry(int numberOfTries, CancellationToken cancellationToken, Option... options) {
        return this.retry(numberOfTries, cancellationToken, this.getScheduler(), options);
    }

    public Task<T> retry(CancellationToken cancellationToken, Option... options) {
        return this.retry(cancellationToken, this.getScheduler(), options);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, retrySupplier);
        Task<T> task = new Task<>(taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(int numberOfTries, Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, numberOfTries);
        Task<T> task = new Task<>(taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(Consumer<Runnable> scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this);
        Task<T> task = new Task<>(taskAction, this.getCancellationToken(), scheduler, options);

        return this.then(task);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, CancellationToken cancellationToken) {
        return this.retry(retrySupplier, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(int numberOfTries, CancellationToken cancellationToken) {
        return this.retry(numberOfTries, cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(CancellationToken cancellationToken) {
        return this.retry(cancellationToken, this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, Consumer<Runnable> scheduler) {
        return this.retry(retrySupplier, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(int numberOfTries, Consumer<Runnable> scheduler) {
        return this.retry(numberOfTries, this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(Consumer<Runnable> scheduler) {
        return this.retry(this.getCancellationToken(), scheduler, this.getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, Option... options) {
        return this.retry(retrySupplier, this.getCancellationToken(), this.getScheduler(), options);
    }

    public Task<T> retry(int numberOfTries, Option... options) {
        return this.retry(numberOfTries, this.getCancellationToken(), this.getScheduler(), options);
    }

    public Task<T> retry(Option... options) {
        return this.retry(this.getCancellationToken(), this.getScheduler(), options);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier) {
        return this.retry(retrySupplier, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(int numberOfTries) {
        return this.retry(numberOfTries, this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

    public Task<T> retry() {
        return this.retry(this.getCancellationToken(), this.getScheduler(), this.getOptions().toArray(new Option[0]));
    }

}
