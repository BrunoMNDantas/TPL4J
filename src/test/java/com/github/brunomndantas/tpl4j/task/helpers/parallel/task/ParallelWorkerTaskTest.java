package com.github.brunomndantas.tpl4j.task.helpers.parallel.task;

import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.ParallelWorkerAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.job.ParallelWorkerJob;
import org.junit.Test;

import java.util.Arrays;
import java.util.Iterator;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ParallelWorkerTaskTest {

    @Test
    public void constructorsTest() {
        String id = "";
        IParallelAction<String,String> action = (e,t) -> "";
        Iterator<String> iterator = Arrays.asList("","").iterator();
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> {};
        Option[] options = new Option[0];

        ParallelWorkerTask<String,String> task = new ParallelWorkerTask<>(id, action, iterator, cancellationToken, scheduler, options);

        assertTrue(task.getJob() instanceof ParallelWorkerJob);
        assertSame(id, task.getJob().getTaskId());
        assertSame(action, ((ParallelWorkerAction)((ParallelWorkerJob)task.getJob()).getAction()).getAction());
        assertSame(iterator, ((ParallelWorkerAction)((ParallelWorkerJob)task.getJob()).getAction()).getIterator());
        assertSame(cancellationToken, task.getJob().getCancellationToken());
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length, task.getJob().getOptions().getOptions().size());
    }

}
