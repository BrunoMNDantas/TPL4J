package com.dantas.tpl4j.task.actions;

import com.dantas.tpl4j.job.actions.LinkAction;
import com.dantas.tpl4j.task.Task;

@FunctionalInterface
public interface TaskLinkTaskAction<T, K> extends LinkAction<Task<T>, Task<K>> {

    Task<T> run(Task<K> task) throws Exception;

}
