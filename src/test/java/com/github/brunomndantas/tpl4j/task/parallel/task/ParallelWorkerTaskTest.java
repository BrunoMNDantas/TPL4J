package com.github.brunomndantas.tpl4j.task.parallel.task;

import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.ParallelWorkerAction;
import com.github.brunomndantas.tpl4j.task.parallel.job.ParallelWorkerJob;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
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
        Consumer<Runnable> scheduler = (r) -> {};
        TaskOption[] options = new TaskOption[0];

        ParallelWorkerTask<String,String> task = new ParallelWorkerTask<>(id, action, iterator, scheduler, options);

        assertTrue(task.getJob() instanceof ParallelWorkerJob);
        assertSame(id, task.getJob().getTaskId());
        assertSame(action, ((ParallelWorkerAction)((ParallelWorkerJob)task.getJob()).getAction()).getAction());
        assertSame(iterator, ((ParallelWorkerAction)((ParallelWorkerJob)task.getJob()).getAction()).getIterator());
        assertSame(scheduler, task.getJob().getScheduler());
        assertEquals(options.length, task.getJob().getOptions().size()); //ATTACH_TO_PARENT
    }

}
