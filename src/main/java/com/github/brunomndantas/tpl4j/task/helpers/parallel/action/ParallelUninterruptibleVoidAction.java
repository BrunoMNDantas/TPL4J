package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;

public class ParallelUninterruptibleVoidAction<T> implements IParallelAction<T,Void> {

    protected IParallelUninterruptibleVoidAction<T> action;
    public IParallelUninterruptibleVoidAction<T> getAction() { return this.action; }



    public ParallelUninterruptibleVoidAction(IParallelUninterruptibleVoidAction<T> action) {
        this.action = action;
    }



    @Override
    public Void run(T value, ICancellationToken token) throws Exception {
        this.action.run(value);
        return null;
    }

}
