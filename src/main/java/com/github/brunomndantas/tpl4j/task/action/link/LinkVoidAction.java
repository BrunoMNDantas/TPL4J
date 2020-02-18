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

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.action.action.IAction;
import com.github.brunomndantas.tpl4j.task.context.cancel.CancellationToken;

public class LinkVoidAction<T> implements IAction<Void> {

    protected volatile Task<T> previousTask;
    public Task<T> getPreviousTask() { return this.previousTask; }

    protected volatile ILinkVoidAction<T> action;
    public ILinkVoidAction<T> getAction() { return this.action; }



    public LinkVoidAction(Task<T> previousTask, ILinkVoidAction<T> action) {
        this.previousTask = previousTask;
        this.action = action;
    }



    @Override
    public Void run(CancellationToken cancellationToken) throws Exception {
        this.action.run(this.previousTask, cancellationToken);
        return null;
    }

}
