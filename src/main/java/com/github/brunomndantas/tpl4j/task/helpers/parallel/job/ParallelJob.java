package com.github.brunomndantas.tpl4j.task.helpers.parallel.job;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.context.job.Job;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.ParallelAction;

import java.util.Collection;
import java.util.function.Consumer;

public class ParallelJob<T,K> extends Job<Collection<K>> {

    private volatile Iterable<T> elements;
    public Iterable<T> getElements() { return this.elements; }



    public ParallelJob(String taskId, IParallelAction<T,K> action, Iterable<T> elements, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<Option> options) {
        super(  taskId,
                new ParallelAction<>(taskId, action, elements, cancellationToken, scheduler, options),
                cancellationToken,
                scheduler,
                options);

        this.elements = elements;
    }

}
