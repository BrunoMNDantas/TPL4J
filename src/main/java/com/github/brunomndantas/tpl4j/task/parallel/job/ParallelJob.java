package com.github.brunomndantas.tpl4j.task.parallel.job;

import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.ParallelAction;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.job.Job;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class ParallelJob<T,K> extends Job<Collection<K>> {

    private static Collection<TaskOption> attachAcceptChildrenOption(Collection<TaskOption> options) {
        options = new LinkedList<>(options);

        if(!options.contains(TaskOption.ACCEPT_CHILDREN))
            options.add(TaskOption.ACCEPT_CHILDREN);

        return options;
    }



    public ParallelJob(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, Consumer<Runnable> scheduler, Collection<TaskOption> options) {
        super(
                taskId,
                new ParallelAction<>(taskId, action, iterator, scheduler, options),
                scheduler,
                attachAcceptChildrenOption(options));
    }

}
