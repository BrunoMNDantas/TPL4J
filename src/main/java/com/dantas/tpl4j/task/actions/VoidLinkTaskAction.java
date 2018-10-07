package com.dantas.tpl4j.task.actions;

import com.dantas.tpl4j.task.Task;

@FunctionalInterface
public interface VoidLinkTaskAction<K> {

    void run(Task<K> task) throws Exception;

}
