package com.github.brunomndantas.tpl4j.task.helpers.unwrap;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.task.Task;
import com.github.brunomndantas.tpl4j.transversal.TestUtils;
import org.junit.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

public class UnwrapActionTest {
    
    @Test
    public void getTaskTest() {
        Task<String> t = new Task<>(() -> null);
        Task<Task<String>> task = new Task<>(() -> t);

        UnwrapAction<String> action = new UnwrapAction<>(task);

        assertSame(task, action.getTask());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String result = "";
        Task<String> t = new Task<>(() -> result);
        Task<Task<String>> task = new Task<>("", (token) -> t, new CancellationToken(), TestUtils.SCHEDULER);

        t.start();
        t.getFinishedEvent().await();

        task.start();
        task.getFinishedEvent().await();

        UnwrapAction<String> action = new UnwrapAction<>(task);

        assertSame(result, action.run(new CancellationToken()));
    }

    @Test
    public void runFailTaskTest() throws InterruptedException {
        Exception result = new Exception();
        Task<Task<String>> task = new Task<>("", (IAction<Task<String>>)(token) -> { throw result; }, new CancellationToken(), TestUtils.SCHEDULER);

        task.start();
        task.getFinishedEvent().await();

        UnwrapAction<String> action = new UnwrapAction<>(task);

        try {
            action.run(new CancellationToken());
            fail("Exception should be thrown!");
        }catch(Exception e) {
            assertSame(result, e);
        }
    }

    @Test
    public void runFailRootTaskTest() throws InterruptedException {
        Exception result = new Exception();
        Task<String> t = new Task<>(() -> { throw result; });
        Task<Task<String>> task = new Task<>("", (token) -> t, new CancellationToken(), TestUtils.SCHEDULER);

        t.start();
        t.getFinishedEvent().await();

        task.start();
        task.getFinishedEvent().await();

        UnwrapAction<String> action = new UnwrapAction<>(task);

        try {
            action.run(new CancellationToken());
            fail("Exception should be thrown!");
        }catch(Exception e) {
            assertSame(result, e);
        }
    }

    @Test(expected = CancelledException.class)
    public void runCancelTaskTest() throws Exception {
        Exception result = new Exception();
        Task<Task<String>> task = new Task<>("", (IAction<Task<String>>) (token) -> { throw result; }, new CancellationToken(), TestUtils.SCHEDULER);

        task.cancel();
        task.start();
        task.getFinishedEvent().await();

        UnwrapAction<String> action = new UnwrapAction<>(task);

        action.run(new CancellationToken());
    }

    @Test(expected = CancelledException.class)
    public void runCancelRootTaskTest() throws Exception {
        String result = "";
        Task<String> t = new Task<>(() -> result);
        Task<Task<String>> task = new Task<>("", (token) -> t, new CancellationToken(), TestUtils.SCHEDULER);

        t.cancel();
        t.start();
        t.getFinishedEvent().await();

        task.start();
        task.getFinishedEvent().await();

        UnwrapAction<String> action = new UnwrapAction<>(task);

        action.run(new CancellationToken());
    }

}
