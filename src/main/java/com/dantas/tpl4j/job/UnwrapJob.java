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

import java.util.function.Consumer;

public class UnwrapJob<T, K> extends Job<T> {

    private Job<Job<T>> job;



    public UnwrapJob(Job<Job<T>> job, Consumer<Runnable> scheduler) {
        super(() -> job.getResult().getResult(), scheduler);
        this.job = job;
    }



    @Override
    public void schedule() {
        super.declareSchedule();
        job.events.finishedEvent.addListener(this::checkForFinish);
    }

    private void checkForFinish() {
        if(job.isCancelled()) {
            super.cancel();
            super.scheduler.accept(this);
        }

        if(job.getException() != null)
            super.scheduler.accept(this);

        job.getValue().events.finishedEvent.addListener(() -> {
            if(job.getValue().isCancelled())
                super.cancel();

            super.scheduler.accept(UnwrapJob.this);
        });
    }

}
