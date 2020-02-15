package com.github.brunomndantas.tpl4j.task.parallel.action;

import com.github.brunomndantas.tpl4j.task.core.cancel.CancellationToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelVoidActionTest {

    @Test
    public void getActionsTest() {
        IParallelVoidAction<String> act = (element, token) -> {};
        ParallelVoidAction<String> action = new ParallelVoidAction<>(act);
        assertSame(act, action.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String value = "";
        CancellationToken token = new CancellationToken();
        IParallelVoidAction<String> act = (element, t) -> {
            assertSame(element, value);
            assertSame(token, t);
        };
        ParallelVoidAction<String> action = new ParallelVoidAction<>(act);

        assertNull(action.run(value, token));
    }

    @Test
    public void runFailTest() {
        Exception result = new Exception();
        IParallelVoidAction<String> act = (element, token) -> { throw result; };
        ParallelVoidAction<String> action = new ParallelVoidAction<>(act);

        try {
            action.run(null, null);
            fail("Exception should be thrown!");
        }catch (Exception e) {
            assertSame(result, e);
        }
    }

}

