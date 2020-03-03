package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.State;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.TaskTestUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.UUID;

import static org.junit.Assert.*;

public class UnwrapTaskTest {

    private static final String SUCCESS_RESULT = "";
    private static final Exception FAIL_RESULT = new Exception();
    private static final IAction<String> SUCCESS_ACTION = (ct) -> { Thread.sleep(1000); return SUCCESS_RESULT; };
    private static final IAction<String> CANCEL_ACTION = (ct) -> { Thread.sleep(1000); ct.cancel(); ct.abortIfCancelRequested(); throw FAIL_RESULT; };
    private static final IAction<String> FAIL_ACTION = (ct) -> { Thread.sleep(1000); throw FAIL_RESULT; };



    @Test
    public void getTaskTest() {
        String id = UUID.randomUUID().toString();
        Task<Task<String>> task = new Task<>(() -> null);
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        UnwrapTask<String> unwrapTask = new UnwrapTask<>(id, task, cancellationToken, scheduler, options);

        assertSame(task, unwrapTask.getTask());
    }

    @Test
    public void constructorsTest() {
        String id = UUID.randomUUID().toString();
        Task<Task<String>> task = new Task<>(() -> null);
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        UnwrapTask<String> unwrapTask = new UnwrapTask<>(id, task, cancellationToken, scheduler, options);

        assertSame(id, unwrapTask.getId());
        assertSame(task, unwrapTask.getTask());
        assertTrue(unwrapTask.getContextExecutor() instanceof UnwrapContextExecutor);
        assertSame(task, ((UnwrapContextExecutor<?>)(unwrapTask.getContextExecutor())).getTask());
        assertSame(cancellationToken, unwrapTask.getCancellationToken());
        assertSame(scheduler, unwrapTask.getScheduler());
        assertEquals(Arrays.asList(options), unwrapTask.getOptions().getOptions());
        assertSame(task, unwrapTask.getTask());
    }

    @Test
    public void executionEndWithSuccessTest() throws Exception {
        Task<String> innerTask = new Task<>(SUCCESS_ACTION);
        Task<Task<String>> outerTask = new Task<>(() -> innerTask);

        innerTask.start();
        outerTask.start();

        UnwrapTask<String> task = new UnwrapTask<>("", outerTask, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), new UnwrapAction<>(outerTask), task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.SUCCEEDED, SUCCESS_RESULT, null);
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executionEndWithCancelTest() throws Exception {
        Task<String> innerTask = new Task<>(CANCEL_ACTION);
        Task<Task<String>> outerTask = new Task<>(() -> innerTask);

        innerTask.start();
        outerTask.start();

        UnwrapTask<String> task = new UnwrapTask<>("", outerTask, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), new UnwrapAction<>(outerTask), task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);


        outerTask = new Task<>((IAction<Task<String>>) (ct) -> { ct.cancel(); ct.abortIfCancelRequested(); return null; });
        outerTask.start();

        task = new UnwrapTask<>("", outerTask, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();

        template = TaskTestUtils.createTemplate(task.getId(), new UnwrapAction<>(outerTask), task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.CANCELED, null, new CancelledException(""));
        TaskTestUtils.validateTask(task, template);
    }

    @Test
    public void executionEndWithFailTest() throws Exception {
        Task<String> innerTask = new Task<>(FAIL_ACTION);
        Task<Task<String>> outerTask = new Task<>(() -> innerTask);

        innerTask.start();
        outerTask.start();

        UnwrapTask<String> task = new UnwrapTask<>("", outerTask, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();

        IContext<String> template = TaskTestUtils.createTemplate(task.getId(), new UnwrapAction<>(outerTask), task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);


        outerTask = new Task<>((IAction<Task<String>>) (ct) -> { throw FAIL_RESULT; });
        outerTask.start();

        task = new UnwrapTask<>("", outerTask, new CancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS);
        task.start();

        template = TaskTestUtils.createTemplate(task.getId(), new UnwrapAction<>(outerTask), task.getCancellationToken(), Task.DEFAULT_SCHEDULER, Task.DEFAULT_OPTIONS, State.FAILED, null, FAIL_RESULT);
        TaskTestUtils.validateTask(task, template);
    }

}
