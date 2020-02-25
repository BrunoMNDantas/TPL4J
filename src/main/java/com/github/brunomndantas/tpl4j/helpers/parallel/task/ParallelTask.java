package com.github.brunomndantas.tpl4j.helpers.parallel.task;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.job.ParallelJob;
import com.github.brunomndantas.tpl4j.task.Task;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

public class ParallelTask<T,K> extends Task<Collection<K>> {

    public ParallelTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        super(new ParallelJob<>(taskId, action, elements.iterator(), cancellationToken, scheduler, Arrays.asList(options)));
    }

}
