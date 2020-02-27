package com.github.brunomndantas.tpl4j.context;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.Status;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class ContextTest {

    @Test
    public void getTaskIdTest() {
        String taskId = "";
        Context<String> context = new Context<>(taskId, null, null, null, null, null, null, null, 0, 0, null, null);
        assertSame(taskId, context.getTaskId());
    }

    @Test
    public void getActionTest() {
        IAction<String> action = (ct) -> null;
        Context<String> context = new Context<>(null, action, null, null, null, null, null, null, 0, 0, null, null);
        assertSame(action, context.getAction());
    }

    @Test
    public void getCancellationTokenTest() {
        CancellationToken cancellationToken = new CancellationToken();
        Context<String> context = new Context<>(null, null, cancellationToken, null, null, null, null, null, 0, 0, null, null);
        assertSame(cancellationToken, context.getCancellationToken());
    }

    @Test
    public void getSchedulerTest() {
        IScheduler scheduler = new IScheduler() {
            @Override public String getId() { return null; }
            @Override public void schedule(Runnable action) { }
            @Override public void close() { }
        };
        Context<String> context = new Context<>(null, null, null, scheduler, null, null, null, null, 0, 0, null, null);
        assertSame(scheduler, context.getScheduler());
    }

    @Test
    public void getOptionsTest() {
        IOptions options = new IOptions() {
            @Override public Collection<Option> getOptions() { return null; }
            @Override public boolean contains(Option option) { return false; }
            @Override public boolean rejectChildren() { return false; }
            @Override public boolean attachToParent() { return false; }
            @Override public boolean notCancelable() { return false; }
        };
        Context<String> context = new Context<>(null, null, null, null, options, null, null, null, 0, 0, null, null);
        assertSame(options, context.getOptions());
    }

    @Test
    public void getStatusTest() {
        Status status = new Status("");
        Context<String> context = new Context<>(null, null, null, null, null, status, null, null, 0, 0, null, null);
        assertSame(status, context.getStatus());
    }

    @Test
    public void getParentTaskIdTest() {
        String parentTaskId = "";
        Context<String> context = new Context<>(null, null, null, null, null, null, parentTaskId, null, 0, 0, null, null);
        assertSame(parentTaskId, context.getParentTaskId());
    }

    @Test
    public void setParentTaskIdTest() {
        String parentTaskId = "";
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, null);

        context.setParentTaskId(parentTaskId);

        assertSame(parentTaskId, context.getParentTaskId());
    }

    @Test
    public void getChildrenTasksIdsTest() {
        Collection<String> childrenTasksIds = new LinkedList<>();
        Context<String> context = new Context<>(null, null, null, null, null, null, null, childrenTasksIds, 0, 0, null, null);
        assertSame(childrenTasksIds, context.getChildrenTasksIds());
    }

    @Test
    public void setChildrenTasksIdsTest() {
        Collection<String> childrenTasksIds = new LinkedList<>();
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, null);

        context.setChildrenTasksIds(childrenTasksIds);

        assertSame(childrenTasksIds, context.getChildrenTasksIds());
    }

    @Test
    public void getCreatorThreadIdTest() {
        long creatorThreadId = 1;
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, creatorThreadId, 0, null, null);
        assertEquals(creatorThreadId, context.getCreatorThreadId());
    }

    @Test
    public void setCreatorThreadIdTest() {
        long creatorThreadId = 1;
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, null);

        context.setCreatorThreadId(creatorThreadId);

        assertEquals(creatorThreadId, context.getCreatorThreadId());
    }

    @Test
    public void getExecutorThreadIdTest() {
        long executorThreadId = 1;
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, executorThreadId, null, null);
        assertEquals(executorThreadId, context.getExecutorThreadId());
    }

    @Test
    public void setExecutorThreadIdTest() {
        long executorThreadId = 1;
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, null);

        context.setExecutorThreadId(executorThreadId);

        assertEquals(executorThreadId, context.getExecutorThreadId());
    }

    @Test
    public void getResultValueTest() {
        String resultValue = "";
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, resultValue, null);
        assertSame(resultValue, context.getResultValue());
    }

    @Test
    public void setResultValueTest() {
        String resultValue = "";
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, null);

        context.setResultValue(resultValue);

        assertSame(resultValue, context.getResultValue());
    }

    @Test
    public void getResultExceptionTest() {
        Exception resultException = new Exception();
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, resultException);
        assertSame(resultException, context.getResultException());
    }

    @Test
    public void setResultExceptionTest() {
        Exception resultException = new Exception();
        Context<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, null);

        context.setResultException(resultException);

        assertSame(resultException, context.getResultException());
    }

    @Test
    public void constructorTest() {
        String taskId = "";
        IAction<String> action = (ct) -> null;
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new IScheduler() {
            @Override public String getId() { return null; }
            @Override public void schedule(Runnable action) { }
            @Override public void close() { }
        };
        IOptions options = new IOptions() {
            @Override public Collection<Option> getOptions() { return null; }
            @Override public boolean contains(Option option) { return false; }
            @Override public boolean rejectChildren() { return false; }
            @Override public boolean attachToParent() { return false; }
            @Override public boolean notCancelable() { return false; }
        };
        Status status = new Status("");
        String parentTaskId = "";
        Collection<String> childrenTasksIds = new LinkedList<>();
        long creatorThreadId = 1;
        long executorThreadId = 2;
        String resultValue = "";
        Exception resultException = new Exception();

        Context<String> context = new Context<>(taskId, action, cancellationToken, scheduler, options, status, parentTaskId, childrenTasksIds, creatorThreadId, executorThreadId, resultValue, resultException);

        assertSame(taskId, context.getTaskId());
        assertSame(action, context.getAction());
        assertSame(cancellationToken, context.getCancellationToken());
        assertSame(scheduler, context.getScheduler());
        assertSame(options, context.getOptions());
        assertSame(status, context.getStatus());
        assertSame(parentTaskId, context.getParentTaskId());
        assertSame(childrenTasksIds, context.getChildrenTasksIds());
        assertEquals(creatorThreadId, context.getCreatorThreadId());
        assertEquals(executorThreadId, context.getExecutorThreadId());
        assertSame(resultValue, context.getResultValue());
        assertSame(resultException, context.getResultException());
    }

    @Test
    public void hasChildTest() {
        String childTaskId = "";
        Context<String> context = new Context<>(null, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);

        assertFalse(context.hasChild(childTaskId));

        context.getChildrenTasksIds().add(childTaskId);

        assertTrue(context.hasChild(childTaskId));
    }

    @Test
    public void addChildTest() {
        String childTaskId = "";
        Context<String> context = new Context<>(null, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);

        assertFalse(context.hasChild(childTaskId));

        context.addChild(childTaskId);

        assertTrue(context.hasChild(childTaskId));
    }

}
