package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;

public class ParallelVoidAction<T> implements IParallelAction<T,Void> {

    protected volatile IParallelVoidAction<T> action;
    public IParallelVoidAction<T> getAction() { return this.action; }



    public ParallelVoidAction(IParallelVoidAction<T> action) {
        this.action = action;
    }



    @Override
    public Void run(T value, ICancellationToken cancellationToken) throws Exception {
        this.action.run(value, cancellationToken);
        return null;
    }

}
