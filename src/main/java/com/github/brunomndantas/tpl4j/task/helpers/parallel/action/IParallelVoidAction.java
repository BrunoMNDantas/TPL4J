package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;

public interface IParallelVoidAction<T> {

    void run(T value, ICancellationToken cancellationToken) throws Exception;

}
