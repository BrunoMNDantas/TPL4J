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
package com.dantas.tpl4j.job;

import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

public class CollectJob<T> extends Job<Collection<T>> {

    private Collection<Job<T>> jobs;



    public CollectJob(Collection<Job<T>> jobs, Consumer<Runnable> scheduler) {
        super(() -> {
            Collection<T> results = new LinkedList<>();

            for(Job<T> job : jobs)
                results.add(job.getResult());

            return results;
        }, scheduler);

        this.jobs = jobs;
    }



    @Override
    public void schedule() {
        super.declareSchedule();
        registerFinishListeners();
    }

    private void registerFinishListeners() {
        if(jobs.isEmpty())
            super.scheduler.accept(this);

        boolean[] finished = new boolean[1];
        for(Job<T> job : jobs) {
            job.events.finishedEvent.addListener(() -> {
                synchronized (finished) {
                    if(finished[0])
                        return;

                    for(Job<T> j : jobs)
                        if(!j.events.finishedEvent.hasFired())
                            return;

                    finished[0] = true;

                    super.scheduler.accept(CollectJob.this);
                }
            });
        }
    }

}
