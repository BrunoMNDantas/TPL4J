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

import com.github.brunomndantas.tpl4j.core.action.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.job.Job;

import java.util.Collection;
import java.util.LinkedList;

public class WhenAllAction<T> implements IAction<Collection<T>> {

    protected volatile Collection<Job<T>> jobs;
    public Collection<Job<T>> getJobs() { return this.jobs; }



    public WhenAllAction(Collection<Job<T>> jobs) {
        this.jobs = jobs;
    }



    @Override
    public Collection<T> run(CancellationToken cancellationToken) throws Exception {
        if(this.jobs.stream().anyMatch((job) -> job.getStatus().failedEvent.hasFired()))
            throw collectErrors();

        if(this.jobs.stream().anyMatch((job) -> job.getStatus().cancelledEvent.hasFired()))
            throw new CancelledException();

        return collectResults(cancellationToken);
    }

    private Exception collectErrors() {
        Exception exception = null;

        for(Job<T> job : this.jobs) {
            if(job.getStatus().failedEvent.hasFired()) {
                if(exception == null)
                    exception = new Exception(job.getException().getMessage(), job.getException());
                else
                    exception.addSuppressed(job.getException());
            }
        }

        return exception;
    }

    private Collection<T> collectResults(CancellationToken token) throws Exception {
        Collection<T> results = new LinkedList<>();

        for(Job<T> job : this.jobs) {
            token.abortIfCancelRequested();
            results.add(job.getResult());
        }

        return results;
    }

}
