package com.github.brunomndantas.tpl4j.task.action.retry;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.cancel.CancelledException;
import com.github.brunomndantas.tpl4j.task.Task;
import org.junit.Test;

import java.util.Random;
import java.util.function.Supplier;

import static org.junit.Assert.*;

public class RetryActionTest {

    @Test
    public void getRetrySupplierAction() {
        Supplier<Boolean> supplier = () -> true;
        RetryAction<String> retryAction = new RetryAction<>(null, supplier);

        assertSame(supplier, retryAction.getRetrySupplier());
    }

    @Test
    public void constructorsTest() {
        Task<String> task = new Task<>(() -> null);
        Supplier<Boolean> supplier = () -> true;
        RetryAction<String> retryAction;

        retryAction = new RetryAction<>(task, supplier);
        assertSame(task, retryAction.getPreviousTask());
        assertSame(supplier, retryAction.getRetrySupplier());

        retryAction = new RetryAction<>(task, 1);
        assertSame(task, retryAction.getPreviousTask());
        assertNotNull(retryAction.getRetrySupplier());

        retryAction = new RetryAction<>(task);
        assertSame(task, retryAction.getPreviousTask());
        assertNotNull(retryAction.getRetrySupplier());
    }

    @Test
    public void runSuccessTest() throws Exception {
        String result = "";
        Task<String> task = new Task<>(() -> {
            if(new Random().nextInt(10) < 8)
                throw new Exception();
            else
                return result;
        });

        task.start();
        task.getFinishedEvent().await();

        RetryAction<String> retryAction = new RetryAction<>(task, RetryAction.RETRY_UNTIL_SUCCESS);

        assertSame(result, retryAction.run(new CancellationToken()));
    }

    @Test
    public void runFailTest() throws Exception {
        int[] count = new int[1];
        Exception result = new Exception();
        Task<String> task = new Task<>(() -> {
            count[0]++;
            throw result;
        });

        task.start();
        task.getFinishedEvent().await();

        RetryAction<String> retryAction = new RetryAction<>(task, 3);

        try {
            retryAction.run(new CancellationToken());
            fail("Exception should be thrown!");
        } catch(Exception e) {
            assertSame(result, e);
            assertEquals(1+3/*Task+retries*/, count[0]);
        }
    }

    @Test(expected = CancelledException.class)
    public void runCancelTest() throws Exception {
        int[] count = new int[1];
        Exception result = new Exception();
        Task<String> task = new Task<>((IAction<String>) (cancelToken) -> {
            count[0]++;

            if(count[0] == 3) {
                cancelToken.cancel();
                cancelToken.abortIfCancelRequested();
            }

            throw result;
        });

        task.start();
        task.getFinishedEvent().await();

        RetryAction<String> retryAction = new RetryAction<>(task, 3);

        retryAction.run(new CancellationToken());
    }

    @Test
    public void retrySucceededTaskTest() throws Exception {
        int[] counter = new int[1];
        Task<String> task = new Task<>(() -> {
            counter[0]++;
            return "";
        });

        task.start();
        task.getFinishedEvent().await();

        RetryAction<String> retryAction = new RetryAction<>(task, RetryAction.RETRY_UNTIL_SUCCESS);

        retryAction.run(new CancellationToken());

        assertSame(1, counter[0]);
    }

}
