package com.github.brunomndantas.tpl4j.task.parallel.task;

import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.job.ParallelWorkerJob;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class ParallelWorkerTask<T,K> extends Task<Collection<K>> {

    public ParallelWorkerTask(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new ParallelWorkerJob<>(taskId, action, iterator, scheduler, Arrays.asList(options)));
    }

}
