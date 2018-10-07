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

import com.dantas.tpl4j.job.actions.LinkAction;

import java.util.function.Consumer;

public class LinkJob<T, K> extends Job<T> {

    private Job<K> job;
    private LinkAction<T, K> action;



    public LinkJob(Job<K> job, LinkAction<T, K> action, Consumer<Runnable> scheduler) {
        super(() -> action.run(job.getResult()), scheduler);

        this.job = job;
        this.action = action;
    }



    @Override
    public void schedule() {
        super.declareSchedule();
        job.events.finishedEvent.addListener(() -> {
            if(job.isCancelled())
                super.cancel();

            super.scheduler.accept(LinkJob.this);
        });
    }

}
