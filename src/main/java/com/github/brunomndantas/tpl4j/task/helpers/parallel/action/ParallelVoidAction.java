package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;

public class ParallelVoidAction<T> implements IParallelAction<T,Void> {

    private volatile IParallelVoidAction<T> action;
    public IParallelVoidAction<T> getAction() { return this.action; }



    public ParallelVoidAction(IParallelVoidAction<T> action) {
        this.action = action;
    }



    @Override
    public Void run(T value, CancellationToken cancellationToken) throws Exception {
        this.action.run(value, cancellationToken);
        return null;
    }

}
