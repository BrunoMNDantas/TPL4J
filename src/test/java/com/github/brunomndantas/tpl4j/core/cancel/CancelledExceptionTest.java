package com.github.brunomndantas.tpl4j.core.cancel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class CancelledExceptionTest {

    @Test
    public void getCancellationTokenIdTest() {
        String tokenId = "";
        CancelledException exception = new CancelledException(tokenId);
        assertSame(tokenId, exception.getCancellationTokenId());
    }

    @Test
    public void constructorTest() {
        String tokenId = "";
        CancelledException exception = new CancelledException(tokenId);
        assertEquals("Task cancelled", exception.getMessage());
        assertSame(tokenId, exception.getCancellationTokenId());
    }

}
