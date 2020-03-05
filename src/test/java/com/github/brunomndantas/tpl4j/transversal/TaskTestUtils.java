package com.github.brunomndantas.tpl4j.transversal;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.IStatus;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.core.status.Status;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.action.action.*;
import com.github.brunomndantas.tpl4j.task.action.link.LinkAction;
import com.github.brunomndantas.tpl4j.task.action.link.LinkEmptyAction;
import com.github.brunomndantas.tpl4j.task.action.link.LinkEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.action.link.LinkVoidAction;
import com.github.brunomndantas.tpl4j.task.action.retry.RetryAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.*;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.context.ParallelContextExecutor;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.task.ParallelTask;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapAction;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapContextExecutor;
import com.github.brunomndantas.tpl4j.task.helpers.unwrap.UnwrapTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllAction;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllContextExecutor;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAll.WhenAllTask;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyAction;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyContextExecutor;
import com.github.brunomndantas.tpl4j.task.helpers.when.whenAny.WhenAnyTask;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

public class TaskTestUtils {

    public static <T> IContext<T> createTemplate(String id, IAction<T> action,
                                                 ICancellationToken cancellationToken, IScheduler scheduler, Option[] options,
                                                 State state, T resultValue, Exception resultException) {
        Context<T> context = new Context<>(
                id, action,
                cancellationToken, scheduler, options == null ? null : new Options(Arrays.asList(options)),
                new Status(""), null, null, 0, 0, resultValue, resultException
        );

        if (!state.equals(State.CREATED))
            context.getStatus().setState(state);

        return context;
    }

    public static <T> IContext<T> createTemplate(String id, IAction<T> action,
                                                 ICancellationToken cancellationToken, IScheduler scheduler, Option[] options,
                                                 State state) {
        return createTemplate(id, action, cancellationToken, scheduler, options, state, null, null);
    }


    public static void validateCreate(Task<?> task, IAction<?> action, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        IContext<?> template = TaskTestUtils.createTemplate(id, action, cancellationToken, scheduler, options, State.CREATED);
        TaskTestUtils.validateCreation(task, template);

        task.start();
        template.getStatus().setState(State.SUCCEEDED);
        TaskTestUtils.validateExecution(task, template);
    }

    public static void validateCreate(Task<?> task, IVoidAction action, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateCreate(task, new VoidAction(action), id, cancellationToken, scheduler, options);
    }

    public static void validateCreate(Task<?> task, IEmptyAction<?> action, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateCreate(task, new EmptyAction<>(action), id, cancellationToken, scheduler, options);
    }

    public static void validateCreate(Task<?> task, IEmptyVoidAction action, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateCreate(task, new EmptyVoidAction(action), id, cancellationToken, scheduler, options);
    }


    public static void validateCreateAndStart(Task<?> task, IAction<?> action, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        IContext<?> template = TaskTestUtils.createTemplate(id, action, cancellationToken, scheduler, options, State.SUCCEEDED);
        TaskTestUtils.validateTask(task, template);
    }

    public static void validateCreateAndStart(Task<?> task, IVoidAction action, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateCreateAndStart(task, new VoidAction(action), id, cancellationToken, scheduler, options);
    }

    public static void validateCreateAndStart(Task<?> task, IEmptyAction<?> action, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateCreateAndStart(task, new EmptyAction<>(action), id, cancellationToken, scheduler, options);
    }

    public static void validateCreateAndStart(Task<?> task, IEmptyVoidAction action, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateCreateAndStart(task, new EmptyVoidAction(action), id, cancellationToken, scheduler, options);
    }


    public static void validateWhenAll(WhenAllTask<?> task, String id, Collection<Task<String>> tasks, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        IContext<?> template = TaskTestUtils.createTemplate(id, new WhenAllAction<>(tasks), cancellationToken, scheduler, options, State.SUCCEEDED);
        TaskTestUtils.validateTask(task, template);
    }


    public static void validateWhenAny(WhenAnyTask<?> task, String id, Collection<Task<String>> tasks, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        IContext<?> template = TaskTestUtils.createTemplate(id, new WhenAnyAction<>(tasks), cancellationToken, scheduler, options, State.SUCCEEDED);
        TaskTestUtils.validateTask(task, template);
    }


