/*-
 * Copyright (c) 2019, Bruno Dantas <bmndantas@gmail.com>
 * All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program.  If not, see <http://www.gnu.org/licenses/>.  */
package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelWorkerTask;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ParallelAction<T,K> implements IAction<Collection<K>> {

    protected static Option[] attachAttachToParentOption(Collection<Option> options) {
        Collection<Option> ops = new LinkedList<>(options);

        if(!ops.contains(Option.ATTACH_TO_PARENT))
            ops.add(Option.ATTACH_TO_PARENT);

        return ops.toArray(new Option[0]);
    }



    protected String taskId;
    public String getTaskId() { return this.taskId; }

    protected IParallelAction<T,K> action;
    public IParallelAction<T,K> getAction() { return this.action; }

    protected Iterable<T> elements;
    public Iterable<T> getElements() { return this.elements; }

    protected ICancellationToken cancellationToken;
    public ICancellationToken getCancellationToken() { return this.cancellationToken; }

    protected IScheduler scheduler;
    public IScheduler getScheduler(){ return this.scheduler; }

    protected Option[] options;
    public Option[] getOptions() { return this.options; }



    public ParallelAction(String taskId, IParallelAction<T, K> action, Iterable<T> elements, ICancellationToken cancellationToken, IScheduler scheduler, Collection<Option> options) {
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

    protected void createWorkTasks(Collection<K> results) {
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
            }, this.cancellationToken, this.scheduler, this.options);

            task.start();
        }
    }

}
