package com.github.brunomndantas.tpl4j.task.factory;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.TaskTestUtils;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.action.action.IVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyTask;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public class TaskFactoryTest {

    private static final IAction<String> ACTION = (token) -> null;
    private static final IEmptyAction<String> EMPTY_ACTION = () -> null;
    private static final IVoidAction VOID_ACTION = (token) -> {};
    private static final IEmptyVoidAction EMPTY_VOID_ACTION = () -> {};
    private static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    private static final Option[] OPTIONS = {};
    private static final IScheduler SCHEDULER = new DedicatedThreadScheduler();



    @Test
    public void createTest() throws Exception {
        String id = "";
        Task<?> task;

        task = TaskFactory.create(id, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.create(id, ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreate(task, ACTION, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.create(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.create(id, ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, VOID_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, EMPTY_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, null, SCHEDULER, OPTIONS);

        task = TaskFactory.create(id, ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, ACTION, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.create(id, ACTION, SCHEDULER);
        TaskTestUtils.validateCreate(task, ACTION, id, null, SCHEDULER, null);
        task = TaskFactory.create(id, VOID_ACTION, SCHEDULER);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, null, SCHEDULER, null);
        task = TaskFactory.create(id, EMPTY_ACTION, SCHEDULER);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, null, SCHEDULER, null);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, SCHEDULER);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, null, SCHEDULER, null);

        task = TaskFactory.create(id, ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, id, null, null, OPTIONS);
        task = TaskFactory.create(id, VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, null, null, OPTIONS);
        task = TaskFactory.create(id, EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, null, null, OPTIONS);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, null, null, OPTIONS);

        task = TaskFactory.create(id, ACTION);
        TaskTestUtils.validateCreate(task, ACTION, id, null, null, null);
        task = TaskFactory.create(id, VOID_ACTION);
        TaskTestUtils.validateCreate(task, VOID_ACTION, id, null, null, null);
        task = TaskFactory.create(id, EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, id, null, null, null);
        task = TaskFactory.create(id, EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, id, null, null, null);

        task = TaskFactory.create(ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.create(ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreate(task, ACTION, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.create(ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.create(ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(VOID_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(EMPTY_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.create(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, null, SCHEDULER, OPTIONS);

        task = TaskFactory.create(ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, ACTION, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.create(ACTION, SCHEDULER);
        TaskTestUtils.validateCreate(task, ACTION, null, null, SCHEDULER, null);
        task = TaskFactory.create(VOID_ACTION, SCHEDULER);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, null, SCHEDULER, null);
        task = TaskFactory.create(EMPTY_ACTION, SCHEDULER);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, null, SCHEDULER, null);
        task = TaskFactory.create(EMPTY_VOID_ACTION, SCHEDULER);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, null, SCHEDULER, null);

        task = TaskFactory.create(ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, ACTION, null, null, null, OPTIONS);
        task = TaskFactory.create(VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, null, null, OPTIONS);
        task = TaskFactory.create(EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, null, null, OPTIONS);
        task = TaskFactory.create(EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, null, null, OPTIONS);

        task = TaskFactory.create(ACTION);
        TaskTestUtils.validateCreate(task, ACTION, null, null, null, null);
        task = TaskFactory.create(VOID_ACTION);
        TaskTestUtils.validateCreate(task, VOID_ACTION, null, null, null, null);
        task = TaskFactory.create(EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_ACTION, null, null, null, null);
        task = TaskFactory.create(EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, EMPTY_VOID_ACTION, null, null, null, null);
    }
    
    @Test
    public void createAndStartTest() throws Exception {
        String id = "";
        Task<?> task;

        task = TaskFactory.createAndStart(id, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.createAndStart(id, ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.createAndStart(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.createAndStart(id, ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, VOID_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, null, SCHEDULER, OPTIONS);

        task = TaskFactory.createAndStart(id, ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.createAndStart(id, ACTION, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, VOID_ACTION, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, null, SCHEDULER, null);

        task = TaskFactory.createAndStart(id, ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, null, null, OPTIONS);
        task = TaskFactory.createAndStart(id, VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, null, null, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, null, null, OPTIONS);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, null, null, OPTIONS);

        task = TaskFactory.createAndStart(id, ACTION);
        TaskTestUtils.validateCreateAndStart(task, ACTION, id, null, null, null);
        task = TaskFactory.createAndStart(id, VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, id, null, null, null);
        task = TaskFactory.createAndStart(id, EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, id, null, null, null);
        task = TaskFactory.createAndStart(id, EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, id, null, null, null);

        task = TaskFactory.createAndStart(ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.createAndStart(ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.createAndStart(ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.createAndStart(ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(VOID_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, null, SCHEDULER, OPTIONS);

        task = TaskFactory.createAndStart(ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(EMPTY_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.createAndStart(ACTION, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(VOID_ACTION, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(EMPTY_ACTION, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, null, SCHEDULER, null);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, null, SCHEDULER, null);

        task = TaskFactory.createAndStart(ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, null, null, OPTIONS);
        task = TaskFactory.createAndStart(VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, null, null, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, null, null, OPTIONS);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION, OPTIONS);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, null, null, OPTIONS);

        task = TaskFactory.createAndStart(ACTION);
        TaskTestUtils.validateCreateAndStart(task, ACTION, null, null, null, null);
        task = TaskFactory.createAndStart(VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, VOID_ACTION, null, null, null, null);
        task = TaskFactory.createAndStart(EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_ACTION, null, null, null, null);
        task = TaskFactory.createAndStart(EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, EMPTY_VOID_ACTION, null, null, null, null);
    }

    @Test
    public void whenAllTest() throws Exception {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        taskA.start();
        Task<String> taskB = new Task<>(() -> "");
        taskB.start();
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAllTask<?> task;

        task = TaskFactory.whenAll(id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAll(id, tasks, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.whenAll(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.whenAll(id, tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, id, tasks, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAll(id, tasks, SCHEDULER, OPTIONS);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAll(id, tasks, SCHEDULER);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, SCHEDULER, null);
        task = TaskFactory.whenAll(id, tasks, OPTIONS);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, null, OPTIONS);
        task = TaskFactory.whenAll(id, tasks);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, null, null);

        task = TaskFactory.whenAll(tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAll(tasks, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.whenAll(tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.whenAll(tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, null, tasks, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAll(tasks, SCHEDULER, OPTIONS);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAll(tasks, SCHEDULER);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, SCHEDULER, null);
        task = TaskFactory.whenAll(tasks, OPTIONS);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, null, OPTIONS);
        task = TaskFactory.whenAll(tasks);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, null, null);
    }

    @Test
    public void whenAnyTest() throws Exception {
        String id = "";
        Task<String> taskA = new Task<>(() -> "");
        taskA.start();
        Task<String> taskB = new Task<>(() -> "");
        taskB.start();
        Collection<Task<String>> tasks = Arrays.asList(taskA, taskB);

        WhenAnyTask<?> task;

        task = TaskFactory.whenAny(id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAny(id, tasks, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.whenAny(id, tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.whenAny(id, tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, id, tasks, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAny(id, tasks, SCHEDULER, OPTIONS);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAny(id, tasks, SCHEDULER);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, SCHEDULER, null);
        task = TaskFactory.whenAny(id, tasks, OPTIONS);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, null, OPTIONS);
        task = TaskFactory.whenAny(id, tasks);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, null, null);

        task = TaskFactory.whenAny(tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAny(tasks, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.whenAny(tasks, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.whenAny(tasks, CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, null, tasks, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAny(tasks, SCHEDULER, OPTIONS);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, SCHEDULER, OPTIONS);
        task = TaskFactory.whenAny(tasks, SCHEDULER);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, SCHEDULER, null);
        task = TaskFactory.whenAny(tasks, OPTIONS);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, null, OPTIONS);
        task = TaskFactory.whenAny(tasks);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, null, null);
    }

    @Test
    public void unwrapTest() throws Exception {
        String id = "";
        Task<Task<String>> taskToUnwrap = new Task<>(() -> { Task<String> t = new Task<>(() -> null); t.start(); return t; });
        taskToUnwrap.start();

        UnwrapTask<?> task;
        
        task = TaskFactory.unwrap(id, taskToUnwrap, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.unwrap(id, taskToUnwrap, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.unwrap(id, taskToUnwrap, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.unwrap(id, taskToUnwrap, CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.unwrap(id, taskToUnwrap, SCHEDULER, OPTIONS);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, null, SCHEDULER, OPTIONS);
        task = TaskFactory.unwrap(id, taskToUnwrap, SCHEDULER);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, null, SCHEDULER, null);
        task = TaskFactory.unwrap(id, taskToUnwrap, OPTIONS);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, null, null, OPTIONS);
        task = TaskFactory.unwrap(id, taskToUnwrap);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, null, null, null);

        task = TaskFactory.unwrap(taskToUnwrap, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.unwrap(taskToUnwrap, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.unwrap(taskToUnwrap, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.unwrap(taskToUnwrap, CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.unwrap(taskToUnwrap, SCHEDULER, OPTIONS);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, null, SCHEDULER, OPTIONS);
        task = TaskFactory.unwrap(taskToUnwrap, SCHEDULER);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, null, SCHEDULER, null);
        task = TaskFactory.unwrap(taskToUnwrap, OPTIONS);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, null, null, OPTIONS);
        task = TaskFactory.unwrap(taskToUnwrap);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, null, null, null);
    }

    @Test
    public void forEachTest() throws Exception {
        String id = "";
        IParallelAction<String,String> action = (e, t) -> "";
        IParallelVoidAction<String> voidAction = (e, t) -> { };
        IParallelUninterruptibleAction<String,String> uninterruptibleAction = (e) -> "";
        IParallelUninterruptibleVoidAction<String> uninterruptibleVoidAction = (e) -> { };
        Iterable<String> elements = Arrays.asList("","");
        ParallelTask<String,?> task;

        task = TaskFactory.forEach(id, elements, action, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, action, id, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, voidAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.forEach(id, elements, action, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateForEach(task, action, id, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, voidAction, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.forEach(id, elements, action, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, action, id, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.forEach(id, elements, action, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, action, id, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, voidAction, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, null, SCHEDULER, OPTIONS);

        task = TaskFactory.forEach(id, elements, action, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, action, id, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, voidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.forEach(id, elements, action, SCHEDULER);
        TaskTestUtils.validateForEach(task, action, id, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, voidAction, SCHEDULER);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, SCHEDULER);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, SCHEDULER);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, null, SCHEDULER, null);

        task = TaskFactory.forEach(id, elements, action, OPTIONS);
        TaskTestUtils.validateForEach(task, action, id, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, voidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, null, null, OPTIONS);

        task = TaskFactory.forEach(id, elements, action);
        TaskTestUtils.validateForEach(task, action, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, voidAction);
        TaskTestUtils.validateForEach(task, voidAction, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleAction);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, uninterruptibleVoidAction);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, id, elements, null, null, null);

        task = TaskFactory.forEach(elements, action, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, action, null, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, voidAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = TaskFactory.forEach(elements, action, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateForEach(task, action, null, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(elements, voidAction, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, CANCELLATION_TOKEN, SCHEDULER, null);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, SCHEDULER);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, CANCELLATION_TOKEN, SCHEDULER, null);

        task = TaskFactory.forEach(elements, action, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, action, null, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(elements, voidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, CANCELLATION_TOKEN, null, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, CANCELLATION_TOKEN, null, OPTIONS);

        task = TaskFactory.forEach(elements, action, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, action, null, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, voidAction, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleAction, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, null, SCHEDULER, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, SCHEDULER, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, null, SCHEDULER, OPTIONS);

        task = TaskFactory.forEach(elements, action, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, action, null, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, voidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, uninterruptibleAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, CANCELLATION_TOKEN, null, null);

        task = TaskFactory.forEach(elements, action, SCHEDULER);
        TaskTestUtils.validateForEach(task, action, null, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(elements, voidAction, SCHEDULER);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(elements, uninterruptibleAction, SCHEDULER);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, null, SCHEDULER, null);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, SCHEDULER);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, null, SCHEDULER, null);

        task = TaskFactory.forEach(elements, action, OPTIONS);
        TaskTestUtils.validateForEach(task, action, null, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(elements, voidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, null, null, OPTIONS);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction, OPTIONS);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, null, null, OPTIONS);

        task = TaskFactory.forEach(elements, action);
        TaskTestUtils.validateForEach(task, action, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, voidAction);
        TaskTestUtils.validateForEach(task, voidAction, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, uninterruptibleAction);
        TaskTestUtils.validateForEach(task, uninterruptibleAction, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, uninterruptibleVoidAction);
        TaskTestUtils.validateForEach(task, uninterruptibleVoidAction, null, elements, null, null, null);
    }

}