    public static void validateUnwrap(UnwrapTask<?> task, Task<Task<String>> taskToUnwrap, String id, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        IContext<?> template = TaskTestUtils.createTemplate(id, new UnwrapAction<>(taskToUnwrap), cancellationToken, scheduler, options, State.SUCCEEDED);
        TaskTestUtils.validateTask(task, template);
    }


    public static void validateForEach(Task<?> task, IParallelAction<String,?> action, String id, Iterable<String> elements, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        if(options == null)
            options = new Option[0];

        IContext<?> template = TaskTestUtils.createTemplate(id, new ParallelAction<>(null, action, elements, null, null, Arrays.asList(options)), cancellationToken, scheduler, options, State.SUCCEEDED);
        TaskTestUtils.validateTask(task, template);
    }

    public static void validateForEach(Task<?> task, IParallelVoidAction<String> action, String id, Iterable<String> elements, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateForEach(task, new ParallelVoidAction<>(action), id, elements, cancellationToken, scheduler, options);
    }

    public static void validateForEach(Task<?> task, IParallelUninterruptibleAction<String,?> action, String id, Iterable<String> elements, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateForEach(task, new ParallelUninterruptibleAction<>(action), id, elements, cancellationToken, scheduler, options);
    }

    public static void validateForEach(Task<?> task, IParallelUninterruptibleVoidAction<String> action, String id, Iterable<String> elements, CancellationToken cancellationToken, IScheduler scheduler, Option... options) throws Exception {
        validateForEach(task, new ParallelUninterruptibleVoidAction<>(action), id, elements, cancellationToken, scheduler, options);
    }


    public static void validateTask(Task<?> task, IContext<?> templateContext) throws Exception {
        validateCreation(task, templateContext);
        validateExecution(task, templateContext);
    }

    public static void validateCreation(Task<?> task, IContext<?> templateContext) throws Exception {
        validateContext(task);
        validateContextManager(task);
        validateContextBuilder(task);
        validateContextExecutor(task);

        validateId(task, templateContext);
        validateAction(task, templateContext);
        validateCancellationToken(task, templateContext);
        validateScheduler(task, templateContext);
        validateOptions(task, templateContext);
    }

    public static void validateContext(Task<?> task) {
        assertNotNull(task.getContext());
    }

    public static void validateContextManager(Task<?> task) {
        assertNotNull(task.getContextManager());
        assertSame(Task.DEFAULT_CONTEXT_MANAGER, task.getContextManager());
    }

    public static void validateContextBuilder(Task<?> task) {
        assertNotNull(task.getContextBuilder());
        assertSame(Task.DEFAULT_CONTEXT_BUILDER, task.getContextBuilder());
    }

    public static void validateContextExecutor(Task<?> task) {
        assertNotNull(task.getContextExecutor());

        if(task instanceof WhenAllTask)
            assertTrue(task.getContextExecutor() instanceof WhenAllContextExecutor);
        else if(task instanceof WhenAnyTask)
            assertTrue(task.getContextExecutor() instanceof WhenAnyContextExecutor);
        else if(task instanceof UnwrapTask)
            assertTrue(task.getContextExecutor() instanceof UnwrapContextExecutor);
        else if(task instanceof ParallelTask)
            assertTrue(task.getContextExecutor() instanceof ParallelContextExecutor);
        else
            assertSame(Task.DEFAULT_CONTEXT_EXECUTOR, task.getContextExecutor());
    }

    public static void validateId(Task<?> task, IContext<?> templateContext) {
        validateId(task, templateContext.getTaskId());
    }

    public static void validateId(Task<?> task, String id) {
        if(id == null) {
            assertNotNull(task.getId());
            assertFalse(task.getId().isEmpty());
        } else {
            assertSame(id, task.getId());
        }
    }

    public static void validateAction(Task<?> task, IContext<?> templateContext) {
        validateAction(task, templateContext.getAction());
    }

