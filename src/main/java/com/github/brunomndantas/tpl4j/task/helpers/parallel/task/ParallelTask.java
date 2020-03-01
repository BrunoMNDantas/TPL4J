package com.github.brunomndantas.tpl4j.task.helpers.parallel.task;

import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.ParallelAction;

import java.util.Arrays;
import java.util.Collection;

public class ParallelTask<T,K> extends Task<Collection<K>> {

    protected volatile Iterable<T> elements;
    public Iterable<T> getElements() { return this.elements; }



    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        super(
                Task.DEFAULT_CONTEXT_BUILDER.build(
                        taskId,
                        new ParallelAction<>(taskId, action, elements, cancellationToken, scheduler, Arrays.asList(options)),
                        cancellationToken,
                        scheduler,
                        new Options(Arrays.asList(options))),
                Task.DEFAULT_CONTEXT_MANAGER,
                Task.DEFAULT_CONTEXT_BUILDER,
                Task.DEFAULT_CONTEXT_EXECUTOR
        );

        this.elements = elements;
    }

}
