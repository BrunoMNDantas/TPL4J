package com.github.brunomndantas.tpl4j.core.job;

import org.junit.Test;

import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class JobContextTest {

    @Test
    public void jobPropertyTest() {
        JobContext context = new JobContext();

        assertNull(context.getJob());

        Job<?> job = new Job<>("ID", null, null, null, new LinkedList<>());
        context.setJob(job);

        assertSame(job, context.getJob());
    }

    @Test
    public void creatorThreadIdPropertyTest() {
        JobContext context = new JobContext();

        assertEquals(0, context.getCreatorThreadId());

        long creatorThreadId = Thread.currentThread().getId();
        context.setCreatorThreadId(creatorThreadId);

        assertEquals(creatorThreadId, context.getCreatorThreadId());
    }

    @Test
    public void executorThreadIdPropertyTest() {
        JobContext context = new JobContext();

        assertEquals(0, context.getExecutorThreadId());

        long executorThreadId = Thread.currentThread().getId();
        context.setExecutorThreadId(executorThreadId);

        assertEquals(executorThreadId, context.getExecutorThreadId());
    }

    @Test
    public void parentPropertyTest() {
        JobContext context = new JobContext();

        assertNull(context.getParent());

        JobContext parent = new JobContext();
        context.setParent(parent);

        assertSame(parent, context.getParent());
    }

    @Test
    public void childrenPropertyTest() {
        JobContext context = new JobContext();

        assertNotNull(context.getChildren());
        assertTrue(context.getChildren().isEmpty());

        Collection<JobContext> children = new LinkedList<>();
        context.setChildren(children);

        assertSame(children, context.getChildren());
    }

}
