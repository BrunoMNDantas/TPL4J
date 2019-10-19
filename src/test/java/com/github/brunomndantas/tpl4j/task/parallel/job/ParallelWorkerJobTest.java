package com.github.brunomndantas.tpl4j.task.parallel.job;

import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.ParallelWorkerAction;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ParallelWorkerJobTest {

    @Test
    public void constructorTest() {
        String id = "";
        IParallelAction<String,String> act = (p,t) -> "";
        Iterator<String> iterator = Arrays.asList("").iterator();
        Consumer<Runnable> scheduler = (r) -> {};
        Collection<TaskOption> options = new LinkedList<>();
        ParallelWorkerJob<String,String> job = new ParallelWorkerJob<>(id, act, iterator, scheduler, options);

        assertSame(id, job.getTaskId());
        assertTrue(job.getAction() instanceof ParallelWorkerAction);
        assertSame(act, ((ParallelWorkerAction<Object, String>)job.getAction()).getAction());
        assertSame(iterator, ((ParallelWorkerAction<Object, String>)job.getAction()).getIterator());
        assertSame(scheduler, job.getScheduler());
        assertEquals(options.size(), job.getOptions().size());
    }

}
