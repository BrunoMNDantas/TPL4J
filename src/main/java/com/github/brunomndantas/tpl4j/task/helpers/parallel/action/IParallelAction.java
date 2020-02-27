package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;

public interface IParallelAction<T,K> {

    K run(T value, ICancellationToken cancellationToken) throws Exception;

}
