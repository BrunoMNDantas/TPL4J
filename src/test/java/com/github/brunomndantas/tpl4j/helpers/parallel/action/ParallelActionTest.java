package com.github.brunomndantas.tpl4j.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.core.options.TaskOption;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ParallelActionTest {

    @Test
    public void constructorTest() {
        String id = "";
        IParallelAction<String,String> act = (e, t) -> "";
        Iterator<String> iterator = Arrays.asList("").iterator();
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> {};
        Collection<TaskOption> options = new LinkedList<>();
        ParallelAction<String,String> action = new ParallelAction<>(id, act, iterator, cancellationToken, scheduler, options);

        assertSame(id, action.getTaskId());
        assertSame(act, action.getAction());
        assertSame(iterator, action.getIterator());
        assertSame(cancellationToken, action.getCancellationToken());
        assertSame(scheduler, action.getScheduler());
        assertEquals(TaskOption.ATTACH_TO_PARENT, action.getOptions()[0]);
    }

    @Test
    public void runTest() throws Exception {
        String id = "";
        IParallelAction<String,String> act = (e, t) -> { Thread.sleep(2000); return e; };
        Iterator<String> iterator = Arrays.asList("1", "2", "3").iterator();
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = Task.DEFAULT_SCHEDULER;
        Collection<TaskOption> options = new LinkedList<>();
        ParallelAction<String,String> action = new ParallelAction<>(id, act, iterator, cancellationToken, scheduler, options);

        Collection<String> result = action.run(null);

        assertFalse(result.containsAll(Arrays.asList("1","2","3")));

        Thread.sleep(3000);

        assertTrue(result.containsAll(Arrays.asList("1","2","3")));
    }

}
