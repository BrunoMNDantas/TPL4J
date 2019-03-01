package com.github.brunomndantas.tpl4j.task.unwrap;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.junit.Assert.*;

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
        String id = "";
        Task<Task<String>> task = new Task<>(() -> null);
        UnwrapTask<String> unwrapTask;

        unwrapTask = new UnwrapTask<>(id, task, SCHEDULER, OPTIONS);
        assertSame(id, unwrapTask.getId());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(task, SCHEDULER, OPTIONS);
        assertNotNull(unwrapTask.getId());
        assertFalse(unwrapTask.getId().isEmpty());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(id, task, OPTIONS);
        assertSame(id, unwrapTask.getId());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(Task.DEFAULT_SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(task, OPTIONS);
        assertNotNull(unwrapTask.getId());
        assertFalse(unwrapTask.getId().isEmpty());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(Task.DEFAULT_SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(id, task, SCHEDULER);
        assertSame(id, unwrapTask.getId());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(task, SCHEDULER);
        assertNotNull(unwrapTask.getId());
        assertFalse(unwrapTask.getId().isEmpty());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(id, task);
        assertSame(id, unwrapTask.getId());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(Task.DEFAULT_SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());

        unwrapTask = new UnwrapTask<>(task);
        assertNotNull(unwrapTask.getId());
        assertFalse(unwrapTask.getId().isEmpty());
        assertTrue(unwrapTask.getJob() instanceof UnwrapJob);
        assertSame(Task.DEFAULT_SCHEDULER, unwrapTask.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), unwrapTask.getOptions());
        assertSame(task.getJob(), unwrapTask.getTask().getJob());
    }

}
