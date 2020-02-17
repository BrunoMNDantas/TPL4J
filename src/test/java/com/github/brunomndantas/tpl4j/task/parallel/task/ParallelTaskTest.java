package com.github.brunomndantas.tpl4j.task.parallel.task;

import com.github.brunomndantas.tpl4j.pool.TaskPool;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.core.job.ChildException;
import com.github.brunomndantas.tpl4j.task.parallel.action.*;
import com.github.brunomndantas.tpl4j.task.parallel.job.ParallelJob;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ParallelTaskTest {

    @Test
    public void constructorsTest() {
        String id = "";
        IParallelAction<String,String> action = (e,t) -> "";
        IParallelVoidAction<String> voidAction = (e, t) -> { };
        IParallelUninterruptibleAction<String,String> uninterruptibleAction = (e) -> "";
        IParallelUninterruptibleVoidAction<String> uninterruptibleVoidAction = (e) -> { };
        Iterable<String> elements = Arrays.asList("","");
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> {};
        TaskOption[] options = new TaskOption[0];
        ParallelTask<String,String> task;

        task = new ParallelTask<>(id, elements, action, cancellationToken, scheduler, options);
        validate(task, id, elements, action, cancellationToken, scheduler, options);
        task = new ParallelTask<>(id, elements, voidAction, cancellationToken, scheduler, options);
        validate(task, id, elements, voidAction, cancellationToken, scheduler, options);
        task = new ParallelTask<>(id, elements, uninterruptibleAction, cancellationToken, scheduler, options);
        validate(task, id, elements, uninterruptibleAction, cancellationToken, scheduler, options);
        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, cancellationToken, scheduler, options);
        validate(task, id, elements, uninterruptibleVoidAction, cancellationToken, scheduler, options);

        task = new ParallelTask<>(id, elements, action, scheduler, options);
        validate(task, id, elements, action, null, scheduler, options);
        task = new ParallelTask<>(id, elements, voidAction, scheduler, options);
        validate(task, id, elements, voidAction, null, scheduler, options);
        task = new ParallelTask<>(id, elements, uninterruptibleAction, scheduler, options);
        validate(task, id, elements, uninterruptibleAction, null, scheduler, options);
        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, scheduler, options);
        validate(task, id, elements, uninterruptibleVoidAction, null, scheduler, options);

        task = new ParallelTask<>(id, elements, action, cancellationToken, scheduler);
        validate(task, id, elements, action, cancellationToken, scheduler, null);
        task = new ParallelTask<>(id, elements, voidAction, cancellationToken, scheduler);
        validate(task, id, elements, voidAction, cancellationToken, scheduler, null);
        task = new ParallelTask<>(id, elements, uninterruptibleAction, cancellationToken, scheduler);
        validate(task, id, elements, uninterruptibleAction, cancellationToken, scheduler, null);
        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, cancellationToken, scheduler);
        validate(task, id, elements, uninterruptibleVoidAction, cancellationToken, scheduler, null);

        task = new ParallelTask<>(id, elements, action, scheduler);
        validate(task, id, elements, action, null, scheduler, null);
        task = new ParallelTask<>(id, elements, voidAction, scheduler);
        validate(task, id, elements, voidAction, null, scheduler, null);
        task = new ParallelTask<>(id, elements, uninterruptibleAction, scheduler);
        validate(task, id, elements, uninterruptibleAction, null, scheduler, null);
        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, scheduler);
        validate(task, id, elements, uninterruptibleVoidAction, null, scheduler, null);

        task = new ParallelTask<>(id, elements, action, cancellationToken, options);
        validate(task, id, elements, action, cancellationToken, null, options);
        task = new ParallelTask<>(id, elements, voidAction, cancellationToken, options);
        validate(task, id, elements, voidAction, cancellationToken, null, options);
        task = new ParallelTask<>(id, elements, uninterruptibleAction, cancellationToken, options);
        validate(task, id, elements, uninterruptibleAction, cancellationToken, null, options);
        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, cancellationToken, options);
        validate(task, id, elements, uninterruptibleVoidAction, cancellationToken, null, options);

        task = new ParallelTask<>(id, elements, action, options);
        validate(task, id, elements, action, null, null, options);
        task = new ParallelTask<>(id, elements, voidAction, options);
        validate(task, id, elements, voidAction, null, null, options);
        task = new ParallelTask<>(id, elements, uninterruptibleAction, options);
        validate(task, id, elements, uninterruptibleAction, null, null, options);
        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, options);
        validate(task, id, elements, uninterruptibleVoidAction, null, null, options);

        task = new ParallelTask<>(id, elements, action, cancellationToken);
        validate(task, id, elements, action, cancellationToken, null, null);
        task = new ParallelTask<>(id, elements, voidAction, cancellationToken);
        validate(task, id, elements, voidAction, cancellationToken, null, null);
        task = new ParallelTask<>(id, elements, uninterruptibleAction, cancellationToken);
        validate(task, id, elements, uninterruptibleAction, cancellationToken, null, null);
        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, cancellationToken);
        validate(task, id, elements, uninterruptibleVoidAction, cancellationToken, null, null);

        task = new ParallelTask<>(id, elements, action);
        validate(task, id, elements, action, null, null, null);
        task = new ParallelTask<>(id, elements, voidAction);
        validate(task, id, elements, voidAction, null, null, null);
        task = new ParallelTask<>(id, elements, uninterruptibleAction);
        validate(task, id, elements, uninterruptibleAction, null, null, null);
        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction);
        validate(task, id, elements, uninterruptibleVoidAction, null, null, null);

        
        task = new ParallelTask<>(elements, action, cancellationToken, scheduler, options);
        validate(task, null, elements, action, cancellationToken, scheduler, options);
        task = new ParallelTask<>(elements, voidAction, cancellationToken, scheduler, options);
        validate(task, null, elements, voidAction, cancellationToken, scheduler, options);
        task = new ParallelTask<>(elements, uninterruptibleAction, cancellationToken, scheduler, options);
        validate(task, null, elements, uninterruptibleAction, cancellationToken, scheduler, options);
        task = new ParallelTask<>(elements, uninterruptibleVoidAction, cancellationToken, scheduler, options);
        validate(task, null, elements, uninterruptibleVoidAction, cancellationToken, scheduler, options);

        task = new ParallelTask<>(elements, action, scheduler, options);
        validate(task, null, elements, action, null, scheduler, options);
        task = new ParallelTask<>(elements, voidAction, scheduler, options);
        validate(task, null, elements, voidAction, null, scheduler, options);
        task = new ParallelTask<>(elements, uninterruptibleAction, scheduler, options);
        validate(task, null, elements, uninterruptibleAction, null, scheduler, options);
        task = new ParallelTask<>(elements, uninterruptibleVoidAction, scheduler, options);
        validate(task, null, elements, uninterruptibleVoidAction, null, scheduler, options);

        task = new ParallelTask<>(elements, action, cancellationToken, scheduler);
        validate(task, null, elements, action, cancellationToken, scheduler, null);
        task = new ParallelTask<>(elements, voidAction, cancellationToken, scheduler);
        validate(task, null, elements, voidAction, cancellationToken, scheduler, null);
        task = new ParallelTask<>(elements, uninterruptibleAction, cancellationToken, scheduler);
        validate(task, null, elements, uninterruptibleAction, cancellationToken, scheduler, null);
        task = new ParallelTask<>(elements, uninterruptibleVoidAction, cancellationToken, scheduler);
        validate(task, null, elements, uninterruptibleVoidAction, cancellationToken, scheduler, null);

        task = new ParallelTask<>(elements, action, scheduler);
        validate(task, null, elements, action, null, scheduler, null);
        task = new ParallelTask<>(elements, voidAction, scheduler);
        validate(task, null, elements, voidAction, null, scheduler, null);
        task = new ParallelTask<>(elements, uninterruptibleAction, scheduler);
        validate(task, null, elements, uninterruptibleAction, null, scheduler, null);
        task = new ParallelTask<>(elements, uninterruptibleVoidAction, scheduler);
        validate(task, null, elements, uninterruptibleVoidAction, null, scheduler, null);

        task = new ParallelTask<>(elements, action, cancellationToken, options);
        validate(task, null, elements, action, cancellationToken, null, options);
        task = new ParallelTask<>(elements, voidAction, cancellationToken, options);
        validate(task, null, elements, voidAction, cancellationToken, null, options);
        task = new ParallelTask<>(elements, uninterruptibleAction, cancellationToken, options);
        validate(task, null, elements, uninterruptibleAction, cancellationToken, null, options);
        task = new ParallelTask<>(elements, uninterruptibleVoidAction, cancellationToken, options);
        validate(task, null, elements, uninterruptibleVoidAction, cancellationToken, null, options);

        task = new ParallelTask<>(elements, action, options);
        validate(task, null, elements, action, null, null, options);
        task = new ParallelTask<>(elements, voidAction, options);
        validate(task, null, elements, voidAction, null, null, options);
        task = new ParallelTask<>(elements, uninterruptibleAction, options);
        validate(task, null, elements, uninterruptibleAction, null, null, options);
        task = new ParallelTask<>(elements, uninterruptibleVoidAction, options);
        validate(task, null, elements, uninterruptibleVoidAction, null, null, options);

        task = new ParallelTask<>(elements, action, cancellationToken);
        validate(task, null, elements, action, cancellationToken, null, null);
        task = new ParallelTask<>(elements, voidAction, cancellationToken);
        validate(task, null, elements, voidAction, cancellationToken, null, null);
        task = new ParallelTask<>(elements, uninterruptibleAction, cancellationToken);
        validate(task, null, elements, uninterruptibleAction, cancellationToken, null, null);
        task = new ParallelTask<>(elements, uninterruptibleVoidAction, cancellationToken);
        validate(task, null, elements, uninterruptibleVoidAction, cancellationToken, null, null);

        task = new ParallelTask<>(elements, action);
        validate(task, null, elements, action, null, null, null);
        task = new ParallelTask<>(elements, voidAction);
        validate(task, null, elements, voidAction, null, null, null);
        task = new ParallelTask<>(elements, uninterruptibleAction);
        validate(task, null, elements, uninterruptibleAction, null, null, null);
        task = new ParallelTask<>(elements, uninterruptibleVoidAction);
        validate(task, null, elements, uninterruptibleVoidAction, null, null, null);
    }

    private void validate(ParallelTask<String,String> task, String id, Iterable<String> elements, IParallelAction<String,String> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id != null)
            assertSame(id, task.getJob().getTaskId());
        else
            assertNotNull(task.getId());

        assertTrue(task.getJob() instanceof ParallelJob);

        assertSame(action, ((ParallelAction)task.getJob().getAction()).getAction());

        if(cancellationToken != null)
            assertSame(cancellationToken, task.getCancellationToken());
        else
            assertNotNull(task.getCancellationToken());

        if(scheduler != null)
            assertSame(scheduler, task.getJob().getScheduler());
        else
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());

        if(options != null)
            assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        else
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
    }

    private void validate(ParallelTask<String,String> task, String id, Iterable<String> elements, IParallelVoidAction<String> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id != null)
            assertSame(id, task.getJob().getTaskId());
        else
            assertNotNull(task.getId());

        assertTrue(task.getJob() instanceof ParallelJob);

        assertSame(action, ((ParallelVoidAction)((ParallelAction)task.getJob().getAction()).getAction()).getAction());

        if(cancellationToken != null)
            assertSame(cancellationToken, task.getCancellationToken());
        else
            assertNotNull(task.getCancellationToken());

        if(scheduler != null)
            assertSame(scheduler, task.getJob().getScheduler());
        else
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());

        if(options != null)
            assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        else
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
    }

    private void validate(ParallelTask<String,String> task, String id, Iterable<String> elements, IParallelUninterruptibleAction<String,String> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id != null)
            assertSame(id, task.getJob().getTaskId());
        else
            assertNotNull(task.getId());

        assertTrue(task.getJob() instanceof ParallelJob);

        assertSame(action, ((ParallelUninterruptibleAction)((ParallelAction)task.getJob().getAction()).getAction()).getAction());

        if(cancellationToken != null)
            assertSame(cancellationToken, task.getCancellationToken());
        else
            assertNotNull(task.getCancellationToken());

        if(scheduler != null)
            assertSame(scheduler, task.getJob().getScheduler());
        else
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());

        if(options != null)
            assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        else
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
    }

    private void validate(ParallelTask<String,String> task, String id, Iterable<String> elements, IParallelUninterruptibleVoidAction<String> action, CancellationToken cancellationToken, Consumer<Runnable> scheduler, TaskOption... options) {
        if(id != null)
            assertSame(id, task.getJob().getTaskId());
        else
            assertNotNull(task.getId());

        assertTrue(task.getJob() instanceof ParallelJob);

        assertSame(action, ((ParallelUninterruptibleVoidAction)((ParallelAction)task.getJob().getAction()).getAction()).getAction());

        if(cancellationToken != null)
            assertSame(cancellationToken, task.getCancellationToken());
        else
            assertNotNull(task.getCancellationToken());

        if(scheduler != null)
            assertSame(scheduler, task.getJob().getScheduler());
        else
            assertSame(Task.DEFAULT_SCHEDULER, task.getScheduler());

        if(options != null)
            assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
        else
            assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN
    }

    @Test
    public void runSucceedsTest() throws Exception {
        TaskPool pool = new TaskPool(1);
        String id = "";
        IParallelAction<String,String> action = (e,t) -> { Thread.sleep(2000); return e; };
        Iterable<String> elements = Arrays.asList("1","2","3","4");
        Consumer<Runnable> scheduler = pool.getScheduler();
        TaskOption[] options = new TaskOption[0];

        ParallelTask<String,String> task = new ParallelTask<>(id, elements, action, scheduler, options);

        try {
            task.start();
            Collection<String> result = task.getResult();
            assertTrue(result.containsAll(Arrays.asList("1","2","3","4")));
        } finally {
            pool.close();
        }
    }

    @Test
    public void runFailsTest() {
        TaskPool pool = new TaskPool(4);
        String id = "";
        Exception exception = new Exception();
        IParallelAction<String,String> action = (e,t) -> { Thread.sleep(2000); throw exception; };
        Iterable<String> elements = Arrays.asList("1","2","3","4");
        Consumer<Runnable> scheduler = pool.getScheduler();
        TaskOption[] options = new TaskOption[0];

        ParallelTask<String,String> task = new ParallelTask<>(id, elements, action, scheduler, options);

        try {
            task.start();
            task.getResult();
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertTrue(e instanceof ChildException);
            assertSame(exception, e.getCause());
        } finally {
            pool.close();
        }
    }

}