    public static void validateAction(Task<?> task, IAction<?> action) {
        assertNotNull(task.getAction());

        if(action instanceof VoidAction)
            validateAction(task, (VoidAction)action);
        else if(action instanceof EmptyAction)
            validateAction(task, (EmptyAction<?>) action);
        else if(action instanceof EmptyVoidAction)
            validateAction(task, (EmptyVoidAction)action);
        else if(action instanceof RetryAction)
            validateAction(task, (RetryAction<?>)action);
        else if(action instanceof LinkAction)
            validateAction(task, (LinkAction<?,?>) action);
        else if(action instanceof LinkVoidAction)
            validateAction(task, (LinkVoidAction<?>)action);
        else if(action instanceof LinkEmptyAction)
            validateAction(task, (LinkEmptyAction<?,?>) action);
        else if(action instanceof LinkEmptyVoidAction)
            validateAction(task, (LinkEmptyVoidAction<?>)action);
        else if(action instanceof ParallelAction)
            validateAction(task, (ParallelAction<?,?>) action);
        else if(action instanceof UnwrapAction)
            validateAction(task, (UnwrapAction<?>) action);
        else if(action instanceof WhenAllAction)
            validateAction(task, (WhenAllAction<?>) action);
        else if(action instanceof WhenAnyAction)
            validateAction(task, (WhenAnyAction<?>) action);
        else
            assertSame(task.getAction(), action);
    }

    public static void validateAction(Task<?> task, VoidAction action) {
        assertTrue(task.getAction() instanceof VoidAction);
        assertSame(((VoidAction)task.getAction()).getAction(), action.getAction());
    }

    public static void validateAction(Task<?> task, EmptyAction<?> action) {
        assertTrue(task.getAction() instanceof EmptyAction);
        assertSame(((EmptyAction<?>)task.getAction()).getAction(), action.getAction());

    }

    public static void validateAction(Task<?> task, EmptyVoidAction action) {
        assertTrue(task.getAction() instanceof EmptyVoidAction);
        assertSame(((EmptyVoidAction)task.getAction()).getAction(), action.getAction());
    }

    public static void validateAction(Task<?> task, RetryAction<?> action) {
        assertTrue(task.getAction() instanceof RetryAction);
        assertSame(((RetryAction<?>)task.getAction()).getPreviousTask(), action.getPreviousTask());
    }

    public static void validateAction(Task<?> task, LinkAction<?,?> action) {
        assertTrue(task.getAction() instanceof LinkAction);
        assertSame(((LinkAction<?,?>)task.getAction()).getAction(), action.getAction());
        assertSame(((LinkAction<?,?>)task.getAction()).getPreviousTask(), action.getPreviousTask());
    }

    public static void validateAction(Task<?> task, LinkVoidAction<?> action) {
        assertTrue(task.getAction() instanceof LinkVoidAction);
        assertSame(((LinkVoidAction<?>)task.getAction()).getAction(), action.getAction());
        assertSame(((LinkVoidAction<?>)task.getAction()).getPreviousTask(), action.getPreviousTask());
    }

    public static void validateAction(Task<?> task, LinkEmptyAction<?,?> action) {
        assertTrue(task.getAction() instanceof LinkEmptyAction);
        assertSame(((LinkEmptyAction<?,?>)task.getAction()).getAction(), action.getAction());
        assertSame(((LinkEmptyAction<?,?>)task.getAction()).getPreviousTask(), action.getPreviousTask());
    }

    public static void validateAction(Task<?> task, LinkEmptyVoidAction<?> action) {
        assertTrue(task.getAction() instanceof LinkEmptyVoidAction);
        assertSame(((LinkEmptyVoidAction<?>)task.getAction()).getAction(), action.getAction());
        assertSame(((LinkEmptyVoidAction<?>)task.getAction()).getPreviousTask(), action.getPreviousTask());
    }

