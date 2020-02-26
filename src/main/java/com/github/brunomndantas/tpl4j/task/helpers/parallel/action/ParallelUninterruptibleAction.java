package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;

public class ParallelUninterruptibleAction<T,K> implements IParallelAction<T,K> {

    private IParallelUninterruptibleAction<T,K> action;
    public IParallelUninterruptibleAction<T,K> getAction() { return this.action; }



    public ParallelUninterruptibleAction(IParallelUninterruptibleAction<T,K> action) {
        this.action = action;
    }



    @Override
    public K run(T value, CancellationToken token) throws Exception {
        return this.action.run(value);
    }

}
