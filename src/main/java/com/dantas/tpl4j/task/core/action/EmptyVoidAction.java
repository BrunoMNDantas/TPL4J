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
package com.dantas.tpl4j.task.core.action;

import com.dantas.tpl4j.task.core.cancel.CancellationToken;

public class EmptyVoidAction implements IAction<Void> {

    private volatile IEmptyVoidAction action;
    public IEmptyVoidAction getAction() { return this.action; }



    public EmptyVoidAction(IEmptyVoidAction action) {
        this.action = action;
    }



    @Override
    public Void run(CancellationToken cancellationToken) throws Exception {
        this.action.run();
        return null;
    }

}
