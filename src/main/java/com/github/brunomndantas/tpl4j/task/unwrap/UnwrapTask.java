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
package com.github.brunomndantas.tpl4j.task.unwrap;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

public class UnwrapTask<T> extends Task<T> {

    protected volatile Task<Task<T>> task;
    public Task<Task<T>> getTask() { return this.task; }



    public UnwrapTask(String taskId, Task<Task<T>> task, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new UnwrapJob<>(taskId, cancellationToken, scheduler, Arrays.asList(options), task.getJob()));
        this.task = task;
    }

    public UnwrapTask(String taskId, Task<Task<T>> task, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new UnwrapJob<>(taskId, new CancellationToken(), scheduler, Arrays.asList(options), task.getJob()));
        this.task = task;
    }

    public UnwrapTask(String taskId, Task<Task<T>> task, CancellationToken cancellationToken, TaskOption... options) {
        this(taskId, task, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public UnwrapTask(String taskId, Task<Task<T>> task, TaskOption... options) {
        this(taskId, task, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public UnwrapTask(String taskId, Task<Task<T>> task, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(taskId, task, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public UnwrapTask(String taskId, Task<Task<T>> task, Consumer<Runnable> scheduler) {
        this(taskId, task, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public UnwrapTask(String taskId, Task<Task<T>> task, CancellationToken cancellationToken) {
        this(taskId, task, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public UnwrapTask(String taskId, Task<Task<T>> task) {
        this(taskId, task, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public UnwrapTask(Task<Task<T>> task, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), task, cancellationToken, scheduler, options);
    }

    public UnwrapTask(Task<Task<T>> task, Consumer<Runnable> scheduler, TaskOption... options) {
        this(UUID.randomUUID().toString(), task, new CancellationToken(), scheduler, options);
    }

    public UnwrapTask(Task<Task<T>> task, CancellationToken cancellationToken, TaskOption... options) {
        this(task, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public UnwrapTask(Task<Task<T>> task, TaskOption... options) {
        this(task, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public UnwrapTask(Task<Task<T>> task, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        this(task, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public UnwrapTask(Task<Task<T>> task, Consumer<Runnable> scheduler) {
        this(task, scheduler, Task.DEFAULT_OPTIONS);
    }

    public UnwrapTask(Task<Task<T>> task, CancellationToken cancellationToken) {
        this(task, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public UnwrapTask(Task<Task<T>> task) {
        this(task, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

}
