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
package com.dantas.tpl4j.task.core.job;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;

public class JobContext {

    private final Object lock = new Object();

    private Job<?> job;
    public Job<?> getJob() { synchronized (lock) { return job; } }
    public void setJob(Job<?> job) { synchronized (lock) { this.job = job; } }

    private long creatorThreadId;
    public long getCreatorThreadId() { synchronized (lock) { return creatorThreadId; } }
    public void setCreatorThreadId(long creatorThreadId) { synchronized (lock) { this.creatorThreadId = creatorThreadId; } }

    private long executorThreadId;
    public long getExecutorThreadId() { synchronized (lock) { return executorThreadId; } }
    public void setExecutorThreadId(long executorThreadId) { synchronized (lock) { this.executorThreadId = executorThreadId; } }

    private JobContext parent;
    public JobContext getParent() { synchronized (lock) { return parent; } }
    public void setParent(JobContext parent) { synchronized (lock) { this.parent = parent; } }

    private Collection<JobContext> children = new ConcurrentLinkedDeque<>();
    public Collection<JobContext> getChildren() { synchronized (lock) { return children; } }
    public void setChildren(Collection<JobContext> children) { synchronized (lock) { this.children = children; } }

}
