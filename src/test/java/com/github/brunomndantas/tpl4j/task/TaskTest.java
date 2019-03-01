package com.github.brunomndantas.tpl4j.task;

import com.github.brunomndantas.tpl4j.task.action.link.*;
import com.github.brunomndantas.tpl4j.task.action.retry.RetryAction;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.action.*;
import org.junit.Test;

import java.util.Arrays;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TaskTest {

    private static final IAction<String> ACTION = (token) -> null;
    private static final IEmptyAction<String> EMPTY_ACTION = () -> null;
    private static final IVoidAction VOID_ACTION = (token) -> {};
    private static final IEmptyVoidAction EMPTY_VOID_ACTION = () -> {};
    private static final ILinkAction<String, String> LINK_ACTION = (task, token) -> null;
    private static final ILinkEmptyAction<String> LINK_EMPTY_ACTION = () -> null;
    private static final ILinkVoidAction<String> LINK_VOID_ACTION = (task, token) -> {};
    private static final ILinkEmptyVoidAction LINK_EMPTY_VOID_ACTION = () -> {};
    private static final TaskOption[] OPTIONS = {};
    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();

    

    @Test
    public void getTokenIdTest() {
        Task<?> taskA = new Task<>(ACTION);
        Task<?> taskB = new Task<>(ACTION);
        String id = "";
        Task<?> taskC = new Task<>(id, ACTION);

        assertNotNull(taskA.getId());
        assertFalse(taskA.getId().isEmpty());

        assertNotNull(taskB.getId());
        assertFalse(taskB.getId().isEmpty());

        assertNotEquals(taskA.getId(), taskB.getId());

        assertSame(id, taskC.getId());
    }

    @Test
    public void constructorsTest() {
        String id = "";
        Task<?> task;

        task = new Task<>(id, ACTION);
        assertSame(id, task.getId());
        assertSame(ACTION, task.getAction());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(ACTION, task.getAction());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(id, ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertSame(ACTION, task.getAction());
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(ACTION, task.getAction());
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(id, ACTION, new TaskOption[0]);
        assertSame(id, task.getId());
        assertSame(ACTION, task.getAction());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(ACTION, new TaskOption[0]);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(ACTION, task.getAction());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(id, ACTION, SCHEDULER, new TaskOption[0]);
        assertSame(id, task.getId());
        assertSame(ACTION, task.getAction());
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(ACTION, SCHEDULER, new TaskOption[0]);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertSame(ACTION, task.getAction());
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(id, EMPTY_ACTION);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(EMPTY_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(id, EMPTY_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(EMPTY_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(id, EMPTY_ACTION, new TaskOption[0]);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(EMPTY_ACTION, new TaskOption[0]);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(id, EMPTY_ACTION, SCHEDULER, new TaskOption[0]);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(EMPTY_ACTION, SCHEDULER, new TaskOption[0]);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(id, VOID_ACTION);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(id, VOID_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(VOID_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(id, VOID_ACTION, new TaskOption[0]);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(VOID_ACTION, new TaskOption[0]);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(id, VOID_ACTION, SCHEDULER, new TaskOption[0]);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(VOID_ACTION, SCHEDULER, new TaskOption[0]);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(id, EMPTY_VOID_ACTION);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(EMPTY_VOID_ACTION);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(id, EMPTY_VOID_ACTION, SCHEDULER);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(EMPTY_VOID_ACTION, SCHEDULER);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(id, EMPTY_VOID_ACTION, new TaskOption[0]);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(EMPTY_VOID_ACTION, new TaskOption[0]);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(id, EMPTY_VOID_ACTION, SCHEDULER, new TaskOption[0]);
        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(EMPTY_VOID_ACTION, SCHEDULER, new TaskOption[0]);
        assertNotNull(task.getId());
        assertFalse(task.getId().isEmpty());
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());
    }

    @Test
    public void thenTaskTest() throws InterruptedException {
        Task<?> task = new Task<>(ACTION);
        Task<Boolean> thenTask = new Task<>(() -> task.getStatus().finishedEvent.hasFired());

        assertSame(thenTask, task.then(thenTask));

        task.start();

        thenTask.getStatus().succeededEvent.await();
        assertTrue(thenTask.getValue());
    }

    @Test
    public void thenTest() {
        String id = "";
        Consumer<Runnable> scheduler = (action) -> new Thread(action).start();
        TaskOption[] options = new TaskOption[0];
        Task<String> task = new Task<>(ACTION, scheduler, options);
        Task<?> thenTask;

        thenTask = task.then(id, LINK_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(id, LINK_EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(id, LINK_VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_VOID_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(id, LINK_ACTION, OPTIONS);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_ACTION, OPTIONS);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(id, LINK_EMPTY_ACTION, OPTIONS);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_ACTION, OPTIONS);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(id, LINK_VOID_ACTION, OPTIONS);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_VOID_ACTION, OPTIONS);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, OPTIONS);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_VOID_ACTION, OPTIONS);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(id, LINK_ACTION, SCHEDULER);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_ACTION, SCHEDULER);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(id, LINK_EMPTY_ACTION, SCHEDULER);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_ACTION, SCHEDULER);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(id, LINK_VOID_ACTION, SCHEDULER);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_VOID_ACTION, SCHEDULER);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, SCHEDULER);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_VOID_ACTION, SCHEDULER);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(id, LINK_ACTION);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_ACTION);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(id, LINK_EMPTY_ACTION);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_ACTION);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(id, LINK_VOID_ACTION);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_VOID_ACTION);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION);
        assertSame(id, thenTask.getId());
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_VOID_ACTION);
        assertNotNull(thenTask.getId());
        assertFalse(thenTask.getId().isEmpty());
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());
    }

    @Test
    public void retryTest() {
        String id = "";
        Consumer<Runnable> scheduler = (action) -> new Thread(action).start();
        TaskOption[] options = new TaskOption[0];
        Task<String> task = new Task<>(ACTION, scheduler, options);
        Task<?> retryTask;

        retryTask = task.retry(id, () -> true, SCHEDULER, OPTIONS);
        assertSame(id, retryTask.getId());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(() -> true, SCHEDULER, OPTIONS);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, 3, SCHEDULER, OPTIONS);
        assertSame(id, retryTask.getId());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(3, SCHEDULER, OPTIONS);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, SCHEDULER, OPTIONS);
        assertSame(id, retryTask.getId());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(SCHEDULER, OPTIONS);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, () -> true, OPTIONS);
        assertSame(id, retryTask.getId());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(() -> true, OPTIONS);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, 3, OPTIONS);
        assertSame(id, retryTask.getId());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(3, OPTIONS);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, OPTIONS);
        assertSame(id, retryTask.getId());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(OPTIONS);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, () -> true, SCHEDULER);
        assertSame(id, retryTask.getId());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(() -> true, SCHEDULER);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, 3, SCHEDULER);
        assertSame(id, retryTask.getId());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(3, SCHEDULER);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, SCHEDULER);
        assertSame(id, retryTask.getId());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(SCHEDULER);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, () -> true);
        assertSame(id, retryTask.getId());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(() -> true);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id, 3);
        assertSame(id, retryTask.getId());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(3);
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(id);
        assertSame(id, retryTask.getId());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry();
        assertNotNull(retryTask.getId());
        assertFalse(retryTask.getId().isEmpty());
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);
    }

}
