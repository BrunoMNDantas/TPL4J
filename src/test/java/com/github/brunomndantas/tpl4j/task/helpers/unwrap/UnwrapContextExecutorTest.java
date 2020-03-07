package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class UnwrapContextExecutorTest {

    @Test
    public void getTaskTest() {
        Task<String> t = new Task<>(() -> null);
        Task<Task<String>> task = new Task<>((token) -> t);
        UnwrapContextExecutor<?> executor = new UnwrapContextExecutor<>(null, task);
        assertSame(task, executor.getTask());
    }

    @Test
    public void constructorTest() {
        ContextManager contextManager = new ContextManager();
        Task<String> t = new Task<>(() -> null);
        Task<Task<String>> task = new Task<>((token) -> t);

        UnwrapContextExecutor<?> executor = new UnwrapContextExecutor<>(contextManager, task);

        assertSame(task, executor.getTask());
        assertSame(contextManager, executor.getContextManager());
        assertTrue(executor.getScheduledStateExecutor() instanceof UnwrapScheduledStateExecutor);
    }

}