    public static void validateAction(Task<?> task, ParallelAction<?,?> action) {
        assertTrue(task.getAction() instanceof ParallelAction);
        assertSame(((ParallelAction<?,?>)task.getAction()).getElements(), action.getElements());

        if(action.getAction() instanceof ParallelVoidAction)
            validateAction(task, (ParallelVoidAction<?>)action.getAction());
        else if(action.getAction() instanceof ParallelUninterruptibleAction)
            validateAction(task, (ParallelUninterruptibleAction<?,?>)action.getAction());
        else if(action.getAction() instanceof ParallelUninterruptibleVoidAction)
            validateAction(task, (ParallelUninterruptibleVoidAction<?>)action.getAction());
        else
            assertSame(((ParallelAction<?,?>)task.getAction()).getAction(), action.getAction());
    }

    public static void validateAction(Task<?> task, ParallelVoidAction<?> action) {
        assertTrue(((ParallelAction<?,?>)task.getAction()).getAction() instanceof ParallelVoidAction);
        assertSame(((ParallelVoidAction<?>)(((ParallelAction<?,?>)task.getAction()).getAction())).getAction(), action.getAction());
    }

    public static void validateAction(Task<?> task, ParallelUninterruptibleAction<?,?> action) {
        assertTrue(((ParallelAction<?,?>)task.getAction()).getAction() instanceof ParallelUninterruptibleAction);
        assertSame(((ParallelUninterruptibleAction<?,?>)(((ParallelAction<?,?>)task.getAction()).getAction())).getAction(), action.getAction());
    }

    public static void validateAction(Task<?> task, ParallelUninterruptibleVoidAction<?> action) {
        assertTrue(((ParallelAction<?,?>) task.getAction()).getAction() instanceof ParallelUninterruptibleVoidAction);
        assertSame(((ParallelUninterruptibleVoidAction<?>)(((ParallelAction<?,?>) task.getAction()).getAction())).getAction(), action.getAction());
    }

    public static void validateAction(Task<?> task, UnwrapAction<?> action) {
        assertTrue(task.getAction() instanceof UnwrapAction);
        assertSame(((UnwrapAction<?>) task.getAction()).getTask(), action.getTask());
    }

    public static void validateAction(Task<?> task, WhenAllAction<?> action) {
        assertTrue(task.getAction() instanceof WhenAllAction);
        assertSame(((WhenAllAction<?>) task.getAction()).getTasks(), action.getTasks());
    }

    public static void validateAction(Task<?> task, WhenAnyAction<?> action) {
        assertTrue(task.getAction() instanceof WhenAnyAction);
        assertSame(((WhenAnyAction<?>) task.getAction()).getTasks(), action.getTasks());
    }

    public static void validateCancellationToken(Task<?> task, IContext<?> templateContext) {
        validateCancellationToken(task, templateContext.getCancellationToken());
    }

    public static void validateCancellationToken(Task<?> task, ICancellationToken cancellationToken) {
        if(cancellationToken == null) {
            assertNotNull(task.getCancellationToken());
        } else {
            assertSame(cancellationToken, task.getCancellationToken());
        }

        assertNotNull(task.getCancellationToken().getId());
        assertFalse(task.getCancellationToken().getId().isEmpty());
    }

    public static void validateScheduler(Task<?> task, IContext<?> templateContext) {
        validateScheduler(task, templateContext.getScheduler());
    }

    public static void validateScheduler(Task<?> task, IScheduler scheduler) {
        if(scheduler == null) {
            assertNotNull(task.getScheduler());
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());
        } else {
            assertSame(scheduler, task.getScheduler());
        }

