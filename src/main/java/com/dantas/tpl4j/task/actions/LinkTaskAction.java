package com.dantas.tpl4j.task.actions;

import com.dantas.tpl4j.task.Task;

@FunctionalInterface
public interface LinkTaskAction<T, K> {

    T run(Task<K> task) throws Exception;

}
