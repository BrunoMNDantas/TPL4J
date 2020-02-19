package com.github.brunomndantas.tpl4j.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.action.action.IAction;
import com.github.brunomndantas.tpl4j.core.context.cancel.CancellationToken;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;

public class ParallelWorkerAction<T,K> implements IAction<Collection<K>> {

    private IParallelAction<T,K> action;
    public IParallelAction<T,K> getAction() { return this.action; }

    private Iterator<T> iterator;
    public Iterator<T> getIterator() { return this.iterator; }



    public ParallelWorkerAction(IParallelAction<T,K> action, Iterator<T> iterator) {
         this.action = action;
         this.iterator = iterator;
    }



    @Override
    public Collection<K> run(CancellationToken cancellationToken) throws Exception {
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
