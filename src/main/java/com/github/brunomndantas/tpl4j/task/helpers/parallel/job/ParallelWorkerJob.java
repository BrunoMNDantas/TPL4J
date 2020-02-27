package com.github.brunomndantas.tpl4j.task.helpers.parallel.job;

import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.context.job.Job;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.ParallelWorkerAction;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class ParallelWorkerJob<T,K> extends Job<Collection<K>> {

    public ParallelWorkerJob(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, ICancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<Option> options) {
        super(taskId, new ParallelWorkerAction<>(action, iterator), cancellationToken, scheduler, options);
    }

}
