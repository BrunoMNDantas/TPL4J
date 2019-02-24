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
package com.dantas.tpl4j.task.when.whenAny;

import com.dantas.tpl4j.task.Task;
import com.dantas.tpl4j.task.core.action.IAction;
import com.dantas.tpl4j.task.core.cancel.CancellationToken;

import java.util.Collection;

public class WhenAnyAction<T> implements IAction<Task<T>> {

    protected volatile Collection<Task<T>> tasks;
    public Collection<Task<T>> getTasks() { return this.tasks; }



    public WhenAnyAction(Collection<Task<T>> tasks) {
        this.tasks = tasks;
    }



    @Override
    public Task<T> run(CancellationToken cancellationToken) throws Exception {
        return this.tasks
                .stream()
                .filter((task) -> task.getStatus().finishedEvent.hasFired())
                .findFirst()
                .orElse(null);
    }

}
