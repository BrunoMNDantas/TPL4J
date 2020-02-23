package com.github.brunomndantas.tpl4j.helpers.parallel.job;

import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.ParallelWorkerAction;
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
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> {};
        Collection<Option> options = new LinkedList<>();
        ParallelWorkerJob<String,String> job = new ParallelWorkerJob<>(id, act, iterator, cancellationToken, scheduler, options);

        assertSame(id, job.getTaskId());
        assertTrue(job.getAction() instanceof ParallelWorkerAction);
        assertSame(act, ((ParallelWorkerAction<Object, String>)job.getAction()).getAction());
        assertSame(iterator, ((ParallelWorkerAction<Object, String>)job.getAction()).getIterator());
        assertSame(cancellationToken, job.getCancellationToken());
        assertSame(scheduler, job.getScheduler());
        assertEquals(options.size(), job.getOptions().size());
    }

}
