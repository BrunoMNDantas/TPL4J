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
package com.github.brunomndantas.tpl4j.factory;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.*;
import com.github.brunomndantas.tpl4j.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.helpers.when.whenAny.WhenAnyTask;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.action.action.IVoidAction;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

public class TaskFactory {

    public static <T> Task<T> create(String taskId, IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(taskId,action, cancellationToken, scheduler, options);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(taskId,action, cancellationToken, scheduler, options);
    }

    public static Task<Void> create(String taskId, IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(taskId,action, cancellationToken, scheduler, options);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(taskId,action, cancellationToken, scheduler, options);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return new Task<>(taskId, action, cancellationToken, scheduler);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return new Task<>(taskId, action, cancellationToken, scheduler);
    }

    public static Task<Void> create(String taskId, IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return new Task<>(taskId, action, cancellationToken, scheduler);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return new Task<>(taskId, action, cancellationToken, scheduler);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(taskId, action, cancellationToken, options);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(taskId, action, cancellationToken, options);
    }

    public static Task<Void> create(String taskId, IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(taskId, action, cancellationToken, options);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(taskId, action, cancellationToken, options);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(taskId, action, scheduler, options);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(taskId, action, scheduler, options);
    }

    public static Task<Void> create(String taskId, IVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(taskId, action, scheduler, options);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(taskId, action, scheduler, options);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, CancellationToken cancellationToken) {
        return new Task<>(taskId, action, cancellationToken);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken) {
        return new Task<>(taskId, action, cancellationToken);
    }

    public static Task<Void> create(String taskId, IVoidAction action, CancellationToken cancellationToken) {
        return new Task<>(taskId, action, cancellationToken);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken) {
        return new Task<>(taskId, action, cancellationToken);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, Consumer<Runnable> scheduler) {
        return new Task<>(taskId, action, scheduler);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, Consumer<Runnable> scheduler) {
        return new Task<>(taskId, action, scheduler);
    }

    public static Task<Void> create(String taskId, IVoidAction action, Consumer<Runnable> scheduler) {
        return new Task<>(taskId, action, scheduler);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, Consumer<Runnable> scheduler) {
        return new Task<>(taskId, action, scheduler);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action, Option... options) {
        return new Task<>(taskId, action, options);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action, Option... options) {
        return new Task<>(taskId, action, options);
    }

    public static Task<Void> create(String taskId, IVoidAction action, Option... options) {
        return new Task<>(taskId, action, options);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action, Option... options) {
        return new Task<>(taskId, action, options);
    }


    public static <T> Task<T> create(String taskId, IAction<T> action) {
        return new Task<>(taskId, action);
    }

    public static <T> Task<T> create(String taskId, IEmptyAction<T> action) {
        return new Task<>(taskId, action);
    }

    public static Task<Void> create(String taskId, IVoidAction action) {
        return new Task<>(taskId, action);
    }

    public static Task<Void> create(String taskId, IEmptyVoidAction action) {
        return new Task<>(taskId, action);
    }


