package com.github.brunomndantas.tpl4j.task.unwrap;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class UnwrapTaskTest {

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
        Task<Task<String>> task = new Task<>(() -> null);
        UnwrapTask<String> unwrapTask;

        unwrapTask = new UnwrapTask<>(task, SCHEDULER, OPTIONS);
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(task, OPTIONS);
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(Task.DEFAULT_SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(task, SCHEDULER);
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(task);
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(Task.DEFAULT_SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());
    }

}
