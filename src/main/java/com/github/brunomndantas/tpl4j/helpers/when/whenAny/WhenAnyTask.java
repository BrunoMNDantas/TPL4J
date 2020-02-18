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
package com.github.brunomndantas.tpl4j.helpers.when.whenAny;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.context.TaskOption;
import com.github.brunomndantas.tpl4j.task.context.cancel.CancellationToken;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

public class WhenAnyTask<T> extends Task<Task<T>> {

    protected volatile Collection<Task<T>> tasks;
    public Collection<Task<T>> getTasks() { return this.tasks; }



    public WhenAnyTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new WhenAnyJob<>(
                taskId,
                cancellationToken,
                scheduler,
                Arrays.asList(options),
                tasks
        ));

        this.tasks = tasks;
    }

    public WhenAnyTask(String taskId, Collection<Task<T>> tasks, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new WhenAnyJob<>(
                taskId,
                new CancellationToken(),
                scheduler,
                Arrays.asList(options),
                tasks
        ));

        this.tasks = tasks;
    }

    public WhenAnyTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        this(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public WhenAnyTask(String taskId, Collection<Task<T>> tasks, TaskOption... options) {
        this(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public WhenAnyTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public WhenAnyTask(String taskId, Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        this(taskId, tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public WhenAnyTask(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        this(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public WhenAnyTask(String taskId, Collection<Task<T>> tasks) {
        this(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public WhenAnyTask(Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, options);
    }

    public WhenAnyTask(Collection<Task<T>> tasks, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, options);
    }

    public WhenAnyTask(Collection<Task<T>> tasks, CancellationToken cancellationToken, TaskOption... options) {
        this(tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public WhenAnyTask(Collection<Task<T>> tasks, TaskOption... options) {
        this(tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public WhenAnyTask(Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public WhenAnyTask(Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        this(tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public WhenAnyTask(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        this(tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public WhenAnyTask(Collection<Task<T>> tasks) {
        this(tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

}
