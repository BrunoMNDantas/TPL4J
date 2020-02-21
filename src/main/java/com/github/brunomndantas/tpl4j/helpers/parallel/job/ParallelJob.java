package com.github.brunomndantas.tpl4j.helpers.parallel.job;

import com.github.brunomndantas.tpl4j.core.TaskOption;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.job.Job;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.ParallelAction;

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



    public ParallelJob(String taskId, IParallelAction<T,K> action, Iterator<T> iterator, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<TaskOption> options) {
        super(
                taskId,
                new ParallelAction<>(taskId, action, iterator, cancellationToken, scheduler, options),
                cancellationToken,
                scheduler,
                attachAcceptChildrenOption(options));
    }

}
