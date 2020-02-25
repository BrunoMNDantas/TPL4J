package com.github.brunomndantas.tpl4j.helpers.parallel.action;

import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.function.Consumer;

import static org.junit.Assert.*;

public class ParallelActionTest {

    @Test
    public void constructorTest() {
        String id = "";
        IParallelAction<String,String> act = (e, t) -> "";
        Iterable<String> elements = Arrays.asList("");
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = (r) -> {};
        Collection<Option> options = new LinkedList<>();
        ParallelAction<String,String> action = new ParallelAction<>(id, act, elements, cancellationToken, scheduler, options);

        assertSame(id, action.getTaskId());
        assertSame(act, action.getAction());
        assertSame(elements, action.getElements());
        assertSame(cancellationToken, action.getCancellationToken());
        assertSame(scheduler, action.getScheduler());
        assertEquals(Option.ATTACH_TO_PARENT, action.getOptions()[0]);
    }

    @Test
    public void runTest() throws Exception {
        String id = "";
        IParallelAction<String,String> act = (e, t) -> { Thread.sleep(2000); return e; };
        Iterable<String> elements = Arrays.asList("1", "2", "3");
        CancellationToken cancellationToken = new CancellationToken();
        Consumer<Runnable> scheduler = Task.DEFAULT_SCHEDULER;
        Collection<Option> options = new LinkedList<>();
        ParallelAction<String,String> action = new ParallelAction<>(id, act, elements, cancellationToken, scheduler, options);

        Collection<String> result = action.run(null);

        assertFalse(result.containsAll(Arrays.asList("1","2","3")));

        Thread.sleep(3000);

        assertTrue(result.containsAll(Arrays.asList("1","2","3")));
    }

}
