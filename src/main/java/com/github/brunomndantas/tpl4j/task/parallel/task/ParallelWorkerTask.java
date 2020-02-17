package com.github.brunomndantas.tpl4j.task.parallel.task;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.job.ParallelWorkerJob;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class ParallelWorkerTask<T,K> extends Task<Collection<K>> {

    public ParallelWorkerTask(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new ParallelWorkerJob<>(taskId, action, iterator, cancellationToken, scheduler, Arrays.asList(options)));
    }

}
