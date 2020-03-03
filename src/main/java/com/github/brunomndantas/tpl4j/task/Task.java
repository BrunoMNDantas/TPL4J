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

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.builder.ContextBuilder;
import com.github.brunomndantas.tpl4j.context.builder.IContextBuilder;
import com.github.brunomndantas.tpl4j.context.executor.ContextExecutor;
import com.github.brunomndantas.tpl4j.context.executor.IContextExecutor;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.IEvent;
import com.github.brunomndantas.tpl4j.core.status.IStatus;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.action.action.*;
import com.github.brunomndantas.tpl4j.task.action.link.*;
import com.github.brunomndantas.tpl4j.task.action.retry.RetryAction;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Supplier;

public class Task<T> {

    public static final IContextManager DEFAULT_CONTEXT_MANAGER = new ContextManager();
    public static final IContextBuilder DEFAULT_CONTEXT_BUILDER = new ContextBuilder(DEFAULT_CONTEXT_MANAGER);
    public static final IContextExecutor DEFAULT_CONTEXT_EXECUTOR = new ContextExecutor(DEFAULT_CONTEXT_MANAGER);
    public static final IScheduler DEFAULT_SCHEDULER = new DedicatedThreadScheduler("DEFAULT_TASK_SCHEDULER");
    public static final Option[] DEFAULT_OPTIONS = new Option[]{ };



    protected volatile IContext<T> context;
    public IContext<T> getContext() { return this.context; }

    protected IContextManager contextManager;
    public IContextManager getContextManager() { return this.contextManager; }

    protected IContextBuilder contextBuilder;
    public IContextBuilder getContextBuilder() { return this.contextBuilder; }

    protected IContextExecutor contextExecutor;
    public IContextExecutor getContextExecutor() { return this.contextExecutor; }



    public Task(IContext<T> context, IContextManager contextManager, IContextBuilder contextBuilder, IContextExecutor contextExecutor) {
        this.context = context;
        this.contextManager = contextManager;
        this.contextBuilder = contextBuilder;
        this.contextExecutor = contextExecutor;
    }


    public Task(String taskId, IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        this(
                DEFAULT_CONTEXT_BUILDER.build(taskId, action, cancellationToken, scheduler, new Options(Arrays.asList(options))),
                DEFAULT_CONTEXT_MANAGER,
                DEFAULT_CONTEXT_BUILDER,
                DEFAULT_CONTEXT_EXECUTOR
        );
    }

