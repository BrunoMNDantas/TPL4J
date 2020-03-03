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
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.action.action.*;
import com.github.brunomndantas.tpl4j.task.action.link.*;
import com.github.brunomndantas.tpl4j.task.action.retry.RetryAction;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

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
    private static final Option[] OPTIONS = {};
    private static final IScheduler SCHEDULER = new DedicatedThreadScheduler();

    private static final String SUCCESS_RESULT = "";
    private static final IAction<String> SUCCESS_ACTION = (token) -> { Thread.sleep(1000); return SUCCESS_RESULT; };

    private static final Exception FAIL_RESULT = new Exception();
    private static final IAction<String> FAIL_ACTION = (token) -> { Thread.sleep(1000); throw FAIL_RESULT; };

    private static final IAction<String> CANCEL_ACTION = (token) -> { Thread.sleep(1000); token.cancel(); token.abortIfCancelRequested(); return SUCCESS_RESULT; };



    @Test
    public void getContextTest() {
        Context<String> context = new Context<>("", (ct)->null, CANCELLATION_TOKEN, SCHEDULER, new Options(Arrays.asList(OPTIONS)), null, null, null, 0, 0, null, null);
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

        task = new Task<>(id, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateConstructor(task, id, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateConstructor(task, id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateConstructor(task, id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateConstructor(task, id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = new Task<>(id, ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateConstructor(task, id, ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateConstructor(task, id, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateConstructor(task, id, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateConstructor(task, id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);

        task = new Task<>(id, ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateConstructor(task, id, ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(id, VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateConstructor(task, id, VOID_ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(id, EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateConstructor(task, id, EMPTY_ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateConstructor(task, id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, null, OPTIONS);

        task = new Task<>(id, ACTION, SCHEDULER, OPTIONS);
        validateConstructor(task, id, ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(id, VOID_ACTION, SCHEDULER, OPTIONS);
        validateConstructor(task, id, VOID_ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(id, EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateConstructor(task, id, EMPTY_ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(id, EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateConstructor(task, id, EMPTY_VOID_ACTION, null, SCHEDULER, OPTIONS);

        task = new Task<>(id, ACTION, CANCELLATION_TOKEN);
        validateConstructor(task, id, ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, VOID_ACTION, CANCELLATION_TOKEN);
        validateConstructor(task, id, VOID_ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, EMPTY_ACTION, CANCELLATION_TOKEN);
        validateConstructor(task, id, EMPTY_ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateConstructor(task, id, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, null, null);

        task = new Task<>(id, ACTION, SCHEDULER);
        validateConstructor(task, id, ACTION, null, SCHEDULER, null);
        task = new Task<>(id, VOID_ACTION, SCHEDULER);
        validateConstructor(task, id, VOID_ACTION, null, SCHEDULER, null);
        task = new Task<>(id, EMPTY_ACTION, SCHEDULER);
        validateConstructor(task, id, EMPTY_ACTION, null, SCHEDULER, null);
        task = new Task<>(id, EMPTY_VOID_ACTION, SCHEDULER);
        validateConstructor(task, id, EMPTY_VOID_ACTION, null, SCHEDULER, null);

        task = new Task<>(id, ACTION, OPTIONS);
        validateConstructor(task, id, ACTION, null, null, OPTIONS);
        task = new Task<>(id, VOID_ACTION, OPTIONS);
        validateConstructor(task, id, VOID_ACTION, null, null, OPTIONS);
        task = new Task<>(id, EMPTY_ACTION, OPTIONS);
        validateConstructor(task, id, EMPTY_ACTION, null, null, OPTIONS);
        task = new Task<>(id, EMPTY_VOID_ACTION, OPTIONS);
        validateConstructor(task, id, EMPTY_VOID_ACTION, null, null, OPTIONS);

        task = new Task<>(id, ACTION);
        validateConstructor(task, id, ACTION, null, null, null);
        task = new Task<>(id, VOID_ACTION);
        validateConstructor(task, id, VOID_ACTION, null, null, null);
        task = new Task<>(id, EMPTY_ACTION);
        validateConstructor(task, id, EMPTY_ACTION, null, null, null);
        task = new Task<>(id, EMPTY_VOID_ACTION);
        validateConstructor(task, id, EMPTY_VOID_ACTION, null, null, null);

        task = new Task<>(ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateConstructor(task, null, ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateConstructor(task, null, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateConstructor(task, null, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        task = new Task<>(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateConstructor(task, null, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        task = new Task<>(ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateConstructor(task, null, ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateConstructor(task, null, VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateConstructor(task, null, EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);
        task = new Task<>(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateConstructor(task, null, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, null);

        task = new Task<>(ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateConstructor(task, null, ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateConstructor(task, null, VOID_ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateConstructor(task, null, EMPTY_ACTION, CANCELLATION_TOKEN, null, OPTIONS);
        task = new Task<>(EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateConstructor(task, null, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, null, OPTIONS);

        task = new Task<>(ACTION, SCHEDULER, OPTIONS);
        validateConstructor(task, null, ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(VOID_ACTION, SCHEDULER, OPTIONS);
        validateConstructor(task, null, VOID_ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateConstructor(task, null, EMPTY_ACTION, null, SCHEDULER, OPTIONS);
        task = new Task<>(EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateConstructor(task, null, EMPTY_VOID_ACTION, null, SCHEDULER, OPTIONS);

        task = new Task<>(ACTION, CANCELLATION_TOKEN);
        validateConstructor(task, null, ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(VOID_ACTION, CANCELLATION_TOKEN);
        validateConstructor(task, null, VOID_ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(EMPTY_ACTION, CANCELLATION_TOKEN);
        validateConstructor(task, null, EMPTY_ACTION, CANCELLATION_TOKEN, null, null);
        task = new Task<>(EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateConstructor(task, null, EMPTY_VOID_ACTION, CANCELLATION_TOKEN, null, null);

        task = new Task<>(ACTION, SCHEDULER);
        validateConstructor(task, null, ACTION, null, SCHEDULER, null);
        task = new Task<>(VOID_ACTION, SCHEDULER);
        validateConstructor(task, null, VOID_ACTION, null, SCHEDULER, null);
        task = new Task<>(EMPTY_ACTION, SCHEDULER);
        validateConstructor(task, null, EMPTY_ACTION, null, SCHEDULER, null);
        task = new Task<>(EMPTY_VOID_ACTION, SCHEDULER);
        validateConstructor(task, null, EMPTY_VOID_ACTION, null, SCHEDULER, null);

        task = new Task<>(ACTION, OPTIONS);
        validateConstructor(task, null, ACTION, null, null, OPTIONS);
        task = new Task<>(VOID_ACTION, OPTIONS);
        validateConstructor(task, null, VOID_ACTION, null, null, OPTIONS);
        task = new Task<>(EMPTY_ACTION, OPTIONS);
        validateConstructor(task, null, EMPTY_ACTION, null, null, OPTIONS);
        task = new Task<>(EMPTY_VOID_ACTION, OPTIONS);
        validateConstructor(task, null, EMPTY_VOID_ACTION, null, null, OPTIONS);

        task = new Task<>(ACTION);
        validateConstructor(task, null, ACTION, null, null, null);
        task = new Task<>(VOID_ACTION);
        validateConstructor(task, null, VOID_ACTION, null, null, null);
        task = new Task<>(EMPTY_ACTION);
        validateConstructor(task, null, EMPTY_ACTION, null, null, null);
        task = new Task<>(EMPTY_VOID_ACTION);
        validateConstructor(task, null, EMPTY_VOID_ACTION, null, null, null);
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
        Task<String> task = new Task(id, SUCCESS_ACTION);
        assertSame(id, task.getId());
    }

    @Test
    public void getActionTest() {
        IAction<String> action = SUCCESS_ACTION;
        Task<String> task = new Task<>(action);
        assertSame(action, task.getAction());
    }

    @Test
    public void getCancellationTokenTest() {
        CancellationToken cancellationToken = new CancellationToken();
        Task<String> task = new Task<>(SUCCESS_ACTION, cancellationToken);
        assertSame(cancellationToken, task.getCancellationToken());
    }

    @Test
    public void getSchedulerTest() {
        IScheduler scheduler = SCHEDULER;
        Task<String> task = new Task<>(SUCCESS_ACTION, scheduler);
        assertSame(scheduler, task.getScheduler());
    }

    @Test
    public void getOptionsTest() { ;
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getOptions(), task.getOptions());
    }

    @Test
    public void getStatusTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus(), task.getStatus());
    }

    @Test
    public void getResultValueTest() throws Exception {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        task.start();
        task.getContext().getStatus().getFinishedEvent().await();
        assertSame(task.getContext().getResultValue(), task.getResultValue());
    }

    @Test
    public void getResultExceptionTest() throws Exception {
        Task<String> task = new Task<>(FAIL_ACTION);
        task.start();
        task.getContext().getStatus().getFinishedEvent().await();
        assertSame(task.getContext().getResultException(), task.getResultException());
    }

    @Test
    public void getStateTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getState(), task.getState());
    }

    @Test
    public void getScheduledEventTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getScheduledEvent(), task.getScheduledEvent());
    }

    @Test
    public void getRunningEventTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getRunningEvent(), task.getRunningEvent());
    }

    @Test
    public void getWaitingForChildrenEventTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getWaitingForChildrenEvent(), task.getWaitingForChildrenEvent());
    }

    @Test
    public void getCancelledEventTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getCancelledEvent(), task.getCancelledEvent());
    }

    @Test
    public void getFailedEventTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getFailedEvent(), task.getFailedEvent());
    }

    @Test
    public void getSucceededEventTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getSucceededEvent(), task.getSucceededEvent());
    }

    @Test
    public void getFinishedEventTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        assertSame(task.getContext().getStatus().getFinishedEvent(), task.getFinishedEvent());
    }
    
    @Test
    public void startTest() throws InterruptedException {
        Task<String> task = new Task<>(SUCCESS_ACTION);

        task.start();

        assertTrue(task.context.getStatus().getScheduledEvent().hasFired());

        task.context.getStatus().getSucceededEvent().await();

        assertSame(SUCCESS_RESULT, task.context.getResultValue());
    }

    @Test
    public void startTwiceTest() {
        Task<String> task = new Task<>(SUCCESS_ACTION);

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
        Task<String> task = new Task<>(SUCCESS_ACTION);

        task.cancel();

        task.start();

        assertTrue(task.context.getCancellationToken().hasCancelRequest());

        task.context.getStatus().getCancelledEvent().await();
    }

    @Test
    public void getSuccessResultTest() throws Exception {
        Task<String> task = new Task<>(SUCCESS_ACTION);

        task.start();

        assertSame(SUCCESS_RESULT, task.getResult());
    }

    @Test
    public void getFailResultTest() {
        Task<String> task = new Task<>(FAIL_ACTION);

        task.start();

        try {
            task.getResult();
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(FAIL_RESULT, e);
        }
    }

    @Test
    public void getCancelResultTest() {
        Task<String> task = new Task<>(CANCEL_ACTION);

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
        Task<?> task = new Task<>(ACTION);
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
        Task<String> task = new Task<>(ACTION, scheduler, options);
        task.start();
        Task<?> thenTask;

        thenTask = task.then(id, LINK_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_ACTION, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_VOID_ACTION, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, id, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        thenTask = task.then(id, LINK_ACTION, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_ACTION, task, id, null, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_VOID_ACTION, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_VOID_ACTION, task, id, null, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, id, null, SCHEDULER, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, id, null, SCHEDULER, OPTIONS);

        thenTask = task.then(id, LINK_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThen(thenTask, LINK_ACTION, task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(id, LINK_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThen(thenTask, LINK_VOID_ACTION, task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(id, LINK_EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, id, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, id, CANCELLATION_TOKEN, SCHEDULER, null);

        thenTask = task.then(id, LINK_ACTION, SCHEDULER);
        validateThen(thenTask, LINK_ACTION, task, id, null, SCHEDULER, null);
        thenTask = task.then(id, LINK_VOID_ACTION, SCHEDULER);
        validateThen(thenTask, LINK_VOID_ACTION, task, id, null, SCHEDULER, null);
        thenTask = task.then(id, LINK_EMPTY_ACTION, SCHEDULER);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, id, null, SCHEDULER, null);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, SCHEDULER);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, id, null, SCHEDULER, null);

        thenTask = task.then(id, LINK_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThen(thenTask, LINK_ACTION, task, id, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(id, LINK_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThen(thenTask, LINK_VOID_ACTION, task, id, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, id, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, id, CANCELLATION_TOKEN, null, OPTIONS);

        thenTask = task.then(id, LINK_ACTION, OPTIONS);
        validateThen(thenTask, LINK_ACTION, task, id, null, null, OPTIONS);
        thenTask = task.then(id, LINK_VOID_ACTION, OPTIONS);
        validateThen(thenTask, LINK_VOID_ACTION, task, id, null, null, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_ACTION, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, id, null, null, OPTIONS);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, id, null, null, OPTIONS);

        thenTask = task.then(id, LINK_ACTION, CANCELLATION_TOKEN);
        validateThen(thenTask, LINK_ACTION, task, id, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, LINK_VOID_ACTION, CANCELLATION_TOKEN);
        validateThen(thenTask, LINK_VOID_ACTION, task, id, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, LINK_EMPTY_ACTION, CANCELLATION_TOKEN);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, id, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, id, CANCELLATION_TOKEN, null, null);

        thenTask = task.then(id, LINK_ACTION);
        validateThen(thenTask, LINK_ACTION, task, id, null, null, null);
        thenTask = task.then(id, LINK_VOID_ACTION);
        validateThen(thenTask, LINK_VOID_ACTION, task, id, null, null, null );
        thenTask = task.then(id, LINK_EMPTY_ACTION);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, id, null, null, null);
        thenTask = task.then(id, LINK_EMPTY_VOID_ACTION);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, id, null, null, null);

        thenTask = task.then(LINK_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_ACTION, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_VOID_ACTION, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, null, CANCELLATION_TOKEN, SCHEDULER, OPTIONS);

        thenTask = task.then(LINK_ACTION, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_ACTION, task, null, null, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_VOID_ACTION, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_VOID_ACTION, task, null, null, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_EMPTY_ACTION, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, null, null, SCHEDULER, OPTIONS);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, SCHEDULER, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, null, null, SCHEDULER, OPTIONS);

        thenTask = task.then(LINK_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThen(thenTask, LINK_ACTION, task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(LINK_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThen(thenTask, LINK_VOID_ACTION, task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(LINK_EMPTY_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, null, CANCELLATION_TOKEN, SCHEDULER, null);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, SCHEDULER);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, null, CANCELLATION_TOKEN, SCHEDULER, null);

        thenTask = task.then(LINK_ACTION, SCHEDULER);
        validateThen(thenTask, LINK_ACTION, task, null, null, SCHEDULER, null);
        thenTask = task.then(LINK_VOID_ACTION, SCHEDULER);
        validateThen(thenTask, LINK_VOID_ACTION, task, null, null, SCHEDULER, null);
        thenTask = task.then(LINK_EMPTY_ACTION, SCHEDULER);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, null, null, SCHEDULER, null);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, SCHEDULER);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, null, null, SCHEDULER, null);

        thenTask = task.then(LINK_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThen(thenTask, LINK_ACTION, task, null, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(LINK_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThen(thenTask, LINK_VOID_ACTION, task, null, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(LINK_EMPTY_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, null, CANCELLATION_TOKEN, null, OPTIONS);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, null, CANCELLATION_TOKEN, null, OPTIONS);

        thenTask = task.then(LINK_ACTION, OPTIONS);
        validateThen(thenTask, LINK_ACTION, task, null, null, null, OPTIONS);
        thenTask = task.then(LINK_VOID_ACTION, OPTIONS);
        validateThen(thenTask, LINK_VOID_ACTION, task, null, null, null, OPTIONS);
        thenTask = task.then(LINK_EMPTY_ACTION, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, null, null, null, OPTIONS);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, OPTIONS);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, null, null, null, OPTIONS);

        thenTask = task.then(LINK_ACTION, CANCELLATION_TOKEN);
        validateThen(thenTask, LINK_ACTION, task, null, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(LINK_VOID_ACTION, CANCELLATION_TOKEN);
        validateThen(thenTask, LINK_VOID_ACTION, task, null, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(LINK_EMPTY_ACTION, CANCELLATION_TOKEN);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, null, CANCELLATION_TOKEN, null, null);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION, CANCELLATION_TOKEN);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, null, CANCELLATION_TOKEN, null, null);

        thenTask = task.then(LINK_ACTION);
        validateThen(thenTask, LINK_ACTION, task, null, null, null, null);
        thenTask = task.then(LINK_VOID_ACTION);
        validateThen(thenTask, LINK_VOID_ACTION, task, null, null, null, null );
        thenTask = task.then(LINK_EMPTY_ACTION);
        validateThen(thenTask, LINK_EMPTY_ACTION, task, null, null, null, null);
        thenTask = task.then(LINK_EMPTY_VOID_ACTION);
        validateThen(thenTask, LINK_EMPTY_VOID_ACTION, task, null, null, null, null);
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
        Task<String> task = new Task<>(ACTION, scheduler, options);
        task.start();
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
            Task<String> task = new Task<>(SUCCESS_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            return SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.SUCCEEDED, SUCCESS_RESULT, null);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeSuccessParentAndCancelChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(CANCEL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            return SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.SUCCEEDED, SUCCESS_RESULT, null);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeSuccessParentAndFailChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(FAIL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            return SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeCancelParentAndSuccessChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(SUCCESS_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            ct.cancel();
            ct.abortIfCancelRequested();
            return SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeCancelParentAndCancelChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(CANCEL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            ct.cancel();
            ct.abortIfCancelRequested();
            return SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeCancelParentAndFailChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(FAIL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            ct.cancel();
            ct.abortIfCancelRequested();
            return SUCCESS_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeFailParentAndSuccessChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(SUCCESS_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            throw FAIL_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeFailParentAndCancelChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(CANCEL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            throw FAIL_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executeFailParentAndFailChildTest() throws Exception {
        IAction<String> action = (ct) -> {
            Task<String> task = new Task<>(FAIL_ACTION, Option.ATTACH_TO_PARENT);
            task.start();
            throw FAIL_RESULT;
        };

        Task<String> task = new Task<>(action);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), action, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executionEndWithSuccessTest() throws Exception {
        Task<String> task = new Task<>(SUCCESS_ACTION);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), SUCCESS_ACTION, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.SUCCEEDED, SUCCESS_RESULT, null);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executionEndWithCancelTest() throws Exception {
        Task<String> task = new Task<>(CANCEL_ACTION);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), CANCEL_ACTION, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executionEndWithFailTest() throws Exception {
        Task<String> task = new Task<>(FAIL_ACTION);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), FAIL_ACTION, task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

}
