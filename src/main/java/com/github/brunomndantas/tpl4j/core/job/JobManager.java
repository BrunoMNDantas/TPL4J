/*-
* Copyright (c) 2019, Bruno Dantas <bmndantas@gmail.com>
* All rights reserved.
*
* This program is free software: you can redistribute it and/or modify it
* under the terms of the GNU General Public License as published by the
* Free Software Foundation, either version 3 of the License, or (at your
* option) any later version.
*
* This program is distributed in the hope that it will be useful, but
* WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
* or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
* for more details.
*
* You should have received a copy of the GNU General Public License along
* with this program.  If not, see <http://www.gnu.org/licenses/>.  */
package com.github.brunomndantas.tpl4j.core.job;

import com.github.brunomndantas.tpl4j.core.TaskOption;
import com.github.brunomndantas.tpl4j.core.status.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class JobManager {

    public static final JobManager INSTANCE = new JobManager();

    private final Logger LOGGER = LogManager.getLogger(JobManager.class);
    private final Object LOCK = new Object();
    private final Map<Job<?>, JobContext> CONTEXTS_BY_JOB = new HashMap<>();



    public void registerJobCreationOnCurrentThread(Job job) {
        registerJobCreation(job, Thread.currentThread().getId());
    }

    public void registerJobCreation(Job job, long threadId) {
        synchronized (LOCK) {
            if(CONTEXTS_BY_JOB.containsKey(job))
                throw new IllegalArgumentException("You are trying to register Task:" + job.status.getTaskId() + " creation twice!");

            JobContext context = createContext(job, threadId);

            CONTEXTS_BY_JOB.put(job, context);

            LOGGER.info("Thread:" + threadId + " declared creation of Task:" + job.status.getTaskId());

            declareJobOnParent(context);
        }
    }

    private JobContext createContext(Job job, long threadId) {
        JobContext context = new JobContext();

        context.setJob(job);
        context.setCreatorThreadId(threadId);
        context.setExecutorThreadId(-1);
        context.setParent(getJobContextExecutingOnThread(threadId));

        return context;
    }

    private void declareJobOnParent(JobContext context) {
        if(context.getParent() != null) {
            context.getParent().getChildren().add(context);
            LOGGER.info("Task:" + context.getJob().status.getTaskId() + " declared as child of Task:" + context.getParent().getJob().status.getTaskId());
        } else {
            LOGGER.info("Task:" + context.getJob().status.getTaskId() + " declared without parent");
        }
    }

    public void registerJobExecutionOnCurrentThread(Job job) {
        registerJobExecution(job, Thread.currentThread().getId());
    }

    public void registerJobExecution(Job job, long threadId) {
        synchronized (LOCK) {
            JobContext context = CONTEXTS_BY_JOB.get(job);

            if(context == null)
                throw new IllegalArgumentException("Trying to register Task:" + job.status.getTaskId() + " execution without register its creation!");

            if(getJobContextExecutingOnThread(threadId) != null)
                throw new IllegalArgumentException("Trying to register Task:" + job.status.getTaskId() + " on thread:" + threadId + " but this thread is already executing Task:" + getJobContextExecutingOnThread(threadId).getExecutorThreadId());

            context.setExecutorThreadId(threadId);

            LOGGER.info("Thread:" + threadId + " declared execution of Task:" + job.status.getTaskId());
        }
    }

    public JobContext getJobContextExecutingOnThread(long threadId) {
        synchronized (LOCK) {
            Collection<JobContext> contexts = getJobContextsExecutedByThread(threadId);

            for(JobContext context : contexts)
                if(context.getJob().status.getValue() == Status.RUNNING)
                    return context;

            return null;
        }
    }

    public Collection<JobContext> getJobContextsManagedByThread(long threadId) {
        synchronized (LOCK) {
            Collection<JobContext> contexts = new LinkedList<>();

            contexts.addAll(getJobContextsCreatedByThread(threadId));
            contexts.addAll(getJobContextsExecutedByThread(threadId));

            return contexts
                    .stream()
                    .distinct()
                    .collect(Collectors.toList());
        }
    }

    public Collection<JobContext> getJobContextsCreatedByThread(long threadId) {
        synchronized (LOCK) {
            return CONTEXTS_BY_JOB.values()
                    .stream()
                    .filter(context -> context.getCreatorThreadId() == threadId)
                    .collect(Collectors.toList());
        }
    }

    public Collection<JobContext> getJobContextsExecutedByThread(long threadId) {
        synchronized (LOCK) {
            return CONTEXTS_BY_JOB.values()
                    .stream()
                    .filter(context -> context.getExecutorThreadId() == threadId)
                    .collect(Collectors.toList());
        }
    }

    public JobContext getJobContext(Job job) {
        synchronized (LOCK) {
            return CONTEXTS_BY_JOB.get(job);
        }
    }

    public Collection<JobContext> getAttachedChildrenContexts(Job<?> job) {
        synchronized (LOCK) {
            Collection<TaskOption> taskOptions = job.options;

            if(taskOptions.contains(TaskOption.REJECT_CHILDREN))
                return new LinkedList<>();

            JobContext taskContext = CONTEXTS_BY_JOB.get(job);
            Collection<JobContext> childrenContexts = taskContext.getChildren();

            return childrenContexts
                    .stream()
                    .filter((childContext) -> {
                        Job<?> childJob = childContext.getJob();
                        Collection<TaskOption> childOptions = childJob.options;

                        return childOptions.contains(TaskOption.ATTACH_TO_PARENT);
                    })
                    .collect(Collectors.toList());
        }
    }

    public void unregisterJobExecution(Job<?> job) {
        synchronized (LOCK) {
            if(!CONTEXTS_BY_JOB.containsKey(job))
                throw new IllegalArgumentException("You are trying to unregister Task:" + job.status.getTaskId() + " which is not registered!");

            if(CONTEXTS_BY_JOB.get(job).getExecutorThreadId() == -1)
                throw new IllegalArgumentException("You are trying to unregister Task:" + job.status.getTaskId() + " which was't execution registered!");

            CONTEXTS_BY_JOB.remove(job);
        }
    }

}
