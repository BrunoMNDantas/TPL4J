package com.github.brunomndantas.tpl4j.task;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.builder.ContextBuilder;
import com.github.brunomndantas.tpl4j.context.executor.ContextExecutor;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.action.action.*;
import com.github.brunomndantas.tpl4j.task.action.link.*;
import com.github.brunomndantas.tpl4j.task.action.retry.RetryAction;
import com.github.brunomndantas.tpl4j.transversal.TaskTestUtils;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

public class TaskTest {
    
    @Test
    public void getContextTest() {
        Context<String> context = new Context<>("", TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS, null, null, null, 0, 0, null, null);
        Task<String> task = new Task<>(context, null, null, null);
        assertSame(context, task.getContext());
    }

    @Test
    public void getContextManagerTest() {
        ContextManager contextManager = new ContextManager();
        Task<String> task = new Task<>(null, contextManager, null, null);
        assertSame(contextManager, task.getContextManager());
    }

    @Test
    public void getContextBuilderTest() {
        ContextBuilder contextBuilder = new ContextBuilder(null);
        Task<String> task = new Task<>(null, null, contextBuilder, null);
        assertSame(contextBuilder, task.getContextBuilder());
    }

    @Test
    public void getContextExecutorTest() {
        ContextExecutor contextExecutor = new ContextExecutor(null);
        Task<String> task = new Task<>(null, null, null, contextExecutor);
        assertSame(contextExecutor, task.getContextExecutor());
    }
    
