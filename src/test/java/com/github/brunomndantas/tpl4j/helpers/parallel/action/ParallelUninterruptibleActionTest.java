package com.github.brunomndantas.tpl4j.helpers.parallel.action;

import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class ParallelUninterruptibleActionTest {

    @Test
    public void getActionsTest() {
        IParallelUninterruptibleAction<String,String> act = (element) -> "";
        ParallelUninterruptibleAction<String,String> action = new ParallelUninterruptibleAction<>(act);
        assertSame(act, action.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String value = "";
        String result = "";
        IParallelUninterruptibleAction<String,String> act = (element) -> {
            assertSame(element, value);
            return result;
        };
        ParallelUninterruptibleAction<String,String> action = new ParallelUninterruptibleAction<>(act);

        assertSame(result, action.run(value, null));
    }

    @Test
    public void runFailTest() {
        Exception result = new Exception();
        IParallelUninterruptibleAction<String,String> act = (element) -> {
            throw result;
        };
        ParallelUninterruptibleAction<String,String> action = new ParallelUninterruptibleAction<>(act);

        try {
            action.run(null, null);
            fail("Exception should be thrown!");
        }catch (Exception e) {
            assertSame(result, e);
        }
    }

}

