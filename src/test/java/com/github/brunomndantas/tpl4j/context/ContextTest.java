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
    public void getParentContextTest() {
        Context<?> parentContext = new Context<>("", (t)->null, null, null, null, null, null, null, 0, 0, null, null);
        Context<String> context = new Context<>(null, null, null, null, null, null, parentContext, null, 0, 0, null, null);
        assertSame(parentContext, context.getParentContext());
    }

    @Test
    public void setParentContextTest() {
        IContext<?> parentContext = new Context<>("", (t)->null, null, null, null, null, null, null, 0, 0, null, null);
        IContext<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, null);

        context.setParentContext(parentContext);

        assertSame(parentContext, context.getParentContext());
    }

    @Test
    public void getChildrenContextsTest() {
        Collection<IContext<?>> childrenContexts = new LinkedList<>();
        IContext<String> context = new Context<>(null, null, null, null, null, null, null, childrenContexts, 0, 0, null, null);
        assertSame(childrenContexts, context.getChildrenContexts());
    }

    @Test
    public void setChildrenContextsTest() {
        Collection<IContext<?>> childrenContexts = new LinkedList<>();
        IContext<String> context = new Context<>(null, null, null, null, null, null, null, null, 0, 0, null, null);

        context.setChildrenContexts(childrenContexts);

        assertSame(childrenContexts, context.getChildrenContexts());
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
        Context<?> parentContext = new Context<>("", (t)->null, null, null, null, null, null, null, 0, 0, null, null);
        Collection<IContext<?>> childrenContexts = new LinkedList<>();
        long creatorThreadId = 1;
        long executorThreadId = 2;
        String resultValue = "";
        Exception resultException = new Exception();

        IContext<String> context = new Context<>(taskId, action, cancellationToken, scheduler, options, status, parentContext, childrenContexts, creatorThreadId, executorThreadId, resultValue, resultException);

        assertSame(taskId, context.getTaskId());
        assertSame(action, context.getAction());
        assertSame(cancellationToken, context.getCancellationToken());
        assertSame(scheduler, context.getScheduler());
        assertSame(options, context.getOptions());
        assertSame(status, context.getStatus());
        assertSame(parentContext, context.getParentContext());
        assertSame(childrenContexts, context.getChildrenContexts());
        assertEquals(creatorThreadId, context.getCreatorThreadId());
        assertEquals(executorThreadId, context.getExecutorThreadId());
        assertSame(resultValue, context.getResultValue());
        assertSame(resultException, context.getResultException());
    }

    @Test
    public void hasChildTest() {
        Context<?> childContext = new Context<>(null, (t)->null, null, null, null, null, null, null, 0, 0, null, null);
        Context<String> context = new Context<>(null, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);

        assertFalse(context.hasChild(childContext));

        context.getChildrenContexts().add(childContext);

        assertTrue(context.hasChild(childContext));
    }

    @Test
    public void addChildTest() {
        Context<?> childContext = new Context<>(null, (t)->null, null, null, null, null, null, null, 0, 0, null, null);
        Context<String> context = new Context<>(null, null, null, null, null, null, null, new LinkedList<>(), 0, 0, null, null);

        assertFalse(context.hasChild(childContext));

        context.addChild(childContext);

        assertTrue(context.hasChild(childContext));
    }

}
