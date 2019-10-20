package com.github.brunomndantas.tpl4j.factory;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.action.IAction;
import com.github.brunomndantas.tpl4j.task.core.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.task.core.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.core.action.IVoidAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelVoidAction;
import com.github.brunomndantas.tpl4j.task.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.when.whenAny.WhenAnyTask;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TaskFactoryTest {

    private static final IAction<String> ACTION = (token) -> null;
    private static final IEmptyAction<String> EMPTY_ACTION = () -> null;
    private static final IVoidAction VOID_ACTION = (token) -> {};
    private static final IEmptyVoidAction EMPTY_VOID_ACTION = () -> {};
    private static final TaskOption[] OPTIONS = {};
    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();



    @Test
    public void createTest() {
        String id = "";
        Task<?> task;

        task = TaskFactory.create(id, ACTION, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(id, ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(id, ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(id, ACTION);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(id, EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(id, EMPTY_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(id, EMPTY_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(id, EMPTY_ACTION);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(id, VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(id, VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(id, VOID_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(id, VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(id, EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(id, EMPTY_VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(id, EMPTY_VOID_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(id, EMPTY_VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(ACTION, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(VOID_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(VOID_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_VOID_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
    }

    @Test
    public void createAndStartTest() {
        String id = "";
        Task<?> task;

        task = TaskFactory.createAndStart(id, ACTION, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, ACTION);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, EMPTY_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, EMPTY_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, EMPTY_ACTION);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, VOID_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(ACTION, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(VOID_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(VOID_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }

    @Test
    public void whenAllTest() {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<?> task;

        task = TaskFactory.whenAll(id, tasks, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(tasks, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(id, tasks, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(tasks, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(id, tasks, SCHEDULER);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(tasks, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(id, tasks);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(tasks);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void whenAnyTest() {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<?> task;

        task = TaskFactory.whenAny(id, tasks, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(tasks, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(id, tasks, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(tasks, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(id, tasks, SCHEDULER);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(tasks, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(id, tasks);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(tasks);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        Assert.assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void unwrapTest() {
        String id = "";
        Task<Task<String>> taskToUnwrap = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<?> task;

        task = TaskFactory.unwrap(id, taskToUnwrap, SCHEDULER, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(taskToUnwrap, SCHEDULER, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(id, taskToUnwrap, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(taskToUnwrap, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(id, taskToUnwrap, SCHEDULER);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(taskToUnwrap, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(id, taskToUnwrap);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(taskToUnwrap);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());
    }
    
    @Test
    public void forEachTest() {
        String id = "";
        IParallelAction<String,String> action = (e, t) -> "";
        IParallelVoidAction<String> voidAction = (e, t) -> { };
        IParallelUninterruptibleAction<String,String> uninterruptibleAction = (e) -> "";
        IParallelUninterruptibleVoidAction<String> uninterruptibleVoidAction = (e) -> { };
        Iterable<String> elements = Arrays.asList("","");
        Task<Collection<String>> task;

        task = TaskFactory.forEach(id, elements, action, SCHEDULER, OPTIONS);
        assertSame(id, task.getJob().getTaskId());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, voidAction, SCHEDULER, OPTIONS);
        assertSame(id, task.getJob().getTaskId());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, uninterruptibleAction, SCHEDULER, OPTIONS);
        assertSame(id, task.getJob().getTaskId());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, SCHEDULER, OPTIONS);
        assertSame(id, task.getJob().getTaskId());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());


        task = TaskFactory.forEach(id, elements, action, SCHEDULER);
        assertSame(id, task.getJob().getTaskId());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, voidAction, SCHEDULER);
        assertSame(id, task.getJob().getTaskId());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, uninterruptibleAction, SCHEDULER);
        assertSame(id, task.getJob().getTaskId());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, SCHEDULER);
        assertSame(id, task.getJob().getTaskId());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());


        task = TaskFactory.forEach(id, elements, action, OPTIONS);
        assertSame(id, task.getJob().getTaskId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, voidAction, OPTIONS);
        assertSame(id, task.getJob().getTaskId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, uninterruptibleAction, OPTIONS);
        assertSame(id, task.getJob().getTaskId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, OPTIONS);
        assertSame(id, task.getJob().getTaskId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());


        task = TaskFactory.forEach(id, elements, action);
        assertSame(id, task.getJob().getTaskId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, voidAction);
        assertSame(id, task.getJob().getTaskId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, uninterruptibleAction);
        assertSame(id, task.getJob().getTaskId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction);
        assertSame(id, task.getJob().getTaskId());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());


        task = TaskFactory.forEach(elements, action, SCHEDULER, OPTIONS);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, voidAction, SCHEDULER, OPTIONS);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, uninterruptibleAction, SCHEDULER, OPTIONS);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, SCHEDULER, OPTIONS);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());


        task = TaskFactory.forEach(elements, action, SCHEDULER);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, voidAction, SCHEDULER);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, uninterruptibleAction, SCHEDULER);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, SCHEDULER);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());


        task = TaskFactory.forEach(elements, action, OPTIONS);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, voidAction, OPTIONS);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, uninterruptibleAction, OPTIONS);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, OPTIONS);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(OPTIONS.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());


        task = TaskFactory.forEach(elements, action);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, voidAction);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, uninterruptibleAction);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.forEach(elements, uninterruptibleVoidAction);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }
    
}
