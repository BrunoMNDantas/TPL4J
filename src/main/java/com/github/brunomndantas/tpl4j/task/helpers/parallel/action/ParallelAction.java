package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelWorkerTask;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class ParallelAction<T,K> implements IAction<Collection<K>> {

    private static Option[] attachAttachToParentOption(Collection<Option> options) {
        Collection<Option> ops = new LinkedList<>(options);

        if(!ops.contains(Option.ATTACH_TO_PARENT))
            ops.add(Option.ATTACH_TO_PARENT);

        return ops.toArray(new Option[0]);
    }



    private String taskId;
    public String getTaskId() { return this.taskId; }

    private IParallelAction<T,K> action;
    public IParallelAction<T,K> getAction() { return this.action; }

    private Iterable<T> elements;
    public Iterable<T> getElements() { return this.elements; }

    private ICancellationToken cancellationToken;
    public ICancellationToken getCancellationToken() { return this.cancellationToken; }

    private Consumer<Runnable> scheduler;
    public Consumer<Runnable> getScheduler(){ return this.scheduler; }

    private Option[] options;
    public Option[] getOptions() { return this.options; }



    public ParallelAction(String taskId, IParallelAction<T, K> action, Iterable<T> elements, ICancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<Option> options) {
        this.taskId = taskId;
        this.elements = elements;
        this.action = action;
        this.cancellationToken = cancellationToken;
        this.scheduler = scheduler;
        this.options = attachAttachToParentOption(options);
    }



    @Override
    public Collection<K> run(ICancellationToken cancellationToken) throws Exception {
        Collection<K> results = new LinkedList<>();
        createWorkTasks(results);
        return results;
    }

    private void createWorkTasks(Collection<K> results) {
        String id;
        Task<Collection<K>> task;
        Iterator<T> iterator = this.elements.iterator();
        for(int i=0; i<Runtime.getRuntime().availableProcessors(); ++i) {
            id = this.taskId + "#"+i;

            task = new ParallelWorkerTask<>(id, this.action, iterator, this.cancellationToken, this.scheduler, this.options);

            id += "Collector";

            task.then(id, (previous, token) -> {
                synchronized (results) {
                    results.addAll(previous.getResult());
                }
            }, this.scheduler, this.options);

            task.start();
        }
    }

}
