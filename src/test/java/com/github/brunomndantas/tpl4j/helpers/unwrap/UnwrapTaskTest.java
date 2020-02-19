package com.github.brunomndantas.tpl4j.helpers.unwrap;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.context.TaskOption;
import com.github.brunomndantas.tpl4j.core.context.cancel.CancellationToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class UnwrapTaskTest {

    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = (action) -> new Thread(action).start();
    private static final TaskOption[] OPTIONS = { };



    @Test
    public void getTaskTest() {
        Task<Task<String>> task = new Task<>(() -> null);
        UnwrapTask<String> unwrapTask = new UnwrapTask<>(task);

        assertSame(task, unwrapTask.getTask());
    }

    @Test
    public void constructorsTest() {
        String id = "";
        Task<Task<String>> task = new Task<>(() -> null);
        UnwrapTask<String> unwrapTask;
        
        unwrapTask = new UnwrapTask<>(id, task, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validate(unwrapTask, id, task, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        unwrapTask = new UnwrapTask<>(id, task, SCHEDULER, OPTIONS);
        validate(unwrapTask, id, task, null, SCHEDULER, OPTIONS);

        unwrapTask = new UnwrapTask<>(id, task, CANCELLATION_TOKEN, SCHEDULER);
        validate(unwrapTask, id, task, CANCELLATION_TOKEN, SCHEDULER, null);
        unwrapTask = new UnwrapTask<>(id, task, SCHEDULER);
        validate(unwrapTask, id, task, null, SCHEDULER, null);

        unwrapTask = new UnwrapTask<>(id, task, CANCELLATION_TOKEN, OPTIONS);
        validate(unwrapTask, id, task, CANCELLATION_TOKEN, null, OPTIONS);
        unwrapTask = new UnwrapTask<>(id, task, OPTIONS);
        validate(unwrapTask, id, task, null, null, OPTIONS);

        unwrapTask = new UnwrapTask<>(id, task, CANCELLATION_TOKEN);
        validate(unwrapTask, id, task, CANCELLATION_TOKEN, null, null);
        unwrapTask = new UnwrapTask<>(id, task);
        validate(unwrapTask, id, task, null, null, null);


        unwrapTask = new UnwrapTask<>(task, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validate(unwrapTask, null, task, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        unwrapTask = new UnwrapTask<>(task, SCHEDULER, OPTIONS);
        validate(unwrapTask, null, task, null, SCHEDULER, OPTIONS);

        unwrapTask = new UnwrapTask<>(task, CANCELLATION_TOKEN, SCHEDULER);
        validate(unwrapTask, null, task, CANCELLATION_TOKEN, SCHEDULER, null);
        unwrapTask = new UnwrapTask<>(task, SCHEDULER);
        validate(unwrapTask, null, task, null, SCHEDULER, null);

        unwrapTask = new UnwrapTask<>(task, CANCELLATION_TOKEN, OPTIONS);
        validate(unwrapTask, null, task, CANCELLATION_TOKEN, null, OPTIONS);
        unwrapTask = new UnwrapTask<>(task, OPTIONS);
        validate(unwrapTask, null, task, null, null, OPTIONS);

        unwrapTask = new UnwrapTask<>(task, CANCELLATION_TOKEN);
        validate(unwrapTask, null, task, CANCELLATION_TOKEN, null, null);
        unwrapTask = new UnwrapTask<>(task);
        validate(unwrapTask, null, task, null, null, null);
    }

    private void validate(UnwrapTask<String> unwrapTask, String id, Task<Task<String>> task, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(unwrapTask.getId());
        else
            assertSame(id, unwrapTask.getId());

        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);

        if(cancellationToken == null)
            assertNotNull(unwrapTask.getCancellationToken());
        else
            assertSame(cancellationToken, unwrapTask.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, unwrapTask.getScheduler());
        else
            assertSame(scheduler, unwrapTask.getScheduler());

        if(options == null)
            Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), unwrapTask.getOptions());
        else
            Assert.assertEquals(Arrays.asList(options), unwrapTask.getOptions());

        assertSame(task.getJob(), unwrapTask.getTask().getJob());
    }

}
