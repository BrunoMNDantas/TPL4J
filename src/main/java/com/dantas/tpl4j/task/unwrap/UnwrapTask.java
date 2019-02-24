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
package com.dantas.tpl4j.task.unwrap;

import com.dantas.tpl4j.task.Task;
import com.dantas.tpl4j.task.core.TaskOption;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

public class UnwrapTask<T> extends Task<T> {

    protected volatile Task<Task<T>> task;
    public Task<Task<T>> getTask() { return this.task; }



    public UnwrapTask(Task<Task<T>> task, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new UnwrapJob<>(UUID.randomUUID().toString(), scheduler, Arrays.asList(options), task.getJob()));
        this.task = task;
    }

    public UnwrapTask(Task<Task<T>> task, TaskOption... options) {
        this(task, Task.DEFAULT_SCHEDULER, options);
    }

    public UnwrapTask(Task<Task<T>> task, Consumer<Runnable> scheduler) {
        this(task, scheduler, Task.DEFAULT_OPTIONS);
    }

    public UnwrapTask(Task<Task<T>> task) {
        this(task, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

}
