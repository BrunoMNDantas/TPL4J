package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class WhenAllContextExecutorTest {
    
    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();
        WhenAllContextExecutor<String> executor = new WhenAllContextExecutor<>(null, tasks);
        assertSame(tasks, executor.getTasks());
    }

    @Test
    public void constructorTest() {
        ContextManager contextManager = new ContextManager();
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAllContextExecutor<String> executor = new WhenAllContextExecutor<>(contextManager, tasks);

        assertSame(contextManager, executor.getContextManager());
        assertSame(tasks, executor.getTasks());
        assertTrue(executor.getScheduledStateExecutor() instanceof WhenAllScheduledStateExecutor);
    }

}
