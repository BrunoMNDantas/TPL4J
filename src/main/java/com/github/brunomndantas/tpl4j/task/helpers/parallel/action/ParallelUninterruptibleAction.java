package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;

public class ParallelUninterruptibleAction<T,K> implements IParallelAction<T,K> {

    protected IParallelUninterruptibleAction<T,K> action;
    public IParallelUninterruptibleAction<T,K> getAction() { return this.action; }



    public ParallelUninterruptibleAction(IParallelUninterruptibleAction<T,K> action) {
        this.action = action;
    }



    @Override
    public K run(T value, ICancellationToken token) throws Exception {
        return this.action.run(value);
    }

}
