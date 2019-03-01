package com.github.brunomndantas.tpl4j.pool;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.action.IAction;
import com.github.brunomndantas.tpl4j.task.core.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.task.core.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.core.action.IVoidAction;
import com.github.brunomndantas.tpl4j.task.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.when.whenAny.WhenAnyTask;
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
        String id = "";
        Task<?> task;

        task = TaskPool.createTask(id, ACTION);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(id, ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(id, EMPTY_ACTION);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(EMPTY_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(id, EMPTY_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(EMPTY_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(id, VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(id, VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(id, EMPTY_VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(EMPTY_VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = TaskPool.createTask(id, EMPTY_VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = TaskPool.createTask(EMPTY_VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
    }

    @Test
    public void staticCreateAndStartTest() {
        String  id = "";
        Task<?> task;

        task = TaskPool.createAndStartTask(id, ACTION);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(id, ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(id, EMPTY_ACTION);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(EMPTY_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(id, EMPTY_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(EMPTY_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(id, VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(id, VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }

    @Test
    public void staticWhenAllTest() {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<String> task = TaskPool.whenAllTask(id, tasks);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskPool.whenAllTask(tasks);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskPool.whenAllTask(id, tasks, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskPool.whenAllTask(tasks, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void staticWhenAnyTest() {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<String> task = TaskPool.whenAnyTask(id, tasks);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskPool.whenAnyTask(tasks);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskPool.whenAnyTask(id, tasks, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = TaskPool.whenAnyTask(tasks, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void staticUnwrapTest() {
        String id = "";
        Task<Task<String>> unwrapTask = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<String> task = TaskPool.unwrapTask(id, unwrapTask);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        task = TaskPool.unwrapTask(unwrapTask);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        task = TaskPool.unwrapTask(id, unwrapTask, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(TaskPool.getTaskScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        task = TaskPool.unwrapTask(unwrapTask, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
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
        String id = "";
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.create(id, ACTION);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(id, ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(id, EMPTY_ACTION);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(EMPTY_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(id, EMPTY_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(EMPTY_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(id, VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(id, VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(id, EMPTY_VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(EMPTY_VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());

        task = pool.create(id, EMPTY_VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        task = pool.create(EMPTY_VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());

        pool.close();
    }

    @Test
    public void createAndStartTest() {
        String  id = "";
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.createAndStart(id, ACTION);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(id, ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(id, EMPTY_ACTION);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(EMPTY_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(id, EMPTY_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(EMPTY_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(id, VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(id, VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(id, EMPTY_VOID_ACTION);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(EMPTY_VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(id, EMPTY_VOID_ACTION, OPTIONS);
        assertSame(id, task.getId());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        task = pool.createAndStart(EMPTY_VOID_ACTION, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertTrue(task.getStatus().scheduledEvent.hasFired());

        pool.close();
    }

    @Test
    public void whenAllTest() {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<String> task = pool.whenAll(id, tasks);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = pool.whenAll(tasks);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = pool.whenAll(id, tasks, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = pool.whenAll(tasks, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        pool.close();
    }

    @Test
    public void whenAnyTest() {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<String> task = pool.whenAny(id, tasks);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = pool.whenAny(tasks);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = pool.whenAny(id, tasks, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        task = pool.whenAny(tasks, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(tasks, task.getTasks());

        pool.close();
    }

    @Test
    public void unwrapTest() {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<Task<String>> unwrapTask = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<String> task = pool.unwrap(id, unwrapTask);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        task = pool.unwrap(unwrapTask);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(Task.DEFAULT_OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        task = pool.unwrap(id, unwrapTask, OPTIONS);
        assertSame(id, task.getId());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        task = pool.unwrap(unwrapTask, OPTIONS);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(pool.getScheduler(), task.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), task.getOptions());
        assertSame(unwrapTask, task.getTask());

        pool.close();
    }

}
