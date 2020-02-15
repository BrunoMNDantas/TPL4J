package com.github.brunomndantas.tpl4j.task.parallel.action;

import com.github.brunomndantas.tpl4j.task.parallel.task.ParallelWorkerTask;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.action.IAction;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

public class ParallelAction<T,K> implements IAction<Collection<K>> {

    private static TaskOption[] attachAttachToParentOption(Collection<TaskOption> options) {
        Collection<TaskOption> ops = new LinkedList<>(options);

        if(!ops.contains(TaskOption.ATTACH_TO_PARENT))
            ops.add(TaskOption.ATTACH_TO_PARENT);

        return ops.toArray(new TaskOption[0]);
    }



    private String taskId;
    public String getTaskId() { return this.taskId; }

    private IParallelAction<T,K> action;
    public IParallelAction<T,K> getAction() { return this.action; }

    private Iterator<T> iterator;
    public Iterator<T> getIterator() { return this.iterator; }

    private Consumer<Runnable> scheduler;
    public Consumer<Runnable> getScheduler(){ return this.scheduler; }

    private TaskOption[] options;
    public TaskOption[] getOptions() { return this.options; }



    public ParallelAction(String taskId, IParallelAction<T, K> action, Iterator<T> iterator, Consumer<Runnable> scheduler, Collection<TaskOption> options) {
        this.taskId = taskId;
        this.iterator = iterator;
        this.action = action;
        this.scheduler = scheduler;
        this.options = attachAttachToParentOption(options);
    }



    @Override
    public Collection<K> run(CancellationToken cancellationToken) throws Exception {
        Collection<K> results = new LinkedList<>();
        createWorkTasks(results);
        return results;
    }

    private void createWorkTasks(Collection<K> results) {
        String id;
        Task<Collection<K>> task;
        for(int i=0; i<Runtime.getRuntime().availableProcessors(); ++i) {
            id = this.taskId + "#"+i;

            task = new ParallelWorkerTask<>(id, this.action, this.iterator, this.scheduler, this.options);

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