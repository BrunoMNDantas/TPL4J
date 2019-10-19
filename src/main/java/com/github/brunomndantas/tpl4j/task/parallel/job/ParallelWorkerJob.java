package com.github.brunomndantas.tpl4j.task.parallel.job;

import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.ParallelWorkerAction;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.job.Job;

import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

public class ParallelWorkerJob<T,K> extends Job<Collection<K>> {

    public ParallelWorkerJob(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler, Collection<TaskOption> options) {
        super(taskId, new ParallelWorkerAction<>(action, iterator), scheduler, options);
    }

}
