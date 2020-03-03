package com.github.brunomndantas.tpl4j.task.helpers.parallel.context;

import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class ParallelContextExecutorTest {

    @Test
    public void constructorTest() {
        ContextManager contextManager = new ContextManager();
        ParallelContextExecutor executor = new ParallelContextExecutor(contextManager);
        assertSame(contextManager, executor.getContextManager());
    }

    @Test
    public void executionEndWithCancelIfChildrenEndsWithCancelTest() throws Exception {
        Task<?> parentTask = new Task<>((t) -> {
            Task<String> childA = new Task<>((ct) -> { ct.cancel(); ct.abortIfCancelRequested(); return null; }, Option.ATTACH_TO_PARENT);
            Task<String> childB = new Task<>((ct) -> { ct.cancel(); ct.abortIfCancelRequested(); return null; }, Option.ATTACH_TO_PARENT);
            childA.start();
            childB.start();
        });

        ParallelContextExecutor executor = new ParallelContextExecutor(Task.DEFAULT_CONTEXT_MANAGER);
        executor.execute(parentTask.getContext());

        parentTask.getFinishedEvent().await();

        assertEquals(parentTask.getState(), State.CANCELED);
    }


}