    public static <T> Task<T> create(IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(action, cancellationToken, scheduler, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(action, cancellationToken, scheduler, options);
    }

    public static Task<Void> create(IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(action, cancellationToken, scheduler, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(action, cancellationToken, scheduler, options);
    }


    public static <T> Task<T> create(IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return new Task<>(action, cancellationToken, scheduler);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return new Task<>(action, cancellationToken, scheduler);
    }

    public static Task<Void> create(IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return new Task<>(action, cancellationToken, scheduler);
    }

    public static Task<Void> create(IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        return new Task<>(action, cancellationToken, scheduler);
    }


    public static <T> Task<T> create(IAction<T> action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(action, cancellationToken, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(action, cancellationToken, options);
    }

    public static Task<Void> create(IVoidAction action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(action, cancellationToken, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        return new Task<>(action, cancellationToken, options);
    }


    public static <T> Task<T> create(IAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(action, scheduler, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(action, scheduler, options);
    }

    public static Task<Void> create(IVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(action, scheduler, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        return new Task<>(action, scheduler, options);
    }


    public static <T> Task<T> create(IAction<T> action, CancellationToken cancellationToken) {
        return new Task<>(action, cancellationToken);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, CancellationToken cancellationToken) {
        return new Task<>(action, cancellationToken);
    }

    public static Task<Void> create(IVoidAction action, CancellationToken cancellationToken) {
        return new Task<>(action, cancellationToken);
    }

    public static Task<Void> create(IEmptyVoidAction action, CancellationToken cancellationToken) {
        return new Task<>(action, cancellationToken);
    }


    public static <T> Task<T> create(IAction<T> action, Consumer<Runnable> scheduler) {
        return new Task<>(action, scheduler);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, Consumer<Runnable> scheduler) {
        return new Task<>(action, scheduler);
    }

    public static Task<Void> create(IVoidAction action, Consumer<Runnable> scheduler) {
        return new Task<>(action, scheduler);
    }

    public static Task<Void> create(IEmptyVoidAction action, Consumer<Runnable> scheduler) {
        return new Task<>(action, scheduler);
    }


    public static <T> Task<T> create(IAction<T> action, Option... options) {
        return new Task<>(action, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, Option... options) {
        return new Task<>(action, options);
    }

    public static Task<Void> create(IVoidAction action, Option... options) {
        return new Task<>(action, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, Option... options) {
        return new Task<>(action, options);
    }


    public static <T> Task<T> create(IAction<T> action) {
        return new Task<>(action);
    }

    public static <T> Task<T> create(IEmptyAction<T> action) {
        return new Task<>(action);
    }

    public static Task<Void> create(IVoidAction action) {
        return new Task<>(action);
    }

    public static Task<Void> create(IEmptyVoidAction action) {
        return new Task<>(action);
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        Task<T> task = create(taskId, action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        Task<T> task = create(taskId, action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        Task<Void> task = create(taskId, action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        Task<Void> task = create(taskId, action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        Task<T> task = create(taskId, action, scheduler, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        Task<T> task = create(taskId, action, scheduler, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        Task<Void> task = create(taskId, action, scheduler, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        Task<Void> task = create(taskId, action, scheduler, options);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, CancellationToken cancellationToken, Option... options) {
        Task<T> task = create(taskId, action, cancellationToken, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        Task<T> task = create(taskId, action, cancellationToken, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, CancellationToken cancellationToken, Option... options) {
        Task<Void> task = create(taskId, action, cancellationToken, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        Task<Void> task = create(taskId, action, cancellationToken, options);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, Option... options) {
        Task<T> task = create(taskId, action, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, Option... options) {
        Task<T> task = create(taskId, action, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, Option... options) {
        Task<Void> task = create(taskId, action, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, Option... options) {
        Task<Void> task = create(taskId, action, options);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        Task<T> task = create(taskId,action, cancellationToken, scheduler);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        Task<T> task = create(taskId,action, cancellationToken, scheduler);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        Task<Void> task = create(taskId,action, cancellationToken, scheduler);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        Task<Void> task = create(taskId,action, cancellationToken, scheduler);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, Consumer<Runnable> scheduler) {
        Task<T> task = create(taskId, action, scheduler);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, Consumer<Runnable> scheduler) {
        Task<T> task = create(taskId, action, scheduler);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, Consumer<Runnable> scheduler) {
        Task<Void> task = create(taskId, action, scheduler);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, Consumer<Runnable> scheduler) {
        Task<Void> task = create(taskId, action, scheduler);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action, CancellationToken cancellationToken) {
        Task<T> task = create(taskId, action, cancellationToken);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action, CancellationToken cancellationToken) {
        Task<T> task = create(taskId, action, cancellationToken);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action, CancellationToken cancellationToken) {
        Task<Void> task = create(taskId, action, cancellationToken);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action, CancellationToken cancellationToken) {
        Task<Void> task = create(taskId, action, cancellationToken);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(String taskId, IAction<T> action) {
        Task<T> task = create(taskId, action);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(String taskId, IEmptyAction<T> action) {
        Task<T> task = create(taskId, action);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IVoidAction action) {
        Task<Void> task = create(taskId, action);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(String taskId, IEmptyVoidAction action) {
        Task<Void> task = create(taskId, action);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        Task<T> task = create(action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        Task<T> task = create(action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        Task<Void> task = create(action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        Task<Void> task = create(action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        Task<T> task = create(action, cancellationToken, scheduler);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        Task<T> task = create(action, cancellationToken, scheduler);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        Task<Void> task = create(action, cancellationToken, scheduler);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        Task<Void> task = create(action, cancellationToken, scheduler);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action, CancellationToken cancellationToken, Option... options) {
        Task<T> task = create(action, cancellationToken, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, CancellationToken cancellationToken, Option... options) {
        Task<T> task = create(action, cancellationToken, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, CancellationToken cancellationToken, Option... options) {
        Task<Void> task = create(action, cancellationToken, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, CancellationToken cancellationToken, Option... options) {
        Task<Void> task = create(action, cancellationToken, options);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        Task<T> task = create(action, scheduler, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        Task<T> task = create(action, scheduler, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        Task<Void> task = create(action, scheduler, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, Consumer<Runnable> scheduler, Option... options) {
        Task<Void> task = create(action, scheduler, options);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action, CancellationToken cancellationToken) {
        Task<T> task = create(action, cancellationToken);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, CancellationToken cancellationToken) {
        Task<T> task = create(action, cancellationToken);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, CancellationToken cancellationToken) {
        Task<Void> task = create(action, cancellationToken);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, CancellationToken cancellationToken) {
        Task<Void> task = create(action, cancellationToken);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action, Consumer<Runnable> scheduler) {
        Task<T> task = create(action, scheduler);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, Consumer<Runnable> scheduler) {
        Task<T> task = create(action, scheduler);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, Consumer<Runnable> scheduler) {
        Task<Void> task = create(action, scheduler);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, Consumer<Runnable> scheduler) {
        Task<Void> task = create(action, scheduler);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action, Option... options) {
        Task<T> task = create(action, options);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, Option... options) {
        Task<T> task = create(action, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, Option... options) {
        Task<Void> task = create(action, options);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, Option... options) {
        Task<Void> task = create(action, options);
        task.start();
        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action) {
        Task<T> task = create(action);
        task.start();
        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action) {
        Task<T> task = create(action);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action) {
        Task<Void> task = create(action);
        task.start();
        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action) {
        Task<Void> task = create(action);
        task.start();
        return task;
    }


    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, Consumer<Runnable> scheduler, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(String taskId, Collection<Task<T>> tasks) {
        WhenAllTask<T> task = new WhenAllTask<>(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        WhenAllTask<T> task = new WhenAllTask<>(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        WhenAllTask<T> task = new WhenAllTask<>(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, Consumer<Runnable> scheduler, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        WhenAllTask<T> task = new WhenAllTask<>(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, Option... options) {
        WhenAllTask<T> task = new WhenAllTask<>(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks) {
        WhenAllTask<T> task = new WhenAllTask<>(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, Consumer<Runnable> scheduler, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(String taskId, Collection<Task<T>> tasks) {
        WhenAnyTask<T> task = new WhenAnyTask<>(taskId, tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        WhenAnyTask<T> task = new WhenAnyTask<>(UUID.randomUUID().toString(), tasks, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, CancellationToken cancellationToken, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, CancellationToken cancellationToken) {
        WhenAnyTask<T> task = new WhenAnyTask<>(UUID.randomUUID().toString(), tasks, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, Consumer<Runnable> scheduler, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        WhenAnyTask<T> task = new WhenAnyTask<>(UUID.randomUUID().toString(), tasks, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, Option... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks) {
        WhenAnyTask<T> task = new WhenAnyTask<>(UUID.randomUUID().toString(), tasks, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, Consumer<Runnable> scheduler, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, Consumer<Runnable> scheduler) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(String taskId, Task<Task<T>> taskToUnwrap) {
        UnwrapTask<T> task = new UnwrapTask<>(taskId, taskToUnwrap, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        UnwrapTask<T> task = new UnwrapTask<>(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, CancellationToken cancellationToken) {
        UnwrapTask<T> task = new UnwrapTask<>(UUID.randomUUID().toString(), taskToUnwrap, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, Consumer<Runnable> scheduler, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, Consumer<Runnable> scheduler) {
        UnwrapTask<T> task = new UnwrapTask<>(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, Option... options) {
        UnwrapTask<T> task = new UnwrapTask<>(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap) {
        UnwrapTask<T> task = new UnwrapTask<>(UUID.randomUUID().toString(), taskToUnwrap, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, options);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, Consumer<Runnable> scheduler) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, Consumer<Runnable> scheduler) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Consumer<Runnable> scheduler) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Consumer<Runnable> scheduler) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelAction<T,K> action) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelVoidAction<T> action) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        ParallelTask<T,K> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(String taskId, Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        ParallelTask<T,Void> task = new ParallelTask<>(taskId, elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, options);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, action, cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Consumer<Runnable> scheduler, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, options);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, action, cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, CancellationToken cancellationToken) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, CancellationToken cancellationToken) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, CancellationToken cancellationToken) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), cancellationToken, Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, Consumer<Runnable> scheduler) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, action, new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, Consumer<Runnable> scheduler) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Consumer<Runnable> scheduler) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Consumer<Runnable> scheduler) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), scheduler, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action, Option... options) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action, Option... options) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, options);
        task.start();
        return task;
    }


    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelAction<T,K> action) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, action, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelVoidAction<T> action) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T,K> ParallelTask<T,K> forEach(Iterable<T> elements, IParallelUninterruptibleAction<T,K> action) {
        ParallelTask<T,K> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;
    }

    public static <T> ParallelTask<T,Void> forEach(Iterable<T> elements, IParallelUninterruptibleVoidAction<T> action) {
        ParallelTask<T,Void> task = new ParallelTask<>(UUID.randomUUID().toString(), elements, new ParallelUninterruptibleVoidAction<>(action), new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();
        return task;

    }

}
