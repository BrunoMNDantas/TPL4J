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
package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.context.job.Job;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.Task;

import java.util.Collection;
import java.util.function.Consumer;

public class WhenAllJob<T> extends Job<Collection<T>> {

    protected volatile Collection<Task<T>> tasks;
    public Collection<Task<T>> getTasks() { return this.tasks; }

    private boolean finished;



    public WhenAllJob(String taskId, ICancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<Option> options, Collection<Task<T>> tasks) {
        super(taskId, new WhenAllAction<>(tasks), cancellationToken, scheduler, options);
        this.tasks = tasks;
    }



    @Override
    protected void run() {
        if(this.tasks.isEmpty())
            this.finish();
        else
            for(Task<T> task : this.tasks)
                task.getStatus().getFinishedEvent().addListener(() -> super.scheduler.accept(this::finish));
    }

    private void finish() {
        synchronized (this.tasks) {
            if(allJobsFinished() && !finished) {
                super.run();
                this.finished = true;
            }
        }
    }

    private boolean allJobsFinished() {
        return this.tasks
                .stream()
                .allMatch((job) -> job.getStatus().getFinishedEvent().hasFired());
    }

}
