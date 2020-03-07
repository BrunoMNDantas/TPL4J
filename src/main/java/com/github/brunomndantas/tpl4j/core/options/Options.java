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
package com.github.brunomndantas.tpl4j.core.options;

import java.util.Collection;

public class Options implements IOptions {

    protected volatile Collection<Option> options;



    public Options(Collection<Option> options) {
        this.options = options;
    }



    @Override
    public Collection<Option> getOptions() {
        return this.options;
    }

    @Override
    public boolean contains(Option option) {
        return this.options.contains(option);
    }

    @Override
    public boolean rejectChildren() {
        return this.contains(Option.REJECT_CHILDREN);
    }

    @Override
    public boolean attachToParent() {
        return this.contains(Option.ATTACH_TO_PARENT);
    }

    @Override
    public boolean notCancelable() {
        return this.contains(Option.NOT_CANCELABLE);
    }

    @Override
    public boolean notPropagateCancellation() {
        return this.contains(Option.NOT_PROPAGATE_CANCELLATION);
    }

    @Override
    public boolean notPropagateFailure() {
        return this.contains(Option.NOT_PROPAGATE_FAILURE);
    }

}
