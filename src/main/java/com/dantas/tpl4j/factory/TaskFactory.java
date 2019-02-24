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
package com.dantas.tpl4j.factory;

import com.dantas.tpl4j.task.Task;
import com.dantas.tpl4j.task.core.TaskOption;
import com.dantas.tpl4j.task.core.action.IAction;
import com.dantas.tpl4j.task.core.action.IEmptyAction;
import com.dantas.tpl4j.task.core.action.IEmptyVoidAction;
import com.dantas.tpl4j.task.core.action.IVoidAction;
import com.dantas.tpl4j.task.unwrap.UnwrapTask;
import com.dantas.tpl4j.task.when.whenAll.WhenAllTask;
import com.dantas.tpl4j.task.when.whenAny.WhenAnyTask;

import java.util.Collection;
import java.util.function.Consumer;

public class TaskFactory {

    public static <T> Task<T> create(IAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        return new Task<>(action, scheduler, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        return new Task<>(action, scheduler, options);
    }

    public static Task<Void> create(IVoidAction action, Consumer<Runnable> scheduler, TaskOption... options) {
        return new Task<>(action, scheduler, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, Consumer<Runnable> scheduler, TaskOption... options) {
        return new Task<>(action, scheduler, options);
    }


    public static <T> Task<T> create(IAction<T> action, TaskOption... options) {
        return new Task<>(action, options);
    }

    public static <T> Task<T> create(IEmptyAction<T> action, TaskOption... options) {
        return new Task<>(action, options);
    }

    public static Task<Void> create(IVoidAction action, TaskOption... options) {
        return new Task<>(action, options);
    }

    public static Task<Void> create(IEmptyVoidAction action, TaskOption... options) {
        return new Task<>(action, options);
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


    public static <T> Task<T> createAndStart(IAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        Task<T> task = create(action, scheduler, options);

        task.start();

        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, Consumer<Runnable> scheduler, TaskOption... options) {
        Task<T> task = create(action, scheduler, options);

        task.start();

        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, Consumer<Runnable> scheduler, TaskOption... options) {
        Task<Void> task = create(action, scheduler, options);

        task.start();

        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, Consumer<Runnable> scheduler, TaskOption... options) {
        Task<Void> task = create(action, scheduler, options);

        task.start();

        return task;
    }


    public static <T> Task<T> createAndStart(IAction<T> action, TaskOption... options) {
        Task<T> task = create(action, options);

        task.start();

        return task;
    }

    public static <T> Task<T> createAndStart(IEmptyAction<T> action, TaskOption... options) {
        Task<T> task = create(action, options);

        task.start();

        return task;
    }

    public static Task<Void> createAndStart(IVoidAction action, TaskOption... options) {
        Task<Void> task = create(action, options);

        task.start();

        return task;
    }

    public static Task<Void> createAndStart(IEmptyVoidAction action, TaskOption... options) {
        Task<Void> task = create(action, options);

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


    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, Consumer<Runnable> scheduler, TaskOption... options) {
        WhenAllTask<T> task = new WhenAllTask<>(tasks, scheduler, options);

        task.start();

        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, TaskOption... options) {
        WhenAllTask<T> task = new WhenAllTask<>(tasks, options);

        task.start();

        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        WhenAllTask<T> task = new WhenAllTask<>(tasks, scheduler);

        task.start();

        return task;
    }

    public static <T> WhenAllTask<T> whenAll(Collection<Task<T>> tasks) {
        WhenAllTask<T> task = new WhenAllTask<>(tasks);

        task.start();

        return task;
    }


    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, Consumer<Runnable> scheduler, TaskOption... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(tasks, scheduler, options);

        task.start();

        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, TaskOption... options) {
        WhenAnyTask<T> task = new WhenAnyTask<>(tasks, options);

        task.start();

        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks, Consumer<Runnable> scheduler) {
        WhenAnyTask<T> task = new WhenAnyTask<>(tasks, scheduler);

        task.start();

        return task;
    }

    public static <T> WhenAnyTask<T> whenAny(Collection<Task<T>> tasks) {
        WhenAnyTask<T> task = new WhenAnyTask<>(tasks);

        task.start();

        return task;
    }


    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, Consumer<Runnable> scheduler, TaskOption... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskToUnwrap, scheduler, options);

        task.start();

        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, TaskOption... options) {
        UnwrapTask<T> task = new UnwrapTask<>(taskToUnwrap, options);

        task.start();

        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap, Consumer<Runnable> scheduler) {
        UnwrapTask<T> task = new UnwrapTask<>(taskToUnwrap, scheduler);

        task.start();

        return task;
    }

    public static <T> UnwrapTask<T> unwrap(Task<Task<T>> taskToUnwrap) {
        UnwrapTask<T> task = new UnwrapTask<>(taskToUnwrap);

        task.start();

        return task;
    }

}
