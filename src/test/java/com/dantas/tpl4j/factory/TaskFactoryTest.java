package com.dantas.tpl4j.factory;

import com.dantas.tpl4j.task.Task;
import com.dantas.tpl4j.task.core.TaskOption;
import com.dantas.tpl4j.task.core.action.IAction;
import com.dantas.tpl4j.task.core.action.IEmptyAction;
import com.dantas.tpl4j.task.core.action.IEmptyVoidAction;
import com.dantas.tpl4j.task.core.action.IVoidAction;
import com.dantas.tpl4j.task.unwrap.UnwrapTask;
import com.dantas.tpl4j.task.when.whenAll.WhenAllTask;
import com.dantas.tpl4j.task.when.whenAny.WhenAnyTask;
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
        Task<?> task;

        task = TaskFactory.create(ACTION, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(ACTION, OPTIONS);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(ACTION, SCHEDULER);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(ACTION);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_ACTION, OPTIONS);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_ACTION, SCHEDULER);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_ACTION);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(VOID_ACTION, OPTIONS);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(VOID_ACTION, SCHEDULER);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(VOID_ACTION);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_VOID_ACTION, OPTIONS);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_VOID_ACTION, SCHEDULER);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskFactory.create(EMPTY_VOID_ACTION);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
    }

    @Test
    public void createAndStartTest() {
        Task<?> task;

        task = TaskFactory.createAndStart(ACTION, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(ACTION, OPTIONS);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(ACTION, SCHEDULER);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(ACTION);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_ACTION, OPTIONS);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_ACTION, SCHEDULER);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_ACTION);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(VOID_ACTION, OPTIONS);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(VOID_ACTION, SCHEDULER);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(VOID_ACTION);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, OPTIONS);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, SCHEDULER);
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }

    @Test
    public void whenAllTest() {
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<?> task;

        task = TaskFactory.whenAll(tasks, SCHEDULER, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(tasks, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(tasks, SCHEDULER);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAll(tasks);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void whenAnyTest() {
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<?> task;

        task = TaskFactory.whenAny(tasks, SCHEDULER, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(tasks, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(tasks, SCHEDULER);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskFactory.whenAny(tasks);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void unwrapTest() {
        Task<Task<String>> taskToUnwrap = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<?> task;

        task = TaskFactory.unwrap(taskToUnwrap, SCHEDULER, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(taskToUnwrap, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(taskToUnwrap, SCHEDULER);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());

        task = TaskFactory.unwrap(taskToUnwrap);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(taskToUnwrap, task.getTask());
    }
    
}
