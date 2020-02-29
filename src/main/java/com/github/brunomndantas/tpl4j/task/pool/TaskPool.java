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
package com.github.brunomndantas.tpl4j.task.pool;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.PoolScheduler;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.action.action.*;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.*;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyTask;

import java.util.Collection;
import java.util.UUID;

public class TaskPool {

    private static final TaskPool INSTANCE = new TaskPool();



    public static IScheduler getTaskScheduler() {
        return INSTANCE.getScheduler();
    }


    public static <T> Task<T> createTask(String taskId, IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.create(taskId, action, cancellationToken, options);
    }

    public static <T> Task<T> createTask(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.create(taskId, action, cancellationToken, options);
    }

    public static Task<Void> createTask(String taskId, IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.create(taskId, action, cancellationToken, options);
    }

    public static Task<Void> createTask(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.create(taskId, action, cancellationToken, options);
    }


    public static <T> Task<T> createTask(String taskId, IAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.create(taskId, action, cancellationToken);
    }

    public static <T> Task<T> createTask(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.create(taskId, action, cancellationToken);
    }

    public static Task<Void> createTask(String taskId, IVoidAction action, CancellationToken cancellationToken) {
        return INSTANCE.create(taskId, action, cancellationToken);
    }

    public static Task<Void> createTask(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken) {
        return INSTANCE.create(taskId, action, cancellationToken);
    }


    public static <T> Task<T> createTask(String taskId, IAction<T> action, Option... options) {
        return INSTANCE.create(taskId, action, options);
    }

    public static <T> Task<T> createTask(String taskId, IEmptyAction<T> action, Option... options) {
        return INSTANCE.create(taskId, action, options);
    }

    public static Task<Void> createTask(String taskId, IVoidAction action, Option... options) {
        return INSTANCE.create(taskId, action, options);
    }

    public static Task<Void> createTask(String taskId, IEmptyVoidAction action, Option... options) {
        return INSTANCE.create(taskId, action, options);
    }


    public static <T> Task<T> createTask(String taskId, IAction<T> action) {
        return INSTANCE.create(taskId, action);
    }

    public static <T> Task<T> createTask(String taskId, IEmptyAction<T> action) {
        return INSTANCE.create(taskId, action);
    }

    public static Task<Void> createTask(String taskId, IVoidAction action) {
        return INSTANCE.create(taskId, action);
    }

    public static Task<Void> createTask(String taskId, IEmptyVoidAction action) {
        return INSTANCE.create(taskId, action);
    }


    public static <T> Task<T> createTask(IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.create(action, cancellationToken, options);
    }

    public static <T> Task<T> createTask(IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.create(action, cancellationToken, options);
    }

    public static Task<Void> createTask(IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.create(action, cancellationToken, options);
    }

    public static Task<Void> createTask(IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.create(action, cancellationToken, options);
    }


    public static <T> Task<T> createTask(IAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.create(action, cancellationToken);
    }

    public static <T> Task<T> createTask(IEmptyAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.create(action, cancellationToken);
    }

    public static Task<Void> createTask(IVoidAction action, CancellationToken cancellationToken) {
        return INSTANCE.create(action, cancellationToken);
    }

    public static Task<Void> createTask(IEmptyVoidAction action, CancellationToken cancellationToken) {
        return INSTANCE.create(action, cancellationToken);
    }


    public static <T> Task<T> createTask(IAction<T> action, Option... options) {
        return INSTANCE.create(action, options);
    }

    public static <T> Task<T> createTask(IEmptyAction<T> action, Option... options) {
        return INSTANCE.create(action, options);
    }

    public static Task<Void> createTask(IVoidAction action, Option... options) {
        return INSTANCE.create(action, options);
    }

    public static Task<Void> createTask(IEmptyVoidAction action, Option... options) {
        return INSTANCE.create(action, options);
    }


    public static <T> Task<T> createTask(IAction<T> action) {
        return INSTANCE.create(action);
    }

    public static <T> Task<T> createTask(IEmptyAction<T> action) {
        return INSTANCE.create(action);
    }

    public static Task<Void> createTask(IVoidAction action) {
        return INSTANCE.create(action);
    }

