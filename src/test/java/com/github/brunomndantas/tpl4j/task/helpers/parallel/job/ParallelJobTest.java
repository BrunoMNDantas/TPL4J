package com.github.brunomndantas.tpl4j.task.helpers.parallel.job;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.ParallelAction;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ParallelJobTest {

    @Test
    public void getElementsTest() {
        Iterable<String> elements = Arrays.asList("");
        ParallelJob<String,String> job = new ParallelJob<>(null, null, elements, null, null, new LinkedList<>());

        assertSame(elements, job.getElements());
    }

    @Test
    public void constructorTest() {
        String id = "";
        IParallelAction<String,String> act = (e, t) -> "";
        Iterable<String> elements = Arrays.asList("");
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> {};
        Collection<Option> options = new LinkedList<>();
        ParallelJob<String,String> job = new ParallelJob<>(id, act, elements, cancellationToken, scheduler, options);

        assertSame(id, job.getTaskId());
        assertSame(elements, job.getElements());
        assertTrue(job.getAction() instanceof ParallelAction);
        assertSame(cancellationToken, job.getCancellationToken());
        assertSame(scheduler, job.getScheduler());
    }

}