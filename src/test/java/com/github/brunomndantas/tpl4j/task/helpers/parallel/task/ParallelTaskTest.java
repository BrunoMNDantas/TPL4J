package com.github.brunomndantas.tpl4j.task.helpers.parallel.task;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.ParallelAction;
import com.github.brunomndantas.tpl4j.task.pool.TaskPool;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import static org.junit.Assert.*;

public class ParallelTaskTest {

    @Test
    public void getElementsTest() {
        String id = UUID.randomUUID().toString();
        IParallelAction<String,String> action = (e,t) -> "";
        Iterable<String> elements = Arrays.asList("","");
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];
        ParallelTask<String,String> task = new ParallelTask<>(id, elements, action, cancellationToken, scheduler, options);

        assertSame(elements, task.getElements());
    }

    @Test
    public void constructorsTest() {
        String id = UUID.randomUUID().toString();
        IParallelAction<String,String> action = (e,t) -> "";
        Iterable<String> elements = Arrays.asList("","");
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];
        ParallelTask<String,String> task = new ParallelTask<>(id, elements, action, cancellationToken, scheduler, options);

        assertSame(id, task.getId());
        assertSame(elements, task.getElements());
        assertTrue(task.getContext().getAction() instanceof ParallelAction);
        assertSame(elements, ((ParallelAction<String,String>)(task.getContext().getAction())).getElements());
        assertSame(cancellationToken, task.getContext().getCancellationToken());
        assertSame(scheduler, task.getContext().getScheduler());
    }

    @Test
    public void runSucceedsTest() throws Exception {
        TaskPool pool = new TaskPool(1);
        String id = "";
        IParallelAction<String,String> action = (e,t) -> { Thread.sleep(2000); return e; };
        Iterable<String> elements = Arrays.asList("1","2","3","4");
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        ParallelTask<String,String> task = new ParallelTask<>(id, elements, action, cancellationToken, scheduler, options);

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
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        ParallelTask<String,String> task = new ParallelTask<>(id, elements, action, cancellationToken, scheduler, options);

        try {
            task.start();
            task.getResult();
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertSame(exception, e);
        } finally {
            pool.close();
        }
    }

}
