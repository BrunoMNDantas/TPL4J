package com.github.brunomndantas.tpl4j.task.pool;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyTask;
import com.github.brunomndantas.tpl4j.transversal.TaskTestUtils;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.AfterClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertNotNull;

public class TaskPoolTest {

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

        task = TaskPool.createTask(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.createTask(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(id, TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(id, TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(id, TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.createTask(id, TestUtils.ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(id, TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.createTask(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createTask(TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createTask(TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.createTask(TestUtils.ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createTask(TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticCreateAndStartTest() throws Exception {
        String  id = "";
        Task<?> task;

        task = TaskPool.createAndStartTask(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.createAndStartTask(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(id, TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(id, TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(id, TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.createAndStartTask(id, TestUtils.ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(id, TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.createAndStartTask(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.createAndStartTask(TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.createAndStartTask(TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.createAndStartTask(TestUtils.ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.createAndStartTask(TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, null, TaskPool.getTaskScheduler(), null);
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
        
        task = TaskPool.whenAllTask(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, id, tasks, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.whenAllTask(id, tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, id, tasks, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAllTask(id, tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.whenAllTask(id, tasks);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.whenAllTask(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, null, tasks, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.whenAllTask(tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, null, tasks, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAllTask(tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
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
        
        task = TaskPool.whenAnyTask(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, id, tasks, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.whenAnyTask(id, tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, id, tasks, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAnyTask(id, tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.whenAnyTask(id, tasks);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.whenAnyTask(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, null, tasks, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.whenAnyTask(tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, null, tasks, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.whenAnyTask(tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.whenAnyTask(tasks);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticUnwrapTest() throws Exception {
        String id = "";
        Task<Task<String>> unwrapTask = new Task<>(() -> { Task<String> t = new Task<>(() -> null); t.start(); return t; });
        unwrapTask.start();

        UnwrapTask<String> task;
        
        task = TaskPool.unwrapTask(id, unwrapTask, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.unwrapTask(id, unwrapTask, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.unwrapTask(id, unwrapTask, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.unwrapTask(id, unwrapTask);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.unwrapTask(unwrapTask, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.unwrapTask(unwrapTask, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.unwrapTask(unwrapTask, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.unwrapTask(unwrapTask);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, null, TaskPool.getTaskScheduler(), null);
    }

    @Test
    public void staticForEachTestTest() throws Exception {
        String id = "";
        Iterable<String> elements = Arrays.asList("","");
        Task<?> task;

        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, null, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task,TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION,  null, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TaskPool.getTaskScheduler(), null);

        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, null, TaskPool.getTaskScheduler(), TestUtils.OPTIONS_ARRAY);

        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, null, TaskPool.getTaskScheduler(), null);
        task = TaskPool.forEachTask(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, null, TaskPool.getTaskScheduler(), null);
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

        task = pool.create(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.create(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.create(id, TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(id, TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(id, TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.create(id, TestUtils.ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, null, pool.getScheduler(), null);
        task = pool.create(id, TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, null, pool.getScheduler(), null);
        task = pool.create(id, TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, null, pool.getScheduler(), null);
        task = pool.create(id, TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, null, pool.getScheduler(), null);

        task = pool.create(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.create(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task,TestUtils.EMPTY_ACTION,  null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.create(TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.create(TestUtils.ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, null, pool.getScheduler(), null);
        task = pool.create(TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, null, pool.getScheduler(), null);
        task = pool.create(TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, null, pool.getScheduler(), null);
        task = pool.create(TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, null, pool.getScheduler(), null);

        pool.close();
    }

    @Test
    public void createAndStartTest() throws Exception {
        String  id = "";
        TaskPool pool = new TaskPool();
        Task<?> task;

        task = pool.createAndStart(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.createAndStart(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.createAndStart(id, TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.createAndStart(id, TestUtils.ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, null, pool.getScheduler(), null);
        task = pool.createAndStart(id, TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, null, pool.getScheduler(), null);

        task = pool.createAndStart(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task,TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.createAndStart(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.createAndStart(TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.createAndStart(TestUtils.ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, null, pool.getScheduler(), null);
        task = pool.createAndStart(TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, null, pool.getScheduler(), null);
        
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

        task = pool.whenAll(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, id, tasks, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.whenAll(id, tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, id, tasks, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAll(id, tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.whenAll(id, tasks);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, pool.getScheduler(), null);

        task = pool.whenAll(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, null, tasks, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.whenAll(tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, null, tasks, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAll(tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
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

        task = pool.whenAny(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, id, tasks, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.whenAny(id, tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, id, tasks, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAny(id, tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.whenAny(id, tasks);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, pool.getScheduler(), null);

        task = pool.whenAny(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, null, tasks, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.whenAny(tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, null, tasks, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.whenAny(tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
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

        task = pool.unwrap(id, unwrapTask, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.unwrap(id, unwrapTask, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.unwrap(id, unwrapTask, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.unwrap(id, unwrapTask);
        TaskTestUtils.validateUnwrap(task, unwrapTask, id, null, pool.getScheduler(), null);

        task = pool.unwrap(unwrapTask, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.unwrap(unwrapTask, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.unwrap(unwrapTask, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.unwrap(unwrapTask);
        TaskTestUtils.validateUnwrap(task, unwrapTask, null, null, pool.getScheduler(), null);

        pool.close();
    }

    @Test
    public void forEachTestTest() throws Exception {
        TaskPool pool = new TaskPool();
        String id = "";
        Iterable<String> elements = Arrays.asList("","");
        Task<?> task;

        task = pool.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION,id, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.forEach(id, elements, TestUtils.PARALLEL_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, null, pool.getScheduler(), null);
        task = pool.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, null, pool.getScheduler(), null);

        task = pool.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);
        task = pool.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, pool.getScheduler(), null);

        task = pool.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);
        task = pool.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, null, pool.getScheduler(), TestUtils.OPTIONS_ARRAY);

        task = pool.forEach(elements, TestUtils.PARALLEL_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, null, pool.getScheduler(), null);
        task = pool.forEach(elements, TestUtils.PARALLEL_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, null, pool.getScheduler(), null);
        task = pool.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, null, pool.getScheduler(), null);
        task = pool.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, null, pool.getScheduler(), null);
    }

}
