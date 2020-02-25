package com.github.brunomndantas.tpl4j.helpers.parallel.job;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.job.Job;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.ParallelAction;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public class ParallelJob<T,K> extends Job<Collection<K>> {

    private static Collection<Option> attachAcceptChildrenOption(Collection<Option> options) {
        options = new LinkedList<>(options);

        if(!options.contains(Option.ACCEPT_CHILDREN))
            options.add(Option.ACCEPT_CHILDREN);

        return options;
    }



    public ParallelJob(String taskId, IParallelAction<T,K> action, Iterable<T> elements, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<Option> options) {
        super(
                taskId,
                new ParallelAction<>(taskId, action, elements, cancellationToken, scheduler, options),
                cancellationToken,
                scheduler,
                attachAcceptChildrenOption(options));
    }

}
