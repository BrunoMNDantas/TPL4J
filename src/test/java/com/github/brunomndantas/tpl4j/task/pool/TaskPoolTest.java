package com.github.brunomndantas.tpl4j.task.pool;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.TaskTestUtils;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.action.action.IVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyTask;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

public class TaskPoolTest {

    private static final IAction<String> ACTION = (token) -> null;
    private static final IEmptyAction<String> EMPTY_ACTION = () -> null;
    private static final IVoidAction VOID_ACTION = (token) -> {};
    private static final IEmptyVoidAction EMPTY_VOID_ACTION = () -> {};
    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Option[] OPTIONS = {};



    @AfterClass
    public static void close() {
        TaskPool.dispose();
    }



    @Test
    public void staticGetSchedulerTest() {
        assertNotNull(TaskPool.getTaskScheduler());
    }

    @Test
    public void staticCreateTest() throws Exception {
        String id = "";
        Task<?> task;

        task = TaskPool.createTask(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createTask(id, ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(id, ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(id, EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createTask(id, ACTION);
        TaskTestUtils.validateCreate(task, ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, VOID_ACTION);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createTask(ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createTask(EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createTask(ACTION);
        TaskTestUtils.validateCreate(task, ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(VOID_ACTION);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticCreateAndStartTest() throws Exception {
        String  id = "";
        Task<?> task;

        task = TaskPool.createAndStartTask(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createAndStartTask(id, ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(id, ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createAndStartTask(id, ACTION);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createAndStartTask(ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.createAndStartTask(ACTION);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticWhenAllTest() throws Exception {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        taskA.start();
        Task<String> taskB = new Task<>(() -> "");
        taskB.start();
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<String> task;
        
        task = TaskPool.whenAllTask(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAllTask(id, tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAllTask(id, tasks, OPTIONS);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAllTask(id, tasks);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.whenAllTask(tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAllTask(tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAllTask(tasks, OPTIONS);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAllTask(tasks);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticWhenAnyTest() throws Exception {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        taskA.start();
        Task<String> taskB = new Task<>(() -> "");
        taskB.start();
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<String> task;
        
        task = TaskPool.whenAnyTask(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAnyTask(id, tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAnyTask(id, tasks, OPTIONS);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAnyTask(id, tasks);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.whenAnyTask(tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAnyTask(tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAnyTask(tasks, OPTIONS);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.whenAnyTask(tasks);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticUnwrapTest() throws Exception {
        String id = "";
        Task<Task<String>> unwrapTask = new Task<>(() -> { Task<String> t = new Task<>(() -> null); t.start(); return t; });
        unwrapTask.start();

        UnwrapTask<String> task;
        
        task = TaskPool.unwrapTask(id, unwrapTask, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.unwrapTask(id, unwrapTask, CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.unwrapTask(id, unwrapTask, OPTIONS);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.unwrapTask(id, unwrapTask);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.unwrapTask(unwrapTask, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.unwrapTask(unwrapTask, CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.unwrapTask(unwrapTask, OPTIONS);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.unwrapTask(unwrapTask);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticForEachTestTest() throws Exception {
        String id = "";
        IParallelAction<String,String> action = (e, t) -> "";
        IParallelVoidAction<String> voidAction = (e, t) -> { };
        IParallelUninterruptibleAction<String,String> uninterruptibleAction = (e) -> "";
        IParallelUninterruptibleVoidAction<String> uninterruptibleVoidAction = (e) -> { };
        Iterable<String> elements = Arrays.asList("","");
        Task<?> task;

        task = TaskPool.forEachTask(id, elements, action, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, action, id, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.forEachTask(id, elements, action, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, action, id, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, voidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, uninterruptibleAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(id, elements, action, OPTIONS);
        TaskTestUtils.validateForEach(task, action, id, elements, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, voidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, uninterruptibleAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(id, elements, uninterruptibleVoidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.forEachTask(id, elements, action);
        TaskTestUtils.validateForEach(task, action, id, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, voidAction);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, uninterruptibleAction);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, uninterruptibleVoidAction);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(elements, action, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, action, null, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task,uninterruptibleAction,  null, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.forEachTask(elements, action, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, action, null, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, voidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, uninterruptibleAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(elements, action, OPTIONS);
        TaskTestUtils.validateForEach(task, action, null, elements, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, voidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, uninterruptibleAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, null, TaskPool.getTaskScheduler(), OPTIONS);
        task = TaskPool.forEachTask(elements, uninterruptibleVoidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, null, TaskPool.getTaskScheduler(), OPTIONS);

        task = TaskPool.forEachTask(elements, action);
        TaskTestUtils.validateForEach(task, action, null, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, voidAction);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, uninterruptibleAction);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, uninterruptibleVoidAction);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void getSchedulerTest() {
        TaskPool pool = new TaskPool();

        assertNotNull(pool.getScheduler());

        pool.close();
    }

    @Test
    public void createTest() throws Exception {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.create(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.create(id, ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.create(id, ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, id, null, pool.getScheduler(), OPTIONS);
        task = pool.create(id, VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, null, pool.getScheduler(), OPTIONS);
        task = pool.create(id, EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, null, pool.getScheduler(), OPTIONS);
        task = pool.create(id, EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, null, pool.getScheduler(), OPTIONS);

        task = pool.create(id, ACTION);
        TaskTestUtils.validateCreate(task, ACTION, id, null, pool.getScheduler(), null);
        task = pool.create(id, VOID_ACTION);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, null, pool.getScheduler(), null);
        task = pool.create(id, EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, null, pool.getScheduler(), null);
        task = pool.create(id, EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, null, pool.getScheduler(), null);

        task = pool.create(ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.create(ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task,EMPTY_ACTION,  null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.create(ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, null, null, pool.getScheduler(), OPTIONS);
        task = pool.create(VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, null, pool.getScheduler(), OPTIONS);
        task = pool.create(EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, null, pool.getScheduler(), OPTIONS);
        task = pool.create(EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, null, pool.getScheduler(), OPTIONS);

        task = pool.create(ACTION);
        TaskTestUtils.validateCreate(task, ACTION, null, null, pool.getScheduler(), null);
        task = pool.create(VOID_ACTION);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, null, pool.getScheduler(), null);
        task = pool.create(EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, null, pool.getScheduler(), null);
        task = pool.create(EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, null, pool.getScheduler(), null);

        pool.close();
    }

    @Test
    public void createAndStartTest() throws Exception {
        String  id = "";
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.createAndStart(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.createAndStart(id, ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.createAndStart(id, ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(id, EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, null, pool.getScheduler(), OPTIONS);

        task = pool.createAndStart(id, ACTION);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, null, pool.getScheduler(), null);

        task = pool.createAndStart(ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task,EMPTY_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.createAndStart(ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.createAndStart(ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, null, pool.getScheduler(), OPTIONS);
        task = pool.createAndStart(EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, null, pool.getScheduler(), OPTIONS);

        task = pool.createAndStart(ACTION);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, null, pool.getScheduler(), null);
        
        pool.close();
    }

    @Test
    public void whenAllTest() throws Exception {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<String> taskA = new Task<>(() -> "");
        taskA.start();
        Task<String> taskB = new Task<>(() -> "");
        taskB.start();
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<String> task;

        task = pool.whenAll(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.whenAll(id, tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAll(id, tasks, OPTIONS);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, pool.getScheduler(), OPTIONS);
        task = pool.whenAll(id, tasks);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, pool.getScheduler(), null);

        task = pool.whenAll(tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.whenAll(tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAll(tasks, OPTIONS);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, pool.getScheduler(), OPTIONS);
        task = pool.whenAll(tasks);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, pool.getScheduler(), null);
        
        pool.close();
    }

    @Test
    public void whenAnyTest() throws Exception {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<String> taskA = new Task<>(() -> "");
        taskA.start();
        Task<String> taskB = new Task<>(() -> "");
        taskB.start();
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<String> task;

        task = pool.whenAny(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.whenAny(id, tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAny(id, tasks, OPTIONS);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, pool.getScheduler(), OPTIONS);
        task = pool.whenAny(id, tasks);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, pool.getScheduler(), null);

        task = pool.whenAny(tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.whenAny(tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAny(tasks, OPTIONS);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, pool.getScheduler(), OPTIONS);
        task = pool.whenAny(tasks);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, pool.getScheduler(), null);
        
        pool.close();
    }

    @Test
    public void unwrapTest() throws Exception {
        String id = "";
        TaskPool pool = new TaskPool();
        Task<Task<String>> unwrapTask = new Task<>(() -> { Task<String> t = new Task<>(() -> null); t.start(); return t; });
        unwrapTask.start();

        UnwrapTask<String> task;

        task = pool.unwrap(id, unwrapTask, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.unwrap(id, unwrapTask, CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.unwrap(id, unwrapTask, OPTIONS);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, null, pool.getScheduler(), OPTIONS);
        task = pool.unwrap(id, unwrapTask);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, null, pool.getScheduler(), null);

        task = pool.unwrap(unwrapTask, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.unwrap(unwrapTask, CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.unwrap(unwrapTask, OPTIONS);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, null, pool.getScheduler(), OPTIONS);
        task = pool.unwrap(unwrapTask);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, null, pool.getScheduler(), null);

        pool.close();
    }

    @Test
    public void forEachTestTest() throws Exception {
        TaskPool pool = new TaskPool();
        String id = "";
        IParallelAction<String,String> action = (e, t) -> "";
        IParallelVoidAction<String> voidAction = (e, t) -> { };
        IParallelUninterruptibleAction<String,String> uninterruptibleAction = (e) -> "";
        IParallelUninterruptibleVoidAction<String> uninterruptibleVoidAction = (e) -> { };
        Iterable<String> elements = Arrays.asList("","");
        Task<?> task;

        task = pool.forEach(id, elements, action, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, action, id, elements, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.forEach(id, elements, action, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, action, id, elements, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, voidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, voidAction,id, elements, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.forEach(id, elements, action, OPTIONS);
        TaskTestUtils.validateForEach(task, action, id, elements, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, voidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, uninterruptibleAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(id, elements, uninterruptibleVoidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, null, pool.getScheduler(), OPTIONS);

        task = pool.forEach(id, elements, action);
        TaskTestUtils.validateForEach(task, action, id, elements, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, voidAction);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, uninterruptibleAction);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, uninterruptibleVoidAction);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, null, pool.getScheduler(), null);

        task = pool.forEach(elements, action, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, action, null, elements, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, CANCELLATION_TOKEN, pool.getScheduler(), OPTIONS);

        task = pool.forEach(elements, action, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, action, null, elements, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, voidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.forEach(elements, action, OPTIONS);
        TaskTestUtils.validateForEach(task, action, null, elements, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, voidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, uninterruptibleAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, null, pool.getScheduler(), OPTIONS);
        task = pool.forEach(elements, uninterruptibleVoidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, null, pool.getScheduler(), OPTIONS);

        task = pool.forEach(elements, action);
        TaskTestUtils.validateForEach(task, action, null, elements, null, pool.getScheduler(), null);
        task = pool.forEach(elements, voidAction);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, null, pool.getScheduler(), null);
        task = pool.forEach(elements, uninterruptibleAction);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, null, pool.getScheduler(), null);
        task = pool.forEach(elements, uninterruptibleVoidAction);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, null, pool.getScheduler(), null);
    }

}
