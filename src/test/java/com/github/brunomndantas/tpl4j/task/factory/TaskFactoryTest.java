package com.github.brunomndantas.tpl4j.task.factory;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyTask;
import com.github.brunomndantas.tpl4j.transversal.TaskTestUtils;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;

public class TaskFactoryTest {

    @Test
    public void createTest() throws Exception {
        String id = "";
        Task<?> task;

        task = TaskFactory.create(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.create(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        task = TaskFactory.create(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.create(id, TestUtils.ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.create(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.create(id, TestUtils.ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(id, TestUtils.VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(id, TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, null, TestUtils.SCHEDULER, null);

        task = TaskFactory.create(id, TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, null, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.create(id, TestUtils.ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, id, null, null, null);
        task = TaskFactory.create(id, TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, id, null, null, null);
        task = TaskFactory.create(id, TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, id, null, null, null);
        task = TaskFactory.create(id, TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, id, null, null, null);

        task = TaskFactory.create(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.create(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        task = TaskFactory.create(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.create(TestUtils.ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.create(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.create(TestUtils.ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(TestUtils.VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, null, TestUtils.SCHEDULER, null);

        task = TaskFactory.create(TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.create(TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, null, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.create(TestUtils.ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.ACTION, null, null, null, null);
        task = TaskFactory.create(TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.VOID_ACTION, null, null, null, null);
        task = TaskFactory.create(TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_ACTION, null, null, null, null);
        task = TaskFactory.create(TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreate(task, TestUtils.EMPTY_VOID_ACTION, null, null, null, null);
    }
    
    @Test
    public void createAndStartTest() throws Exception {
        String id = "";
        Task<?> task;

        task = TaskFactory.createAndStart(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.createAndStart(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        task = TaskFactory.createAndStart(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.createAndStart(id, TestUtils.ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.createAndStart(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.createAndStart(id, TestUtils.ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, null, TestUtils.SCHEDULER, null);

        task = TaskFactory.createAndStart(id, TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, null, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.createAndStart(id, TestUtils.ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, id, null, null, null);
        task = TaskFactory.createAndStart(id, TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, id, null, null, null);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, id, null, null, null);
        task = TaskFactory.createAndStart(id, TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, id, null, null, null);

        task = TaskFactory.createAndStart(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.createAndStart(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        task = TaskFactory.createAndStart(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.createAndStart(TestUtils.ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.createAndStart(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.createAndStart(TestUtils.ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(TestUtils.VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, null, TestUtils.SCHEDULER, null);

        task = TaskFactory.createAndStart(TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, null, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.createAndStart(TestUtils.ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.ACTION, null, null, null, null);
        task = TaskFactory.createAndStart(TestUtils.VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.VOID_ACTION, null, null, null, null);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_ACTION, null, null, null, null);
        task = TaskFactory.createAndStart(TestUtils.EMPTY_VOID_ACTION);
        TaskTestUtils.validateCreateAndStart(task, TestUtils.EMPTY_VOID_ACTION, null, null, null, null);
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

        task = TaskFactory.whenAll(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAll(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateWhenAll(task, id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.whenAll(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, id, tasks, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAll(id, tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, id, tasks, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAll(id, tasks, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAll(id, tasks, TestUtils.SCHEDULER);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.whenAll(id, tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAll(id, tasks);
        TaskTestUtils.validateWhenAll(task, id, tasks, null, null, null);

        task = TaskFactory.whenAll(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, null, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAll(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateWhenAll(task, null, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.whenAll(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, null, tasks, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAll(tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAll(task, null, tasks, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAll(tasks, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAll(tasks, TestUtils.SCHEDULER);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.whenAll(tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAll(task, null, tasks, null, null, TestUtils.OPTIONS_ARRAY);
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

        task = TaskFactory.whenAny(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAny(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateWhenAny(task, id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.whenAny(id, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, id, tasks, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAny(id, tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, id, tasks, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAny(id, tasks, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAny(id, tasks, TestUtils.SCHEDULER);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.whenAny(id, tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAny(id, tasks);
        TaskTestUtils.validateWhenAny(task, id, tasks, null, null, null);

        task = TaskFactory.whenAny(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, null, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAny(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateWhenAny(task, null, tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.whenAny(tasks, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, null, tasks, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAny(tasks, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateWhenAny(task, null, tasks, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.whenAny(tasks, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAny(tasks, TestUtils.SCHEDULER);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.whenAny(tasks, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.whenAny(tasks);
        TaskTestUtils.validateWhenAny(task, null, tasks, null, null, null);
    }

    @Test
    public void unwrapTest() throws Exception {
        String id = "";
        Task<Task<String>> taskToUnwrap = new Task<>(() -> { Task<String> t = new Task<>(() -> null); t.start(); return t; });
        taskToUnwrap.start();

        UnwrapTask<?> task;
        
        task = TaskFactory.unwrap(id, taskToUnwrap, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.unwrap(id, taskToUnwrap, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.unwrap(id, taskToUnwrap, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.unwrap(id, taskToUnwrap, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.unwrap(id, taskToUnwrap, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.unwrap(id, taskToUnwrap, TestUtils.SCHEDULER);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.unwrap(id, taskToUnwrap, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.unwrap(id, taskToUnwrap);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, id, null, null, null);

        task = TaskFactory.unwrap(taskToUnwrap, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.unwrap(taskToUnwrap, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.unwrap(taskToUnwrap, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.unwrap(taskToUnwrap, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.unwrap(taskToUnwrap, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.unwrap(taskToUnwrap, TestUtils.SCHEDULER);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.unwrap(taskToUnwrap, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.unwrap(taskToUnwrap);
        TaskTestUtils.validateUnwrap(task, taskToUnwrap, null, null, null, null);
    }

    @Test
    public void forEachTest() throws Exception {
        String id = "";
        Iterable<String> elements = Arrays.asList("","");
        ParallelTask<String,?> task;

        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, null, TestUtils.SCHEDULER, null);

        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, null, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, id, elements, null, null, null);
        task = TaskFactory.forEach(id, elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, id, elements, null, null, null);

        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, null, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, TestUtils.CANCELLATION_TOKEN, null, null);

        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, null, TestUtils.SCHEDULER, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.SCHEDULER);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, null, TestUtils.SCHEDULER, null);

        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, null, null, TestUtils.OPTIONS_ARRAY);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, null, null, TestUtils.OPTIONS_ARRAY);

        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_ACTION, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_VOID_ACTION, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_ACTION, null, elements, null, null, null);
        task = TaskFactory.forEach(elements, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION);
        TaskTestUtils.validateForEach(task, TestUtils.PARALLEL_UNINTERRUPTIBLE_VOID_ACTION, null, elements, null, null, null);
    }

}
