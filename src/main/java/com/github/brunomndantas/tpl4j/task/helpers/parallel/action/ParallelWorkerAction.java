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
package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ParallelWorkerAction<T,K> implements IAction<Collection<K>> {

    protected volatile IParallelAction<T,K> action;
    public IParallelAction<T,K> getAction() { return this.action; }

    protected volatile Iterator<T> iterator;
    public Iterator<T> getIterator() { return this.iterator; }



    public ParallelWorkerAction(IParallelAction<T,K> action, Iterator<T> iterator) {
         this.action = action;
         this.iterator = iterator;
    }



    @Override
    public Collection<K> run(ICancellationToken cancellationToken) throws Exception {
        Collection<K> results = new LinkedList<>();
        T element;
        K result;

        while(true) {
            synchronized (iterator) {
                if(iterator.hasNext())
                    element = iterator.next();
                else
                    break;
            }

            result = action.run(element, cancellationToken);
            results.add(result);
        }

        return results;
    }

}
