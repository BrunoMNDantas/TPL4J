package com.dantas.tpl4j.task.action.link;

import com.dantas.tpl4j.task.Task;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkVoidActionTest {

    @Test
    public void getPreviousTaskTest() {
        Task<Void> task = new Task<>(() -> {});
        LinkVoidAction<Void> linkAction = new LinkVoidAction<>(task, null);

        assertSame(task, linkAction.getPreviousTask());
    }

    @Test
    public void getActionTest() {
        ILinkVoidAction<Void> action = (previous, token) -> {};
        LinkVoidAction<Void> linkAction = new LinkVoidAction<>(null, action);

        assertSame(action, linkAction.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        Task<Void> task = new Task<>(() -> {});
        ILinkVoidAction<Void> action = (previous, token) -> assertSame(task, previous);
        LinkVoidAction<Void> linkAction = new LinkVoidAction<>(task, action);

        assertNull(linkAction.run(null));
    }

    @Test
    public  void runFailTest() {
        Task<Void> task = new Task<>(() -> {});
        Exception result = new Exception();
        ILinkVoidAction<Void> action = (previous, token) -> {
            assertSame(task, previous);
            throw result;
        };
        LinkVoidAction<Void> linkAction = new LinkVoidAction<>(task, action);

        try {
            linkAction.run(null);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(result, e);
        }
    }

}
