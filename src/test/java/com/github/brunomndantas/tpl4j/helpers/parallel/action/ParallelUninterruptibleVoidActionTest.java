package com.github.brunomndantas.tpl4j.helpers.parallel.action;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelUninterruptibleVoidActionTest {

    @Test
    public void getActionsTest() {
        IParallelUninterruptibleVoidAction<String> act = (element) -> { };
        ParallelUninterruptibleVoidAction<String> action = new ParallelUninterruptibleVoidAction<>(act);
        assertSame(act, action.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String value = "";
        IParallelUninterruptibleVoidAction<String> act = (element) -> assertSame(element, value);
        ParallelUninterruptibleVoidAction<String> action = new ParallelUninterruptibleVoidAction<>(act);

        assertNull(action.run(value, null));
    }

    @Test
    public void runFailTest() {
        Exception result = new Exception();
        IParallelUninterruptibleVoidAction<String> act = (element) -> {
            throw result;
        };
        ParallelUninterruptibleVoidAction<String> action = new ParallelUninterruptibleVoidAction<>(act);

        try {
            action.run(null, null);
            fail("Exception should be thrown!");
        }catch (Exception e) {
            assertSame(result, e);
        }
    }

}

