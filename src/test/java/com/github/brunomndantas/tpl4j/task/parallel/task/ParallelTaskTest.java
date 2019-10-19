package com.github.brunomndantas.tpl4j.task.parallel.task;

import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelVoidAction;
import com.github.brunomndantas.tpl4j.task.parallel.job.ParallelJob;
import com.github.brunomndantas.tpl4j.pool.TaskPool;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import com.github.brunomndantas.tpl4j.task.core.job.ChildException;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ParallelTaskTest {

    @Test
    public void constructorsTest() {
        String id = "";
        IParallelAction<String,String> action = (e,t) -> "";
        IParallelVoidAction<String> voidAction = (e, t) -> { };
        IParallelUninterruptibleAction<String,String> uninterruptibleAction = (e) -> "";
        IParallelUninterruptibleVoidAction<String> uninterruptiblevoidAction = (e) -> { };
        Iterator<String> iterator = Arrays.asList("","").iterator();
        Consumer<Runnable> scheduler = (r) -> {};
        TaskOption[] options = new TaskOption[0];
        ParallelTask<String,String> task;

        task = new ParallelTask<>(id, action, iterator, scheduler, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, voidAction, iterator, scheduler, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, uninterruptibleAction, iterator, scheduler, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, uninterruptiblevoidAction, iterator, scheduler, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(id, action, iterator, scheduler);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, voidAction, iterator, scheduler);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, uninterruptibleAction, iterator, scheduler);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, uninterruptiblevoidAction, iterator, scheduler);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(id, action, iterator, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, voidAction, iterator, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, uninterruptibleAction, iterator, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, uninterruptiblevoidAction, iterator, options);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(id, action, iterator);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, voidAction, iterator);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, uninterruptibleAction, iterator);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(id, uninterruptiblevoidAction, iterator);
        assertSame(id, task.getJob().getTaskId());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(action, iterator, scheduler, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(voidAction, iterator, scheduler, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(uninterruptibleAction, iterator, scheduler, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(uninterruptiblevoidAction, iterator, scheduler, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(action, iterator, scheduler);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(voidAction, iterator, scheduler);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(uninterruptibleAction, iterator, scheduler);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(uninterruptiblevoidAction, iterator, scheduler);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(action, iterator, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(voidAction, iterator, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(uninterruptibleAction, iterator, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(uninterruptiblevoidAction, iterator, options);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(options.length+1, task.getJob().getOptions().size()); //ACCEPT_CHILDREN


        task = new ParallelTask<>(action, iterator);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(voidAction, iterator);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(uninterruptibleAction, iterator);
        assertNotNull(task.getJob().getTaskId());
        assertFalse(task.getJob().getTaskId().isEmpty());
        assertTrue(task.getJob() instanceof ParallelJob);
        assertSame(Task.DEFAULT_SCHEDULER, task.getJob().getScheduler());
        assertEquals(Task.DEFAULT_OPTIONS.length, task.getJob().getOptions().size()); //ACCEPT_CHILDREN

        task = new ParallelTask<>(uninterruptiblevoidAction, iterator);
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
        Iterator<String> iterator = Arrays.asList("1","2","3","4").iterator();
        Consumer<Runnable> scheduler = pool.getScheduler();
        TaskOption[] options = new TaskOption[0];

        ParallelTask<String,String> task = new ParallelTask<>(id, action, iterator, scheduler, options);

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
        Iterator<String> iterator = Arrays.asList("1","2","3","4").iterator();
        Consumer<Runnable> scheduler = pool.getScheduler();
        TaskOption[] options = new TaskOption[0];

        ParallelTask<String,String> task = new ParallelTask<>(id, action, iterator, scheduler, options);

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
