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
package com.dantas.tpl4j.task.when.whenAny;

import com.dantas.tpl4j.task.Task;
import com.dantas.tpl4j.task.core.TaskOption;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

public class WhenAnyTask<T> extends Task<Task<T>> {

    protected volatile Collection<Task<T>> tasks;
    public Collection<Task<T>> getTasks() { return this.tasks; }



    public WhenAnyTask(Collection<Task<T>> tasks, Consumer<Runnable> scheduler, TaskOption... options) {
        super(new WhenAnyJob<>(
                UUID.randomUUID().toString(),
                scheduler,
                Arrays.asList(options),
                tasks
        ));

        this.tasks = tasks;
    }

    public WhenAnyTask(Collection<Task<T>> tasks, TaskOption... options) {
        this(tasks, Task.DEFAULT_SCHEDULER, options);
    }

    public WhenAnyTask(Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        this(tasks, scheduler, Task.DEFAULT_OPTIONS);
    }

    public WhenAnyTask(Collection<Task<T>> tasks) {
        this(tasks, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

}
