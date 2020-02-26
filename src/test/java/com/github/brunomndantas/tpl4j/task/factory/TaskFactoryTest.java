package com.github.brunomndantas.tpl4j.task.factory;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyTask;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.action.action.IVoidAction;
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
    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Option[] OPTIONS = {};
    private static final Consumer<Runnable> SCHEDULER = (job) -> new Thread(job).start();



    @Test
    public void createTest() {
        String id = "";
        Task<?> task;

        task = TaskFactory.create(id, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.create(id, ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreate(task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreate(task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreate(task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreate(task, id, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.create(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.create(id, ACTION, SCHEDULER, OPTIONS);
        validateCreate(task, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, VOID_ACTION, SCHEDULER, OPTIONS);
        validateCreate(task, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateCreate(task, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateCreate(task, id, null, SCHEDULER, OPTIONS);

        task = TaskFactory.create(id, ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.create(id, ACTION, SCHEDULER);
        validateCreate(task, id, null, SCHEDULER, null);
        task = TaskFactory.create(id, VOID_ACTION, SCHEDULER);
        validateCreate(task, id, null, SCHEDULER, null);
        task = TaskFactory.create(id, EMPTY_ACTION, SCHEDULER);
        validateCreate(task, id, null, SCHEDULER, null);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, SCHEDULER);
        validateCreate(task, id, null, SCHEDULER, null);

        task = TaskFactory.create(id, ACTION, OPTIONS);
        validateCreate(task, id, null, null, OPTIONS);
        task = TaskFactory.create(id, VOID_ACTION, OPTIONS);
        validateCreate(task, id, null, null, OPTIONS);
        task = TaskFactory.create(id, EMPTY_ACTION, OPTIONS);
        validateCreate(task, id, null, null, OPTIONS);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, OPTIONS);
        validateCreate(task, id, null, null, OPTIONS);

        task = TaskFactory.create(id, ACTION);
        validateCreate(task, id, null, null, null);
        task = TaskFactory.create(id, VOID_ACTION);
        validateCreate(task, id, null, null, null);
        task = TaskFactory.create(id, EMPTY_ACTION);
        validateCreate(task, id, null, null, null);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION);
        validateCreate(task, id, null, null, null);

        task = TaskFactory.create(ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.create(ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreate(task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreate(task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreate(task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreate(task, null, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.create(ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.create(ACTION, SCHEDULER, OPTIONS);
        validateCreate(task, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(VOID_ACTION, SCHEDULER, OPTIONS);
        validateCreate(task, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateCreate(task, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateCreate(task, null, null, SCHEDULER, OPTIONS);

        task = TaskFactory.create(ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.create(ACTION, SCHEDULER);
        validateCreate(task, null, null, SCHEDULER, null);
        task = TaskFactory.create(VOID_ACTION, SCHEDULER);
        validateCreate(task, null, null, SCHEDULER, null);
        task = TaskFactory.create(EMPTY_ACTION, SCHEDULER);
        validateCreate(task, null, null, SCHEDULER, null);
        task = TaskFactory.create(EMPTY_VOID_ACTION, SCHEDULER);
        validateCreate(task, null, null, SCHEDULER, null);

        task = TaskFactory.create(ACTION, OPTIONS);
        validateCreate(task, null, null, null, OPTIONS);
        task = TaskFactory.create(VOID_ACTION, OPTIONS);
        validateCreate(task, null, null, null, OPTIONS);
        task = TaskFactory.create(EMPTY_ACTION, OPTIONS);
        validateCreate(task, null, null, null, OPTIONS);
        task = TaskFactory.create(EMPTY_VOID_ACTION, OPTIONS);
        validateCreate(task, null, null, null, OPTIONS);

        task = TaskFactory.create(ACTION);
        validateCreate(task, null, null, null, null);
        task = TaskFactory.create(VOID_ACTION);
        validateCreate(task, null, null, null, null);
        task = TaskFactory.create(EMPTY_ACTION);
        validateCreate(task, null, null, null, null);
        task = TaskFactory.create(EMPTY_VOID_ACTION);
        validateCreate(task, null, null, null, null);
    }

    private void validateCreate(Task<?> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getJob().getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().getOptions().size());

        assertFalse(task.getStatus().scheduledEvent.hasFired());
    }

    @Test
    public void createAndStartTest() {
        String id = "";
        Task<?> task;

        task = TaskFactory.createAndStart(id, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.createAndStart(id, ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.createAndStart(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.createAndStart(id, ACTION, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, VOID_ACTION, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, id, null, SCHEDULER, OPTIONS);

        task = TaskFactory.createAndStart(id, ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.createAndStart(id, ACTION, SCHEDULER);
        validateCreateAndStart(task, id, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, VOID_ACTION, SCHEDULER);
        validateCreateAndStart(task, id, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, SCHEDULER);
        validateCreateAndStart(task, id, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, SCHEDULER);
        validateCreateAndStart(task, id, null, SCHEDULER, null);

        task = TaskFactory.createAndStart(id, ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, null, OPTIONS);
        task = TaskFactory.createAndStart(id, VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, null, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, null, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, null, OPTIONS);

        task = TaskFactory.createAndStart(id, ACTION);
        validateCreateAndStart(task, id, null, null, null);
        task = TaskFactory.createAndStart(id, VOID_ACTION);
        validateCreateAndStart(task, id, null, null, null);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION);
        validateCreateAndStart(task, id, null, null, null);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION);
        validateCreateAndStart(task, id, null, null, null);

        task = TaskFactory.createAndStart(ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.createAndStart(ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.createAndStart(ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.createAndStart(ACTION, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(VOID_ACTION, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateCreateAndStart(task, null, null, SCHEDULER, OPTIONS);

        task = TaskFactory.createAndStart(ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.createAndStart(ACTION, SCHEDULER);
        validateCreateAndStart(task, null, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(VOID_ACTION, SCHEDULER);
        validateCreateAndStart(task, null, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(EMPTY_ACTION, SCHEDULER);
        validateCreateAndStart(task, null, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, SCHEDULER);
        validateCreateAndStart(task, null, null, SCHEDULER, null);

        task = TaskFactory.createAndStart(ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, null, OPTIONS);
        task = TaskFactory.createAndStart(VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, null, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, null, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, null, OPTIONS);

        task = TaskFactory.createAndStart(ACTION);
        validateCreateAndStart(task, null, null, null, null);
        task = TaskFactory.createAndStart(VOID_ACTION);
        validateCreateAndStart(task, null, null, null, null);
        task = TaskFactory.createAndStart(EMPTY_ACTION);
        validateCreateAndStart(task, null, null, null, null);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION);
        validateCreateAndStart(task, null, null, null, null);
    }

    private void validateCreateAndStart(Task<?> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getJob().getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().getOptions().size());

        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }

    @Test
    public void whenAllTest() {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<?> task;

        task = TaskFactory.whenAll(id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAll(id, tasks, CANCELLATION_TOKEN, SCHEDULER);
        validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.whenAll(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.whenAll(id, tasks, CANCELLATION_TOKEN);
        validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAll(id, tasks, SCHEDULER, OPTIONS);
        validateWhenAll(task, id, tasks, null, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAll(id, tasks, SCHEDULER);
        validateWhenAll(task, id, tasks, null, SCHEDULER, null);
        task = TaskFactory.whenAll(id, tasks, OPTIONS);
        validateWhenAll(task, id, tasks, null, null, OPTIONS);
        task = TaskFactory.whenAll(id, tasks);
        validateWhenAll(task, id, tasks, null, null, null);

        task = TaskFactory.whenAll(tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAll(tasks, CANCELLATION_TOKEN, SCHEDULER);
        validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.whenAll(tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.whenAll(tasks, CANCELLATION_TOKEN);
        validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAll(tasks, SCHEDULER, OPTIONS);
        validateWhenAll(task, null, tasks, null, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAll(tasks, SCHEDULER);
        validateWhenAll(task, null, tasks, null, SCHEDULER, null);
        task = TaskFactory.whenAll(tasks, OPTIONS);
        validateWhenAll(task, null, tasks, null, null, OPTIONS);
        task = TaskFactory.whenAll(tasks);
        validateWhenAll(task, null, tasks, null, null, null);
    }

    private void validateWhenAll(WhenAllTask<?> task, String id, Collection<Task<String>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getJob().getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().getOptions().size());

        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void whenAnyTest() {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<?> task;

        task = TaskFactory.whenAny(id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAny(id, tasks, CANCELLATION_TOKEN, SCHEDULER);
        validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.whenAny(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.whenAny(id, tasks, CANCELLATION_TOKEN);
        validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAny(id, tasks, SCHEDULER, OPTIONS);
        validateWhenAny(task, id, tasks, null, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAny(id, tasks, SCHEDULER);
        validateWhenAny(task, id, tasks, null, SCHEDULER, null);
        task = TaskFactory.whenAny(id, tasks, OPTIONS);
        validateWhenAny(task, id, tasks, null, null, OPTIONS);
        task = TaskFactory.whenAny(id, tasks);
        validateWhenAny(task, id, tasks, null, null, null);

        task = TaskFactory.whenAny(tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAny(tasks, CANCELLATION_TOKEN, SCHEDULER);
        validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.whenAny(tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.whenAny(tasks, CANCELLATION_TOKEN);
        validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAny(tasks, SCHEDULER, OPTIONS);
        validateWhenAny(task, null, tasks, null, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAny(tasks, SCHEDULER);
        validateWhenAny(task, null, tasks, null, SCHEDULER, null);
        task = TaskFactory.whenAny(tasks, OPTIONS);
        validateWhenAny(task, null, tasks, null, null, OPTIONS);
        task = TaskFactory.whenAny(tasks);
        validateWhenAny(task, null, tasks, null, null, null);
    }

    private void validateWhenAny(WhenAnyTask<?> task, String id, Collection<Task<String>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getJob().getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().getOptions().size());

        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void unwrapTest() {
        String id = "";
        Task<Task<String>> taskToUnwrap = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<?> task;
        
        task = TaskFactory.unwrap(id, taskToUnwrap, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateUnwrap(task, taskToUnwrap, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.unwrap(id, taskToUnwrap, CANCELLATION_TOKEN, SCHEDULER);
        validateUnwrap(task, taskToUnwrap, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.unwrap(id, taskToUnwrap, CANCELLATION_TOKEN, OPTIONS);
        validateUnwrap(task, taskToUnwrap, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.unwrap(id, taskToUnwrap, CANCELLATION_TOKEN);
        validateUnwrap(task, taskToUnwrap, id, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.unwrap(id, taskToUnwrap, SCHEDULER, OPTIONS);
        validateUnwrap(task, taskToUnwrap, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.unwrap(id, taskToUnwrap, SCHEDULER);
        validateUnwrap(task, taskToUnwrap, id, null, SCHEDULER, null);
        task = TaskFactory.unwrap(id, taskToUnwrap, OPTIONS);
        validateUnwrap(task, taskToUnwrap, id, null, null, OPTIONS);
        task = TaskFactory.unwrap(id, taskToUnwrap);
        validateUnwrap(task, taskToUnwrap, id, null, null, null);

        task = TaskFactory.unwrap(taskToUnwrap, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateUnwrap(task, taskToUnwrap, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.unwrap(taskToUnwrap, CANCELLATION_TOKEN, SCHEDULER);
        validateUnwrap(task, taskToUnwrap, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.unwrap(taskToUnwrap, CANCELLATION_TOKEN, OPTIONS);
        validateUnwrap(task, taskToUnwrap, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.unwrap(taskToUnwrap, CANCELLATION_TOKEN);
        validateUnwrap(task, taskToUnwrap, null, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.unwrap(taskToUnwrap, SCHEDULER, OPTIONS);
        validateUnwrap(task, taskToUnwrap, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.unwrap(taskToUnwrap, SCHEDULER);
        validateUnwrap(task, taskToUnwrap, null, null, SCHEDULER, null);
        task = TaskFactory.unwrap(taskToUnwrap, OPTIONS);
        validateUnwrap(task, taskToUnwrap, null, null, null, OPTIONS);
        task = TaskFactory.unwrap(taskToUnwrap);
        validateUnwrap(task, taskToUnwrap, null, null, null, null);
    }

    private void validateUnwrap(UnwrapTask<?> task, Task<?> taskToUnwrap, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getJob().getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().getOptions().size());

        assertTrue(task.getStatus().scheduledEvent.hasFired());

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
        ParallelTask<String,?> task;

        task = TaskFactory.forEach(id, elements, action, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, voidAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.forEach(id, elements, action, CANCELLATION_TOKEN, SCHEDULER);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, voidAction, CANCELLATION_TOKEN, SCHEDULER);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, SCHEDULER);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, SCHEDULER);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.forEach(id, elements, action, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.forEach(id, elements, action, SCHEDULER, OPTIONS);
        validateForEach(task, id, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, voidAction, SCHEDULER, OPTIONS);
        validateForEach(task, id, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, SCHEDULER, OPTIONS);
        validateForEach(task, id, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, SCHEDULER, OPTIONS);
        validateForEach(task, id, elements, null, SCHEDULER, OPTIONS);

        task = TaskFactory.forEach(id, elements, action, CANCELLATION_TOKEN);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, voidAction, CANCELLATION_TOKEN);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        validateForEach(task, id, elements, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.forEach(id, elements, action, SCHEDULER);
        validateForEach(task, id, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, voidAction, SCHEDULER);
        validateForEach(task, id, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, SCHEDULER);
        validateForEach(task, id, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, SCHEDULER);
        validateForEach(task, id, elements, null, SCHEDULER, null);

        task = TaskFactory.forEach(id, elements, action, OPTIONS);
        validateForEach(task, id, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, voidAction, OPTIONS);
        validateForEach(task, id, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, OPTIONS);
        validateForEach(task, id, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, OPTIONS);
        validateForEach(task, id, elements, null, null, OPTIONS);

        task = TaskFactory.forEach(id, elements, action);
        validateForEach(task, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, voidAction);
        validateForEach(task, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction);
        validateForEach(task, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction);
        validateForEach(task, id, elements, null, null, null);

        task = TaskFactory.forEach(elements, action, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, voidAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.forEach(elements, action, CANCELLATION_TOKEN, SCHEDULER);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(elements, voidAction, CANCELLATION_TOKEN, SCHEDULER);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN, SCHEDULER);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, SCHEDULER);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.forEach(elements, action, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.forEach(elements, action, SCHEDULER, OPTIONS);
        validateForEach(task, null, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, voidAction, SCHEDULER, OPTIONS);
        validateForEach(task, null, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleAction, SCHEDULER, OPTIONS);
        validateForEach(task, null, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, SCHEDULER, OPTIONS);
        validateForEach(task, null, elements, null, SCHEDULER, OPTIONS);

        task = TaskFactory.forEach(elements, action, CANCELLATION_TOKEN);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, voidAction, CANCELLATION_TOKEN);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        validateForEach(task, null, elements, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.forEach(elements, action, SCHEDULER);
        validateForEach(task, null, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(elements, voidAction, SCHEDULER);
        validateForEach(task, null, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(elements, uninterruptibleAction, SCHEDULER);
        validateForEach(task, null, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, SCHEDULER);
        validateForEach(task, null, elements, null, SCHEDULER, null);

        task = TaskFactory.forEach(elements, action, OPTIONS);
        validateForEach(task, null, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(elements, voidAction, OPTIONS);
        validateForEach(task, null, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleAction, OPTIONS);
        validateForEach(task, null, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, OPTIONS);
        validateForEach(task, null, elements, null, null, OPTIONS);

        task = TaskFactory.forEach(elements, action);
        validateForEach(task, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, voidAction);
        validateForEach(task, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, uninterruptibleAction);
        validateForEach(task, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction);
        validateForEach(task, null, elements, null, null, null);
    }

    private void validateForEach(ParallelTask<String,?> task, String id, Iterable<String> elements, CancellationToken cancellationToken, Consumer<Runnable> scheduler, Option... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        assertSame(elements, task.getElements());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        if(scheduler == null)
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        else
            assertSame(scheduler, task.getJob().getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().getOptions().size()); //ACCEPT_CHILDREN

        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }
    
}
