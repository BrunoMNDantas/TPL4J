package com.github.brunomndantas.tpl4j.task.parallel.job;

import com.github.brunomndantas.tpl4j.task.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.parallel.action.ParallelAction;
import com.github.brunomndantas.tpl4j.task.core.TaskOption;
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
        Consumer<Runnable> scheduler = (r) -> {};
        Collection<TaskOption> options = new LinkedList<>();
        ParallelJob<String,String> job = new ParallelJob<>(id, act, iterator, scheduler, options);

        assertSame(id, job.getTaskId());
        assertTrue(job.getAction() instanceof ParallelAction);
        assertSame(scheduler, job.getScheduler());
        assertTrue(job.getOptions().contains(TaskOption.ACCEPT_CHILDREN));
    }

}
