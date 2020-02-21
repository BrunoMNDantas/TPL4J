package com.github.brunomndantas.tpl4j.task.action.link;

import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class LinkActionTest {

    @Test
    public void getPreviousTaskTest() {
        Task<Void> task = new Task<>(() -> {});
        LinkAction<String,Void> linkAction = new LinkAction<>(task, null);

        assertSame(task, linkAction.getPreviousTask());
    }

    @Test
    public void getActionTest() {
        ILinkAction<String,Void> action = (previous, token) -> null;
        LinkAction<String,Void> linkAction = new LinkAction<>(null, action);

        assertSame(action, linkAction.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        Task<Void> task = new Task<>(() -> {});
        String result = "";
        ILinkAction<String,Void> action = (previous, token) -> {
            assertSame(task, previous);
            return result;
        };
        LinkAction<String,Void> linkAction = new LinkAction<>(task, action);

        assertSame(result, linkAction.run(null));
    }

    @Test
    public  void runFailTest() {
        Task<Void> task = new Task<>(() -> {});
        Exception result = new Exception();
        ILinkAction<String,Void> action = (previous, token) -> {
            assertSame(task, previous);
            throw result;
        };
        LinkAction<String,Void> linkAction = new LinkAction<>(task, action);

        try {
            linkAction.run(null);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(result, e);
        }
    }

}
