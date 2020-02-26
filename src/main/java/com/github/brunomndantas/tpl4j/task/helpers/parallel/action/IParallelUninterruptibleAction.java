package com.github.brunomndantas.tpl4j.task.helpers.parallel.action;

public interface IParallelUninterruptibleAction<T,K> {

    K run(T value) throws Exception;

}
