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
package com.github.brunomndantas.tpl4j.task.core;

import com.github.brunomndantas.tpl4j.task.core.action.IAction;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.core.job.Job;
import com.github.brunomndantas.tpl4j.task.core.status.TaskStatus;

import java.util.Collection;
import java.util.function.Consumer;

public class BaseTask<T> {

    private volatile Job<T> job;
    public Job<T> getJob() { return this.job; }

    public String getId() { return this.job.getTaskId(); }

    public IAction<T> getAction() { return this.job.getAction(); }

    public Consumer<Runnable> getScheduler() { return this.job.getScheduler(); }

    public Collection<TaskOption> getOptions() { return this.job.getOptions(); }

    public T getValue() { return job.getValue(); }

    public Exception getException() { return job.getException(); }

    public CancellationToken getCancellationToken() { return job.getCancellationToken(); }

    public TaskStatus getStatus() { return job.getStatus(); }



    public BaseTask(Job<T> job) {
        this.job = job;
    }



    public void start() {
        job.schedule();
    }

    public void cancel() {
        job.cancel();
    }

    public T getResult() throws Exception {
        return job.getResult();
    }

    public boolean hasCancelRequest() {
        return job.hasCancelRequest();
    }

}
