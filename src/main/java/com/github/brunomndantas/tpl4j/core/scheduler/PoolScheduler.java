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
package com.github.brunomndantas.tpl4j.core.scheduler;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PoolScheduler implements IScheduler {

    protected volatile ExecutorService pool;
    protected volatile String id;

    protected volatile int numberOfThreads;
    public int getNumberOfThreads() { return this.numberOfThreads; }



    public PoolScheduler(String id, int numberOfThreads) {
        this.id = id;
        this.numberOfThreads = numberOfThreads;
        this.pool = Executors.newFixedThreadPool(numberOfThreads);
    }

    public PoolScheduler(int numberOfThreads) {
        this(UUID.randomUUID().toString(), numberOfThreads);
    }

    public PoolScheduler(String id) {
        this(id, Runtime.getRuntime().availableProcessors());
    }

    public PoolScheduler() {
        this(UUID.randomUUID().toString(), Runtime.getRuntime().availableProcessors());
    }



    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public void schedule(Runnable action) {
        this.pool.submit(action);
    }

    @Override
    public void close() {
        this.pool.shutdown();
    }

}
