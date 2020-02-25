package com.github.brunomndantas.tpl4j.helpers.unwrap;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class UnwrapTaskTest {

    @Test
    public void getTaskTest() {
        String id = UUID.randomUUID().toString();
        Task<Task<String>> task = new Task<>(() -> null);
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> new Thread(r).start();
        Option[] options = new Option[0];

        UnwrapTask<String> unwrapTask = new UnwrapTask<>(id, task, cancellationToken, scheduler, options);

        assertSame(task, unwrapTask.getTask());
    }

    @Test
    public void constructorsTest() {
        String id = UUID.randomUUID().toString();
        Task<Task<String>> task = new Task<>(() -> null);
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> new Thread(r).start();
        Option[] options = new Option[0];

        UnwrapTask<String> unwrapTask = new UnwrapTask<>(id, task, cancellationToken, scheduler, options);

        assertSame(id, unwrapTask.getId());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(task.getJob(), unwrapTask.getTask().getJob());
        assertSame(cancellationToken, unwrapTask.getCancellationToken());
        assertSame(scheduler, unwrapTask.getScheduler());
        assertEquals(Arrays.asList(options), unwrapTask.getOptions());
        assertSame(task, unwrapTask.getTask());
    }

}
