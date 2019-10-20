package com.github.brunomndantas.tpl4j.task.parallel.task;

import com.github.brunomndantas.tpl4j.pool.TaskPool;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.job.ChildException;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelVoidAction;
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
        Consumer<Runnable> scheduler = (r) -> {};
        TaskOption[] options = new TaskOption[0];
        ParallelTask<String,String> task;

        task = new ParallelTask<>(id, elements, action, scheduler, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, voidAction, scheduler, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, uninterruptibleAction, scheduler, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, scheduler, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(id, elements, action, scheduler);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, voidAction, scheduler);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, uninterruptibleAction, scheduler);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, scheduler);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(id, elements, action, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, voidAction, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, uninterruptibleAction, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(id, elements, action);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, voidAction);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, uninterruptibleAction);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, elements, uninterruptibleVoidAction);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(elements, action, scheduler, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, voidAction, scheduler, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, uninterruptibleAction, scheduler, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, uninterruptibleVoidAction, scheduler, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(elements, action, scheduler);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, voidAction, scheduler);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, uninterruptibleAction, scheduler);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, uninterruptibleVoidAction, scheduler);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(elements, action, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, voidAction, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, uninterruptibleAction, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, uninterruptibleVoidAction, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(elements, action);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, voidAction);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, uninterruptibleAction);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(elements, uninterruptibleVoidAction);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
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
