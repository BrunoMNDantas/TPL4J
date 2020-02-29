package com.github.brunomndantas.tpl4j.task.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

public class WhenAllTaskTest {

    @Test
    public void getTasksTest() {
        String id = UUID.randomUUID().toString();
        Task<String> t1 = new Task<>(() -> "");
        Task<String> t2 = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(t1, t2);
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        WhenAllTask<String> task = new WhenAllTask<>(id, tasks, cancellationToken, scheduler, options);

        assertSame(tasks, task.getTasks());
    }

    @Test
    public void constructorsTest() {
        String id = UUID.randomUUID().toString();
        Task<String> t1 = new Task<>(() -> "");
        Task<String> t2 = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(t1, t2);
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        WhenAllTask<String> task = new WhenAllTask<>(id, tasks, cancellationToken, scheduler, options);

        assertSame(id, task.getId());
        assertSame(tasks, task.getTasks());
        assertTrue(task.getContextExecutor() instanceof WhenAllContextExecutor);
        assertSame(tasks, ((WhenAllContextExecutor<?>)task.getContextExecutor()).getTasks());
        assertSame(cancellationToken, task.getContext().getCancellationToken());
        assertSame(scheduler, task.getContext().getScheduler());
        assertEquals(Arrays.asList(options), task.getContext().getOptions().getOptions());
        assertSame(tasks, task.getTasks());
    }

}
