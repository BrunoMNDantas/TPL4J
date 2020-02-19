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
package com.github.brunomndantas.tpl4j.core.action.link;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.action.action.IAction;
import com.github.brunomndantas.tpl4j.core.context.cancel.CancellationToken;

public class LinkEmptyAction<T, K> implements IAction<T> {

    protected volatile Task<K> previousTask;
    public Task<K> getPreviousTask() { return this.previousTask; }

    protected volatile ILinkEmptyAction<T> action;
    public ILinkEmptyAction<T> getAction() { return this.action; }



    public LinkEmptyAction(Task<K> previousTask, ILinkEmptyAction<T> action) {
        this.previousTask = previousTask;
        this.action = action;
    }



    @Override
    public T run(CancellationToken cancellationToken) throws Exception {
        return this.action.run();
    }

}
