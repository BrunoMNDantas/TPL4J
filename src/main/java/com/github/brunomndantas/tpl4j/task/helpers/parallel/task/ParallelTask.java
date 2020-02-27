package com.github.brunomndantas.tpl4j.task.helpers.parallel.task;

import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.job.ParallelJob;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public class ParallelTask<T,K> extends Task<Collection<K>> {

    private volatile Iterable<T> elements;
    public Iterable<T> getElements() { return this.elements; }



    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        super(new ParallelJob<>(taskId, action, elements, cancellationToken, scheduler, Arrays.asList(options)));
        this.elements = elements;
    }

}