        assertNotNull(task.getScheduler().getId());
        assertFalse(task.getScheduler().getId().isEmpty());
    }

    public static void validateOptions(Task<?> task, IContext<?> templateContext) {
        validateOptions(task, templateContext.getOptions());
    }

    public static void validateOptions(Task<?> task, IOptions options) {
        if(options == null)
            options = new Options(Arrays.asList(Task.DEFAULT_OPTIONS));

        assertNotNull(task.getOptions());
        assertEquals(options.getOptions().size(), task.getOptions().getOptions().size());
        for(Option option : options.getOptions())
            assertTrue(task.getOptions().contains(option));
    }

    public static void validateExecution(Task<?> task, IContext<?> templateContext) throws Exception {
        task.getFinishedEvent().await();
        validateStatus(task, templateContext);
        validateResult(task, templateContext);
    }

    public static void validateResult(Task<?> task, IContext<?> templateContext) {
        validateResult(task, templateContext.getResultValue(), templateContext.getResultException());
    }

    public static void validateResult(Task<?> task, Object resultValue, Exception resultException) {
        validateParentResult(task);
        validateChildrenResult(task);
        validateTaskResult(task, resultValue, resultException);
    }

    public static void validateParentResult(Task<?> task) {
        IContext<?> parentContext = task.getContext();

        if(parentContext != null && !parentContext.getOptions().rejectChildren()) {
            if(task.getOptions().attachToParent()) {
                assertTrue(parentContext.getStatus().getFinishedEvent().hasFired());

                if(task.getState().equals(State.FAILED)) {
                    assertEquals(State.FAILED, parentContext.getStatus().getState());

                    Collection<Throwable> parentExceptions = Arrays.asList(parentContext.getResultException().getSuppressed());
                    assertTrue(task.getResultException().equals(parentContext.getResultException()) ||
                            parentExceptions.contains(task.getResultException()));
                }
            }
        }
    }

    public static void validateChildrenResult(Task<?> task) {
        if(!task.getContext().getChildrenContexts().isEmpty() && !task.getOptions().rejectChildren()) {
            for(IContext<?> childContext : task.getContext().getChildrenContexts()) {
                if(childContext.getOptions().attachToParent()) {
                    assertTrue(childContext.getStatus().getFinishedEvent().hasFired());

                    if(childContext.getStatus().getState().equals(State.FAILED)) {
                        assertEquals(State.FAILED, task.getState());

                        Collection<Throwable> taskExceptions = Arrays.asList(task.getResultException().getSuppressed());
                        assertTrue(task.getResultException().equals(childContext.getResultException()) ||
                                taskExceptions.contains(childContext.getResultException()));
                    }
                }
            }
        }
    }

    public static void validateTaskResult(Task<?> task, Object resultValue, Exception resultException) {
        if(resultException instanceof CancelledException) {
            assertEquals(task.getState(), State.CANCELED);
            assertNotNull(task.getResultException());
            assertTrue(task.getResultException() instanceof CancelledException);
        } else if(resultException != null) {
            assertEquals(task.getState(), State.FAILED);
            assertEquals(resultException, task.getResultException());
        } else {
            assertEquals(task.getState(), State.SUCCEEDED);
            if(resultValue != null)
                assertEquals(resultValue, task.getResultValue());
        }
    }

    public static void validateStatus(Task<?> task, IContext<?> templateContext) {
        validateStatus(task, templateContext.getStatus());
    }

    public static void validateStatus(Task<?> task, IStatus status) {
        assertEquals(status.getState(), task.getState());

        switch (task.getState()) {
            case CREATED : { return; }
            case SCHEDULED : {
                assertTrue(task.getScheduledEvent().hasFired());
                return;
            }
            case RUNNING : {
                assertTrue(task.getScheduledEvent().hasFired());
                assertTrue(task.getRunningEvent().hasFired());
                return;
            }
            case WAITING_CHILDREN : {
                assertTrue(task.getScheduledEvent().hasFired());
                assertTrue(task.getRunningEvent().hasFired());
                assertTrue(task.getWaitingForChildrenEvent().hasFired());
                return;
            }
            case SUCCEEDED : {
                assertTrue(task.getScheduledEvent().hasFired());
                assertTrue(task.getRunningEvent().hasFired());
                assertTrue(task.getSucceededEvent().hasFired());
                assertTrue(task.getFinishedEvent().hasFired());
                return;
            }
            case FAILED : {
                assertTrue(task.getScheduledEvent().hasFired());
                assertTrue(task.getRunningEvent().hasFired());
                assertTrue(task.getFailedEvent().hasFired());
                assertTrue(task.getFinishedEvent().hasFired());
                return;
            }
            case CANCELED : {
                assertTrue(task.getScheduledEvent().hasFired());
                assertTrue(task.getCancelledEvent().hasFired());
                assertTrue(task.getFinishedEvent().hasFired());
                return;
            }
            default: fail("Unknown state!");
        }
    }

}
