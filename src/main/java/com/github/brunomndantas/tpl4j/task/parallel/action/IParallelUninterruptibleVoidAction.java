package com.github.brunomndantas.tpl4j.task.parallel.action;

public interface IParallelUninterruptibleVoidAction<T> {

    void run(T value) throws Exception;

}
