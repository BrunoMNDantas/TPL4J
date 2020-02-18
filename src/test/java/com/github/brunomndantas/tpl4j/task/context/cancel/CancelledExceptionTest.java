package com.github.brunomndantas.tpl4j.task.context.cancel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CancelledExceptionTest {

    @Test
    public void constructorTest() {
        CancelledException exception = new CancelledException();
        assertEquals("Task cancelled", exception.getMessage());
    }

}
