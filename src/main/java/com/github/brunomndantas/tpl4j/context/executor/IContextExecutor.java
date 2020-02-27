package com.github.brunomndantas.tpl4j.context.executor;

import com.github.brunomndantas.tpl4j.context.Context;

public interface IContextExecutor {

    <T> void execute(Context<T> context);

}
