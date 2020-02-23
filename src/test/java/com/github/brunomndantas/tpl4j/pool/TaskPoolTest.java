package com.github.brunomndantas.tpl4j.pool;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.TaskOption;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.action.action.IVoidAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelVoidAction;
import com.github.brunomndantas.tpl4j.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.helpers.when.whenAny.WhenAnyTask;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class TaskPoolTest {

    private static final IAction<String> ACTION = (token) -> null;
    private static final IEmptyAction<String> EMPTY_ACTION = () -> null;
    private static final IVoidAction VOID_ACTION = (token) -> {};
    private static final IEmptyVoidAction EMPTY_VOID_ACTION = () -> {};
    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
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

        task = TaskPool.createTask(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createTask(id, ACTION, OPTIONS);
        validateCreate(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, VOID_ACTION, OPTIONS);
        validateCreate(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, EMPTY_ACTION, OPTIONS);
        validateCreate(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, EMPTY_VOID_ACTION, OPTIONS);
        validateCreate(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createTask(id, ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(id, ACTION);
        validateCreate(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, VOID_ACTION);
        validateCreate(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, EMPTY_ACTION);
        validateCreate(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, EMPTY_VOID_ACTION);
        validateCreate(task, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createTask(ACTION, OPTIONS);
        validateCreate(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(VOID_ACTION, OPTIONS);
        validateCreate(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(EMPTY_ACTION, OPTIONS);
        validateCreate(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(EMPTY_VOID_ACTION, OPTIONS);
        validateCreate(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createTask(ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(ACTION);
        validateCreate(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(VOID_ACTION);
        validateCreate(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(EMPTY_ACTION);
        validateCreate(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(EMPTY_VOID_ACTION);
        validateCreate(task, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticCreateAndStartTest() {
        String  id = "";
        Task<?> task;

        task = TaskPool.createAndStartTask(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createAndStartTask(id, ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, EMPTY_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createAndStartTask(id, ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(id, ACTION);
        validateCreateAndStart(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, VOID_ACTION);
        validateCreateAndStart(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, EMPTY_ACTION);
        validateCreateAndStart(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION);
        validateCreateAndStart(task, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createAndStartTask(ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(EMPTY_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createAndStartTask(ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(ACTION);
        validateCreateAndStart(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(VOID_ACTION);
        validateCreateAndStart(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(EMPTY_ACTION);
        validateCreateAndStart(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION);
        validateCreateAndStart(task, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticWhenAllTest() {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<String> task;
        
        task = TaskPool.whenAllTask(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAllTask(id, tasks, OPTIONS);
        validateWhenAll(task, id, tasks, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.whenAllTask(id, tasks, CANCELLATION_TOKEN);
        validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAllTask(id, tasks);
        validateWhenAll(task, id, tasks, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.whenAllTask(tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAllTask(tasks, OPTIONS);
        validateWhenAll(task, null, tasks, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.whenAllTask(tasks, CANCELLATION_TOKEN);
        validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAllTask(tasks);
        validateWhenAll(task, null, tasks, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticWhenAnyTest() {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<String> task;
        
        task = TaskPool.whenAnyTask(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAnyTask(id, tasks, OPTIONS);
        validateWhenAny(task, id, tasks, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.whenAnyTask(id, tasks, CANCELLATION_TOKEN);
        validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAnyTask(id, tasks);
        validateWhenAny(task, id, tasks, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.whenAnyTask(tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAnyTask(tasks, OPTIONS);
        validateWhenAny(task, null, tasks, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.whenAnyTask(tasks, CANCELLATION_TOKEN);
        validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAnyTask(tasks);
        validateWhenAny(task, null, tasks, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticUnwrapTest() {
        String id = "";
        Task<Task<String>> unwrapTask = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<String> task;
        
        task = TaskPool.unwrapTask(id, unwrapTask, CANCELLATION_TOKEN, OPTIONS);
        validateUnwrap(task, unwrapTask, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.unwrapTask(id, unwrapTask, OPTIONS);
        validateUnwrap(task, unwrapTask, id, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.unwrapTask(id, unwrapTask, CANCELLATION_TOKEN);
        validateUnwrap(task, unwrapTask, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.unwrapTask(id, unwrapTask);
        validateUnwrap(task, unwrapTask, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.unwrapTask(unwrapTask, CANCELLATION_TOKEN, OPTIONS);
        validateUnwrap(task, unwrapTask, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.unwrapTask(unwrapTask, OPTIONS);
        validateUnwrap(task, unwrapTask, null, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.unwrapTask(unwrapTask, CANCELLATION_TOKEN);
        validateUnwrap(task, unwrapTask, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.unwrapTask(unwrapTask);
        validateUnwrap(task, unwrapTask, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticForEachTestTest() {
        String id = "";
        IParallelAction<String,String> action = (e, t) -> "";
        IParallelVoidAction<String> voidAction = (e, t) -> { };
        IParallelUninterruptibleAction<String,String> uninterruptibleAction = (e) -> "";
        IParallelUninterruptibleVoidAction<String> uninterruptibleVoidAction = (e) -> { };
        Iterable<String> elements = Arrays.asList("","");
        Task<Collection<String>> task;

        task = TaskPool.forEachTask(id, elements, action, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.forEachTask(id, elements, action, OPTIONS);
        validateForEach(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, voidAction, OPTIONS);
        validateForEach(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, uninterruptibleAction, OPTIONS);
        validateForEach(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, uninterruptibleVoidAction, OPTIONS);
        validateForEach(task, id, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.forEachTask(id, elements, action, CANCELLATION_TOKEN);
        validateForEach(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, voidAction, CANCELLATION_TOKEN);
        validateForEach(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, uninterruptibleAction, CANCELLATION_TOKEN);
        validateForEach(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        validateForEach(task, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(id, elements, action);
        validateForEach(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, voidAction);
        validateForEach(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, uninterruptibleAction);
        validateForEach(task, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, uninterruptibleVoidAction);
        validateForEach(task, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(elements, action, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.forEachTask(elements, action, OPTIONS);
        validateForEach(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, voidAction, OPTIONS);
        validateForEach(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, uninterruptibleAction, OPTIONS);
        validateForEach(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, uninterruptibleVoidAction, OPTIONS);
        validateForEach(task, null, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.forEachTask(elements, action, CANCELLATION_TOKEN);
        validateForEach(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, voidAction, CANCELLATION_TOKEN);
        validateForEach(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, uninterruptibleAction, CANCELLATION_TOKEN);
        validateForEach(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        validateForEach(task, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(elements, action);
        validateForEach(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, voidAction);
        validateForEach(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, uninterruptibleAction);
        validateForEach(task, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, uninterruptibleVoidAction);
        validateForEach(task, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void getSchedulerTest() {
        TaskPool pool = new TaskPool();

        assertNotNull(pool.getScheduler());

        pool.close();
    }

    @Test
    public void createTest() {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.create(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.create(id, ACTION, OPTIONS);
        validateCreate(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.create(id, VOID_ACTION, OPTIONS);
        validateCreate(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.create(id, EMPTY_ACTION, OPTIONS);
        validateCreate(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.create(id, EMPTY_VOID_ACTION, OPTIONS);
        validateCreate(task, id, null, pool.getScheduler(), OPTIONS);

        task = pool.create(id, ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.create(id, ACTION);
        validateCreate(task, id, null, pool.getScheduler(), null);
        task = pool.create(id, VOID_ACTION);
        validateCreate(task, id, null, pool.getScheduler(), null);
        task = pool.create(id, EMPTY_ACTION);
        validateCreate(task, id, null, pool.getScheduler(), null);
        task = pool.create(id, EMPTY_VOID_ACTION);
        validateCreate(task, id, null, pool.getScheduler(), null);

        task = pool.create(ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreate(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.create(ACTION, OPTIONS);
        validateCreate(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.create(VOID_ACTION, OPTIONS);
        validateCreate(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.create(EMPTY_ACTION, OPTIONS);
        validateCreate(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.create(EMPTY_VOID_ACTION, OPTIONS);
        validateCreate(task, null, null, pool.getScheduler(), OPTIONS);

        task = pool.create(ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreate(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.create(ACTION);
        validateCreate(task, null, null, pool.getScheduler(), null);
        task = pool.create(VOID_ACTION);
        validateCreate(task, null, null, pool.getScheduler(), null);
        task = pool.create(EMPTY_ACTION);
        validateCreate(task, null, null, pool.getScheduler(), null);
        task = pool.create(EMPTY_VOID_ACTION);
        validateCreate(task, null, null, pool.getScheduler(), null);

        pool.close();
    }

    private void validateCreate(Task<?> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        assertSame(scheduler, task.getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().size());

        assertFalse(task.getStatus().scheduledEvent.hasFired());
    }

    @Test
    public void createAndStartTest() {
        String  id = "";
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.createAndStart(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.createAndStart(id, ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, EMPTY_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, EMPTY_VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, id, null, pool.getScheduler(), OPTIONS);

        task = pool.createAndStart(id, ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.createAndStart(id, ACTION);
        validateCreateAndStart(task, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, VOID_ACTION);
        validateCreateAndStart(task, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, EMPTY_ACTION);
        validateCreateAndStart(task, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, EMPTY_VOID_ACTION);
        validateCreateAndStart(task, id, null, pool.getScheduler(), null);

        task = pool.createAndStart(ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.createAndStart(ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(EMPTY_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(EMPTY_VOID_ACTION, OPTIONS);
        validateCreateAndStart(task, null, null, pool.getScheduler(), OPTIONS);

        task = pool.createAndStart(ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateCreateAndStart(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.createAndStart(ACTION);
        validateCreateAndStart(task, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(VOID_ACTION);
        validateCreateAndStart(task, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(EMPTY_ACTION);
        validateCreateAndStart(task, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(EMPTY_VOID_ACTION);
        validateCreateAndStart(task, null, null, pool.getScheduler(), null);
        
        pool.close();
    }

    private void validateCreateAndStart(Task<?> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        assertSame(scheduler, task.getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().size());

        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }

    @Test
    public void whenAllTest() {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<String> task;

        task = pool.whenAll(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.whenAll(id, tasks, OPTIONS);
        validateWhenAll(task, id, tasks, null, pool.getScheduler(), OPTIONS);

        task = pool.whenAll(id, tasks, CANCELLATION_TOKEN);
        validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAll(id, tasks);
        validateWhenAll(task, id, tasks, null, pool.getScheduler(), null);

        task = pool.whenAll(tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.whenAll(tasks, OPTIONS);
        validateWhenAll(task, null, tasks, null, pool.getScheduler(), OPTIONS);

        task = pool.whenAll(tasks, CANCELLATION_TOKEN);
        validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAll(tasks);
        validateWhenAll(task, null, tasks, null, pool.getScheduler(), null);
        
        pool.close();
    }

    private void validateWhenAll(WhenAllTask<?> task, String id, Collection<Task<String>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        assertSame(scheduler, task.getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().size());

        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void whenAnyTest() {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<String> taskA = new Task<>(() -> "");
        Task<String> taskB = new Task<>(() -> "");
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<String> task;

        task = pool.whenAny(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.whenAny(id, tasks, OPTIONS);
        validateWhenAny(task, id, tasks, null, pool.getScheduler(), OPTIONS);

        task = pool.whenAny(id, tasks, CANCELLATION_TOKEN);
        validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAny(id, tasks);
        validateWhenAny(task, id, tasks, null, pool.getScheduler(), null);

        task = pool.whenAny(tasks, CANCELLATION_TOKEN, OPTIONS);
        validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.whenAny(tasks, OPTIONS);
        validateWhenAny(task, null, tasks, null, pool.getScheduler(), OPTIONS);

        task = pool.whenAny(tasks, CANCELLATION_TOKEN);
        validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAny(tasks);
        validateWhenAny(task, null, tasks, null, pool.getScheduler(), null);
        
        pool.close();
    }

    private void validateWhenAny(WhenAnyTask<?> task, String id, Collection<Task<String>> tasks, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        assertSame(scheduler, task.getJob().getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().size());

        assertTrue(task.getStatus().scheduledEvent.hasFired());
        assertSame(tasks, task.getTasks());
    }

    @Test
    public void unwrapTest() {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<Task<String>> unwrapTask = new Task<>(() -> new Task<>(() -> null));

        UnwrapTask<String> task;

        task = pool.unwrap(id, unwrapTask, CANCELLATION_TOKEN, OPTIONS);
        validateUnwrap(task, unwrapTask, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.unwrap(id, unwrapTask, OPTIONS);
        validateUnwrap(task, unwrapTask, id, null, pool.getScheduler(), OPTIONS);

        task = pool.unwrap(id, unwrapTask, CANCELLATION_TOKEN);
        validateUnwrap(task, unwrapTask, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.unwrap(id, unwrapTask);
        validateUnwrap(task, unwrapTask, id, null, pool.getScheduler(), null);

        task = pool.unwrap(unwrapTask, CANCELLATION_TOKEN, OPTIONS);
        validateUnwrap(task, unwrapTask, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.unwrap(unwrapTask, OPTIONS);
        validateUnwrap(task, unwrapTask, null, null, pool.getScheduler(), OPTIONS);

        task = pool.unwrap(unwrapTask, CANCELLATION_TOKEN);
        validateUnwrap(task, unwrapTask, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.unwrap(unwrapTask);
        validateUnwrap(task, unwrapTask, null, null, pool.getScheduler(), null);

        pool.close();
    }

    private void validateUnwrap(UnwrapTask<?> task, Task<?> taskToUnwrap, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        assertSame(scheduler, task.getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size());
        else
            assertEquals(options.length, task.getJob().getOptions().size());

        assertTrue(task.getStatus().scheduledEvent.hasFired());

        assertSame(taskToUnwrap, task.getTask());
    }

    @Test
    public void forEachTestTest() {
        TaskPool pool = new TaskPool();
        String id = "";
        IParallelAction<String,String> action = (e, t) -> "";
        IParallelVoidAction<String> voidAction = (e, t) -> { };
        IParallelUninterruptibleAction<String,String> uninterruptibleAction = (e) -> "";
        IParallelUninterruptibleVoidAction<String> uninterruptibleVoidAction = (e) -> { };
        Iterable<String> elements = Arrays.asList("","");
        Task<Collection<String>> task;

        task = pool.forEach(id, elements, action, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.forEach(id, elements, action, OPTIONS);
        validateForEach(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, voidAction, OPTIONS);
        validateForEach(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, uninterruptibleAction, OPTIONS);
        validateForEach(task, id, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, uninterruptibleVoidAction, OPTIONS);
        validateForEach(task, id, null, pool.getScheduler(), OPTIONS);

        task = pool.forEach(id, elements, action, CANCELLATION_TOKEN);
        validateForEach(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, voidAction, CANCELLATION_TOKEN);
        validateForEach(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN);
        validateForEach(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        validateForEach(task, id, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.forEach(id, elements, action);
        validateForEach(task, id, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, voidAction);
        validateForEach(task, id, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, uninterruptibleAction);
        validateForEach(task, id, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, uninterruptibleVoidAction);
        validateForEach(task, id, null, pool.getScheduler(), null);

        task = pool.forEach(elements, action, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        validateForEach(task, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.forEach(elements, action, OPTIONS);
        validateForEach(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, voidAction, OPTIONS);
        validateForEach(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, uninterruptibleAction, OPTIONS);
        validateForEach(task, null, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, uninterruptibleVoidAction, OPTIONS);
        validateForEach(task, null, null, pool.getScheduler(), OPTIONS);

        task = pool.forEach(elements, action, CANCELLATION_TOKEN);
        validateForEach(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, voidAction, CANCELLATION_TOKEN);
        validateForEach(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN);
        validateForEach(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        validateForEach(task, null, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.forEach(elements, action);
        validateForEach(task, null, null, pool.getScheduler(), null);
        task = pool.forEach(elements, voidAction);
        validateForEach(task, null, null, pool.getScheduler(), null);
        task = pool.forEach(elements, uninterruptibleAction);
        validateForEach(task, null, null, pool.getScheduler(), null);
        task = pool.forEach(elements, uninterruptibleVoidAction);
        validateForEach(task, null, null, pool.getScheduler(), null);
    }

    private void validateForEach(Task<?> task, String id, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id == null)
            assertNotNull(task.getId());
        else
            assertSame(id, task.getId());

        if(cancellationToken == null)
            assertNotNull(task.getCancellationToken());
        else
            assertSame(cancellationToken, task.getCancellationToken());

        assertSame(scheduler, task.getScheduler());

        if(options == null)
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size());
        else
            assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        assertTrue(task.getStatus().scheduledEvent.hasFired());
    }

}