    @Test
    public void constructorsTest() throws Exception {
        String id = "";
        Task<?> task;

        task = new Task<>(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = new Task<>(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateConstructor(task, id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = new Task<>(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateConstructor(task, id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = new Task<>(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateConstructor(task, id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = new Task<>(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateConstructor(task, id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        task = new Task<>(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        task = new Task<>(id, TestUtils.ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.ACTION, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.VOID_ACTION, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.EMPTY_ACTION, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = new Task<>(id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        validateConstructor(task, id, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateConstructor(task, id, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateConstructor(task, id, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateConstructor(task, id, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, null, null);

        task = new Task<>(id, TestUtils.ACTION, TestUtils.SCHEDULER);
        validateConstructor(task, id, TestUtils.ACTION, null, TestUtils.SCHEDULER, null);
        task = new Task<>(id, TestUtils.VOID_ACTION, TestUtils.SCHEDULER);
        validateConstructor(task, id, TestUtils.VOID_ACTION, null, TestUtils.SCHEDULER, null);
        task = new Task<>(id, TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER);
        validateConstructor(task, id, TestUtils.EMPTY_ACTION, null, TestUtils.SCHEDULER, null);
        task = new Task<>(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER);
        validateConstructor(task, id, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.SCHEDULER, null);

        task = new Task<>(id, TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.ACTION, null, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.VOID_ACTION, null, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.EMPTY_ACTION, null, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(id, TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, id, TestUtils.EMPTY_VOID_ACTION, null, null, TestUtils.OPTIONS_ARRAY);

        task = new Task<>(id, TestUtils.ACTION);
        validateConstructor(task, id, TestUtils.ACTION, null, null, null);
        task = new Task<>(id, TestUtils.VOID_ACTION);
        validateConstructor(task, id, TestUtils.VOID_ACTION, null, null, null);
        task = new Task<>(id, TestUtils.EMPTY_ACTION);
        validateConstructor(task, id, TestUtils.EMPTY_ACTION, null, null, null);
        task = new Task<>(id, TestUtils.EMPTY_VOID_ACTION);
        validateConstructor(task, id, TestUtils.EMPTY_VOID_ACTION, null, null, null);

        task = new Task<>(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = new Task<>(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateConstructor(task, null, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = new Task<>(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateConstructor(task, null, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = new Task<>(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateConstructor(task, null, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        task = new Task<>(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateConstructor(task, null, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        task = new Task<>(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        task = new Task<>(TestUtils.ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.ACTION, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.VOID_ACTION, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.EMPTY_ACTION, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        task = new Task<>(TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN);
        validateConstructor(task, null, TestUtils.ACTION, TestUtils.CANCELLATION_TOKEN, null, null);
        task = new Task<>(TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateConstructor(task, null, TestUtils.VOID_ACTION, TestUtils.CANCELLATION_TOKEN, null, null);
        task = new Task<>(TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateConstructor(task, null, TestUtils.EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, null, null);
        task = new Task<>(TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateConstructor(task, null, TestUtils.EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, null, null);

        task = new Task<>(TestUtils.ACTION, TestUtils.SCHEDULER);
        validateConstructor(task, null, TestUtils.ACTION, null, TestUtils.SCHEDULER, null);
        task = new Task<>(TestUtils.VOID_ACTION, TestUtils.SCHEDULER);
        validateConstructor(task, null, TestUtils.VOID_ACTION, null, TestUtils.SCHEDULER, null);
        task = new Task<>(TestUtils.EMPTY_ACTION, TestUtils.SCHEDULER);
        validateConstructor(task, null, TestUtils.EMPTY_ACTION, null, TestUtils.SCHEDULER, null);
        task = new Task<>(TestUtils.EMPTY_VOID_ACTION, TestUtils.SCHEDULER);
        validateConstructor(task, null, TestUtils.EMPTY_VOID_ACTION, null, TestUtils.SCHEDULER, null);

        task = new Task<>(TestUtils.ACTION, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.ACTION, null, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.VOID_ACTION, null, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.EMPTY_ACTION, null, null, TestUtils.OPTIONS_ARRAY);
        task = new Task<>(TestUtils.EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        validateConstructor(task, null, TestUtils.EMPTY_VOID_ACTION, null, null, TestUtils.OPTIONS_ARRAY);

        task = new Task<>(TestUtils.ACTION);
        validateConstructor(task, null, TestUtils.ACTION, null, null, null);
        task = new Task<>(TestUtils.VOID_ACTION);
        validateConstructor(task, null, TestUtils.VOID_ACTION, null, null, null);
        task = new Task<>(TestUtils.EMPTY_ACTION);
        validateConstructor(task, null, TestUtils.EMPTY_ACTION, null, null, null);
        task = new Task<>(TestUtils.EMPTY_VOID_ACTION);
        validateConstructor(task, null, TestUtils.EMPTY_VOID_ACTION, null, null, null);
    }

    private void validateConstructor(Task<?> task, String id, IAction<?> action, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        IContext<?> template = TaskTestUtils.createTemplate(id, action, cancellationToken, scheduler, options, State.CREATED);
        TaskTestUtils.validateCreation(task, template);

        task.start();
        template.getStatus().setState(State.SUCCEEDED);
        TaskTestUtils.validateExecution(task, template);
    }

    private void validateConstructor(Task<?> task, String id, IVoidAction action, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateConstructor(task, id, new VoidAction(action), cancellationToken, scheduler, options);
    }

    private void validateConstructor(Task<?> task, String id, IEmptyAction<?> action, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateConstructor(task, id, new EmptyAction<>(action), cancellationToken, scheduler, options);
    }

    private void validateConstructor(Task<?> task, String id, IEmptyVoidAction action, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateConstructor(task, id, new EmptyVoidAction(action), cancellationToken, scheduler, options);
    }

    @Test
    public void getIdTest() {
        String id = UUID.randomUUID().toString();
        Task<String> task = new Task<>(id, TestUtils.SUCCESS_ACTION);
        assertSame(id, task.getId());
    }

    @Test
    public void getActionTest() {
        IAction<String> action = TestUtils.SUCCESS_ACTION;
        Task<String> task = new Task<>(action);
        assertSame(action, task.getAction());
    }

    @Test
    public void getCancellationTokenTest() {
        CancellationToken cancellationToken = new CancellationToken();
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION, cancellationToken);
        assertSame(cancellationToken, task.getCancellationToken());
    }

    @Test
    public void getSchedulerTest() {
        IScheduler scheduler = TestUtils.SCHEDULER;
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION, scheduler);
        assertSame(scheduler, task.getScheduler());
    }

    @Test
    public void getOptionsTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getOptions(), task.getOptions());
    }

    @Test
    public void getStatusTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus(), task.getStatus());
    }

    @Test
    public void getResultValueTest() throws Exception {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        task.start();
        task.getContext().getStatus().getFinishedEvent().await();
        assertSame(task.getContext().getResultValue(), task.getResultValue());
    }

    @Test
    public void getResultExceptionTest() throws Exception {
        Task<String> task = new Task<>(TestUtils.FAIL_ACTION);
        task.start();
        task.getContext().getStatus().getFinishedEvent().await();
        assertSame(task.getContext().getResultException(), task.getResultException());
    }

    @Test
    public void getStateTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getState(), task.getState());
    }

    @Test
    public void getScheduledEventTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getScheduledEvent(), task.getScheduledEvent());
    }

    @Test
    public void getRunningEventTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getRunningEvent(), task.getRunningEvent());
    }

    @Test
    public void getWaitingForChildrenEventTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getWaitingForChildrenEvent(), task.getWaitingForChildrenEvent());
    }

    @Test
    public void getCancelledEventTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getCancelledEvent(), task.getCancelledEvent());
    }

    @Test
    public void getFailedEventTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getFailedEvent(), task.getFailedEvent());
    }

    @Test
    public void getSucceededEventTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getSucceededEvent(), task.getSucceededEvent());
    }

    @Test
    public void getFinishedEventTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getFinishedEvent(), task.getFinishedEvent());
    }
    
    @Test
    public void startTest() throws InterruptedException {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);

        task.start();

        assertTrue(task.context.getStatus().getScheduledEvent().hasFired());

        task.context.getStatus().getSucceededEvent().await();

        assertSame(TestUtils.SUCCESS_RESULT, task.context.getResultValue());
    }

    @Test
    public void startTwiceTest() {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);

        task.start();

        try {
            task.start();
            fail("Exception should be thrown!");
        } catch (RuntimeException e) {
            assertTrue(e.getMessage().contains("already scheduled"));
        }
    }

    @Test
    public void cancelTest() throws InterruptedException {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);

        task.cancel();

        task.start();

        assertTrue(task.context.getCancellationToken().hasCancelRequest());

        task.context.getStatus().getCancelledEvent().await();
    }

    @Test
    public void getSuccessResultTest() throws Exception {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);

        task.start();

        assertSame(TestUtils.SUCCESS_RESULT, task.getResult());
    }

    @Test
    public void getFailResultTest() {
        Task<String> task = new Task<>(TestUtils.FAIL_ACTION);

        task.start();

        try {
            task.getResult();
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(TestUtils.FAIL_RESULT, e);
        }
    }

    @Test
    public void getCancelResultTest() {
        Task<String> task = new Task<>(TestUtils.CANCEL_ACTION);

        task.start();

        try {
            task.getResult();
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertTrue(e instanceof CancelledException);
        }
    }

    @Test
    public void thenTaskTest() throws InterruptedException {
        Task<?> task = new Task<>(TestUtils.ACTION);
        Task<Boolean> thenTask = new Task<>(() -> task.context.getStatus().getFinishedEvent().hasFired());

        assertSame(thenTask, task.then(thenTask));

        task.start();

        thenTask.context.getStatus().getSucceededEvent().await();
        assertTrue(thenTask.context.getResultValue());
    }

    @Test
    public void thenTest() throws Exception {
        String id = "";
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];
        Task<String> task = new Task<>(TestUtils.ACTION, scheduler, options);
        task.start();
        Task<?> thenTask;

        thenTask = task.then(id, TestUtils.LINK_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        thenTask = task.then(id, TestUtils.LINK_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        thenTask = task.then(id, TestUtils.LINK_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        thenTask = task.then(id, TestUtils.LINK_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        thenTask = task.then(id, TestUtils.LINK_ACTION, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, id, null, TestUtils.SCHEDULER, null);
        thenTask = task.then(id, TestUtils.LINK_VOID_ACTION, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, id, null, TestUtils.SCHEDULER, null);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_ACTION, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, id, null, TestUtils.SCHEDULER, null);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, id, null, TestUtils.SCHEDULER, null);

        thenTask = task.then(id, TestUtils.LINK_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        thenTask = task.then(id, TestUtils.LINK_ACTION, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, id, null, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, id, null, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, id, null, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, id, null, null, TestUtils.OPTIONS_ARRAY);

        thenTask = task.then(id, TestUtils.LINK_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, TestUtils.LINK_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, id, TestUtils.CANCELLATION_TOKEN, null, null);

        thenTask = task.then(id, TestUtils.LINK_ACTION);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, id, null, null, null);
        thenTask = task.then(id, TestUtils.LINK_VOID_ACTION);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, id, null, null, null );
        thenTask = task.then(id, TestUtils.LINK_EMPTY_ACTION);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, id, null, null, null);
        thenTask = task.then(id, TestUtils.LINK_EMPTY_VOID_ACTION);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, id, null, null, null);

        thenTask = task.then(TestUtils.LINK_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        thenTask = task.then(TestUtils.LINK_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_EMPTY_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        thenTask = task.then(TestUtils.LINK_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        thenTask = task.then(TestUtils.LINK_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        thenTask = task.then(TestUtils.LINK_EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        thenTask = task.then(TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        thenTask = task.then(TestUtils.LINK_ACTION, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, null, null, TestUtils.SCHEDULER, null);
        thenTask = task.then(TestUtils.LINK_VOID_ACTION, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, null, null, TestUtils.SCHEDULER, null);
        thenTask = task.then(TestUtils.LINK_EMPTY_ACTION, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, null, null, TestUtils.SCHEDULER, null);
        thenTask = task.then(TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.SCHEDULER);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, null, null, TestUtils.SCHEDULER, null);

        thenTask = task.then(TestUtils.LINK_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        thenTask = task.then(TestUtils.LINK_ACTION, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, null, null, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, null, null, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_EMPTY_ACTION, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, null, null, null, TestUtils.OPTIONS_ARRAY);
        thenTask = task.then(TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.OPTIONS_ARRAY);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, null, null, null, TestUtils.OPTIONS_ARRAY);

        thenTask = task.then(TestUtils.LINK_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, null, null);
        thenTask = task.then(TestUtils.LINK_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, null, null);
        thenTask = task.then(TestUtils.LINK_EMPTY_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, null, null);
        thenTask = task.then(TestUtils.LINK_EMPTY_VOID_ACTION, TestUtils.CANCELLATION_TOKEN);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, null, TestUtils.CANCELLATION_TOKEN, null, null);

        thenTask = task.then(TestUtils.LINK_ACTION);
        validateThen(thenTask, TestUtils.LINK_ACTION, task, null, null, null, null);
        thenTask = task.then(TestUtils.LINK_VOID_ACTION);
        validateThen(thenTask, TestUtils.LINK_VOID_ACTION, task, null, null, null, null );
        thenTask = task.then(TestUtils.LINK_EMPTY_ACTION);
        validateThen(thenTask, TestUtils.LINK_EMPTY_ACTION, task, null, null, null, null);
        thenTask = task.then(TestUtils.LINK_EMPTY_VOID_ACTION);
        validateThen(thenTask, TestUtils.LINK_EMPTY_VOID_ACTION, task, null, null, null, null);
    }

    private void validateThen(Task<?> thenTask, IAction<?> action, Task<String> task, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        if(scheduler == null)
            scheduler = task.getScheduler();

        if(options == null)
            options = task.getOptions().getOptions().toArray(new Option[0]);

        IContext<?> template = TaskTestUtils.createTemplate(id, action, cancellationToken, scheduler, options, State.SUCCEEDED);
        TaskTestUtils.validateTask(thenTask, template);
    }

    private void validateThen(Task<?> thenTask, ILinkAction<?,String> action, Task<String> task, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateThen(thenTask, new LinkAction<>(task, action), task, id, cancellationToken, scheduler, options);
    }

    private void validateThen(Task<?> thenTask, ILinkVoidAction<String> action, Task<String> task, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateThen(thenTask, new LinkVoidAction<>(task, action), task, id, cancellationToken, scheduler, options);
    }

    private void validateThen(Task<?> thenTask, ILinkEmptyAction<String> action, Task<String> task, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateThen(thenTask, new LinkEmptyAction<>(task, action), task, id, cancellationToken, scheduler, options);
    }

    private void validateThen(Task<?> thenTask, ILinkEmptyVoidAction action, Task<String> task, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateThen(thenTask, new LinkEmptyVoidAction<>(task, action), task, id, cancellationToken, scheduler, options);
    }

    @Test
    public void retryTest() throws Exception {
        String id = "";
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];
        Task<String> task = new Task<>(TestUtils.ACTION, scheduler, options);
        task.start();
        Task<String> retryTask;

        retryTask = task.retry(id, () -> true, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(id, 3, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        retryTask = task.retry(id, () -> true, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(id, 3, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(id, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        retryTask = task.retry(id, () -> true, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        retryTask = task.retry(id, 3, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        retryTask = task.retry(id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        retryTask = task.retry(id, () -> true, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, id, null, TestUtils.SCHEDULER, null);
        retryTask = task.retry(id, 3, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, id, null, TestUtils.SCHEDULER, null);
        retryTask = task.retry(id, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, id, null, TestUtils.SCHEDULER, null);

        retryTask = task.retry(id, () -> true, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(id, 3, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(id, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        retryTask = task.retry(id, () -> true, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, null, null, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(id, 3, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, null, null, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(id, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, id, null, null, TestUtils.OPTIONS_ARRAY);

        retryTask = task.retry(id, () -> true, TestUtils.CANCELLATION_TOKEN);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, null, null);
        retryTask = task.retry(id, 3, TestUtils.CANCELLATION_TOKEN);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, null, null);
        retryTask = task.retry(id, TestUtils.CANCELLATION_TOKEN);
        validateRetry(retryTask, task, id, TestUtils.CANCELLATION_TOKEN, null, null);

        retryTask = task.retry(id, () -> true);
        validateRetry(retryTask, task, id, null, null, null);
        retryTask = task.retry(id, 3);
        validateRetry(retryTask, task, id, null, null, null);
        retryTask = task.retry(id);
        validateRetry(retryTask, task, id, null, null, null);

        retryTask = task.retry(() -> true, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(3, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        retryTask = task.retry(() -> true, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(3, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, null, TestUtils.SCHEDULER, TestUtils.OPTIONS_ARRAY);

        retryTask = task.retry(() -> true, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        retryTask = task.retry(3, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);
        retryTask = task.retry(TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, TestUtils.SCHEDULER, null);

        retryTask = task.retry(() -> true, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, null, null, TestUtils.SCHEDULER, null);
        retryTask = task.retry(3, TestUtils.SCHEDULER);
        validateRetry(retryTask, task, null, null, TestUtils.SCHEDULER, null);
        retryTask = task.retry(TestUtils.SCHEDULER);
        validateRetry(retryTask, task, null, null, TestUtils.SCHEDULER, null);

        retryTask = task.retry(() -> true, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(3, TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(TestUtils.CANCELLATION_TOKEN, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, null, TestUtils.OPTIONS_ARRAY);

        retryTask = task.retry(() -> true, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, null, null, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(3, TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, null, null, TestUtils.OPTIONS_ARRAY);
        retryTask = task.retry(TestUtils.OPTIONS_ARRAY);
        validateRetry(retryTask, task, null, null, null, TestUtils.OPTIONS_ARRAY);

        retryTask = task.retry(() -> true, TestUtils.CANCELLATION_TOKEN);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, null, null);
        retryTask = task.retry(3, TestUtils.CANCELLATION_TOKEN);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, null, null);
        retryTask = task.retry(TestUtils.CANCELLATION_TOKEN);
        validateRetry(retryTask, task, null, TestUtils.CANCELLATION_TOKEN, null, null);

        retryTask = task.retry(() -> true);
        validateRetry(retryTask, task, null, null, null, null);
        retryTask = task.retry(3);
        validateRetry(retryTask, task, null, null, null, null);
        retryTask = task.retry();
        validateRetry(retryTask, task, null, null, null, null);
    }

    private void validateRetry(Task<String> retryTask, Task<String> task, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        if(scheduler == null)
            scheduler = task.getScheduler();

        if(options == null)
            options = task.getOptions().getOptions().toArray(new Option[0]);

        IContext<?> template = TaskTestUtils.createTemplate(id, new RetryAction<>(task), cancellationToken, scheduler, options, State.SUCCEEDED);
        TaskTestUtils.validateTask(retryTask, template);
    }

    @Test
    public void executeSuccessParentAndSuccessChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            return TestUtils.SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.SUCCEEDED, TestUtils.SUCCESS_RESULT, null);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeSuccessParentAndCancelChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.CANCEL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            return TestUtils.SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeSuccessParentAndFailChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.FAIL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            return TestUtils.SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, TestUtils.FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeCancelParentAndSuccessChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            ct.cancel();
            ct.abortIfCancelRequested();
            return TestUtils.SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeCancelParentAndCancelChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.CANCEL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            ct.cancel();
            ct.abortIfCancelRequested();
            return TestUtils.SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeCancelParentAndFailChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.FAIL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            ct.cancel();
            ct.abortIfCancelRequested();
            return TestUtils.SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, TestUtils.FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeFailParentAndSuccessChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            throw TestUtils.FAIL_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, TestUtils.FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeFailParentAndCancelChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.CANCEL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            throw TestUtils.FAIL_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, TestUtils.FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeFailParentAndFailChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(TestUtils.FAIL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            throw TestUtils.FAIL_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, TestUtils.FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executionEndWithSuccessTest() throws Exception {
        Task<String> task = new Task<>(TestUtils.SUCCESS_ACTION);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), TestUtils.SUCCESS_ACTION, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.SUCCEEDED, TestUtils.SUCCESS_RESULT, null);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executionEndWithCancelTest() throws Exception {
        Task<String> task = new Task<>(TestUtils.CANCEL_ACTION);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), TestUtils.CANCEL_ACTION, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executionEndWithFailTest() throws Exception {
        Task<String> task = new Task<>(TestUtils.FAIL_ACTION);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), TestUtils.FAIL_ACTION, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, TestUtils.FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

}
