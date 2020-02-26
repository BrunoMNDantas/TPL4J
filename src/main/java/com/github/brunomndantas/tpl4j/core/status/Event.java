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
package com.github.brunomndantas.tpl4j.core.status;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Event implements IEvent {

    private static final ExecutorService LISTENERS_RUNNER = Executors.newFixedThreadPool(1, (r) -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
    });



    private final Collection<Runnable> listeners = new LinkedList<>();
    private boolean fired;



    @Override
    public synchronized void addListener(Runnable listener) {
        this.listeners.add(listener);

        if(this.fired)
            LISTENERS_RUNNER.submit(listener);
    }

    @Override
    public synchronized void removeListener(Runnable listener) {
        this.listeners.remove(listener);
    }

    @Override
    public synchronized boolean await() throws InterruptedException {
        while(!this.fired)
            this.wait();

        return true;
    }

    @Override
    public synchronized boolean await(long timeout) throws InterruptedException {
        long finalTime = System.currentTimeMillis() + timeout;
        long timeToFinish;

        while(true) {
            timeToFinish = finalTime - System.currentTimeMillis();

            if(this.fired || timeToFinish<=0)
                break;

            this.wait(timeToFinish);
        }

        return this.fired;
    }

    @Override
    public synchronized void fire() {
        if(!this.fired) {
            this.fired = true;

            this.notifyAll();

            this.listeners.forEach(LISTENERS_RUNNER::submit);
        }
    }

    @Override
    public synchronized boolean hasFired() {
        return this.fired;
    }

}
