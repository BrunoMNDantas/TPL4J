package com.github.brunomndantas.tpl4j.task;

import com.github.brunomndantas.tpl4j.core.action.link.*;
import com.github.brunomndantas.tpl4j.core.action.retry.RetryAction;
import com.github.brunomndantas.tpl4j.core.context.TaskOption;
import com.github.brunomndantas.tpl4j.core.action.action.*;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
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
    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
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

        task = new Task<>(id, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateAction(task, id, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateVoidAction(task, id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateEmptyAction(task, id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateEmptyVoidAction(task, id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = new Task<>(id, ACTION, SCHEDULER, OPTIONS);
        validateAction(task, id, ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(id, VOID_ACTION, SCHEDULER, OPTIONS);
        validateVoidAction(task, id, VOID_ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(id, EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateEmptyAction(task, id, EMPTY_ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(id, EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateEmptyVoidAction(task, id, EMPTY_VOID_ACTION, null, SCHEDULER, OPTIONS);

        task = new Task<>(id, ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateAction(task, id, ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateVoidAction(task, id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateEmptyAction(task, id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateEmptyVoidAction(task, id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);

        task = new Task<>(id, ACTION, SCHEDULER);
        validateAction(task, id, ACTION, null, SCHEDULER, null);
        task = new Task<>(id, VOID_ACTION, SCHEDULER);
        validateVoidAction(task, id, VOID_ACTION, null, SCHEDULER, null);
        task = new Task<>(id, EMPTY_ACTION, SCHEDULER);
        validateEmptyAction(task, id, EMPTY_ACTION, null, SCHEDULER, null);
        task = new Task<>(id, EMPTY_VOID_ACTION, SCHEDULER);
        validateEmptyVoidAction(task, id, EMPTY_VOID_ACTION, null, SCHEDULER, null);

        task = new Task<>(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateAction(task, id, ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateVoidAction(task, id, VOID_ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateEmptyAction(task, id, EMPTY_ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateEmptyVoidAction(task, id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, null, OPTIONS);

        task = new Task<>(id, ACTION, OPTIONS);
        validateAction(task, id, ACTION, null, null, OPTIONS);
        task = new Task<>(id, VOID_ACTION, OPTIONS);
        validateVoidAction(task, id, VOID_ACTION, null, null, OPTIONS);
        task = new Task<>(id, EMPTY_ACTION, OPTIONS);
        validateEmptyAction(task, id, EMPTY_ACTION, null, null, OPTIONS);
        task = new Task<>(id, EMPTY_VOID_ACTION, OPTIONS);
        validateEmptyVoidAction(task, id, EMPTY_VOID_ACTION, null, null, OPTIONS);

        task = new Task<>(id, ACTION, CANCELLATION_TOKEN);
        validateAction(task, id, ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, VOID_ACTION, CANCELLATION_TOKEN);
        validateVoidAction(task, id, VOID_ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        validateEmptyAction(task, id, EMPTY_ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateEmptyVoidAction(task, id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, null, null);

        task = new Task<>(id, ACTION);
        validateAction(task, id, ACTION, null, null, null);
        task = new Task<>(id, VOID_ACTION);
        validateVoidAction(task, id, VOID_ACTION, null, null, null);
        task = new Task<>(id, EMPTY_ACTION);
        validateEmptyAction(task, id, EMPTY_ACTION, null, null, null);
        task = new Task<>(id, EMPTY_VOID_ACTION);
        validateEmptyVoidAction(task, id, EMPTY_VOID_ACTION, null, null, null);

        task = new Task<>(ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateAction(task, null, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateVoidAction(task, null, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateEmptyAction(task, null, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateEmptyVoidAction(task, null, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = new Task<>(ACTION, SCHEDULER, OPTIONS);
        validateAction(task, null, ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(VOID_ACTION, SCHEDULER, OPTIONS);
        validateVoidAction(task, null, VOID_ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateEmptyAction(task, null, EMPTY_ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateEmptyVoidAction(task, null, EMPTY_VOID_ACTION, null, SCHEDULER, OPTIONS);

        task = new Task<>(ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateAction(task, null, ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateVoidAction(task, null, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateEmptyAction(task, null, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateEmptyVoidAction(task, null, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);

        task = new Task<>(ACTION, SCHEDULER);
        validateAction(task, null, ACTION, null, SCHEDULER, null);
        task = new Task<>(VOID_ACTION, SCHEDULER);
        validateVoidAction(task, null, VOID_ACTION, null, SCHEDULER, null);
        task = new Task<>(EMPTY_ACTION, SCHEDULER);
        validateEmptyAction(task, null, EMPTY_ACTION, null, SCHEDULER, null);
        task = new Task<>(EMPTY_VOID_ACTION, SCHEDULER);
        validateEmptyVoidAction(task, null, EMPTY_VOID_ACTION, null, SCHEDULER, null);

        task = new Task<>(ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateAction(task, null, ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateVoidAction(task, null, VOID_ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateEmptyAction(task, null, EMPTY_ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateEmptyVoidAction(task, null, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, null, OPTIONS);

        task = new Task<>(ACTION, OPTIONS);
        validateAction(task, null, ACTION, null, null, OPTIONS);
        task = new Task<>(VOID_ACTION, OPTIONS);
        validateVoidAction(task, null, VOID_ACTION, null, null, OPTIONS);
        task = new Task<>(EMPTY_ACTION, OPTIONS);
        validateEmptyAction(task, null, EMPTY_ACTION, null, null, OPTIONS);
        task = new Task<>(EMPTY_VOID_ACTION, OPTIONS);
        validateEmptyVoidAction(task, null, EMPTY_VOID_ACTION, null, null, OPTIONS);

        task = new Task<>(ACTION, CANCELLATION_TOKEN);
        validateAction(task, null, ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(VOID_ACTION, CANCELLATION_TOKEN);
        validateVoidAction(task, null, VOID_ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(EMPTY_ACTION, CANCELLATION_TOKEN);
        validateEmptyAction(task, null, EMPTY_ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateEmptyVoidAction(task, null, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, null, null);

        task = new Task<>(ACTION);
        validateAction(task, null, ACTION, null, null, null);
        task = new Task<>(VOID_ACTION);
        validateVoidAction(task, null, VOID_ACTION, null, null, null);
        task = new Task<>(EMPTY_ACTION);
        validateEmptyAction(task, null, EMPTY_ACTION, null, null, null);
        task = new Task<>(EMPTY_VOID_ACTION);
        validateEmptyVoidAction(task, null, EMPTY_VOID_ACTION, null, null, null);
    }

    private void validateAction(Task<?> task, String id, IAction<?> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        assertSame(action, task.getAction());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getScheduler());

        if(options == null) {
            assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
            assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));
        } else {
            assertTrue(task.getOptions().containsAll(Arrays.asList(options)));
            assertTrue(Arrays.asList(options).containsAll(task.getOptions()));
        }
    }

    private void validateVoidAction(Task<?> task, String id, IVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        assertSame(action, ((VoidAction)task.getAction()).getAction());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getScheduler());

        if(options == null) {
            assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
            assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));
        } else {
            assertTrue(task.getOptions().containsAll(Arrays.asList(options)));
            assertTrue(Arrays.asList(options).containsAll(task.getOptions()));
        }
    }

    private void validateEmptyAction(Task<?> task, String id, IEmptyAction<?> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        assertSame(action, ((EmptyAction)task.getAction()).getAction());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getScheduler());

        if(options == null) {
            assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
            assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));
        } else {
            assertTrue(task.getOptions().containsAll(Arrays.asList(options)));
            assertTrue(Arrays.asList(options).containsAll(task.getOptions()));
        }
    }

    private void validateEmptyVoidAction(Task<?> task, String id, IEmptyVoidAction action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        assertSame(action, ((EmptyVoidAction)task.getAction()).getAction());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getScheduler());

        if(options == null) {
            assertTrue(task.getOptions().containsAll(Arrays.asList(Task.DEFAULT_OPTIONS)));
            assertTrue(Arrays.asList(Task.DEFAULT_OPTIONS).containsAll(task.getOptions()));
        } else {
            assertTrue(task.getOptions().containsAll(Arrays.asList(options)));
            assertTrue(Arrays.asList(options).containsAll(task.getOptions()));
        }
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

        thenTask = task.then(id, LINK_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThenLinkAction(thenTask, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThenLinkVoidAction(thenTask, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThenLinkEmptyAction(thenTask, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThenLinkEmptyVoidAction(thenTask, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        thenTask = task.then(id, LINK_ACTION, SCHEDULER, OPTIONS);
        validateThenLinkAction(thenTask, task, id, null, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_VOID_ACTION, SCHEDULER, OPTIONS);
        validateThenLinkVoidAction(thenTask, task, id, null, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateThenLinkEmptyAction(thenTask, task, id, null, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateThenLinkEmptyVoidAction(thenTask, task, id, null, SCHEDULER, OPTIONS);

        thenTask = task.then(id, LINK_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThenLinkAction(thenTask, task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(id, LINK_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThenLinkVoidAction(thenTask, task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(id, LINK_EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThenLinkEmptyAction(thenTask, task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThenLinkEmptyVoidAction(thenTask, task, id, CANCELLATION_TOKEN, SCHEDULER, null);

        thenTask = task.then(id, LINK_ACTION, SCHEDULER);
        validateThenLinkAction(thenTask, task, id, null, SCHEDULER, null);
        thenTask = task.then(id, LINK_VOID_ACTION, SCHEDULER);
        validateThenLinkVoidAction(thenTask, task, id, null, SCHEDULER, null);
        thenTask = task.then(id, LINK_EMPTY_ACTION, SCHEDULER);
        validateThenLinkEmptyAction(thenTask, task, id, null, SCHEDULER, null);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, SCHEDULER);
        validateThenLinkEmptyVoidAction(thenTask, task, id, null, SCHEDULER, null);

        thenTask = task.then(id, LINK_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThenLinkAction(thenTask, task, id, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(id, LINK_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThenLinkVoidAction(thenTask, task, id, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThenLinkEmptyAction(thenTask, task, id, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThenLinkEmptyVoidAction(thenTask, task, id, CANCELLATION_TOKEN, null, OPTIONS);

        thenTask = task.then(id, LINK_ACTION, OPTIONS);
        validateThenLinkAction(thenTask, task, id, null, null, OPTIONS);
        thenTask = task.then(id, LINK_VOID_ACTION, OPTIONS);
        validateThenLinkVoidAction(thenTask, task, id, null, null, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_ACTION, OPTIONS);
        validateThenLinkEmptyAction(thenTask, task, id, null, null, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, OPTIONS);
        validateThenLinkEmptyVoidAction(thenTask, task, id, null, null, OPTIONS);

        thenTask = task.then(id, LINK_ACTION, CANCELLATION_TOKEN);
        validateThenLinkAction(thenTask, task, id, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, LINK_VOID_ACTION, CANCELLATION_TOKEN);
        validateThenLinkVoidAction(thenTask, task, id, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, LINK_EMPTY_ACTION, CANCELLATION_TOKEN);
        validateThenLinkEmptyAction(thenTask, task, id, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateThenLinkEmptyVoidAction(thenTask, task, id, CANCELLATION_TOKEN, null, null);

        thenTask = task.then(id, LINK_ACTION);
        validateThenLinkAction(thenTask, task, id, null, null, null);
        thenTask = task.then(id, LINK_VOID_ACTION);
        validateThenLinkVoidAction(thenTask, task, id, null, null, null );
        thenTask = task.then(id, LINK_EMPTY_ACTION);
        validateThenLinkEmptyAction(thenTask, task, id, null, null, null);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION);
        validateThenLinkEmptyVoidAction(thenTask, task, id, null, null, null);

        thenTask = task.then(LINK_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThenLinkAction(thenTask, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThenLinkVoidAction(thenTask, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThenLinkEmptyAction(thenTask, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThenLinkEmptyVoidAction(thenTask, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        thenTask = task.then(LINK_ACTION, SCHEDULER, OPTIONS);
        validateThenLinkAction(thenTask, task, null, null, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_VOID_ACTION, SCHEDULER, OPTIONS);
        validateThenLinkVoidAction(thenTask, task, null, null, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateThenLinkEmptyAction(thenTask, task, null, null, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateThenLinkEmptyVoidAction(thenTask, task, null, null, SCHEDULER, OPTIONS);

        thenTask = task.then(LINK_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThenLinkAction(thenTask, task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(LINK_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThenLinkVoidAction(thenTask, task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(LINK_EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThenLinkEmptyAction(thenTask, task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThenLinkEmptyVoidAction(thenTask, task, null, CANCELLATION_TOKEN, SCHEDULER, null);

        thenTask = task.then(LINK_ACTION, SCHEDULER);
        validateThenLinkAction(thenTask, task, null, null, SCHEDULER, null);
        thenTask = task.then(LINK_VOID_ACTION, SCHEDULER);
        validateThenLinkVoidAction(thenTask, task, null, null, SCHEDULER, null);
        thenTask = task.then(LINK_EMPTY_ACTION, SCHEDULER);
        validateThenLinkEmptyAction(thenTask, task, null, null, SCHEDULER, null);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, SCHEDULER);
        validateThenLinkEmptyVoidAction(thenTask, task, null, null, SCHEDULER, null);

        thenTask = task.then(LINK_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThenLinkAction(thenTask, task, null, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(LINK_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThenLinkVoidAction(thenTask, task, null, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(LINK_EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThenLinkEmptyAction(thenTask, task, null, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThenLinkEmptyVoidAction(thenTask, task, null, CANCELLATION_TOKEN, null, OPTIONS);

        thenTask = task.then(LINK_ACTION, OPTIONS);
        validateThenLinkAction(thenTask, task, null, null, null, OPTIONS);
        thenTask = task.then(LINK_VOID_ACTION, OPTIONS);
        validateThenLinkVoidAction(thenTask, task, null, null, null, OPTIONS);
        thenTask = task.then(LINK_EMPTY_ACTION, OPTIONS);
        validateThenLinkEmptyAction(thenTask, task, null, null, null, OPTIONS);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, OPTIONS);
        validateThenLinkEmptyVoidAction(thenTask, task, null, null, null, OPTIONS);

        thenTask = task.then(LINK_ACTION, CANCELLATION_TOKEN);
        validateThenLinkAction(thenTask, task, null, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(LINK_VOID_ACTION, CANCELLATION_TOKEN);
        validateThenLinkVoidAction(thenTask, task, null, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(LINK_EMPTY_ACTION, CANCELLATION_TOKEN);
        validateThenLinkEmptyAction(thenTask, task, null, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateThenLinkEmptyVoidAction(thenTask, task, null, CANCELLATION_TOKEN, null, null);

        thenTask = task.then(LINK_ACTION);
        validateThenLinkAction(thenTask, task, null, null, null, null);
        thenTask = task.then(LINK_VOID_ACTION);
        validateThenLinkVoidAction(thenTask, task, null, null, null, null );
        thenTask = task.then(LINK_EMPTY_ACTION);
        validateThenLinkEmptyAction(thenTask, task, null, null, null, null);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION);
        validateThenLinkEmptyVoidAction(thenTask, task, null, null, null, null);
    }

    private void validateThenLinkAction(Task<?> thenTask, Task<String> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(thenTask.getId());
        else
            assertSame(id, thenTask.getId());

        assertTrue(thenTask.getAction() instanceof LinkAction);

        if(cancellationToken == null)
            assertNotNull(thenTask.getCancellationToken());
        else
            assertSame(cancellationToken, thenTask.getCancellationToken());

        if(scheduler == null)
            assertSame(task.getScheduler(), thenTask.getScheduler());
        else
            assertSame(scheduler, thenTask.getScheduler());

        if(options == null)
            assertEquals(task.getOptions(), thenTask.getOptions());
        else
            assertEquals(Arrays.asList(options), thenTask.getOptions());
    }

    private void validateThenLinkVoidAction(Task<?> thenTask, Task<String> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(thenTask.getId());
        else
            assertSame(id, thenTask.getId());

        assertTrue(thenTask.getAction() instanceof LinkVoidAction);

        if(cancellationToken == null)
            assertNotNull(thenTask.getCancellationToken());
        else
            assertSame(cancellationToken, thenTask.getCancellationToken());

        if(scheduler == null)
            assertSame(task.getScheduler(), thenTask.getScheduler());
        else
            assertSame(scheduler, thenTask.getScheduler());

        if(options == null)
            assertEquals(task.getOptions(), thenTask.getOptions());
        else
            assertEquals(Arrays.asList(options), thenTask.getOptions());
    }

    private void validateThenLinkEmptyAction(Task<?> thenTask, Task<String> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(thenTask.getId());
        else
            assertSame(id, thenTask.getId());

        assertTrue(thenTask.getAction() instanceof LinkEmptyAction);

        if(cancellationToken == null)
            assertNotNull(thenTask.getCancellationToken());
        else
            assertSame(cancellationToken, thenTask.getCancellationToken());

        if(scheduler == null)
            assertSame(task.getScheduler(), thenTask.getScheduler());
        else
            assertSame(scheduler, thenTask.getScheduler());

        if(options == null)
            assertEquals(task.getOptions(), thenTask.getOptions());
        else
            assertEquals(Arrays.asList(options), thenTask.getOptions());
    }

    private void validateThenLinkEmptyVoidAction(Task<?> thenTask, Task<String> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(thenTask.getId());
        else
            assertSame(id, thenTask.getId());

        assertTrue(thenTask.getAction() instanceof LinkEmptyVoidAction);

        if(cancellationToken == null)
            assertNotNull(thenTask.getCancellationToken());
        else
            assertSame(cancellationToken, thenTask.getCancellationToken());

        if(scheduler == null)
            assertSame(task.getScheduler(), thenTask.getScheduler());
        else
            assertSame(scheduler, thenTask.getScheduler());

        if(options == null)
            assertEquals(task.getOptions(), thenTask.getOptions());
        else
            assertEquals(Arrays.asList(options), thenTask.getOptions());
    }

    @Test
    public void retryTest() {
        String id = "";
        Consumer<Runnable> scheduler = (action) -> new Thread(action).start();
        TaskOption[] options = new TaskOption[0];
        Task<String> task = new Task<>(ACTION, scheduler, options);
        Task<String> retryTask;

        retryTask = task.retry(id, () -> true, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        retryTask = task.retry(id, 3, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        retryTask = task.retry(id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        retryTask = task.retry(id, () -> true, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, id, null, SCHEDULER, OPTIONS);
        retryTask = task.retry(id, 3, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, id, null, SCHEDULER, OPTIONS);
        retryTask = task.retry(id, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, id, null, SCHEDULER, OPTIONS);

        retryTask = task.retry(id, () -> true, CANCELLATION_TOKEN, SCHEDULER);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        retryTask = task.retry(id, 3, CANCELLATION_TOKEN, SCHEDULER);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        retryTask = task.retry(id, CANCELLATION_TOKEN, SCHEDULER);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, SCHEDULER, null);

        retryTask = task.retry(id, () -> true, SCHEDULER);
        validateRetry(retryTask, task, id, null, SCHEDULER, null);
        retryTask = task.retry(id, 3, SCHEDULER);
        validateRetry(retryTask, task, id, null, SCHEDULER, null);
        retryTask = task.retry(id, SCHEDULER);
        validateRetry(retryTask, task, id, null, SCHEDULER, null);

        retryTask = task.retry(id, () -> true, CANCELLATION_TOKEN, OPTIONS);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, null, OPTIONS);
        retryTask = task.retry(id, 3, CANCELLATION_TOKEN, OPTIONS);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, null, OPTIONS);
        retryTask = task.retry(id, CANCELLATION_TOKEN, OPTIONS);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, null, OPTIONS);

        retryTask = task.retry(id, () -> true, OPTIONS);
        validateRetry(retryTask, task, id, null, null, OPTIONS);
        retryTask = task.retry(id, 3, OPTIONS);
        validateRetry(retryTask, task, id, null, null, OPTIONS);
        retryTask = task.retry(id, OPTIONS);
        validateRetry(retryTask, task, id, null, null, OPTIONS);

        retryTask = task.retry(id, () -> true, CANCELLATION_TOKEN);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, null, null);
        retryTask = task.retry(id, 3, CANCELLATION_TOKEN);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, null, null);
        retryTask = task.retry(id, CANCELLATION_TOKEN);
        validateRetry(retryTask, task, id, CANCELLATION_TOKEN, null, null);

        retryTask = task.retry(id, () -> true);
        validateRetry(retryTask, task, id, null, null, null);
        retryTask = task.retry(id, 3);
        validateRetry(retryTask, task, id, null, null, null);
        retryTask = task.retry(id);
        validateRetry(retryTask, task, id, null, null, null);

        retryTask = task.retry(() -> true, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        retryTask = task.retry(3, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        retryTask = task.retry(CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        retryTask = task.retry(() -> true, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, null, null, SCHEDULER, OPTIONS);
        retryTask = task.retry(3, SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, null, null, SCHEDULER, OPTIONS);
        retryTask = task.retry(SCHEDULER, OPTIONS);
        validateRetry(retryTask, task, null, null, SCHEDULER, OPTIONS);

        retryTask = task.retry(() -> true, CANCELLATION_TOKEN, SCHEDULER);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        retryTask = task.retry(3, CANCELLATION_TOKEN, SCHEDULER);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        retryTask = task.retry(CANCELLATION_TOKEN, SCHEDULER);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, SCHEDULER, null);

        retryTask = task.retry(() -> true, SCHEDULER);
        validateRetry(retryTask, task, null, null, SCHEDULER, null);
        retryTask = task.retry(3, SCHEDULER);
        validateRetry(retryTask, task, null, null, SCHEDULER, null);
        retryTask = task.retry(SCHEDULER);
        validateRetry(retryTask, task, null, null, SCHEDULER, null);

        retryTask = task.retry(() -> true, CANCELLATION_TOKEN, OPTIONS);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, null, OPTIONS);
        retryTask = task.retry(3, CANCELLATION_TOKEN, OPTIONS);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, null, OPTIONS);
        retryTask = task.retry(CANCELLATION_TOKEN, OPTIONS);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, null, OPTIONS);

        retryTask = task.retry(() -> true, OPTIONS);
        validateRetry(retryTask, task, null, null, null, OPTIONS);
        retryTask = task.retry(3, OPTIONS);
        validateRetry(retryTask, task, null, null, null, OPTIONS);
        retryTask = task.retry(OPTIONS);
        validateRetry(retryTask, task, null, null, null, OPTIONS);

        retryTask = task.retry(() -> true, CANCELLATION_TOKEN);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, null, null);
        retryTask = task.retry(3, CANCELLATION_TOKEN);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, null, null);
        retryTask = task.retry(CANCELLATION_TOKEN);
        validateRetry(retryTask, task, null, CANCELLATION_TOKEN, null, null);

        retryTask = task.retry(() -> true);
        validateRetry(retryTask, task, null, null, null, null);
        retryTask = task.retry(3);
        validateRetry(retryTask, task, null, null, null, null);
        retryTask = task.retry();
        validateRetry(retryTask, task, null, null, null, null);
    }

    private void validateRetry(Task<String> retryTask, Task<String> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(retryTask.getId());
        else
            assertSame(id, retryTask.getId());

        if(cancellationToken == null)
            assertNotNull(retryTask.getCancellationToken());
        else
            assertSame(cancellationToken, retryTask.getCancellationToken());

        if(scheduler == null)
            assertSame(task.getScheduler(), retryTask.getScheduler());
        else
            assertSame(scheduler, retryTask.getScheduler());

        if(options == null)
            assertEquals(task.getOptions(), retryTask.getOptions());
        else
            assertEquals(Arrays.asList(options), retryTask.getOptions());

        assertTrue(retryTask.getAction() instanceof RetryAction);
    }

}