    public static Task<Void> createTask(IEmptyVoidAction action) {
        return INSTANCE.create(action);
    }


    public static <T> Task<T> createAndStartTask(String taskId, IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken, options);
    }

    public static <T> Task<T> createAndStartTask(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken, options);
    }

    public static Task<Void> createAndStartTask(String taskId, IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken, options);
    }

    public static Task<Void> createAndStartTask(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken, options);
    }


    public static <T> Task<T> createAndStartTask(String taskId, IAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken);
    }

    public static <T> Task<T> createAndStartTask(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken);
    }

    public static Task<Void> createAndStartTask(String taskId, IVoidAction action, CancellationToken cancellationToken) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken);
    }

    public static Task<Void> createAndStartTask(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken);
    }


    public static <T> Task<T> createAndStartTask(String taskId, IAction<T> action, Option... options) {
        return INSTANCE.createAndStart(taskId, action, options);
    }

    public static <T> Task<T> createAndStartTask(String taskId, IEmptyAction<T> action, Option... options) {
        return INSTANCE.createAndStart(taskId, action, options);
    }

    public static Task<Void> createAndStartTask(String taskId, IVoidAction action, Option... options) {
        return INSTANCE.createAndStart(taskId, action, options);
    }

    public static Task<Void> createAndStartTask(String taskId, IEmptyVoidAction action, Option... options) {
        return INSTANCE.createAndStart(taskId, action, options);
    }


    public static <T> Task<T> createAndStartTask(String taskId, IAction<T> action) {
        return INSTANCE.createAndStart(taskId, action);
    }

    public static <T> Task<T> createAndStartTask(String taskId, IEmptyAction<T> action) {
        return INSTANCE.createAndStart(taskId, action);
    }

    public static Task<Void> createAndStartTask(String taskId, IVoidAction action) {
        return INSTANCE.createAndStart(taskId, action);
    }

    public static Task<Void> createAndStartTask(String taskId, IEmptyVoidAction action) {
        return INSTANCE.createAndStart(taskId, action);
    }


    public static <T> Task<T> createAndStartTask(IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.createAndStart(action, cancellationToken, options);
    }

    public static <T> Task<T> createAndStartTask(IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.createAndStart(action, cancellationToken, options);
    }

    public static Task<Void> createAndStartTask(IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.createAndStart(action, cancellationToken, options);
    }

    public static Task<Void> createAndStartTask(IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.createAndStart(action, cancellationToken, options);
    }


    public static <T> Task<T> createAndStartTask(IAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.createAndStart(action, cancellationToken);
    }

    public static <T> Task<T> createAndStartTask(IEmptyAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.createAndStart(action, cancellationToken);
    }

    public static Task<Void> createAndStartTask(IVoidAction action, CancellationToken cancellationToken) {
        return INSTANCE.createAndStart(action, cancellationToken);
    }

    public static Task<Void> createAndStartTask(IEmptyVoidAction action, CancellationToken cancellationToken) {
        return INSTANCE.createAndStart(action, cancellationToken);
    }


    public static <T> Task<T> createAndStartTask(IAction<T> action, Option... options) {
        return INSTANCE.createAndStart(action, options);
    }

    public static <T> Task<T> createAndStartTask(IEmptyAction<T> action, Option... options) {
        return INSTANCE.createAndStart(action, options);
    }

    public static Task<Void> createAndStartTask(IVoidAction action, Option... options) {
        return INSTANCE.createAndStart(action, options);
    }

    public static Task<Void> createAndStartTask(IEmptyVoidAction action, Option... options) {
        return INSTANCE.createAndStart(action, options);
    }


    public static <T> Task<T> createAndStartTask(IAction<T> action) {
        return INSTANCE.createAndStart(action);
    }

    public static <T> Task<T> createAndStartTask(IEmptyAction<T> action) {
        return INSTANCE.createAndStart(action);
    }

    public static Task<Void> createAndStartTask(IVoidAction action) {
        return INSTANCE.createAndStart(action);
    }

    public static Task<Void> createAndStartTask(IEmptyVoidAction action) {
        return INSTANCE.createAndStart(action);
    }


    public static <T> WhenAllTask<T> whenAllTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.whenAll(taskId, tasks, cancellationToken, options);
    }

    public static <T> WhenAllTask<T> whenAllTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return INSTANCE.whenAll(taskId, tasks, cancellationToken);
    }

    public static <T> WhenAllTask<T> whenAllTask(String taskId, Collection<Task<T>> tasks, Option... options) {
        return INSTANCE.whenAll(taskId, tasks, options);
    }

    public static <T> WhenAllTask<T> whenAllTask(String taskId, Collection<Task<T>> tasks) {
        return INSTANCE.whenAll(taskId, tasks);
    }


    public static <T> WhenAllTask<T> whenAllTask(Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.whenAll(tasks, cancellationToken, options);
    }

    public static <T> WhenAllTask<T> whenAllTask(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return INSTANCE.whenAll(tasks, cancellationToken);
    }

    public static <T> WhenAllTask<T> whenAllTask(Collection<Task<T>> tasks, Option... options) {
        return INSTANCE.whenAll(tasks, options);
    }

    public static <T> WhenAllTask<T> whenAllTask(Collection<Task<T>> tasks) {
        return INSTANCE.whenAll(tasks);
    }


    public static <T> WhenAnyTask<T> whenAnyTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.whenAny(taskId, tasks, cancellationToken, options);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return INSTANCE.whenAny(taskId, tasks, cancellationToken);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(String taskId, Collection<Task<T>> tasks, Option... options) {
        return INSTANCE.whenAny(taskId, tasks, options);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(String taskId, Collection<Task<T>> tasks) {
        return INSTANCE.whenAny(taskId, tasks);
    }


    public static <T> WhenAnyTask<T> whenAnyTask(Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.whenAny(tasks, cancellationToken, options);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return INSTANCE.whenAny(tasks, cancellationToken);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(Collection<Task<T>> tasks, Option... options) {
       return INSTANCE.whenAny(tasks, options);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(Collection<Task<T>> tasks) {
        return INSTANCE.whenAny(tasks);
    }


    public static <T> UnwrapTask<T> unwrapTask(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.unwrap(taskId, taskToUnwrap, cancellationToken, options);
    }

    public static <T> UnwrapTask<T> unwrapTask(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        return INSTANCE.unwrap(taskId, taskToUnwrap, cancellationToken);
    }

    public static <T> UnwrapTask<T> unwrapTask(String taskId, Task<Task<T>> taskToUnwrap, Option... options) {
        return INSTANCE.unwrap(taskId, taskToUnwrap, options);
    }

    public static <T> UnwrapTask<T> unwrapTask(String taskId, Task<Task<T>> taskToUnwrap) {
        return INSTANCE.unwrap(taskId, taskToUnwrap);
    }


    public static <T> UnwrapTask<T> unwrapTask(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.unwrap(taskToUnwrap, cancellationToken, options);
    }

    public static <T> UnwrapTask<T> unwrapTask(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        return INSTANCE.unwrap(taskToUnwrap, cancellationToken);
    }

    public static <T> UnwrapTask<T> unwrapTask(Task<Task<T>> taskToUnwrap, Option... options) {
        return INSTANCE.unwrap(taskToUnwrap, options);
    }

    public static <T> UnwrapTask<T> unwrapTask(Task<Task<T>> taskToUnwrap) {
        return INSTANCE.unwrap(taskToUnwrap);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken, options);
    }

    public static <T> ParallelTask<T,Void> forEachTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken, options);
    }

    public static <T> ParallelTask<T,Void> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken, options);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken);
    }

    public static <T> ParallelTask<T,Void> forEachTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken);
    }

    public static <T> ParallelTask<T,Void> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, Option... options) {
        return INSTANCE.forEach(taskId, elements, action, options);
    }

    public static <T> ParallelTask<T,Void> forEachTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, Option... options) {
        return INSTANCE.forEach(taskId, elements, action, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Option... options) {
        return INSTANCE.forEach(taskId, elements, action, options);
    }

    public static <T> ParallelTask<T,Void> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Option... options) {
        return INSTANCE.forEach(taskId, elements, action, options);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action) {
        return INSTANCE.forEach(taskId, elements, action);
    }

    public static <T> ParallelTask<T,Void> forEachTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action) {
        return INSTANCE.forEach(taskId, elements, action);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        return INSTANCE.forEach(taskId, elements, action);
    }

    public static <T> ParallelTask<T,Void> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        return INSTANCE.forEach(taskId, elements, action);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.forEach(elements, action, cancellationToken, options);
    }

    public static <T> ParallelTask<T,Void> forEachTask(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.forEach(elements, action, cancellationToken, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.forEach(elements, action, cancellationToken, options);
    }

    public static <T> ParallelTask<T,Void> forEachTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return INSTANCE.forEach(elements, action, cancellationToken, options);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(elements, action, cancellationToken);
    }

    public static <T> ParallelTask<T,Void> forEachTask(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(elements, action, cancellationToken);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(elements, action, cancellationToken);
    }

    public static <T> ParallelTask<T,Void> forEachTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(elements, action, cancellationToken);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelAction<T,K> action, Option... options) {
        return INSTANCE.forEach(elements, action, options);
    }

    public static <T> ParallelTask<T,Void> forEachTask(Iterable<T> elements, IParallelVoidAction<T> action, Option... options) {
        return INSTANCE.forEach(elements, action, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Option... options) {
        return INSTANCE.forEach(elements, action, options);
    }

    public static <T> ParallelTask<T,Void> forEachTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Option... options) {
        return INSTANCE.forEach(elements, action, options);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelAction<T,K> action) {
        return INSTANCE.forEach(elements, action);
    }

    public static <T> ParallelTask<T,Void> forEachTask(Iterable<T> elements, IParallelVoidAction<T> action) {
        return INSTANCE.forEach(elements, action);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        return INSTANCE.forEach(elements, action);
    }

    public static <T> ParallelTask<T,Void> forEachTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        return INSTANCE.forEach(elements, action);
    }



    public static void dispose() {
        INSTANCE.close();
    }



    protected volatile IScheduler scheduler;
    public IScheduler getScheduler() { return this.scheduler; }



    public TaskPool(int nThreads) {
       this.scheduler = new PoolScheduler(nThreads);
    }

    public TaskPool() {
       this(Runtime.getRuntime().availableProcessors());
    }



    public <T> Task<T> create(String taskId, IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler, options);
    }

    public <T> Task<T> create(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler, options);
    }

    public Task<Void> create(String taskId, IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler, options);
    }

    public Task<Void> create(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler, options);
    }


    public <T> Task<T> create(String taskId, IAction<T> action, CancellationToken cancellationToken) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler);
    }

    public <T> Task<T> create(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler);
    }

    public Task<Void> create(String taskId, IVoidAction action, CancellationToken cancellationToken) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler);
    }

    public Task<Void> create(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler);
    }


    public <T> Task<T> create(String taskId, IAction<T> action, Option... options) {
        return new Task<>(taskId, action, this.scheduler, options);
    }

    public <T> Task<T> create(String taskId, IEmptyAction<T> action, Option... options) {
        return new Task<>(taskId, action, this.scheduler, options);
    }

    public Task<Void> create(String taskId, IVoidAction action, Option... options) {
        return new Task<>(taskId, action, this.scheduler, options);
    }

    public Task<Void> create(String taskId, IEmptyVoidAction action, Option... options) {
        return new Task<>(taskId, action, this.scheduler, options);
    }


    public <T> Task<T> create(String taskId, IAction<T> action) {
        return new Task<>(taskId, action, this.scheduler);
    }

    public <T> Task<T> create(String taskId, IEmptyAction<T> action) {
        return new Task<>(taskId, action, this.scheduler);
    }

    public Task<Void> create(String taskId, IVoidAction action) {
        return new Task<>(taskId, action, this.scheduler);
    }

    public Task<Void> create(String taskId, IEmptyVoidAction action) {
        return new Task<>(taskId, action, this.scheduler);
    }


    public <T> Task<T> create(IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(action, cancellationToken, this.scheduler, options);
    }

    public <T> Task<T> create(IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(action, cancellationToken, this.scheduler, options);
    }

    public Task<Void> create(IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(action, cancellationToken, this.scheduler, options);
    }

    public Task<Void> create(IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(action, cancellationToken, this.scheduler, options);
    }


    public <T> Task<T> create(IAction<T> action, CancellationToken cancellationToken) {
        return new Task<>(action, cancellationToken, this.scheduler);
    }

    public <T> Task<T> create(IEmptyAction<T> action, CancellationToken cancellationToken) {
        return new Task<>(action, cancellationToken, this.scheduler);
    }

    public Task<Void> create(IVoidAction action, CancellationToken cancellationToken) {
        return new Task<>(action, cancellationToken, this.scheduler);
    }

    public Task<Void> create(IEmptyVoidAction action, CancellationToken cancellationToken) {
        return new Task<>(action, cancellationToken, this.scheduler);
    }


    public <T> Task<T> create(IAction<T> action, Option... options) {
        return new Task<>(action, this.scheduler, options);
    }

    public <T> Task<T> create(IEmptyAction<T> action, Option... options) {
        return new Task<>(action, this.scheduler, options);
    }

    public Task<Void> create(IVoidAction action, Option... options) {
        return new Task<>(action, this.scheduler, options);
    }

    public Task<Void> create(IEmptyVoidAction action, Option... options) {
        return new Task<>(action, this.scheduler, options);
    }


    public <T> Task<T> create(IAction<T> action) {
        return new Task<>(action, this.scheduler);
    }

    public <T> Task<T> create(IEmptyAction<T> action) {
        return new Task<>(action, this.scheduler);
    }

    public Task<Void> create(IVoidAction action) {
        return new Task<>(action, this.scheduler);
    }

    public Task<Void> create(IEmptyVoidAction action) {
        return new Task<>(action, this.scheduler);
    }


    public <T> Task<T> createAndStart(String taskId, IAction<T> action, CancellationToken cancellationToken, Option... options) {
        Task<T> task = new Task<>(taskId, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.createAndStart(taskId, new EmptyAction<>(action), cancellationToken, options);
    }

    public Task<Void> createAndStart(String taskId, IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return this.createAndStart(taskId, new VoidAction(action), cancellationToken, options);
    }

    public Task<Void> createAndStart(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return this.createAndStart(taskId, new EmptyVoidAction(action), cancellationToken, options);
    }


    public <T> Task<T> createAndStart(String taskId, IAction<T> action, CancellationToken cancellationToken) {
        return this.createAndStart(taskId, action, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken) {
        return this.createAndStart(taskId, new EmptyAction<>(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public Task<Void> createAndStart(String taskId, IVoidAction action, CancellationToken cancellationToken) {
        return this.createAndStart(taskId, new VoidAction(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public Task<Void> createAndStart(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken) {
        return this.createAndStart(taskId, new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }


    public <T> Task<T> createAndStart(String taskId, IAction<T> action, Option... options) {
        return this.createAndStart(taskId, action, new CancellationToken(), options);
    }

    public <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, Option... options) {
        return this.createAndStart(taskId, new EmptyAction<>(action), new CancellationToken(), options);
    }

    public Task<Void> createAndStart(String taskId, IVoidAction action, Option... options) {
        return this.createAndStart(taskId, new VoidAction(action), new CancellationToken(), options);
    }

    public Task<Void> createAndStart(String taskId, IEmptyVoidAction action, Option... options) {
        return this.createAndStart(taskId, new EmptyVoidAction(action), new CancellationToken(), options);
    }


    public <T> Task<T> createAndStart(String taskId, IAction<T> action) {
        return this.createAndStart(taskId, action, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action) {
        return this.createAndStart(taskId, new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public Task<Void> createAndStart(String taskId, IVoidAction action) {
        return this.createAndStart(taskId, new VoidAction(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public Task<Void> createAndStart(String taskId, IEmptyVoidAction action) {
        return this.createAndStart(taskId, new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T> Task<T> createAndStart(IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.createAndStart(UUID.randomUUID().toString(), action, cancellationToken, options);
    }

    public <T> Task<T> createAndStart(IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, options);
    }

    public Task<Void> createAndStart(IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return this.createAndStart(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, options);
    }

    public Task<Void> createAndStart(IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return this.createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, options);
    }


    public <T> Task<T> createAndStart(IAction<T> action, CancellationToken cancellationToken) {
        return this.createAndStart(UUID.randomUUID().toString(), action, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> Task<T> createAndStart(IEmptyAction<T> action, CancellationToken cancellationToken) {
        return this.createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public Task<Void> createAndStart(IVoidAction action, CancellationToken cancellationToken) {
        return this.createAndStart(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public Task<Void> createAndStart(IEmptyVoidAction action, CancellationToken cancellationToken) {
        return this.createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }


    public <T> Task<T> createAndStart(IAction<T> action, Option... options) {
        return this.createAndStart(UUID.randomUUID().toString(), action, new CancellationToken(), options);
    }

    public <T> Task<T> createAndStart(IEmptyAction<T> action, Option... options) {
        return this.createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), options);
    }

    public Task<Void> createAndStart(IVoidAction action, Option... options) {
        return this.createAndStart(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), options);
    }

    public Task<Void> createAndStart(IEmptyVoidAction action, Option... options) {
        return this.createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), options);
    }


    public <T> Task<T> createAndStart(IAction<T> action) {
        return this.createAndStart(UUID.randomUUID().toString(), action, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public <T> Task<T> createAndStart(IEmptyAction<T> action) {
        return this.createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public Task<Void> createAndStart(IVoidAction action) {
        return this.createAndStart(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public Task<Void> createAndStart(IEmptyVoidAction action) {
        return this.createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return this.whenAll(taskId, tasks, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, Option... options) {
        return this.whenAll(taskId, tasks, new CancellationToken(), options);
    }

    public <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks) {
        return this.whenAll(taskId, tasks, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        return this.whenAll(UUID.randomUUID().toString(), tasks, cancellationToken, options);
    }

    public <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return this.whenAll(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, Option... options) {
        return this.whenAll(UUID.randomUUID().toString(), tasks, new CancellationToken(), options);
    }

    public <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks) {
        return this.whenAll(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return this.whenAny(taskId, tasks, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, Option... options) {
        return this.whenAny(taskId, tasks, new CancellationToken(), options);
    }

    public <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks) {
        return this.whenAny(taskId, tasks, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        return this.whenAny(UUID.randomUUID().toString(), tasks, cancellationToken, options);
    }

    public <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return this.whenAny(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, Option... options) {
        return this.whenAny(UUID.randomUUID().toString(), tasks, new CancellationToken(), options);
    }

    public <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks) {
        return this.whenAny(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        return this.unwrap(taskId, taskToUnwrap, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, Option... options) {
        return this.unwrap(taskId, taskToUnwrap, new CancellationToken(), options);
    }

    public <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap) {
        return this.unwrap(taskId, taskToUnwrap, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Option... options) {
        return this.unwrap(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, options);
    }

    public <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        return this.unwrap(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, Option... options) {
        return this.unwrap(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), options);
    }

    public <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap) {
        return this.unwrap(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.forEach(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, options);
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        return this.forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, options);
    }

    public <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, options);
    }


    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        return this.forEach(taskId, elements, action, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        return this.forEach(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        return this.forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        return this.forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }


    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, Option... options) {
        return this.forEach(taskId, elements, action, new CancellationToken(), options);
    }

    public <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, Option... options) {
        return this.forEach(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), options);
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Option... options) {
        return this.forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), options);
    }

    public <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Option... options) {
        return this.forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), options);
    }

    
    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action) {
        return this.forEach(taskId, elements, action, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action) {
        return this.forEach(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        return this.forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        return this.forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        return this.forEach(UUID.randomUUID().toString(), elements, action, cancellationToken, options);
    }

    public <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, options);
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, options);
    }

    public <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, options);
    }


    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        return this.forEach(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }

    public <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_OPTIONS);
    }


    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, Option... options) {
        return this.forEach(UUID.randomUUID().toString(), elements, action, new CancellationToken(), options);
    }

    public <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, Option... options) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), options);
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Option... options) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), options);
    }

    public <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Option... options) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), options);
    }


    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action) {
        return this.forEach(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }

    public <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        return this.forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_OPTIONS);
    }


    public void close() {
        this.scheduler.close();
    }

}
