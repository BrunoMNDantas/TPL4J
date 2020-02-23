package com.github.brunomndantas.tpl4j.helpers.parallel.job;

import com.github.brunomndantas.tpl4j.core.options.TaskOption;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.helpers.parallel.action.ParallelAction;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ParallelJobTest {

    @Test
    public void constructorTest() {
        String id = "";
        IParallelAction<String,String> act = (e, t) -> "";
        Iterator<String> iterator = Arrays.asList("").iterator();
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> {};
        Collection<TaskOption> options = new LinkedList<>();
        ParallelJob<String,String> job = new ParallelJob<>(id, act, iterator, cancellationToken, scheduler, options);

        assertSame(id, job.getTaskId());
        assertTrue(job.getAction() instanceof ParallelAction);
        assertSame(cancellationToken, job.getCancellationToken());
        assertSame(scheduler, job.getScheduler());
        assertTrue(job.getOptions().contains(TaskOption.ACCEPT_CHILDREN));
    }

}
