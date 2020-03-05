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
package com.github.brunomndantas.tpl4j.context;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.IStatus;

import java.util.Collection;

public interface IContext<T> {

    String getTaskId();

    IAction<T> getAction();

    ICancellationToken getCancellationToken();

    IScheduler getScheduler();

    IOptions getOptions();

    IStatus getStatus();


    IContext<?> getParentContext();

    void setParentContext(IContext<?> parentContext);


    Collection<IContext<?>> getChildrenContexts();

    boolean hasChild(IContext<?> context);

    void addChild(IContext<?> context);


    long getCreatorThreadId();

    void setCreatorThreadId(long creatorThreadId);


    long getExecutorThreadId();

    void setExecutorThreadId(long executorThreadId);


    T getResultValue();

    void setResultValue(T resultValue);


    Exception getResultException();

    void setResultException(Exception resultException);

}
