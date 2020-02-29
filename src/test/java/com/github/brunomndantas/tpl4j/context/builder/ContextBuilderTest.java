package com.github.brunomndantas.tpl4j.context.builder;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class ContextBuilderTest {

    @Test
    public void getContextManager() {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        assertSame(contextManager, contextBuilder.getContextManager());
    }

    @Test
    public void constructorTest() {
        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        assertSame(contextManager, contextBuilder.getContextManager());
    }

    @Test
    public void buildContextTest() {
        String taskId = "";
        IAction<String> action = (ct) -> null;
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new IScheduler() {
            @Override public String getId() { return null; }
            @Override public void schedule(Runnable action) { }
            @Override public void close() { }
        };
        IOptions options = new IOptions() {
            @Override public Collection<Option> getOptions() { return new LinkedList<>(); }
            @Override public boolean contains(Option option) { return false; }
            @Override public boolean rejectChildren() { return false; }
            @Override public boolean attachToParent() { return false; }
            @Override public boolean notCancelable() { return false; }
        };

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        Context<String> context = contextBuilder.build(taskId, action, cancellationToken, scheduler, options);

        assertSame(taskId, context.getTaskId());
        assertSame(action, context.getAction());
        assertSame(cancellationToken, context.getCancellationToken());
        assertSame(scheduler, context.getScheduler());
        assertSame(options, context.getOptions());
        assertNotNull(context.getStatus());
        assertNotNull(context.getChildrenContexts());

        assertSame(Thread.currentThread().getId(), context.getCreatorThreadId());
    }

    @Test
    public void buildContextWithChildTest() {
        String parentTaskId = "parent";
        String childTaskId = "child";
        IAction<String> action = (ct) -> null;
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new IScheduler() {
            @Override public String getId() { return null; }
            @Override public void schedule(Runnable action) { }
            @Override public void close() { }
        };
        IOptions options = new IOptions() {
            @Override public Collection<Option> getOptions() { return new LinkedList<>(); }
            @Override public boolean contains(Option option) { return false; }
            @Override public boolean rejectChildren() { return false; }
            @Override public boolean attachToParent() { return false; }
            @Override public boolean notCancelable() { return false; }
        };

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        Context<String> parentContext = contextBuilder.build(parentTaskId, action, cancellationToken, scheduler, options);

        contextManager.registerCurrentThreadAsExecutorOfContext(parentContext);

        Context<String> childContext = contextBuilder.build(childTaskId, action, cancellationToken, scheduler, options);

        assertSame(parentContext, childContext.getParentContext());
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
    }

}
