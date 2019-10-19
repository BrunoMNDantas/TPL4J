package com.github.brunomndantas.tpl4j.task.parallel.action;

import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;

public interface IParallelVoidAction<T> {

    void run(T value, CancellationToken cancellationToken) throws Exception;

}
