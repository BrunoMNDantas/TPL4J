package com.dantas.tpl4j.task;

import com.dantas.tpl4j.task.action.link.*;
import com.dantas.tpl4j.task.action.retry.RetryAction;
import com.dantas.tpl4j.task.core.TaskOption;
import com.dantas.tpl4j.task.core.action.*;
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

        assertNotNull(taskA.getId());
        assertFalse(taskA.getId().isEmpty());

        assertNotNull(taskB.getId());
        assertFalse(taskB.getId().isEmpty());

        assertNotEquals(taskA.getId(), taskB.getId());
    }

    @Test
    public void constructorsTest() {
        Task<?> task;

        task = new Task<>(ACTION);
        assertSame(ACTION, task.getAction());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(ACTION, SCHEDULER);
        assertSame(ACTION, task.getAction());
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(ACTION, new TaskOption[0]);
        assertSame(ACTION, task.getAction());
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(ACTION, SCHEDULER, new TaskOption[0]);
        assertSame(ACTION, task.getAction());
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(EMPTY_ACTION);
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(EMPTY_ACTION, SCHEDULER);
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(EMPTY_ACTION, new TaskOption[0]);
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(EMPTY_ACTION, SCHEDULER, new TaskOption[0]);
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(VOID_ACTION);
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(VOID_ACTION, SCHEDULER);
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(VOID_ACTION, new TaskOption[0]);
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(VOID_ACTION, SCHEDULER, new TaskOption[0]);
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(EMPTY_VOID_ACTION);
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(EMPTY_VOID_ACTION, SCHEDULER);
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
        assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));

        task = new Task<>(EMPTY_VOID_ACTION, new TaskOption[0]);
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        assertTrue(task.getOptions().isEmpty());

        task = new Task<>(EMPTY_VOID_ACTION, SCHEDULER, new TaskOption[0]);
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
        Consumer<Runnable> scheduler = (action) -> new Thread(action).start();
        TaskOption[] options = new TaskOption[0];
        Task<String> task = new Task<>(ACTION, scheduler, options);
        Task<?> thenTask;

        thenTask = task.then(LINK_ACTION, SCHEDULER, OPTIONS);
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_ACTION, SCHEDULER, OPTIONS);
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_VOID_ACTION, SCHEDULER, OPTIONS);
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_ACTION, OPTIONS);
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_ACTION, OPTIONS);
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_VOID_ACTION, OPTIONS);
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_VOID_ACTION, OPTIONS);
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), thenTask.getOptions());

        thenTask = task.then(LINK_ACTION, SCHEDULER);
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_ACTION, SCHEDULER);
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_VOID_ACTION, SCHEDULER);
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_VOID_ACTION, SCHEDULER);
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(SCHEDULER, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_ACTION);
        assertTrue(thenTask.getAction() instanceof LinkAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_ACTION);
        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_VOID_ACTION);
        assertTrue(thenTask.getAction() instanceof LinkVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());

        thenTask = task.then(LINK_EMPTY_VOID_ACTION);
        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);
        assertSame(scheduler, thenTask.getScheduler());
        assertEquals(Arrays.asList(options), thenTask.getOptions());
    }

    @Test
    public void retryTest() {
        Consumer<Runnable> scheduler = (action) -> new Thread(action).start();
        TaskOption[] options = new TaskOption[0];
        Task<String> task = new Task<>(ACTION, scheduler, options);
        Task<?> retryTask;

        retryTask = task.retry(() -> true, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(3, SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(SCHEDULER, OPTIONS);
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(() -> true, OPTIONS);
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(3, OPTIONS);
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(OPTIONS);
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(OPTIONS), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(() -> true, SCHEDULER);
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(3, SCHEDULER);
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(SCHEDULER);
        assertSame(SCHEDULER, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(() -> true);
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry(3);
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);

        retryTask = task.retry();
        assertSame(scheduler, retryTask.getScheduler());
        assertEquals(Arrays.asList(options), retryTask.getOptions());
        assertTrue(retryTask.getAction() instanceof RetryAction);
    }

}
