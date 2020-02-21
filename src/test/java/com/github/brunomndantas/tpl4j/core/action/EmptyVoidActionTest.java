package com.github.brunomndantas.tpl4j.core.action;

import org.junit.Test;

import static org.junit.Assert.*;

public class EmptyVoidActionTest {

    @Test
    public void getActionTest() {
        IEmptyVoidAction action = () -> {};
        EmptyVoidAction emptyVoidAction = new EmptyVoidAction(action);

        assertSame(action, emptyVoidAction.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        IEmptyVoidAction action = () -> {};
        EmptyVoidAction emptyVoidAction = new EmptyVoidAction(action);

        assertNull(emptyVoidAction.run(null));
    }

    @Test
    public void runFailTest() {
        Exception result = new Exception();
        IEmptyVoidAction action = () -> { throw result; };
        EmptyVoidAction emptyVoidAction = new EmptyVoidAction(action);

        try {
            emptyVoidAction.run(null);
            fail("Exception should be thrown!");
        }catch (Exception e) {
            assertSame(result, e);
        }
    }

}
