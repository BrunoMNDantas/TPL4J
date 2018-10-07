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
package com.dantas.tpl4j.pool;

import com.dantas.tpl4j.job.CollectJob;
import com.dantas.tpl4j.job.Job;
import com.dantas.tpl4j.job.VoidJob;
import com.dantas.tpl4j.job.actions.Action;
import com.dantas.tpl4j.job.actions.VoidAction;
import com.dantas.tpl4j.task.Task;

import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class TaskPool implements AutoCloseable {

    private static final TaskPool INSTANCE = new TaskPool();



    public static <T> Task<T> createTask(Action<T> action) {
        return INSTANCE.create(action);
    }

    public static Task<Void> createTask(VoidAction action) {
        return INSTANCE.create(action);
    }

    public static <T> Task<T> createAndExecuteTask(Action<T> action) {
        return INSTANCE.createAndRun(action);
    }

    public static Task<Void> createAndExecuteTask(VoidAction action) {
        return INSTANCE.createAndRun(action);
    }

    public static <T> Task<Collection<T>> whenAllTask(Collection<Task<T>> tasks) {
        return INSTANCE.whenAll(tasks);
    }


    public static void dispose() {
        INSTANCE.close();
    }



    private ExecutorService pool;



    public TaskPool(int nThreads) {
        this.pool = Executors.newFixedThreadPool(nThreads);
    }

    public TaskPool() {
        this(Runtime.getRuntime().availableProcessors());
    }



    public <T> Task<T> create(Action<T> action) {
        Job<T> job = new Job<>(action, pool::submit);

        return new Task<>(job, pool::submit);
    }

    public Task<Void> create(VoidAction action) {
        VoidJob job = new VoidJob(action, pool::submit);

        return new Task<>(job, pool::submit);
    }

    public <T> Task<T> createAndRun(Action<T> action) {
        Task<T> task = create(action);

        task.run();

        return task;
    }

    public Task<Void> createAndRun(VoidAction action) {
        Task<Void> task = create(action);

        task.run();

        return task;
    }

    public <T> Task<Collection<T>> whenAll(Collection<Task<T>> tasks) {
        Collection<Job<T>> jobs = tasks.stream().map((task) -> task.job).collect(Collectors.toList());
        CollectJob<T> job = new CollectJob<>(jobs, pool::submit);

        Task<Collection<T>> task = new Task<>(job, pool::submit);

        task.run();

        return task;
    }


    public void close() {
        pool.shutdown();
    }

}
