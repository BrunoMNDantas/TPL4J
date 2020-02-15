package com.github.brunomndantas.tpl4j.task.core.cancel;

import org.junit.Test;

import static org.junit.Assert.*;

public class CancellationTokenTest {

    @Test
    public void getIdTest() {
        String id = "ID";
        CancellationToken token = new CancellationToken(id);
        assertSame(id, token.getId());
    }

    @Test
    public void constructorsTest() {
        CancellationToken token = new CancellationToken();
        assertNotNull(token.getId());
        assertFalse(token.getId().isEmpty());

        String id = "ID";
        token = new CancellationToken(id);
        assertSame(id, token.getId());
    }

    @Test
    public void hasCancelRequestTest() {
        CancellationToken token = new CancellationToken();

        assertFalse(token.hasCancelRequest());

        token.cancel();

        assertTrue(token.hasCancelRequest());
    }

    @Test
    public void cancelTest() {
        CancellationToken token = new CancellationToken();

        token.cancel();

        assertTrue(token.hasCancelRequest());
    }

    @Test
    public void abortTest() throws CancelledException {
        CancellationToken token = new CancellationToken();

        assertNotNull(token.abort());
    }

}
