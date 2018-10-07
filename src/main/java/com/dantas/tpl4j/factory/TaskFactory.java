/*-
 * Copyright (c) 2016, NGSPipes Team <ngspipes@gmail.com>
 * All rights reserved.
 *
 * This file is part of NGSPipes <http://ngspipes.github.io/>.
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
 * with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.dantas.tpl4j.factory;

import com.dantas.tpl4j.job.CollectJob;
import com.dantas.tpl4j.job.Job;
import com.dantas.tpl4j.job.VoidJob;
import com.dantas.tpl4j.job.actions.Action;
import com.dantas.tpl4j.job.actions.VoidAction;
import com.dantas.tpl4j.task.Task;

import java.util.Collection;
import java.util.function.Consumer;
import java.util.stream.Collectors;


public class TaskFactory {

    public static <T> Task<T> create(Action<T> action, Consumer<Runnable> scheduler) {
        Job<T> job = new Job<>(action, scheduler);
        return new Task<>(job, scheduler);
    }

    public static <T> Task<T> create(Action<T> action) {
        return create(action, Task.DEFAULT_SCHEDULER);
    }

    public static Task<Void> create(VoidAction action, Consumer<Runnable> scheduler) {
        VoidJob job = new VoidJob(action, scheduler);
        return new Task<>(job, scheduler);
    }

    public static Task<Void> create(VoidAction action) {
        return create(action, Task.DEFAULT_SCHEDULER);
    }


    public static <T> Task<T> createAndRun(Action<T> action, Consumer<Runnable> scheduler) {
        Task<T> task = create(action, scheduler);

        task.run();

        return task;
    }

    public static <T> Task<T> createAndRun(Action<T> action) {
        Task<T> task = create(action);

        task.run();

        return task;
    }

    public static Task<Void> createAndRun(VoidAction action, Consumer<Runnable> scheduler) {
        Task<Void> task = create(action, scheduler);

        task.run();

        return task;
    }

    public static Task<Void> createAndRun(VoidAction action) {
        Task<Void> task = create(action);

        task.run();

        return task;
    }


    public static <T> Task<Collection<T>> whenAll(Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        Collection<Job<T>> jobs = tasks.stream().map((task) -> task.job).collect(Collectors.toList());
        CollectJob<T> job = new CollectJob<>(jobs, scheduler);

        Task<Collection<T>> task = new Task<>(job, scheduler);

        task.run();

        return task;
    }

    public static <T> Task<Collection<T>> whenAll(Collection<Task<T>> tasks) {
        return whenAll(tasks, Task.DEFAULT_SCHEDULER);
    }

}
