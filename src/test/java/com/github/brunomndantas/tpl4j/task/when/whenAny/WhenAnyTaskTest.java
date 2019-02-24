package com.github.brunomndantas.tpl4j.task.when.whenAny;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class WhenAnyTaskTest {

    private static final Consumer<Runnable> SCHEDULER = (action) -> new Thread(action).start();
    private static final TaskOption[] OPTIONS = { TaskOption.ACCEPT_CHILDREN };



    @Test
    public void getTasksTest() {
        Collection<Task<String>> tasks = new LinkedList<>();

        WhenAnyTask<String> task = new WhenAnyTask<>(tasks);

        assertSame(tasks, task.getTasks());
    }

    @Test
    public void constructorsTest() {
        Task<String> t1 = new Task<>(() -> "");
        Task<String> t2 = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(t1, t2);
        WhenAnyTask<String> task;

        task = new WhenAnyTask<>(tasks, SCHEDULER, OPTIONS);
        assertTrue(task.getJob() instanceof WhenAnyJob);
        assertEquals(tasks, ((WhenAnyJob<String>)task.getJob()).getTasks());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = new WhenAnyTask<>(tasks, OPTIONS);
        assertTrue(task.getJob() instanceof WhenAnyJob);
        assertEquals(tasks, ((WhenAnyJob<String>)task.getJob()).getTasks());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = new WhenAnyTask<>(tasks, SCHEDULER);
        assertTrue(task.getJob() instanceof WhenAnyJob);
        assertEquals(tasks, ((WhenAnyJob<String>)task.getJob()).getTasks());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = new WhenAnyTask<>(tasks);
        assertTrue(task.getJob() instanceof WhenAnyJob);
        assertEquals(tasks, ((WhenAnyJob<String>)task.getJob()).getTasks());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
    }

}
