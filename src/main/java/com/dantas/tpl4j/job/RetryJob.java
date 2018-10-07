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
import java.util.function.Supplier;

public class RetryJob<T> extends Job<T> {

    public static final int RETRY_UNTIL_SUCCESS = -1;



    private Job<T> job;
    private Supplier<Boolean> retrySupplier;



    public RetryJob(Job<T> job, Supplier<Boolean> retrySupplier, Consumer<Runnable> scheduler) {
        super(job::getResult, scheduler);
        this.job = job;
        this.retrySupplier = retrySupplier;
    }

    public RetryJob(Job<T> job, int numberOfRetries, Consumer<Runnable> scheduler) {
        this(job, new Supplier<Boolean>() {
            private int count = 0;

            @Override
            public Boolean get() {
                if(numberOfRetries == RETRY_UNTIL_SUCCESS)
                    return true;

                return ++count <= numberOfRetries;
            }
        }, scheduler);
    }

    public RetryJob(Job<T> job, Consumer<Runnable> scheduler) {
        this(job, 1, scheduler);
    }



    @Override
    public void schedule() {
        super.declareSchedule();
        job.events.finishedEvent.addListener(this::checkFinish);
    }

    private void checkFinish() {
        if(job.isCancelled()) {
            super.cancel();
            super.scheduler.accept(this);
            return;
        }

        if(job.getException() != null && retrySupplier.get()) {
            resetJobState();
            job.schedule();
            return;
        }

        super.scheduler.accept(this);
    }

    private void resetJobState() {
        job.setException(null);
        job.setValue(null);

        job.setStatus(JobStatus.CREATED);

        job.events.scheduledEvent.resetState();
        job.events.runningEvent.resetState();
        job.events.succeededEvent.resetState();
        job.events.failedEvent.resetState();
        job.events.cancelledEvent.resetState();
        job.events.finishedEvent.resetState();

        job.hasCancelRequest = false;
    }

}
