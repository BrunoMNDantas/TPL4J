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
package com.github.brunomndantas.tpl4j.task.helpers.parallel.context;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.executor.ContextExecutor;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.status.State;

import java.util.Collection;

public class ParallelContextExecutor extends ContextExecutor {

    public ParallelContextExecutor(IContextManager contextManager) {
        super(contextManager);
    }



    @Override
    protected <T> void endExecution(IContext<T> context, Collection<IContext<?>> childrenContexts, T value, Exception exception) {
        Exception childException = null;
        for(IContext<?> childContext : childrenContexts) {
            if(childContext.getStatus().getState().equals(State.CANCELED)) {
                if(childException == null)
                    childException = childContext.getResultException();
                else if(childException != childContext.getResultException())
                    childException.addSuppressed(childContext.getResultException());
            }
        }

        if(exception == null)
            exception = childException;

        super.endExecution(context, childrenContexts, value, exception);
    }

}
