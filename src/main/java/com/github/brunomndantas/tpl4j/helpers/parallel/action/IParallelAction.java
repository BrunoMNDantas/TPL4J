package com.github.brunomndantas.tpl4j.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;

public interface IParallelAction<T,K> {

    K run(T value, CancellationToken cancellationToken) throws Exception;

}
