package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.status.State;

public interface IStateExecutor {

    State getState();

    <T> void execute(IContext<T> context);

}
