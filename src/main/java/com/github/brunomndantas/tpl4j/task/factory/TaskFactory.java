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
package com.github.brunomndantas.tpl4j.task.factory;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.action.action.*;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.*;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyTask;

import java.util.Collection;
import java.util.UUID;

public class TaskFactory {

    public static <T> Task<T> create(String taskId, IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return new Task<>(taskId, action, cancellationToken, scheduler, options);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return create(taskId, new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    public static Task<Void> create(String taskId, IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return create(taskId, new VoidAction(action), cancellationToken, scheduler, options);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return create(taskId, new EmptyVoidAction(action), cancellationToken, scheduler, options);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return create(taskId, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return create(taskId, new EmptyAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(String taskId, IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return create(taskId, new VoidAction(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return create(taskId, new EmptyVoidAction(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return create(taskId, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return create(taskId, new EmptyAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> create(String taskId, IVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return create(taskId, new VoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return create(taskId, new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, IScheduler scheduler, Option... options) {
        return create(taskId, action, new CancellationToken(), scheduler, options);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, IScheduler scheduler, Option... options) {
        return create(taskId, new EmptyAction<>(action), new CancellationToken(), scheduler, options);
    }

    public static Task<Void> create(String taskId, IVoidAction action, IScheduler scheduler, Option... options) {
        return create(taskId, new VoidAction(action), new CancellationToken(), scheduler, options);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, IScheduler scheduler, Option... options) {
        return create(taskId, new EmptyVoidAction(action), new CancellationToken(), scheduler, options);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, ICancellationToken cancellationToken) {
        return create(taskId, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken) {
        return create(taskId, new EmptyAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(String taskId, IVoidAction action, ICancellationToken cancellationToken) {
        return create(taskId, new VoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken) {
        return create(taskId, new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, IScheduler scheduler) {
        return create(taskId, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, IScheduler scheduler) {
        return create(taskId, new EmptyAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(String taskId, IVoidAction action, IScheduler scheduler) {
        return create(taskId, new VoidAction(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, IScheduler scheduler) {
        return create(taskId, new EmptyVoidAction(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, Option... options) {
        return create(taskId, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, Option... options) {
        return create(taskId, new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> create(String taskId, IVoidAction action, Option... options) {
        return create(taskId, new VoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, Option... options) {
        return create(taskId, new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action) {
        return create(taskId, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action) {
        return create(taskId, new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(String taskId, IVoidAction action) {
        return create(taskId, new VoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action) {
        return create(taskId, new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> create(IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return create(UUID.randomUUID().toString(), action, cancellationToken, scheduler, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return create(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    public static Task<Void> create(IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return create(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, scheduler, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return create(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, scheduler, options);
    }


    public static <T> Task<T> create(IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return create(UUID.randomUUID().toString(), action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return create(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return create(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return create(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> create(IAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return create(UUID.randomUUID().toString(), action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return create(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> create(IVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return create(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return create(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }


    public static <T> Task<T> create(IAction<T> action, IScheduler scheduler, Option... options) {
        return create(UUID.randomUUID().toString(), action, new CancellationToken(), scheduler, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, IScheduler scheduler, Option... options) {
        return create(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), scheduler, options);
    }

    public static Task<Void> create(IVoidAction action, IScheduler scheduler, Option... options) {
        return create(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), scheduler, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, IScheduler scheduler, Option... options) {
        return create(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), scheduler, options);
    }


    public static <T> Task<T> create(IAction<T> action, ICancellationToken cancellationToken) {
        return create(UUID.randomUUID().toString(), action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, ICancellationToken cancellationToken) {
        return create(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(IVoidAction action, ICancellationToken cancellationToken) {
        return create(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(IEmptyVoidAction action, ICancellationToken cancellationToken) {
        return create(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> create(IAction<T> action, IScheduler scheduler) {
        return create(UUID.randomUUID().toString(), action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, IScheduler scheduler) {
        return create(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(IVoidAction action, IScheduler scheduler) {
        return create(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(IEmptyVoidAction action, IScheduler scheduler) {
        return create(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> create(IAction<T> action, Option... options) {
        return create(UUID.randomUUID().toString(), action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, Option... options) {
        return create(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> create(IVoidAction action, Option... options) {
        return create(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, Option... options) {
        return create(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }


    public static <T> Task<T> create(IAction<T> action) {
        return create(UUID.randomUUID().toString(), action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> create(IEmptyAction<T> action) {
        return create(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(IVoidAction action) {
        return create(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> create(IEmptyVoidAction action) {
        return create(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        Task<T> task = new Task<>(taskId, action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return createAndStart(taskId, new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return createAndStart(taskId, new VoidAction(action), cancellationToken, scheduler, options);
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return createAndStart(taskId, new EmptyVoidAction(action), cancellationToken, scheduler, options);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return createAndStart(taskId, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return createAndStart(taskId, new EmptyAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return createAndStart(taskId, new VoidAction(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return createAndStart(taskId, new EmptyVoidAction(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return createAndStart(taskId, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return createAndStart(taskId, new EmptyAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return createAndStart(taskId, new VoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return createAndStart(taskId, new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, IScheduler scheduler, Option... options) {
        return createAndStart(taskId, action, new CancellationToken(), scheduler, options);
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, IScheduler scheduler, Option... options) {
        return createAndStart(taskId, new EmptyAction<>(action), new CancellationToken(), scheduler, options);
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, IScheduler scheduler, Option... options) {
        return createAndStart(taskId, new VoidAction(action), new CancellationToken(), scheduler, options);
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, IScheduler scheduler, Option... options) {
        return createAndStart(taskId, new EmptyVoidAction(action), new CancellationToken(), scheduler, options);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, ICancellationToken cancellationToken) {
        return createAndStart(taskId, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, ICancellationToken cancellationToken) {
        return createAndStart(taskId, new EmptyAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, ICancellationToken cancellationToken) {
        return createAndStart(taskId, new VoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, ICancellationToken cancellationToken) {
        return createAndStart(taskId, new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, IScheduler scheduler) {
        return createAndStart(taskId, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, IScheduler scheduler) {
        return createAndStart(taskId, new EmptyAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, IScheduler scheduler) {
        return createAndStart(taskId, new VoidAction(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, IScheduler scheduler) {
        return createAndStart(taskId, new EmptyVoidAction(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, Option... options) {
        return createAndStart(taskId, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, Option... options) {
        return createAndStart(taskId, new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, Option... options) {
        return createAndStart(taskId, new VoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, Option... options) {
        return createAndStart(taskId, new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action) {
        return createAndStart(taskId, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action) {
        return createAndStart(taskId, new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action) {
        return createAndStart(taskId, new VoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action) {
        return createAndStart(taskId, new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> createAndStart(IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), action, cancellationToken, scheduler, options);
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    public static Task<Void> createAndStart(IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, scheduler, options);
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, scheduler, options);
    }


    public static <T> Task<T> createAndStart(IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return createAndStart(UUID.randomUUID().toString(), action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(IVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return createAndStart(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> createAndStart(IAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> createAndStart(IVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, ICancellationToken cancellationToken, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }


    public static <T> Task<T> createAndStart(IAction<T> action, IScheduler scheduler, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), action, new CancellationToken(), scheduler, options);
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, IScheduler scheduler, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), scheduler, options);
    }

    public static Task<Void> createAndStart(IVoidAction action, IScheduler scheduler, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), scheduler, options);
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, IScheduler scheduler, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), scheduler, options);
    }


    public static <T> Task<T> createAndStart(IAction<T> action, ICancellationToken cancellationToken) {
        return createAndStart(UUID.randomUUID().toString(), action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, ICancellationToken cancellationToken) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(IVoidAction action, ICancellationToken cancellationToken) {
        return createAndStart(UUID.randomUUID().toString(), new VoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, ICancellationToken cancellationToken) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> createAndStart(IAction<T> action, IScheduler scheduler) {
        return createAndStart(UUID.randomUUID().toString(), action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, IScheduler scheduler) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(IVoidAction action, IScheduler scheduler) {
        return createAndStart(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, IScheduler scheduler) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T> Task<T> createAndStart(IAction<T> action, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> createAndStart(IVoidAction action, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, Option... options) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }


    public static <T> Task<T> createAndStart(IAction<T> action) {
        return createAndStart(UUID.randomUUID().toString(), action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(IVoidAction action) {
        return createAndStart(UUID.randomUUID().toString(), new VoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action) {
        return createAndStart(UUID.randomUUID().toString(), new EmptyVoidAction(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, ICancellationToken cancellationToken, IScheduler scheduler) {
        return whenAll(taskId, tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, ICancellationToken cancellationToken, Option... options) {
        return whenAll(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, ICancellationToken cancellationToken) {
        return whenAll(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, IScheduler scheduler, Option... options) {
        return whenAll(taskId, tasks, new CancellationToken(), scheduler, options);
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, IScheduler scheduler) {
        return whenAll(taskId, tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, Option... options) {
        return whenAll(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks) {
        return whenAll(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return whenAll(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, options);
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, ICancellationToken cancellationToken, IScheduler scheduler) {
        return whenAll(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, ICancellationToken cancellationToken, Option... options) {
        return whenAll(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, ICancellationToken cancellationToken) {
        return whenAll(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, IScheduler scheduler, Option... options) {
        return whenAll(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, options);
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, IScheduler scheduler) {
        return whenAll(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, Option... options) {
        return whenAll(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks) {
        return whenAll(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, ICancellationToken cancellationToken, IScheduler scheduler) {
        return whenAny(taskId, tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, ICancellationToken cancellationToken, Option... options) {
        return whenAny(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, ICancellationToken cancellationToken) {
        return whenAny(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, IScheduler scheduler, Option... options) {
        return whenAny(taskId, tasks, new CancellationToken(), scheduler, options);
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, IScheduler scheduler) {
        return whenAny(taskId, tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, Option... options) {
        return whenAny(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks) {
        return whenAny(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return whenAny(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, options);
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, ICancellationToken cancellationToken, IScheduler scheduler) {
        return whenAny(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, ICancellationToken cancellationToken, Option... options) {
        return whenAny(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, ICancellationToken cancellationToken) {
        return whenAny(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, IScheduler scheduler, Option... options) {
        return whenAny(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, options);
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, IScheduler scheduler) {
        return whenAny(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, Option... options) {
        return whenAny(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks) {
        return whenAny(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, ICancellationToken cancellationToken, IScheduler scheduler) {
        return unwrap(taskId, taskToUnwrap, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, ICancellationToken cancellationToken, Option... options) {
        return unwrap(taskId, taskToUnwrap, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, ICancellationToken cancellationToken) {
        return unwrap(taskId, taskToUnwrap, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, IScheduler scheduler, Option... options) {
        return unwrap(taskId, taskToUnwrap, new CancellationToken(), scheduler, options);
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, IScheduler scheduler) {
        return unwrap(taskId, taskToUnwrap, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, Option... options) {
        return unwrap(taskId, taskToUnwrap, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap) {
        return unwrap(taskId, taskToUnwrap, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return unwrap(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, scheduler, options);
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, ICancellationToken cancellationToken, IScheduler scheduler) {
        return unwrap(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, ICancellationToken cancellationToken, Option... options) {
        return unwrap(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, ICancellationToken cancellationToken) {
        return unwrap(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, IScheduler scheduler, Option... options) {
        return unwrap(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), scheduler, options);
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, IScheduler scheduler) {
        return unwrap(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, Option... options) {
        return unwrap(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap) {
        return unwrap(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return forEach(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, scheduler, options);
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, options);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, options);
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return forEach(taskId, elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return forEach(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken, Option... options) {
        return forEach(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return forEach(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, ICancellationToken cancellationToken, Option... options) {
        return forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, IScheduler scheduler, Option... options) {
        return forEach(taskId, elements, action, new CancellationToken(), scheduler, options);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, IScheduler scheduler, Option... options) {
        return forEach(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), scheduler, options);
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, IScheduler scheduler, Option... options) {
        return forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, options);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, IScheduler scheduler, Option... options) {
        return forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, options);
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken) {
        return forEach(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, ICancellationToken cancellationToken) {
        return forEach(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, ICancellationToken cancellationToken) {
        return forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, ICancellationToken cancellationToken) {
        return forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, IScheduler scheduler) {
        return forEach(taskId, elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, IScheduler scheduler) {
        return forEach(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, IScheduler scheduler) {
        return forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, IScheduler scheduler) {
        return forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, Option... options) {
        return forEach(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, Option... options) {
        return forEach(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Option... options) {
        return forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Option... options) {
        return forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action) {
        return forEach(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action) {
        return forEach(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        return forEach(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        return forEach(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, options);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, scheduler, options);
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, options);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, options);
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return forEach(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, ICancellationToken cancellationToken, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, ICancellationToken cancellationToken, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, IScheduler scheduler, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, options);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, IScheduler scheduler, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), scheduler, options);
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, IScheduler scheduler, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, options);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, IScheduler scheduler, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, options);
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, ICancellationToken cancellationToken) {
        return forEach(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, ICancellationToken cancellationToken) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, ICancellationToken cancellationToken) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, ICancellationToken cancellationToken) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, IScheduler scheduler) {
        return forEach(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, IScheduler scheduler) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, IScheduler scheduler) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, IScheduler scheduler) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Option... options) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action) {
        return forEach(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        return forEach(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
    }

}
