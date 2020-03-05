package com.github.brunomndantas.tpl4j.context.builder;

import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.context.manager.ContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import static org.junit.Assert.*;

public class ContextBuilderTest {

    @Test
    public void getContextManagerTest() {
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
        IAction<String> action = TestUtils.ACTION;
        CancellationToken cancellationToken = TestUtils.CANCELLATION_TOKEN;
        IScheduler scheduler = TestUtils.SCHEDULER;
        IOptions options = TestUtils.OPTIONS;

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<String> context = contextBuilder.build(taskId, action, cancellationToken, scheduler, options);

        assertSame(taskId, context.getTaskId());
        assertSame(action, context.getAction());
        assertSame(cancellationToken, context.getCancellationToken());
        assertSame(scheduler, context.getScheduler());
        assertSame(options, context.getOptions());
        assertNotNull(context.getStatus());
        assertNull(context.getParentContext());
        assertNotNull(context.getChildrenContexts());

        assertSame(Thread.currentThread().getId(), context.getCreatorThreadId());
    }

    @Test
    public void buildContextWithChildTest() {
        String parentTaskId = "parent";
        String childTaskId = "child";
        IAction<String> action = TestUtils.ACTION;
        CancellationToken cancellationToken = TestUtils.CANCELLATION_TOKEN;
        IScheduler scheduler = TestUtils.SCHEDULER;
        IOptions options = TestUtils.OPTIONS;

        ContextManager contextManager = new ContextManager();
        ContextBuilder contextBuilder = new ContextBuilder(contextManager);
        IContext<String> parentContext = contextBuilder.build(parentTaskId, action, cancellationToken, scheduler, options);

        contextManager.registerCurrentThreadAsExecutorOfContext(parentContext);

        IContext<String> childContext = contextBuilder.build(childTaskId, action, cancellationToken, scheduler, options);

        assertSame(parentContext, childContext.getParentContext());
        assertTrue(parentContext.getChildrenContexts().contains(childContext));
    }

}
