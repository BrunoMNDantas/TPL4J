package com.github.brunomndantas.tpl4j.helpers.parallel.task;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.TaskOption;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.*;
import com.github.brunomndantas.tpl4j.helpers.parallel.job.ParallelJob;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

public class ParallelTask<T,K> extends Task<Collection<K>> {

    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new ParallelJob<>(taskId, action, elements.iterator(), cancellationToken, scheduler, Arrays.asList(options)));
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, elements, (IParallelAction<T,K>)new ParallelVoidAction<>(action), cancellationToken, scheduler, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, elements, (IParallelAction<T,K>)new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, options);
    }


    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new ParallelJob<>(taskId, action, elements.iterator(), new CancellationToken(), scheduler, Arrays.asList(options)));
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, elements, (IParallelAction<T,K>)new ParallelVoidAction<>(action), new CancellationToken(), scheduler, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(taskId, elements, (IParallelAction<T,K>)new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, options);
    }


    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, Consumer<Runnable> scheduler) {
        this(taskId, elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, Consumer<Runnable> scheduler) {
        this(taskId, elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Consumer<Runnable> scheduler) {
        this(taskId, elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Consumer<Runnable> scheduler) {
        this(taskId, elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        this(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        this(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        this(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        this(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }


    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, TaskOption... options) {
        this(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, TaskOption... options) {
        this(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, TaskOption... options) {
        this(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, TaskOption... options) {
        this(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }


    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        this(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        this(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        this(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        this(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action) {
        this(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action) {
        this(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        this(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        this(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, options);
    }


    public ParallelTask(Iterable<T> elements, IParallelAction<T,K> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelVoidAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, options);
    }


    public ParallelTask(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(Iterable<T> elements, IParallelAction<T,K> action, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelVoidAction<T> action, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Consumer<Runnable> scheduler) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }


    public ParallelTask(Iterable<T> elements, IParallelAction<T,K> action, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelVoidAction<T> action, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, TaskOption... options) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }


    public ParallelTask(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        this(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public ParallelTask(Iterable<T> elements, IParallelAction<T,K> action) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelVoidAction<T> action) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public ParallelTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        this(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

}
