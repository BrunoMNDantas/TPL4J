package com.github.brunomndantas.tpl4j.helpers.when.whenAny;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAnyTaskTest {

    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = (action) -> new Thread(action).start();
    private static final Option[] OPTIONS = { Option.ACCEPT_CHILDREN };



    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAnyTask<String> task = new WhenAnyTask<>(tasks);

        assertSame(tasks, task.getTasks());
    }

    @Test
    public void constructorsTest() {
        String id = "";
        Task<String> t1 = new Task<>(() -> "");
        Task<String> t2 = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(t1, t2);
        WhenAnyTask<String> task;



        task = new WhenAnyTask<>(id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validate(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new WhenAnyTask<>(id, tasks, SCHEDULER, OPTIONS);
        validate(task, id, tasks, null, SCHEDULER, OPTIONS);

        task = new WhenAnyTask<>(id, tasks, CANCELLATION_TOKEN, SCHEDULER);
        validate(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new WhenAnyTask<>(id, tasks, SCHEDULER);
        validate(task, id, tasks, null, SCHEDULER, null);

        task = new WhenAnyTask<>(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        validate(task, id, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = new WhenAnyTask<>(id, tasks, OPTIONS);
        validate(task, id, tasks, null, null, OPTIONS);

        task = new WhenAnyTask<>(id, tasks, CANCELLATION_TOKEN);
        validate(task, id, tasks, CANCELLATION_TOKEN, null, null);
        task = new WhenAnyTask<>(id, tasks);
        validate(task, id, tasks, null, null, null);

        task = new WhenAnyTask<>(tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validate(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new WhenAnyTask<>(tasks, SCHEDULER, OPTIONS);
        validate(task, null, tasks, null, SCHEDULER, OPTIONS);

        task = new WhenAnyTask<>(tasks, CANCELLATION_TOKEN, SCHEDULER);
        validate(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new WhenAnyTask<>(tasks, SCHEDULER);
        validate(task, null, tasks, null, SCHEDULER, null);

        task = new WhenAnyTask<>(tasks, CANCELLATION_TOKEN, OPTIONS);
        validate(task, null, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = new WhenAnyTask<>(tasks, OPTIONS);
        validate(task, null, tasks, null, null, OPTIONS);

        task = new WhenAnyTask<>(tasks, CANCELLATION_TOKEN);
        validate(task, null, tasks, CANCELLATION_TOKEN, null, null);
        task = new WhenAnyTask<>(tasks);
        validate(task, null, tasks, null, null, null);
    }

    private void validate(WhenAnyTask<String> task, String id, Collection<Task<String>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        assertTrue(task.getJob() instanceof WhenAnyJob);

        assertEquals(tasks, ((WhenAnyJob<String>)task.getJob()).getTasks());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getScheduler());

        if(options == null)
            assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        else
            assertEquals(Arrays.asList(options), task.getOptions());
    }

}
