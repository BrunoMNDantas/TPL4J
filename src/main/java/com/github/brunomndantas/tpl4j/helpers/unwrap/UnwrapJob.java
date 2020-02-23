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
package com.github.brunomndantas.tpl4j.helpers.unwrap;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.options.TaskOption;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.job.Job;
import com.github.brunomndantas.tpl4j.core.status.State;

import java.util.Collection;
import java.util.function.Consumer;

public class UnwrapJob<T> extends Job<T> {

    protected volatile Job<Task<T>> job;
    public Job<Task<T>> getJob() { return this.job; }



    public UnwrapJob(String taskId, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<TaskOption> options, Job<Task<T>> job) {
        super(taskId, new UnwrapAction<>(job), cancellationToken, scheduler, options);
        this.job = job;
    }



    @Override
    protected void run() {
        this.job.getStatus().finishedEvent.addListener(() -> {
            super.scheduler.accept(() -> {
                if(this.job.getStatus().getValue() != State.SUCCEEDED)
                    super.scheduler.accept(() -> super.run());
                else
                    this.job.getValue().getStatus().finishedEvent.addListener(() -> {
                        super.scheduler.accept(() -> super.run());
                    });
            });
        });
    }

}
