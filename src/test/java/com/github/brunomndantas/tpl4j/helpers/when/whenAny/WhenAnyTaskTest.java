package com.github.brunomndantas.tpl4j.helpers.when.whenAny;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAnyTaskTest {

    @Test
    public void getTasksTest() {
        String id = UUID.randomUUID().toString();
        Task<String> t1 = new Task<>(() -> "");
        Task<String> t2 = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(t1, t2);
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> new Thread(r).start();
        Option[] options = new Option[0];

        WhenAnyTask<String> task = new WhenAnyTask<>(id, tasks, cancellationToken, scheduler, options);

        assertSame(tasks, task.getTasks());
    }

    @Test
    public void constructorsTest() {
        String id = UUID.randomUUID().toString();
        Task<String> t1 = new Task<>(() -> "");
        Task<String> t2 = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(t1, t2);
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> new Thread(r).start();
        Option[] options = new Option[0];

        WhenAnyTask<String> task = new WhenAnyTask<>(id, tasks, cancellationToken, scheduler, options);

        assertSame(id, task.getId());
        assertTrue(task.getJob() instanceof WhenAnyJob);
        assertEquals(tasks, ((WhenAnyJob<String>)task.getJob()).getTasks());
        assertSame(cancellationToken, task.getCancellationToken());
        assertSame(scheduler, task.getScheduler());
        assertEquals(Arrays.asList(options), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

}
