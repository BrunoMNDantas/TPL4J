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
package com.github.brunomndantas.tpl4j.task.action.link;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.task.Task;

public class LinkAction<T, K> implements IAction<T> {

    protected volatile Task<K> previousTask;
    public Task<K> getPreviousTask() { return this.previousTask; }

    protected volatile ILinkAction<T, K> action;
    public ILinkAction<T, K> getAction() { return this.action; }



    public LinkAction(Task<K> previousTask, ILinkAction<T, K> action) {
        this.previousTask = previousTask;
        this.action = action;
    }



    @Override
    public T run(ICancellationToken cancellationToken) throws Exception {
        return this.action.run(this.previousTask, cancellationToken);
    }

}
