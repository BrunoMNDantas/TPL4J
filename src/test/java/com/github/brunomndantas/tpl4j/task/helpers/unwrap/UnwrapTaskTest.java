package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;

public class UnwrapTaskTest {

    @Test
    public void getTaskTest() {
        String id = UUID.randomUUID().toString();
        Task<Task<String>> task = new Task<>(() -> null);
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        UnwrapTask<String> unwrapTask = new UnwrapTask<>(id, task, cancellationToken, scheduler, options);

        assertSame(task, unwrapTask.getTask());
    }

    @Test
    public void constructorsTest() {
        String id = UUID.randomUUID().toString();
        Task<Task<String>> task = new Task<>(() -> null);
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        UnwrapTask<String> unwrapTask = new UnwrapTask<>(id, task, cancellationToken, scheduler, options);

        assertSame(id, unwrapTask.getId());
        assertSame(task, unwrapTask.getTask());
        assertTrue(unwrapTask.getContextExecutor() instanceof UnwrapContextExecutor);
        assertSame(task, ((UnwrapContextExecutor<?>)(unwrapTask.getContextExecutor())).getTask());
        assertSame(cancellationToken, unwrapTask.getCancellationToken());
        assertSame(scheduler, unwrapTask.getScheduler());
        assertEquals(Arrays.asList(options), unwrapTask.getOptions().getOptions());
        assertSame(task, unwrapTask.getTask());
    }

}
