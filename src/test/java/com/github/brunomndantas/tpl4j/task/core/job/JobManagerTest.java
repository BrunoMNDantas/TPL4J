package com.github.brunomndantas.tpl4j.task.core.job;

import com.github.brunomndantas.tpl4j.task.core.TaskOption;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class JobManagerTest {

    @Test
    public void registerJobCreationOnCurrentThreadTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();
        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreationOnCurrentThread(job);

        JobContext context = manager.getJobContext(job);

        assertNotNull(context);
        assertSame(job, context.getJob());
        assertEquals(currentThreadId, context.getCreatorThreadId());
        assertEquals(-1, context.getExecutorThreadId());
        assertNull(context.getParent());
        assertNotNull(context.getChildren());
        assertEquals(0, context.getChildren().size());
    }

    @Test
    public void registerJobCreationTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();
        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreation(job, currentThreadId);

        JobContext context = manager.getJobContext(job);

        assertNotNull(context);
        assertSame(job, context.getJob());
        assertEquals(currentThreadId, context.getCreatorThreadId());
        assertEquals(-1, context.getExecutorThreadId());
        assertNull(context.getParent());
        assertNotNull(context.getChildren());
        assertEquals(0, context.getChildren().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerJobCreationTwiceTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();
        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreation(job, currentThreadId);
        manager.registerJobCreation(job, currentThreadId);
    }

    @Test
    public void registerJobExecutionOnCurrentThreadTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();
        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreation(job, currentThreadId);
        manager.registerJobExecutionOnCurrentThread(job);

        JobContext context = manager.getJobContext(job);

        assertNotNull(context);
        assertEquals(currentThreadId, context.getExecutorThreadId());
    }

    @Test
    public void registerJobExecutionTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();
        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreation(job, currentThreadId);
        manager.registerJobExecution(job, currentThreadId);

        JobContext context = manager.getJobContext(job);

        assertNotNull(context);
        assertEquals(currentThreadId, context.getExecutorThreadId());
    }

    @Test
    public void registerChildJobCreation() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Job<?> parentJob = new Job<>("ID", null, null, new LinkedList<>());
        Job<?> childJob = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreation(parentJob, currentThreadId);
        manager.registerJobExecution(parentJob, currentThreadId);
        parentJob.status.declareRun();

        manager.registerJobCreation(childJob, currentThreadId);

        JobContext parentContext = manager.getJobContext(parentJob);
        JobContext childContext = manager.getJobContext(childJob);

        assertNotNull(parentContext);
        assertNotNull(childContext);
        assertTrue(parentContext.getChildren().contains(childContext));
        assertSame(parentContext, childContext.getParent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerJobExecutionWithoutRegisterCreationTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobExecution(job, currentThreadId);
    }

    @Test(expected = IllegalArgumentException.class)
    public void registerTowJobExecutionOnOneThreadTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Job<?> jobA = new Job<>("ID", null, null, new LinkedList<>());
        Job<?> jobB = new Job<>("ID", null, null, new LinkedList<>());

        try {
            manager.registerJobCreation(jobA, currentThreadId);
            manager.registerJobCreation(jobB, currentThreadId);

            jobA.getStatus().declareRun();
            jobB.getStatus().declareRun();

            manager.registerJobExecution(jobA, currentThreadId);
        } catch (Exception e) {
            fail("It should not produce exception!");
        }

        manager.registerJobExecution(jobB, currentThreadId);
    }

    @Test
    public void getJobContextExecutingOnThreadTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreation(job, currentThreadId);

        job.status.declareRun();

        manager.registerJobExecution(job, currentThreadId);

        JobContext context = manager.getJobContext(job);
        JobContext executingContext = manager.getJobContextExecutingOnThread(currentThreadId);

        assertSame(context, executingContext);
    }

    @Test
    public void getNonExistentJobContextExecutingOnThreadTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreation(job, currentThreadId);

        JobContext executingContext = manager.getJobContextExecutingOnThread(currentThreadId);

        assertSame(null, executingContext);

        manager.getJobContextExecutingOnThread(currentThreadId+1);

        assertSame(null, executingContext);
    }

    @Test
    public void getJobContextsCreatedByThreadTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Collection<JobContext> contexts = manager.getJobContextsCreatedByThread(currentThreadId);

        assertNotNull(contexts);
        assertEquals(0, contexts.size());

        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());
        manager.registerJobCreation(job, currentThreadId);

        contexts = manager.getJobContextsCreatedByThread(currentThreadId);

        assertNotNull(contexts);
        assertEquals(1, contexts.size());
        assertSame(job, contexts.stream().findFirst().get().getJob());
    }

    @Test
    public void getJobContextsExecutedByThreadTest(){
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());
        manager.registerJobCreation(job, currentThreadId);

        Collection<JobContext> contexts = manager.getJobContextsExecutedByThread(currentThreadId);

        assertNotNull(contexts);
        assertEquals(0, contexts.size());

        manager.registerJobExecution(job, currentThreadId);

        contexts = manager.getJobContextsExecutedByThread(currentThreadId);

        assertNotNull(contexts);
        assertEquals(1, contexts.size());
        assertSame(job, contexts.stream().findFirst().get().getJob());
    }

    @Test
    public void getJobContextsManagerByThreadTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Collection<JobContext> contexts = manager.getJobContextsManagedByThread(currentThreadId);

        assertNotNull(contexts);
        assertEquals(0, contexts.size());

        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());
        manager.registerJobCreation(job, currentThreadId);

        contexts = manager.getJobContextsManagedByThread(currentThreadId);

        assertNotNull(contexts);
        assertEquals(1, contexts.size());
        assertSame(job, contexts.stream().findFirst().get().getJob());

        manager.registerJobExecution(job, currentThreadId);

        contexts = manager.getJobContextsManagedByThread(currentThreadId);

        assertNotNull(contexts);
        assertEquals(1, contexts.size());
        assertSame(job, contexts.stream().findFirst().get().getJob());
    }

    @Test
    public void getAttachedChildrenContextTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Job<?> parentJob = new Job<>("ID", null, null, new LinkedList<>());
        Job<?> childJob = new Job<>("ID", null, null, new LinkedList<>());
        Job<?> attachedChildJob = new Job<>("ID", null, null, Arrays.asList(TaskOption.ATTACH_TO_PARENT));

        manager.registerJobCreation(parentJob, currentThreadId);
        manager.registerJobExecution(parentJob, currentThreadId);
        parentJob.status.declareRun();

        manager.registerJobCreation(childJob, currentThreadId);

        Collection<JobContext> attachedChildrenContexts = manager.getAttachedChildrenContexts(parentJob);
        assertSame(0, attachedChildrenContexts.size());

        manager.registerJobCreation(attachedChildJob, currentThreadId);

        JobContext attachedChildContext = manager.getJobContext(attachedChildJob);

        attachedChildrenContexts = manager.getAttachedChildrenContexts(parentJob);
        assertSame(1, attachedChildrenContexts.size());
        assertTrue(attachedChildrenContexts.contains(attachedChildContext));
    }

    @Test
    public void getAttachedChildrenContextOfParentThatRejectsChildrenTest() {
        JobManager manager = new JobManager();
        long currentThreadId = Thread.currentThread().getId();

        Job<?> parentJob = new Job<>("ID", null, null, Arrays.asList(TaskOption.REJECT_CHILDREN));
        Job<?> attachedChildJob = new Job<>("ID", null, null, Arrays.asList(TaskOption.ATTACH_TO_PARENT));

        manager.registerJobCreation(parentJob, currentThreadId);
        manager.registerJobExecution(parentJob, currentThreadId);
        parentJob.status.declareRun();

        manager.registerJobCreation(attachedChildJob, currentThreadId);

        Collection<JobContext> attachedChildrenContexts = manager.getAttachedChildrenContexts(parentJob);
        assertSame(0, attachedChildrenContexts.size());
    }

    @Test
    public void unregisterJobExecutionTest() {
        JobManager manager = new JobManager();
        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobCreationOnCurrentThread(job);
        manager.registerJobExecutionOnCurrentThread(job);
        manager.unregisterJobExecution(job);

        JobContext context = manager.getJobContext(job);

        assertNull(context);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unregisterUnregisteredJobExecutionTest() {
        JobManager manager = new JobManager();
        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.unregisterJobExecution(job);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unregisterExecutionUnregisteredJobExecutionTest() {
        JobManager manager = new JobManager();
        Job<?> job = new Job<>("ID", null, null, new LinkedList<>());

        manager.registerJobExecutionOnCurrentThread(job);
        manager.unregisterJobExecution(job);
    }

}

