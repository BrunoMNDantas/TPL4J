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
package com.github.brunomndantas.tpl4j.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.job.Job;

import java.util.Collection;
import java.util.function.Consumer;

public class WhenAllJob<T> extends Job<Collection<T>> {

    protected volatile Collection<Job<T>> jobs;
    public Collection<Job<T>> getJobs() { return this.jobs; }

    private boolean finished;



    public WhenAllJob(String taskId, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Collection<Option> options, Collection<Job<T>> jobs) {
        super(taskId, new WhenAllAction<>(jobs), cancellationToken, scheduler, options);
        this.jobs = jobs;
    }



    @Override
    protected void run() {
        if(this.jobs.isEmpty())
            this.finish();
        else
            for(Job<T> job : this.jobs)
                job.getStatus().finishedEvent.addListener(() -> super.scheduler.accept(this::finish));
    }

    private void finish() {
        synchronized (this.jobs) {
            if(allJobsFinished() && !finished) {
                super.run();
                this.finished = true;
            }
        }
    }

    private boolean allJobsFinished() {
        return this.jobs
                .stream()
                .allMatch((job) -> job.getStatus().finishedEvent.hasFired());
    }

}
