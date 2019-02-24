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
package com.github.brunomndantas.tpl4j.task.action.retry;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.action.link.LinkAction;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.task.core.status.Status;

import java.util.function.Supplier;

public class RetryAction<T> extends LinkAction<T,T> {

    public static final int RETRY_UNTIL_SUCCESS = -1;



    protected volatile Supplier<Boolean> retrySupplier;
    public Supplier<Boolean> getRetrySupplier() { return this.retrySupplier; }



    public RetryAction(Task<T> previousTask, Supplier<Boolean> retrySupplier) {
        super(previousTask, null);
        this.retrySupplier = retrySupplier;
    }

    public RetryAction(Task<T> previousTask, int numberOfRetries) {
        this(previousTask, new Supplier<Boolean>() {
            private int count = 0;

            @Override
            public Boolean get() {
                if(numberOfRetries == RETRY_UNTIL_SUCCESS)
                    return true;

                return ++count <= numberOfRetries;
            }
        });
    }

    public RetryAction(Task<T> previousTask) {
        this(previousTask, 1);
    }



    @Override
    public T run(CancellationToken cancellationToken) throws Exception {
        if(super.previousTask.getStatus().getValue() == Status.SUCCEEDED) {
            return super.previousTask.getValue();
        } else {
            Exception exception = null;

            while(this.retrySupplier.get()) {
                try {
                    return super.getPreviousTask().getAction().run(cancellationToken);
                } catch (Exception e) {
                    if(e instanceof CancelledException)
                        throw e;

                    exception = e;
                }
            }

            throw exception;
        }
    }

}
