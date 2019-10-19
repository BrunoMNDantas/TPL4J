package com.github.brunomndantas.tpl4j.task.parallel.task;

import com.github.brunomndantas.tpl4j.task.parallel.action.*;
import com.github.brunomndantas.tpl4j.task.parallel.job.ParallelJob;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.UUID;
import java.util.function.Consumer;

public class ParallelTask<T,K> extends Task<Collection<K>> {

    public ParallelTask(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new ParallelJob<>(taskId, action, iterator, scheduler, Arrays.asList(options)));
    }

    public ParallelTask(String taskId, IParallelVoidAction<T> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, (IParallelAction<T,K>)new ParallelVoidAction<>(action), iterator, scheduler, options);
    }

    public ParallelTask(String taskId, IParallelUninterruptibleAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, new ParallelUninterruptibleAction<>(action), iterator, scheduler, options);
    }

    public ParallelTask(String taskId, IParallelUninterruptibleVoidAction<T> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, (IParallelAction<T,K>)new ParallelUninterruptibleVoidAction<>(action), iterator, scheduler, options);
    }


    public ParallelTask(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler) {
        this(taskId, action, iterator, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, IParallelVoidAction<T> action, Iterator<T> iterator, Consumer<Runnable> scheduler) {
        this(taskId, action, iterator, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, IParallelUninterruptibleAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler) {
        this(taskId, action, iterator, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, IParallelUninterruptibleVoidAction<T> action, Iterator<T> iterator, Consumer<Runnable> scheduler) {
        this(taskId, action, iterator, scheduler, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, TaskOption... options) {
        this(taskId, action, iterator, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, IParallelVoidAction<T> action, Iterator<T> iterator, TaskOption... options) {
        this(taskId, action, iterator, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, IParallelUninterruptibleAction<T,K> action, Iterator<T> iterator, TaskOption... options) {
        this(taskId, action, iterator, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, IParallelUninterruptibleVoidAction<T> action, Iterator<T> iterator, TaskOption... options) {
        this(taskId, action, iterator, Task.DEFAULT_SCHEDULER, options);
    }


    public ParallelTask(String taskId, IParallelAction<T,K> action, Iterator<T> iterator) {
        this(taskId, action, iterator, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, IParallelVoidAction<T> action, Iterator<T> iterator) {
        this(taskId, action, iterator, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, IParallelUninterruptibleAction<T,K> action, Iterator<T> iterator) {
        this(taskId, action, iterator, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, IParallelUninterruptibleVoidAction<T> action, Iterator<T> iterator) {
        this(taskId, action, iterator, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(IParallelAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), action, iterator, scheduler, options);
    }

    public ParallelTask(IParallelVoidAction<T> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), action, iterator, scheduler, options);
    }

    public ParallelTask(IParallelUninterruptibleAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), action, iterator, scheduler, options);
    }

    public ParallelTask(IParallelUninterruptibleVoidAction<T> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), action, iterator, scheduler, options);
    }


    public ParallelTask(IParallelAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), action, iterator, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(IParallelVoidAction<T> action, Iterator<T> iterator, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), action, iterator, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(IParallelUninterruptibleAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), action, iterator, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(IParallelUninterruptibleVoidAction<T> action, Iterator<T> iterator, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), action, iterator, scheduler, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(IParallelAction<T,K> action, Iterator<T> iterator, TaskOption... options) {
        this(UUID.randomUUID().toString(), action, iterator, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(IParallelVoidAction<T> action, Iterator<T> iterator, TaskOption... options) {
        this(UUID.randomUUID().toString(), action, iterator, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(IParallelUninterruptibleAction<T,K> action, Iterator<T> iterator, TaskOption... options) {
        this(UUID.randomUUID().toString(), action, iterator, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(IParallelUninterruptibleVoidAction<T> action, Iterator<T> iterator, TaskOption... options) {
        this(UUID.randomUUID().toString(), action, iterator, Task.DEFAULT_SCHEDULER, options);
    }


    public ParallelTask(IParallelAction<T,K> action, Iterator<T> iterator) {
        this(UUID.randomUUID().toString(), action, iterator, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(IParallelVoidAction<T> action, Iterator<T> iterator) {
        this(UUID.randomUUID().toString(), action, iterator, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(IParallelUninterruptibleAction<T,K> action, Iterator<T> iterator) {
        this(UUID.randomUUID().toString(), action, iterator, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(IParallelUninterruptibleVoidAction<T> action, Iterator<T> iterator) {
        this(UUID.randomUUID().toString(), action, iterator, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

}
