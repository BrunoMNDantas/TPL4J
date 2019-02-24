package com.dantas.tpl4j.task.when.whenAll;

import com.dantas.tpl4j.task.Task;
import com.dantas.tpl4j.task.core.TaskOption;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAllTaskTest {

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
        Task<String> t1 = new Task<>(() -> "");
        Task<String> t2 = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(t1, t2);
        WhenAllTask<String> task;

        task = new WhenAllTask<>(tasks, SCHEDULER, OPTIONS);
        assertTrue(task.getJob() instanceof WhenAllJob);
        assertTrue(((WhenAllJob<String>)task.getJob()).getJobs().containsAll(Arrays.asList(t1.getJob(), t2.getJob())));
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = new WhenAllTask<>(tasks, OPTIONS);
        assertTrue(task.getJob() instanceof WhenAllJob);
        assertTrue(((WhenAllJob<String>)task.getJob()).getJobs().containsAll(Arrays.asList(t1.getJob(), t2.getJob())));
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = new WhenAllTask<>(tasks, SCHEDULER);
        assertTrue(task.getJob() instanceof WhenAllJob);
        assertTrue(((WhenAllJob<String>)task.getJob()).getJobs().containsAll(Arrays.asList(t1.getJob(), t2.getJob())));
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = new WhenAllTask<>(tasks);
        assertTrue(task.getJob() instanceof WhenAllJob);
        assertTrue(((WhenAllJob<String>)task.getJob()).getJobs().containsAll(Arrays.asList(t1.getJob(), t2.getJob())));
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
    }

}
