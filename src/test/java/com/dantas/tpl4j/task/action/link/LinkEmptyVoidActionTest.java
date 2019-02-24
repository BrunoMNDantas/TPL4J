package com.dantas.tpl4j.task.action.link;

import com.dantas.tpl4j.task.Task;
import org.junit.Test;

import static org.junit.Assert.*;

public class LinkEmptyVoidActionTest {

    @Test
    public void getPreviousTaskTest() {
        Task<Void> task = new Task<>(() -> {});
        LinkEmptyVoidAction<Void> linkAction = new LinkEmptyVoidAction<>(task, null);

        assertSame(task, linkAction.getPreviousTask());
    }

    @Test
    public void getActionTest() {
        ILinkEmptyVoidAction action = () -> {};
        LinkEmptyVoidAction linkAction = new LinkEmptyVoidAction<>(null, action);

        assertSame(action, linkAction.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        Task<Void> task = new Task<>(() -> {});
        ILinkEmptyVoidAction action = () -> {};
        LinkEmptyVoidAction<Void> linkAction = new LinkEmptyVoidAction<>(task, action);

        assertNull(linkAction.run(null));
    }

    @Test
    public  void runFailTest() {
        Task<Void> task = new Task<>(() -> {});
        Exception result = new Exception();
        ILinkEmptyVoidAction action = () -> {
            throw result;
        };
        LinkEmptyVoidAction<Void> linkAction = new LinkEmptyVoidAction<>(task, action);

        try {
            linkAction.run(null);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(result, e);
        }
    }

}
