package com.dantas.tpl4j.task.core.cancel;

import org.junit.Test;

import static org.junit.Assert.*;

public class CancellationTokenTest {

    @Test
    public void getTaskIdTest() {
        String taskId = "ID";
        CancellationToken token = new CancellationToken(taskId);

        assertEquals(taskId, token.getTaskId());
    }

    @Test
    public void hasCancelRequestTest() {
        CancellationToken token = new CancellationToken("ID");

        assertFalse(token.hasCancelRequest());

        token.cancel();

        assertTrue(token.hasCancelRequest());
    }

    @Test
    public void cancelTest() {
        CancellationToken token = new CancellationToken("ID");

        token.cancel();

        assertTrue(token.hasCancelRequest());
    }

    @Test
    public void abortTest() throws CancelledException {
        CancellationToken token = new CancellationToken("ID");

        assertNotNull(token.abort());
    }

}
