package com.github.brunomndantas.tpl4j.task.helpers.parallel.task;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.ParallelWorkerAction;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ParallelWorkerTaskTest {

    @Test
    public void constructorsTest() {
        String id = "";
        IParallelAction<String,String> action = (e,t) -> "";
        Iterator<String> iterator = Arrays.asList("","").iterator();
        CancellationToken cancellationToken = new CancellationToken();
        IScheduler scheduler = new DedicatedThreadScheduler();
        Option[] options = new Option[0];

        ParallelWorkerTask<String,String> task = new ParallelWorkerTask<>(id, action, iterator, cancellationToken, scheduler, options);

        assertSame(id, task.getId());
        assertTrue(task.getAction() instanceof ParallelWorkerAction);
        assertSame(iterator, ((ParallelWorkerAction<Object, String>)(task.getAction())).getIterator());
        assertSame(cancellationToken, task.getCancellationToken());
        assertSame(scheduler, task.getScheduler());
        assertEquals(options.length, task.getOptions().getOptions().size());
    }

}
