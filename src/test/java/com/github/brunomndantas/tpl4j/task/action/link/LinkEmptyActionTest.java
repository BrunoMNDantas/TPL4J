package com.github.brunomndantas.tpl4j.task.action.link;

import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class LinkEmptyActionTest {

    @Test
    public void getPreviousTaskTest() {
        Task<Void> task = new Task<>(() -> {});
        LinkEmptyAction<String, Void> linkAction = new LinkEmptyAction<>(task, null);

        assertSame(task, linkAction.getPreviousTask());
    }

    @Test
    public void getActionTest() {
        ILinkEmptyAction<Void> action = () -> null;
        LinkEmptyAction<Void, Void> linkAction = new LinkEmptyAction<>(null, action);

        assertSame(action, linkAction.getAction());
    }

    @Test
    public void runSuccessTest() throws Exception {
        Task<Void> task = new Task<>(() -> {});
        String result = "";
        ILinkEmptyAction<String> action = () -> result;
        LinkEmptyAction<String, Void> linkAction = new LinkEmptyAction<>(task, action);

        assertSame(result, linkAction.run(null));
    }

    @Test
    public  void runFailTest() {
        Task<Void> task = new Task<>(() -> {});
        Exception result = new Exception();
        ILinkEmptyAction<Void> action = () -> {
            throw result;
        };
        LinkEmptyAction<Void, Void> linkAction = new LinkEmptyAction<>(task, action);

        try {
            linkAction.run(null);
            fail("Exception should be thrown!");
        } catch (Exception e) {
            assertSame(result, e);
        }
    }

}
