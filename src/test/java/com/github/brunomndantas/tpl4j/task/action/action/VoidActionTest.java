package com.github.brunomndantas.tpl4j.task.action.action;

import com.github.brunomndantas.tpl4j.task.action.action.IVoidAction;
import com.github.brunomndantas.tpl4j.task.action.action.VoidAction;
import org.junit.Test;

import static org.junit.Assert.*;

public class VoidActionTest {

    @Test
    public void getActionTest() {
        IVoidAction action = (token) -> {};
        VoidAction voidAction = new VoidAction(action);

        assertSame(action, voidAction.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        IVoidAction action = (token) -> {};
        VoidAction voidAction = new VoidAction(action);

        assertNull(voidAction.run(null));
    }

    @Test
    public void runFailTest() {
        Exception result = new Exception();
        IVoidAction action = (token) -> { throw result; };
        VoidAction voidAction = new VoidAction(action);

        try {
            voidAction.run(null);
            fail("Exception should be thrown!");
        }catch (Exception e) {
            assertSame(result, e);
        }
    }

}
