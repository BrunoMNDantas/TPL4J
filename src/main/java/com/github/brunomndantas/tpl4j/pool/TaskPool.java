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
package com.github.brunomndantas.tpl4j.pool;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.context.TaskOption;
import com.github.brunomndantas.tpl4j.core.action.action.IAction;
import com.github.brunomndantas.tpl4j.core.action.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.core.action.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.core.action.action.IVoidAction;
import com.github.brunomndantas.tpl4j.core.context.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelVoidAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.helpers.when.whenAny.WhenAnyTask;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class TaskPool {

    private static final TaskPool INSTANCE = new TaskPool();



    public static Consumer<Runnable> getTaskScheduler() {
        return INSTANCE.getScheduler();
    }


    public static <T> Task<T> createTask(String taskId, IAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.create(taskId, action, cancellationToken, options);
    }

    public static <T> Task<T> createTask(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.create(taskId, action, cancellationToken, options);
    }

    public static Task<Void> createTask(String taskId, IVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.create(taskId, action, cancellationToken, options);
    }

    public static Task<Void> createTask(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.create(taskId, action, cancellationToken, options);
    }


    public static <T> Task<T> createTask(String taskId, IAction<T> action, TaskOption... options) {
        return INSTANCE.create(taskId, action, options);
    }

    public static <T> Task<T> createTask(String taskId, IEmptyAction<T> action, TaskOption... options) {
        return INSTANCE.create(taskId, action, options);
    }

    public static Task<Void> createTask(String taskId, IVoidAction action, TaskOption... options) {
        return INSTANCE.create(taskId, action, options);
    }

    public static Task<Void> createTask(String taskId, IEmptyVoidAction action, TaskOption... options) {
        return INSTANCE.create(taskId, action, options);
    }


    public static <T> Task<T> createTask(IAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.create(action, cancellationToken, options);
    }

    public static <T> Task<T> createTask(IEmptyAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.create(action, cancellationToken, options);
    }

    public static Task<Void> createTask(IVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.create(action, cancellationToken, options);
    }

    public static Task<Void> createTask(IEmptyVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.create(action, cancellationToken, options);
    }


    public static <T> Task<T> createTask(IAction<T> action, TaskOption... options) {
        return INSTANCE.create(action, options);
    }

    public static <T> Task<T> createTask(IEmptyAction<T> action, TaskOption... options) {
        return INSTANCE.create(action, options);
    }

    public static Task<Void> createTask(IVoidAction action, TaskOption... options) {
        return INSTANCE.create(action, options);
    }

    public static Task<Void> createTask(IEmptyVoidAction action, TaskOption... options) {
        return INSTANCE.create(action, options);
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


    public static <T> Task<T> createAndStartTask(String taskId, IAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken, options);
    }

    public static <T> Task<T> createAndStartTask(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken, options);
    }

    public static Task<Void> createAndStartTask(String taskId, IVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken, options);
    }

    public static Task<Void> createAndStartTask(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.createAndStart(taskId, action, cancellationToken, options);
    }


    public static <T> Task<T> createAndStartTask(String taskId, IAction<T> action, TaskOption... options) {
        return INSTANCE.createAndStart(taskId, action, options);
    }

    public static <T> Task<T> createAndStartTask(String taskId, IEmptyAction<T> action, TaskOption... options) {
        return INSTANCE.createAndStart(taskId, action, options);
    }

    public static Task<Void> createAndStartTask(String taskId, IVoidAction action, TaskOption... options) {
        return INSTANCE.createAndStart(taskId, action, options);
    }

    public static Task<Void> createAndStartTask(String taskId, IEmptyVoidAction action, TaskOption... options) {
        return INSTANCE.createAndStart(taskId, action, options);
    }


    public static <T> Task<T> createAndStartTask(IAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.createAndStart(action, cancellationToken, options);
    }

    public static <T> Task<T> createAndStartTask(IEmptyAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.createAndStart(action, cancellationToken, options);
    }

    public static Task<Void> createAndStartTask(IVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.createAndStart(action, cancellationToken, options);
    }

    public static Task<Void> createAndStartTask(IEmptyVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.createAndStart(action, cancellationToken, options);
    }


    public static <T> Task<T> createAndStartTask(IAction<T> action, TaskOption... options) {
        return INSTANCE.createAndStart(action, options);
    }

    public static <T> Task<T> createAndStartTask(IEmptyAction<T> action, TaskOption... options) {
        return INSTANCE.createAndStart(action, options);
    }

    public static Task<Void> createAndStartTask(IVoidAction action, TaskOption... options) {
        return INSTANCE.createAndStart(action, options);
    }

    public static Task<Void> createAndStartTask(IEmptyVoidAction action, TaskOption... options) {
        return INSTANCE.createAndStart(action, options);
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


    public static <T> WhenAllTask<T> whenAllTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.whenAll(taskId, tasks, cancellationToken, options);
    }

    public static <T> WhenAllTask<T> whenAllTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return INSTANCE.whenAll(taskId, tasks, cancellationToken);
    }


    public static <T> WhenAllTask<T> whenAllTask(String taskId, Collection<Task<T>> tasks, TaskOption... options) {
        return INSTANCE.whenAll(taskId, tasks, options);
    }

    public static <T> WhenAllTask<T> whenAllTask(String taskId, Collection<Task<T>> tasks) {
        return INSTANCE.whenAll(taskId, tasks);
    }


    public static <T> WhenAllTask<T> whenAllTask(Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.whenAll(tasks, cancellationToken, options);
    }

    public static <T> WhenAllTask<T> whenAllTask(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return INSTANCE.whenAll(tasks, cancellationToken);
    }


    public static <T> WhenAllTask<T> whenAllTask(Collection<Task<T>> tasks, TaskOption... options) {
        return INSTANCE.whenAll(tasks, options);
    }

    public static <T> WhenAllTask<T> whenAllTask(Collection<Task<T>> tasks) {
        return INSTANCE.whenAll(tasks);
    }


    public static <T> WhenAnyTask<T> whenAnyTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.whenAny(taskId, tasks, cancellationToken, options);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return INSTANCE.whenAny(taskId, tasks, cancellationToken);
    }


    public static <T> WhenAnyTask<T> whenAnyTask(String taskId, Collection<Task<T>> tasks, TaskOption... options) {
        return INSTANCE.whenAny(taskId, tasks, options);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(String taskId, Collection<Task<T>> tasks) {
        return INSTANCE.whenAny(taskId, tasks);
    }


    public static <T> WhenAnyTask<T> whenAnyTask(Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.whenAny(tasks, cancellationToken, options);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        return INSTANCE.whenAny(tasks, cancellationToken);
    }


    public static <T> WhenAnyTask<T> whenAnyTask(Collection<Task<T>> tasks, TaskOption... options) {
       return INSTANCE.whenAny(tasks, options);
    }

    public static <T> WhenAnyTask<T> whenAnyTask(Collection<Task<T>> tasks) {
        return INSTANCE.whenAny(tasks);
    }


    public static <T> UnwrapTask<T> unwrapTask(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.unwrap(taskId, taskToUnwrap, cancellationToken, options);
    }

    public static <T> UnwrapTask<T> unwrapTask(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        return INSTANCE.unwrap(taskId, taskToUnwrap, cancellationToken);
    }


    public static <T> UnwrapTask<T> unwrapTask(String taskId, Task<Task<T>> taskToUnwrap, TaskOption... options) {
        return INSTANCE.unwrap(taskId, taskToUnwrap, options);
    }

    public static <T> UnwrapTask<T> unwrapTask(String taskId, Task<Task<T>> taskToUnwrap) {
        return INSTANCE.unwrap(taskId, taskToUnwrap);
    }


    public static <T> UnwrapTask<T> unwrapTask(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.unwrap(taskToUnwrap, cancellationToken, options);
    }

    public static <T> UnwrapTask<T> unwrapTask(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        return INSTANCE.unwrap(taskToUnwrap, cancellationToken);
    }


    public static <T> UnwrapTask<T> unwrapTask(Task<Task<T>> taskToUnwrap, TaskOption... options) {
        return INSTANCE.unwrap(taskToUnwrap, options);
    }

    public static <T> UnwrapTask<T> unwrapTask(Task<Task<T>> taskToUnwrap) {
        return INSTANCE.unwrap(taskToUnwrap);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken, options);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, TaskOption... options) {
        return INSTANCE.forEach(taskId, elements, action, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, TaskOption... options) {
        return INSTANCE.forEach(taskId, elements, action, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, TaskOption... options) {
        return INSTANCE.forEach(taskId, elements, action, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, TaskOption... options) {
        return INSTANCE.forEach(taskId, elements, action, options);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(taskId, elements, action, cancellationToken);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelAction<T,K> action) {
        return INSTANCE.forEach(taskId, elements, action);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelVoidAction<T> action) {
        return INSTANCE.forEach(taskId, elements, action);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        return INSTANCE.forEach(taskId, elements, action);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        return INSTANCE.forEach(taskId, elements, action);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.forEach(elements, action, cancellationToken, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.forEach(elements, action, cancellationToken, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.forEach(elements, action, cancellationToken, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return INSTANCE.forEach(elements, action, cancellationToken, options);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelAction<T,K> action, TaskOption... options) {
        return INSTANCE.forEach(elements, action, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelVoidAction<T> action, TaskOption... options) {
        return INSTANCE.forEach(elements, action, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, TaskOption... options) {
        return INSTANCE.forEach(elements, action, options);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, TaskOption... options) {
        return INSTANCE.forEach(elements, action, options);
    }


    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(elements, action, cancellationToken);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(elements, action, cancellationToken);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(elements, action, cancellationToken);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        return INSTANCE.forEach(elements, action, cancellationToken);

    }


    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelAction<T,K> action) {
        return INSTANCE.forEach(elements, action);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelVoidAction<T> action) {
        return INSTANCE.forEach(elements, action);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        return INSTANCE.forEach(elements, action);
    }

    public static <T,K> ParallelTask<T,K> forEachTask(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        return INSTANCE.forEach(elements, action);

    }



    public static void dispose() {
        INSTANCE.close();
    }



    protected volatile ExecutorService pool;

    protected volatile Consumer<Runnable> scheduler;
    public Consumer<Runnable> getScheduler() { return this.scheduler; }



    public TaskPool(int nThreads) {
        this.pool = Executors.newFixedThreadPool(nThreads);
        this.scheduler = pool::submit;
    }

    public TaskPool() {
        this(Runtime.getRuntime().availableProcessors());
    }



    public <T> Task<T> create(String taskId, IAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler, options);
    }

    public <T> Task<T> create(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler, options);
    }

    public Task<Void> create(String taskId, IVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler, options);
    }

    public Task<Void> create(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return new Task<>(taskId, action, cancellationToken, this.scheduler, options);
    }


    public <T> Task<T> create(String taskId, IAction<T> action, TaskOption... options) {
        return new Task<>(taskId, action, this.scheduler, options);
    }

    public <T> Task<T> create(String taskId, IEmptyAction<T> action, TaskOption... options) {
        return new Task<>(taskId, action, this.scheduler, options);
    }

    public Task<Void> create(String taskId, IVoidAction action, TaskOption... options) {
        return new Task<>(taskId, action, this.scheduler, options);
    }

    public Task<Void> create(String taskId, IEmptyVoidAction action, TaskOption... options) {
        return new Task<>(taskId, action, this.scheduler, options);
    }


    public <T> Task<T> create(IAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return new Task<>(action, cancellationToken, this.scheduler, options);
    }

    public <T> Task<T> create(IEmptyAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        return new Task<>(action, cancellationToken, this.scheduler, options);
    }

    public Task<Void> create(IVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return new Task<>(action, cancellationToken, this.scheduler, options);
    }

    public Task<Void> create(IEmptyVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        return new Task<>(action, cancellationToken, this.scheduler, options);
    }


    public <T> Task<T> create(IAction<T> action, TaskOption... options) {
        return new Task<>(action, this.scheduler, options);
    }

    public <T> Task<T> create(IEmptyAction<T> action, TaskOption... options) {
        return new Task<>(action, this.scheduler, options);
    }

    public Task<Void> create(IVoidAction action, TaskOption... options) {
        return new Task<>(action, this.scheduler, options);
    }

    public Task<Void> create(IEmptyVoidAction action, TaskOption... options) {
        return new Task<>(action, this.scheduler, options);
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


    public <T> Task<T> createAndStart(String taskId, IAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        Task<T> task = create(taskId, action, cancellationToken, options);

        task.start();

        return task;
    }

    public <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        Task<T> task = create(taskId, action, cancellationToken, options);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(String taskId, IVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        Task<Void> task = create(taskId, action, cancellationToken, options);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        Task<Void> task = create(taskId, action, cancellationToken, options);

        task.start();

        return task;
    }


    public <T> Task<T> createAndStart(String taskId, IAction<T> action, TaskOption... options) {
        Task<T> task = create(taskId, action, options);

        task.start();

        return task;
    }

    public <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, TaskOption... options) {
        Task<T> task = create(taskId, action, options);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(String taskId, IVoidAction action, TaskOption... options) {
        Task<Void> task = create(taskId, action, options);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(String taskId, IEmptyVoidAction action, TaskOption... options) {
        Task<Void> task = create(taskId, action, options);

        task.start();

        return task;
    }


    public <T> Task<T> createAndStart(IAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        Task<T> task = create(action, cancellationToken, options);

        task.start();

        return task;
    }

    public <T> Task<T> createAndStart(IEmptyAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        Task<T> task = create(action, cancellationToken, options);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(IVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        Task<Void> task = create(action, cancellationToken, options);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(IEmptyVoidAction action, CancellationToken cancellationToken, TaskOption... options) {
        Task<Void> task = create(action, cancellationToken, options);

        task.start();

        return task;
    }


    public <T> Task<T> createAndStart(IAction<T> action, TaskOption... options) {
        Task<T> task = create(action, options);

        task.start();

        return task;
    }

    public <T> Task<T> createAndStart(IEmptyAction<T> action, TaskOption... options) {
        Task<T> task = create(action, options);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(IVoidAction action, TaskOption... options) {
        Task<Void> task = create(action, options);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(IEmptyVoidAction action, TaskOption... options) {
        Task<Void> task = create(action, options);

        task.start();

        return task;
    }


    public <T> Task<T> createAndStart(String taskId, IAction<T> action, CancellationToken cancellationToken) {
        Task<T> task = create(taskId, action, cancellationToken);

        task.start();

        return task;
    }

    public <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken) {
        Task<T> task = create(taskId, action, cancellationToken);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(String taskId, IVoidAction action, CancellationToken cancellationToken) {
        Task<Void> task = create(taskId, action, cancellationToken);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken) {
        Task<Void> task = create(taskId, action, cancellationToken);

        task.start();

        return task;
    }


    public <T> Task<T> createAndStart(String taskId, IAction<T> action) {
        Task<T> task = create(taskId, action);

        task.start();

        return task;
    }

    public <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action) {
        Task<T> task = create(taskId, action);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(String taskId, IVoidAction action) {
        Task<Void> task = create(taskId, action);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(String taskId, IEmptyVoidAction action) {
        Task<Void> task = create(taskId, action);

        task.start();

        return task;
    }


    public <T> Task<T> createAndStart(IAction<T> action, CancellationToken cancellationToken) {
        Task<T> task = create(action, cancellationToken);

        task.start();

        return task;
    }

    public <T> Task<T> createAndStart(IEmptyAction<T> action, CancellationToken cancellationToken) {
        Task<T> task = create(action, cancellationToken);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(IVoidAction action, CancellationToken cancellationToken) {
        Task<Void> task = create(action, cancellationToken);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(IEmptyVoidAction action, CancellationToken cancellationToken) {
        Task<Void> task = create(action, cancellationToken);

        task.start();

        return task;
    }


    public <T> Task<T> createAndStart(IAction<T> action) {
        Task<T> task = create(action);

        task.start();

        return task;
    }

    public <T> Task<T> createAndStart(IEmptyAction<T> action) {
        Task<T> task = create(action);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(IVoidAction action) {
        Task<Void> task = create(action);

        task.start();

        return task;
    }

    public Task<Void> createAndStart(IEmptyVoidAction action) {
        Task<Void> task = create(action);

        task.start();

        return task;
    }


    public <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, cancellationToken, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, cancellationToken, this.scheduler);

        task.start();

        return task;
    }


    public <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, TaskOption... options) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, this.scheduler);

        task.start();

        return task;
    }


    public <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        WhenAllTask<T> task = new WhenAllTask<>(tasks, cancellationToken, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        WhenAllTask<T> task = new WhenAllTask<>(tasks, cancellationToken, this.scheduler);

        task.start();

        return task;
    }


    public <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, TaskOption... options) {
        WhenAllTask<T> task = new WhenAllTask<>(tasks, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks) {
        WhenAllTask<T> task = new WhenAllTask<>(tasks, this.scheduler);

        task.start();

        return task;
    }


    public <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, cancellationToken, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, cancellationToken, this.scheduler);

        task.start();

        return task;
    }


    public <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, TaskOption... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, this.scheduler);

        task.start();

        return task;
    }


    public <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(tasks, cancellationToken, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        WhenAnyTask<T> task = new WhenAnyTask<>(tasks, cancellationToken, this.scheduler);

        task.start();

        return task;
    }


    public <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, TaskOption... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(tasks, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks) {
        WhenAnyTask<T> task = new WhenAnyTask<>(tasks, this.scheduler);

        task.start();

        return task;
    }


    public <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, TaskOption... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, cancellationToken, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, cancellationToken, this.scheduler);

        task.start();

        return task;
    }


    public <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, TaskOption... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, this.scheduler);

        task.start();

        return task;
    }


    public <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, TaskOption... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskToUnwrap, cancellationToken, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        UnwrapTask<T> task = new UnwrapTask<>(taskToUnwrap, cancellationToken, this.scheduler);

        task.start();

        return task;
    }



    public <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, TaskOption... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskToUnwrap, this.scheduler, options);

        task.start();

        return task;
    }

    public <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap) {
        UnwrapTask<T> task = new UnwrapTask<>(taskToUnwrap, this.scheduler);

        task.start();

        return task;
    }


    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }


    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, this.scheduler, options);
        task.start();
        return task;
    }


    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, this.scheduler);
        task.start();
        return task;
    }

    
    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, this.scheduler);
        task.start();
        return task;
    }


    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, cancellationToken, this.scheduler, options);
        task.start();
        return task;
    }


    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelVoidAction<T> action, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, this.scheduler, options);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, TaskOption... options) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, this.scheduler, options);
        task.start();
        return task;
    }


    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, cancellationToken, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, cancellationToken, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, cancellationToken, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, cancellationToken, this.scheduler);
        task.start();
        return task;

    }


    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelVoidAction<T> action) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, this.scheduler);
        task.start();
        return task;
    }

    public <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        ParallelTask<T,K> task = new ParallelTask<>(elements, action, this.scheduler);
        task.start();
        return task;

    }


    public void close() {
        pool.shutdown();
    }

}
