package com.dantas.tpl4j.pool;

import com.dantas.tpl4j.task.Task;
import com.dantas.tpl4j.task.core.TaskOption;
import com.dantas.tpl4j.task.core.action.IAction;
import com.dantas.tpl4j.task.core.action.IEmptyAction;
import com.dantas.tpl4j.task.core.action.IEmptyVoidAction;
import com.dantas.tpl4j.task.core.action.IVoidAction;
import com.dantas.tpl4j.task.unwrap.UnwrapTask;
import com.dantas.tpl4j.task.when.whenAll.WhenAllTask;
import com.dantas.tpl4j.task.when.whenAny.WhenAnyTask;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class TaskPoolTest {

    private static final IAction<String> ACTION = (token) -> null;
    private static final IEmptyAction<String> EMPTY_ACTION = () -> null;
    private static final IVoidAction VOID_ACTION = (token) -> {};
    private static final IEmptyVoidAction EMPTY_VOID_ACTION = () -> {};
    private static final TaskOption[] OPTIONS = {};



    @AfterClass
    public static void close() {
        TaskPool.dispose();
    }



    @Test
    public void staticGetSchedulerTest() {
        assertNotNull(TaskPool.getTaskScheduler());
    }

    @Test
    public void staticCreateTest() {
        Task<?> task;

        task = TaskPool.createTask(ACTION);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(ACTION, OPTIONS);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(EMPTY_ACTION);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(EMPTY_ACTION, OPTIONS);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(VOID_ACTION);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(VOID_ACTION, OPTIONS);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(EMPTY_VOID_ACTION);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(EMPTY_VOID_ACTION, OPTIONS);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
    }

    @Test
    public void staticCreateAndStartTest() {
        Task<?> task;

        task = TaskPool.createAndStartTask(ACTION);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(ACTION, OPTIONS);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(EMPTY_ACTION);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(EMPTY_ACTION, OPTIONS);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(VOID_ACTION);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(VOID_ACTION, OPTIONS);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION, OPTIONS);
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }

    @Test
    public void staticWhenAllTest() {
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<String> task = TaskPool.whenAllTask(tasks);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskPool.whenAllTask(tasks, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void staticWhenAnyTest() {
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<String> task = TaskPool.whenAnyTask(tasks);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskPool.whenAnyTask(tasks, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void staticUnwrapTest() {
        Task<Task<String>> unwrapTask = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<String> task = TaskPool.unwrapTask(unwrapTask);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        task = TaskPool.unwrapTask(unwrapTask, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());
    }


    @Test
    public void getSchedulerTest() {
        TaskPool pool = new TaskPool();

        assertNotNull(pool.getTaskScheduler());

        pool.close();
    }

    @Test
    public void createTest() {
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.create(ACTION);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(ACTION, OPTIONS);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(EMPTY_ACTION);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(EMPTY_ACTION, OPTIONS);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(VOID_ACTION);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(VOID_ACTION, OPTIONS);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(EMPTY_VOID_ACTION);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(EMPTY_VOID_ACTION, OPTIONS);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        pool.close();
    }

    @Test
    public void createAndStartTest() {
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.createAndStart(ACTION);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(ACTION, OPTIONS);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(EMPTY_ACTION);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(EMPTY_ACTION, OPTIONS);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(VOID_ACTION);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(VOID_ACTION, OPTIONS);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(EMPTY_VOID_ACTION);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(EMPTY_VOID_ACTION, OPTIONS);
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        pool.close();
    }

    @Test
    public void whenAllTest() {
        TaskPool pool = new TaskPool();
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<String> task = pool.whenAll(tasks);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = pool.whenAll(tasks, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        pool.close();
    }

    @Test
    public void whenAnyTest() {
        TaskPool pool = new TaskPool();
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<String> task = pool.whenAny(tasks);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = pool.whenAny(tasks, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        pool.close();
    }

    @Test
    public void unwrapTest() {
        TaskPool pool = new TaskPool();
        Task<Task<String>> unwrapTask = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<String> task = pool.unwrap(unwrapTask);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        task = pool.unwrap(unwrapTask, OPTIONS);
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        pool.close();
    }

}