    public Task(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        this(taskId, new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    public Task(String taskId, IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        this(taskId, (IAction<T>)(new VoidAction(action)), cancellationToken, scheduler, options);
    }

    public Task(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, scheduler, options);
    }


    public Task(String taskId, IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        this(taskId, action, cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        this(taskId, new EmptyAction<>(action), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        this(taskId, (IAction<T>)(new VoidAction(action)), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }


    public Task(String taskId, IAction<T> action, ICancellationToken cancellationToken, Option... options) {
        this(taskId, action, cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, Option... options) {
        this(taskId, new EmptyAction<>(action), cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IVoidAction action, ICancellationToken cancellationToken, Option... options) {
        this(taskId, (IAction<T>)(new VoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, Option... options) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, options);
    }


    public Task(String taskId, IAction<T> action, IScheduler scheduler, Option... options) {
        this(taskId, action, new CancellationToken(), scheduler, options);
    }

    public Task(String taskId, IEmptyAction<T> action, IScheduler scheduler, Option... options) {
        this(taskId, new EmptyAction<>(action), new CancellationToken(), scheduler, options);
    }

    public Task(String taskId, IVoidAction action, IScheduler scheduler, Option... options) {
        this(taskId, (IAction<T>)(new VoidAction(action)), new CancellationToken(), scheduler, options);
    }

    public Task(String taskId, IEmptyVoidAction action, IScheduler scheduler, Option... options) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), scheduler, options);
    }


    public Task(String taskId, IAction<T> action, ICancellationToken cancellationToken) {
        this(taskId, action, cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken) {
        this(taskId, new EmptyAction<>(action), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IVoidAction action, ICancellationToken cancellationToken) {
        this(taskId, (IAction<T>)(new VoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken) {
        this(taskId, (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }


    public Task(String taskId, IAction<T> action, IScheduler scheduler) {
        this(taskId, action, new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyAction<T> action, IScheduler scheduler) {
        this(taskId, new EmptyAction<>(action), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IVoidAction action, IScheduler scheduler) {
        this(taskId, (IAction<T>)(new VoidAction(action)), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(String taskId, IEmptyVoidAction action, IScheduler scheduler) {
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


    public Task(IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        this(UUID.randomUUID().toString(), action, cancellationToken, scheduler, options);
    }

    public Task(IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        this(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    public Task(IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        this(UUID.randomUUID().toString(), (IAction<T>)(new VoidAction(action)), cancellationToken, scheduler, options);
    }

    public Task(IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        this(UUID.randomUUID().toString(), (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, scheduler, options);
    }


    public Task(IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        this(UUID.randomUUID().toString(), action, cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        this(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        this(UUID.randomUUID().toString(), (IAction<T>)(new VoidAction(action)), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        this(UUID.randomUUID().toString(), (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, scheduler, DEFAULT_OPTIONS);
    }


    public Task(IAction<T> action, ICancellationToken cancellationToken, Option... options) {
        this(UUID.randomUUID().toString(), action, cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyAction<T> action, ICancellationToken cancellationToken, Option... options) {
        this(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(IVoidAction action, ICancellationToken cancellationToken, Option... options) {
        this(UUID.randomUUID().toString(), (IAction<T>)(new VoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyVoidAction action, ICancellationToken cancellationToken, Option... options) {
        this(UUID.randomUUID().toString(), (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, options);
    }


    public Task(IAction<T> action, IScheduler scheduler, Option... options) {
        this(UUID.randomUUID().toString(), action, new CancellationToken(), scheduler, options);
    }

    public Task(IEmptyAction<T> action, IScheduler scheduler, Option... options) {
        this(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), scheduler, options);
    }

    public Task(IVoidAction action, IScheduler scheduler, Option... options) {
        this(UUID.randomUUID().toString(), (IAction<T>)(new VoidAction(action)), new CancellationToken(), scheduler, options);
    }

    public Task(IEmptyVoidAction action, IScheduler scheduler, Option... options) {
        this(UUID.randomUUID().toString(), (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), scheduler, options);
    }


    public Task(IAction<T> action, ICancellationToken cancellationToken) {
        this(UUID.randomUUID().toString(), action, cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action, ICancellationToken cancellationToken) {
        this(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action, ICancellationToken cancellationToken) {
        this(UUID.randomUUID().toString(), (IAction<T>)(new VoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action, ICancellationToken cancellationToken) {
        this(UUID.randomUUID().toString(), (IAction<T>) (new EmptyVoidAction(action)), cancellationToken, DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }


    public Task(IAction<T> action, IScheduler scheduler) {
        this(UUID.randomUUID().toString(), action, new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action, IScheduler scheduler) {
        this(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action, IScheduler scheduler) {
        this(UUID.randomUUID().toString(), (IAction<T>)(new VoidAction(action)), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action, IScheduler scheduler) {
        this(UUID.randomUUID().toString(), (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), scheduler, DEFAULT_OPTIONS);
    }


    public Task(IAction<T> action, Option... options) {
        this(UUID.randomUUID().toString(), action, new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyAction<T> action, Option... options) {
        this(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(IVoidAction action, Option... options) {
        this(UUID.randomUUID().toString(), (IAction<T>)(new VoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }

    public Task(IEmptyVoidAction action, Option... options) {
        this(UUID.randomUUID().toString(), (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, options);
    }


    public Task(IAction<T> action) {
        this(UUID.randomUUID().toString(), action, new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyAction<T> action) {
        this(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IVoidAction action) {
        this(UUID.randomUUID().toString(), (IAction<T>)(new VoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }

    public Task(IEmptyVoidAction action) {
        this(UUID.randomUUID().toString(), (IAction<T>) (new EmptyVoidAction(action)), new CancellationToken(), DEFAULT_SCHEDULER, DEFAULT_OPTIONS);
    }



    public String getId() {
        return this.context.getTaskId();
    }

    public IAction<T> getAction() {
        return this.context.getAction();
    }

    public ICancellationToken getCancellationToken() {
        return this.context.getCancellationToken();
    }

    public IScheduler getScheduler() {
        return this.context.getScheduler();
    }

    public IOptions getOptions() {
        return this.context.getOptions();
    }

    public IStatus getStatus() {
        return this.context.getStatus();
    }

    public T getResultValue() {
        return this.context.getResultValue();
    }

    public Exception getResultException() {
        return this.context.getResultException();
    }

    public State getState() {
        return this.context.getStatus().getState();
    }

    public IEvent getScheduledEvent() {
        return this.context.getStatus().getScheduledEvent();
    }

    public IEvent getRunningEvent() {
        return this.context.getStatus().getRunningEvent();
    }

    public IEvent getWaitingForChildrenEvent() {
        return this.context.getStatus().getWaitingForChildrenEvent();
    }

    public IEvent getCancelledEvent() {
        return this.context.getStatus().getCancelledEvent();
    }

    public IEvent getFailedEvent() {
        return this.context.getStatus().getFailedEvent();
    }

    public IEvent getSucceededEvent() {
        return this.context.getStatus().getSucceededEvent();
    }

    public IEvent getFinishedEvent() {
        return this.context.getStatus().getFinishedEvent();
    }

    public void start() {
        this.contextExecutor.execute(this.context);
    }

    public void cancel() {
        this.context.getCancellationToken().cancel();
    }

    public T getResult() throws Exception {
        this.context.getStatus().getFinishedEvent().await();

        if(this.context.getStatus().getState() == State.FAILED)
            throw this.context.getResultException();
        if(this.context.getStatus().getState() == State.CANCELED)
            throw this.context.getResultException();
        else
            return this.context.getResultValue();
    }


    public <K> Task<K> then(Task<K> task) {
        this.context.getStatus().getFinishedEvent().addListener(task::start);
        return task;
    }


    protected <K> Task<K> then(String taskId, IAction<K> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        Task<K> task = new Task<>(taskId, action, cancellationToken, scheduler, options);
        return this.then(task);
    }

    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.then(taskId, new LinkAction<>(this, action), cancellationToken, scheduler, options);
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.then(taskId, new LinkVoidAction<>(this, action), cancellationToken, scheduler, options);
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.then(taskId, new LinkEmptyAction<>(this, action), cancellationToken, scheduler, options);
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.then(taskId, new LinkEmptyVoidAction<>(this, action), cancellationToken, scheduler, options);
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.then(taskId, new LinkAction<>(this, action), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.then(taskId, new LinkVoidAction<>(this, action), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.then(taskId, new LinkEmptyAction<>(this, action), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.then(taskId, new LinkEmptyVoidAction<>(this, action), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, ICancellationToken cancellationToken, Option... options) {
        return this.then(taskId, new LinkAction<>(this, action), cancellationToken, this.context.getScheduler(), options);
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return this.then(taskId, new LinkVoidAction<>(this, action), cancellationToken, this.context.getScheduler(), options);
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, ICancellationToken cancellationToken, Option... options) {
        return this.then(taskId, new LinkEmptyAction<>(this, action), cancellationToken, this.context.getScheduler(), options);
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return this.then(taskId, new LinkEmptyVoidAction<>(this, action), cancellationToken, this.context.getScheduler(), options);
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, IScheduler scheduler, Option... options) {
        return this.then(taskId, new LinkAction<>(this, action), new CancellationToken(), scheduler, options);
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, IScheduler scheduler, Option... options) {
        return this.then(taskId, new LinkVoidAction<>(this, action), new CancellationToken(), scheduler, options);
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, IScheduler scheduler, Option... options) {
        return this.then(taskId, new LinkEmptyAction<>(this, action), new CancellationToken(), scheduler, options);
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, IScheduler scheduler, Option... options) {
        return this.then(taskId, new LinkEmptyVoidAction<>(this, action), new CancellationToken(), scheduler, options);
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, ICancellationToken cancellationToken) {
        return this.then(taskId, new LinkAction<>(this, action), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, ICancellationToken cancellationToken) {
        return this.then(taskId, new LinkVoidAction<>(this, action), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, ICancellationToken cancellationToken) {
        return this.then(taskId, new LinkEmptyAction<>(this, action), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, ICancellationToken cancellationToken) {
        return this.then(taskId, new LinkEmptyVoidAction<>(this, action), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, IScheduler scheduler) {
        return this.then(taskId, new LinkAction<>(this, action), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, IScheduler scheduler) {
        return this.then(taskId, new LinkVoidAction<>(this, action), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, IScheduler scheduler) {
        return this.then(taskId, new LinkEmptyAction<>(this, action), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, IScheduler scheduler) {
        return this.then(taskId, new LinkEmptyVoidAction<>(this, action), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action, Option... options) {
        return this.then(taskId, new LinkAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action, Option... options) {
        return this.then(taskId, new LinkVoidAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action, Option... options) {
        return this.then(taskId, new LinkEmptyAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action, Option... options) {
        return this.then(taskId, new LinkEmptyVoidAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }


    public <K> Task<K> then(String taskId, ILinkAction<K, T> action) {
        return this.then(taskId, new LinkAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkVoidAction<T> action) {
        return this.then(taskId, new LinkVoidAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(String taskId, ILinkEmptyAction<K> action) {
        return this.then(taskId, new LinkEmptyAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(String taskId, ILinkEmptyVoidAction action) {
        return this.then(taskId, new LinkEmptyVoidAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkAction<>(this, action), cancellationToken, scheduler, options);
    }

    public Task<Void> then(ILinkVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkVoidAction<>(this, action), cancellationToken, scheduler, options);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyAction<>(this, action), cancellationToken, scheduler, options);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyVoidAction<>(this, action), cancellationToken, scheduler, options);
    }


    public <K> Task<K> then(ILinkAction<K, T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.then(UUID.randomUUID().toString(), new LinkAction<>(this, action), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.then(UUID.randomUUID().toString(), new LinkVoidAction<>(this, action), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyAction<>(this, action), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyVoidAction<>(this, action), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, ICancellationToken cancellationToken, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkAction<>(this, action), cancellationToken, this.context.getScheduler(), options);
    }

    public Task<Void> then(ILinkVoidAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkVoidAction<>(this, action), cancellationToken, this.context.getScheduler(), options);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, ICancellationToken cancellationToken, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyAction<>(this, action), cancellationToken, this.context.getScheduler(), options);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyVoidAction<>(this, action), cancellationToken, this.context.getScheduler(), options);
    }


    public <K> Task<K> then(ILinkAction<K, T> action, IScheduler scheduler, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkAction<>(this, action), new CancellationToken(), scheduler, options);
    }

    public Task<Void> then(ILinkVoidAction<T> action, IScheduler scheduler, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkVoidAction<>(this, action), new CancellationToken(), scheduler, options);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, IScheduler scheduler, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyAction<>(this, action), new CancellationToken(), scheduler, options);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, IScheduler scheduler, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyVoidAction<>(this, action), new CancellationToken(), scheduler, options);
    }


    public <K> Task<K> then(ILinkAction<K, T> action, ICancellationToken cancellationToken) {
        return this.then(UUID.randomUUID().toString(), new LinkAction<>(this, action), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action, ICancellationToken cancellationToken) {
        return this.then(UUID.randomUUID().toString(), new LinkVoidAction<>(this, action), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, ICancellationToken cancellationToken) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyAction<>(this, action), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action, ICancellationToken cancellationToken) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyVoidAction<>(this, action), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, IScheduler scheduler) {
        return this.then(UUID.randomUUID().toString(), new LinkAction<>(this, action), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action, IScheduler scheduler) {
        return this.then(UUID.randomUUID().toString(), new LinkVoidAction<>(this, action), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, IScheduler scheduler) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyAction<>(this, action), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action, IScheduler scheduler) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyVoidAction<>(this, action), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public <K> Task<K> then(ILinkAction<K, T> action, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public Task<Void> then(ILinkVoidAction<T> action, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkVoidAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public Task<Void> then(ILinkEmptyVoidAction action, Option... options) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyVoidAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }


    public <K> Task<K> then(ILinkAction<K, T> action) {
        return this.then(UUID.randomUUID().toString(), new LinkAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkVoidAction<T> action) {
        return this.then(UUID.randomUUID().toString(), new LinkVoidAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public <K> Task<K> then(ILinkEmptyAction<K> action) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<Void> then(ILinkEmptyVoidAction action) {
        return this.then(UUID.randomUUID().toString(), new LinkEmptyVoidAction<>(this, action), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, retrySupplier);
        Task<T> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(String taskId, int numberOfTries, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this, numberOfTries);
        Task<T> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }

    public Task<T> retry(String taskId, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        IAction<T> taskAction = new RetryAction<>(this);
        Task<T> task = new Task<>(taskId, taskAction, cancellationToken, scheduler, options);

        return this.then(task);
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.retry(taskId, retrySupplier, cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, int numberOfTries, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.retry(taskId, numberOfTries, cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.retry(taskId, cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, ICancellationToken cancellationToken, Option... options) {
        return this.retry(taskId, retrySupplier, cancellationToken, this.context.getScheduler(), options);
    }

    public Task<T> retry(String taskId, int numberOfTries, ICancellationToken cancellationToken, Option... options) {
        return this.retry(taskId, numberOfTries, cancellationToken, this.context.getScheduler(), options);
    }

    public Task<T> retry(String taskId, ICancellationToken cancellationToken, Option... options) {
        return this.retry(taskId, cancellationToken, this.context.getScheduler(), options);
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, IScheduler scheduler, Option... options) {
        return this.retry(taskId, retrySupplier, new CancellationToken(), scheduler, options);
    }

    public Task<T> retry(String taskId, int numberOfTries, IScheduler scheduler, Option... options) {
        return this.retry(taskId, numberOfTries, new CancellationToken(), scheduler, options);
    }

    public Task<T> retry(String taskId, IScheduler scheduler, Option... options) {
        return this.retry(taskId, new CancellationToken(), scheduler, options);
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, ICancellationToken cancellationToken) {
        return this.retry(taskId, retrySupplier, cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, int numberOfTries, ICancellationToken cancellationToken) {
        return this.retry(taskId, numberOfTries, cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, ICancellationToken cancellationToken) {
        return this.retry(taskId, cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, IScheduler scheduler) {
        return this.retry(taskId, retrySupplier, this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, int numberOfTries, IScheduler scheduler) {
        return this.retry(taskId, numberOfTries, this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, IScheduler scheduler) {
        return this.retry(taskId, this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier, Option... options) {
        return this.retry(taskId, retrySupplier, this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public Task<T> retry(String taskId, int numberOfTries, Option... options) {
        return this.retry(taskId, numberOfTries, this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public Task<T> retry(String taskId, Option... options) {
        return this.retry(taskId, this.context.getCancellationToken(), this.context.getScheduler(), options);
    }


    public Task<T> retry(String taskId, Supplier<Boolean> retrySupplier) {
        return this.retry(taskId, retrySupplier, this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId, int numberOfTries) {
        return this.retry(taskId, numberOfTries, this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(String taskId) {
        return this.retry(taskId, this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.retry(UUID.randomUUID().toString(), retrySupplier, cancellationToken, scheduler, options);
    }

    public Task<T> retry(int numberOfTries, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.retry(UUID.randomUUID().toString(), numberOfTries, cancellationToken, scheduler, options);
    }

    public Task<T> retry(ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return this.retry(UUID.randomUUID().toString(), cancellationToken, scheduler, options);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.retry(UUID.randomUUID().toString(), retrySupplier, cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(int numberOfTries, ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.retry(UUID.randomUUID().toString(), numberOfTries, cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(ICancellationToken cancellationToken, IScheduler scheduler) {
        return this.retry(UUID.randomUUID().toString(), cancellationToken, scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, ICancellationToken cancellationToken, Option... options) {
        return this.retry(UUID.randomUUID().toString(), retrySupplier, cancellationToken, this.context.getScheduler(), options);
    }

    public Task<T> retry(int numberOfTries, ICancellationToken cancellationToken, Option... options) {
        return this.retry(UUID.randomUUID().toString(), numberOfTries, cancellationToken, this.context.getScheduler(), options);
    }

    public Task<T> retry(ICancellationToken cancellationToken, Option... options) {
        return this.retry(UUID.randomUUID().toString(), cancellationToken, this.context.getScheduler(), options);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, IScheduler scheduler, Option... options) {
        return this.retry(UUID.randomUUID().toString(), retrySupplier, this.context.getCancellationToken(), scheduler, options);
    }

    public Task<T> retry(int numberOfTries, IScheduler scheduler, Option... options) {
        return this.retry(UUID.randomUUID().toString(), numberOfTries, this.context.getCancellationToken(), scheduler, options);
    }

    public Task<T> retry(IScheduler scheduler, Option... options) {
        return this.retry(UUID.randomUUID().toString(), this.context.getCancellationToken(), scheduler, options);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, ICancellationToken cancellationToken) {
        return this.retry(UUID.randomUUID().toString(), retrySupplier, cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(int numberOfTries, ICancellationToken cancellationToken) {
        return this.retry(UUID.randomUUID().toString(), numberOfTries, cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(ICancellationToken cancellationToken) {
        return this.retry(UUID.randomUUID().toString(), cancellationToken, this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, IScheduler scheduler) {
        return this.retry(UUID.randomUUID().toString(), retrySupplier, this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(int numberOfTries, IScheduler scheduler) {
        return this.retry(UUID.randomUUID().toString(), numberOfTries, this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(IScheduler scheduler) {
        return this.retry(UUID.randomUUID().toString(), this.context.getCancellationToken(), scheduler, this.context.getOptions().getOptions().toArray(new Option[0]));
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier, Option... options) {
        return this.retry(UUID.randomUUID().toString(), retrySupplier, this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public Task<T> retry(int numberOfTries, Option... options) {
        return this.retry(UUID.randomUUID().toString(), numberOfTries, this.context.getCancellationToken(), this.context.getScheduler(), options);
    }

    public Task<T> retry(Option... options) {
        return this.retry(UUID.randomUUID().toString(), this.context.getCancellationToken(), this.context.getScheduler(), options);
    }


    public Task<T> retry(Supplier<Boolean> retrySupplier) {
        return this.retry(UUID.randomUUID().toString(), retrySupplier, this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry(int numberOfTries) {
        return this.retry(UUID.randomUUID().toString(), numberOfTries, this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

    public Task<T> retry() {
        return this.retry(UUID.randomUUID().toString(), this.context.getCancellationToken(), this.context.getScheduler(), this.context.getOptions().getOptions().toArray(new Option[0]));
    }

}
