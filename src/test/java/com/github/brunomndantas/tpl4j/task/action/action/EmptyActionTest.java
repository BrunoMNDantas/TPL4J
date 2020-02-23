package com.github.brunomndantas.tpl4j.task.action.action;

import com.github.brunomndantas.tpl4j.task.action.action.EmptyAction;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyAction;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class EmptyActionTest {

    @Test
    public void getActionTest() {
        IEmptyAction<?> action = () -> "";
        EmptyAction<?> emptyAction = new EmptyAction<>(action);

        assertSame(action, emptyAction.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String result = "";
        IEmptyAction<?> action = () -> result;
        EmptyAction<?> emptyAction = new EmptyAction<>(action);

        assertSame(result, emptyAction.run(null));
    }

    @Test
    public void runFailTest() {
        Exception result = new Exception();
        IEmptyAction<?> action = () -> { throw result; };
        EmptyAction<?> emptyAction = new EmptyAction<>(action);

        try {
            emptyAction.run(null);
            fail("Exception should be thrown!");
        }catch (Exception e) {
            assertSame(result, e);
        }
    }

}
