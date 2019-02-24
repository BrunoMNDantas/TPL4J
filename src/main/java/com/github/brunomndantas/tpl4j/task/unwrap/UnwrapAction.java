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
package com.github.brunomndantas.tpl4j.task.unwrap;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.action.IAction;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.core.job.Job;

public class UnwrapAction<T> implements IAction<T> {

    protected volatile Job<Task<T>> job;
    public Job<Task<T>> getJob() { return this.job; }



    public UnwrapAction(Job<Task<T>> job) {
        this.job = job;
    }



    @Override
    public T run(CancellationToken cancellationToken) throws Exception {
        if(this.job.getStatus().cancelledEvent.hasFired())
            throw cancellationToken.abort();

        Task<T> task = job.getResult();

        if(task.getStatus().cancelledEvent.hasFired())
            throw cancellationToken.abort();

        return task.getResult();
    }

}
