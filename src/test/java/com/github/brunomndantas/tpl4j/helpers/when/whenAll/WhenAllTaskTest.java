package com.github.brunomndantas.tpl4j.helpers.when.whenAll;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.context.TaskOption;
import com.github.brunomndantas.tpl4j.core.context.cancel.CancellationToken;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class WhenAllTaskTest {

    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Consumer<Runnable> SCHEDULER = (action) -> new Thread(action).start();
    private static final TaskOption[] OPTIONS = { TaskOption.ACCEPT_CHILDREN };



    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAllTask<String> task = new WhenAllTask<>(tasks);

        assertSame(tasks, task.getTasks());
    }

    @Test
    public void constructorsTest() {
        String id = "";
        Task<String> t1 = new Task<>(() -> "");
        Task<String> t2 = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(t1, t2);
        WhenAllTask<String> task;

        task = new WhenAllTask<>(id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validate(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new WhenAllTask<>(id, tasks, SCHEDULER, OPTIONS);
        validate(task, id, tasks, null, SCHEDULER, OPTIONS);

        task = new WhenAllTask<>(id, tasks, CANCELLATION_TOKEN, SCHEDULER);
        validate(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new WhenAllTask<>(id, tasks, SCHEDULER);
        validate(task, id, tasks, null, SCHEDULER, null);

        task = new WhenAllTask<>(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        validate(task, id, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = new WhenAllTask<>(id, tasks, OPTIONS);
        validate(task, id, tasks, null, null, OPTIONS);

        task = new WhenAllTask<>(id, tasks, CANCELLATION_TOKEN);
        validate(task, id, tasks, CANCELLATION_TOKEN, null, null);
        task = new WhenAllTask<>(id, tasks);
        validate(task, id, tasks, null, null, null);

        task = new WhenAllTask<>(tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validate(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new WhenAllTask<>(tasks, SCHEDULER, OPTIONS);
        validate(task, null, tasks, null, SCHEDULER, OPTIONS);

        task = new WhenAllTask<>(tasks, CANCELLATION_TOKEN, SCHEDULER);
        validate(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new WhenAllTask<>(tasks, SCHEDULER);
        validate(task, null, tasks, null, SCHEDULER, null);

        task = new WhenAllTask<>(tasks, CANCELLATION_TOKEN, OPTIONS);
        validate(task, null, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = new WhenAllTask<>(tasks, OPTIONS);
        validate(task, null, tasks, null, null, OPTIONS);

        task = new WhenAllTask<>(tasks, CANCELLATION_TOKEN);
        validate(task, null, tasks, CANCELLATION_TOKEN, null, null);
        task = new WhenAllTask<>(tasks);
        validate(task, null, tasks, null, null, null);
    }

    private void validate(WhenAllTask<String> task, String id, Collection<Task<String>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        assertTrue(task.getJob() instanceof WhenAllJob);

        assertTrue(((WhenAllJob<String>)task.getJob()).getJobs().containsAll(tasks.stream().map(Task::getJob).collect(Collectors.toList())));

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getScheduler());

        if(options == null)
            Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        else
            Assert.assertEquals(Arrays.asList(options), task.getOptions());
    }

}
