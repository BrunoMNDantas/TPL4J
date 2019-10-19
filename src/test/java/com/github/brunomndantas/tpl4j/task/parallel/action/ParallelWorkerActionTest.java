package com.github.brunomndantas.tpl4j.task.parallel.action;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

import static org.junit.Assert.*;

public class ParallelWorkerActionTest {

    @Test
    public void getActionTest() {
        IParallelAction<String, String> act = (element, token) -> "";
        ParallelWorkerAction<String, String> action = new ParallelWorkerAction<>(act, null);
        assertSame(act, action.getAction());
    }

    @Test
    public void getIteratorTest() {
        Iterator<String> iterator = new Iterator<String>() {
            @Override public boolean hasNext() { return false; }
            @Override public String next() { return null; }
        };
        ParallelWorkerAction<String, String> action = new ParallelWorkerAction<>(null, iterator);
        assertSame(iterator, action.getIterator());
    }

    @Test
    public void runSuccessTest() throws Exception {
        Collection<String> elements = Arrays.asList("1", "2", "3");
        Iterator<String> iterator = elements.iterator();
        IParallelAction<String, Integer> act = (element, token) -> Integer.parseInt(element);
        ParallelWorkerAction<String, Integer> action = new ParallelWorkerAction<>(act, iterator);

        Collection<Integer> result = action.run(null);

        assertTrue(result.contains(1));
        assertTrue(result.contains(2));
        assertTrue(result.contains(3));
    }

    @Test
    public void runFailTest() {
        Collection<String> elements = Arrays.asList("1", "2", "3");
        Iterator<String> iterator = elements.iterator();
        Exception result = new Exception();
        IParallelAction<String, Integer> act = (element, token) -> { throw result; };
        ParallelWorkerAction<String, Integer> action = new ParallelWorkerAction<>(act, iterator);

        try {
            action.run(null);
            fail("Exception should be thrown!");
        }catch (Exception e) {
            assertSame(result, e);
        }
    }

}
