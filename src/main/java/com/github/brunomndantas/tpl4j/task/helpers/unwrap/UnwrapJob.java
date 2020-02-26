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
package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.context.job.Job;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.Task;

import java.util.Collection;
import java.util.function.Consumer;

public class UnwrapJob<T> extends Job<T> {

    protected volatile Task<Task<T>> task;
    public Task<Task<T>> getTask() { return this.task; }



    public UnwrapJob(String taskId, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<Option> options, Task<Task<T>> task) {
        super(taskId, new UnwrapAction<>(task), cancellationToken, scheduler, options);
        this.task = task;
    }



    @Override
    protected void run() {
        this.task.getStatus().finishedEvent.addListener(() -> {
            super.scheduler.accept(() -> {
                if(this.task.getStatus().getState() != State.SUCCEEDED)
                    super.scheduler.accept(() -> super.run());
                else
                    this.task.getValue().getStatus().finishedEvent.addListener(() -> {
                        super.scheduler.accept(() -> super.run());
                    });
            });
        });
    }

}
