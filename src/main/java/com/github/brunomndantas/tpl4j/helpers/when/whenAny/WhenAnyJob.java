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
package com.github.brunomndantas.tpl4j.helpers.when.whenAny;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.options.TaskOption;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.job.Job;

import java.util.Collection;
import java.util.function.Consumer;

public class WhenAnyJob<T> extends Job<Task<T>> {

    protected volatile Collection<Task<T>> tasks;
    public Collection<Task<T>> getTasks() { return this.tasks; }

    private boolean finished;



    public WhenAnyJob(String taskId, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<TaskOption> options, Collection<Task<T>> tasks) {
        super(taskId, new WhenAnyAction<>(tasks), cancellationToken, scheduler, options);
        this.tasks = tasks;
    }



    @Override
    protected void run() {
        if(this.tasks.isEmpty())
            this.finish();
        else
            for(Task<T> task : this.tasks)
                task.getStatus().finishedEvent.addListener(() -> super.scheduler.accept(this::finish));
    }

    private void finish() {
        synchronized (this.tasks) {
            if(anyTaskFinished() && !finished) {
                super.run();
                this.finished = true;
            }
        }
    }

    private boolean anyTaskFinished() {
        if(this.tasks.isEmpty())
            return true;

        return this.tasks
                .stream()
                .anyMatch((task) -> task.getStatus().finishedEvent.hasFired());
    }

}
