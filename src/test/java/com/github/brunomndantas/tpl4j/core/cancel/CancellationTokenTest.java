package com.github.brunomndantas.tpl4j.core.cancel;

import org.junit.Test;

import static org.junit.Assert.*;

public class CancellationTokenTest {

    @Test
    public void constructorsTest() {
        CancellationToken token = new CancellationToken();
        assertNotNull(token.getId());
        assertFalse(token.getId().isEmpty());
        assertNotEquals(token.getId(), new CancellationToken().getId());

        String id = "ID";
        token = new CancellationToken(id);
        assertSame(id, token.getId());
    }

    @Test
    public void getIdTest() {
        String id = "ID";
        CancellationToken token = new CancellationToken(id);
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
    public void abortIfCancelRequestedWithoutTest() throws CancelledException {
        CancellationToken token = new CancellationToken();
        token.abortIfCancelRequested();
    }

    @Test
    public void abortIfCancelRequestedWithRequestTest() {
        CancellationToken token = new CancellationToken();
        token.cancel();

        try {
            token.abortIfCancelRequested();
            fail("Exception should be thrown!");
        } catch (CancelledException e) {
            assertNotNull(e.getMessage());
            assertSame(token.getId(), e.getCancellationTokenId());
        }
    }

}
