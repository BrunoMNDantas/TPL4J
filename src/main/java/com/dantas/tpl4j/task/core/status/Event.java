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
package com.dantas.tpl4j.task.core.status;

import java.util.Collection;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Event {

    private static final ExecutorService LISTENERS_RUNNER = Executors.newFixedThreadPool(1, (r) -> {
                Thread t = Executors.defaultThreadFactory().newThread(r);
                t.setDaemon(true);
                return t;
    });



    private final Object lock = new Object();

    private final Collection<Runnable> listeners = new LinkedList<>();

    private boolean fired;



    public void addListener(Runnable listener) {
        synchronized (lock) {
            listeners.add(listener);

            if(fired)
                listener.run();
        }
    }

    public void removeListener(Runnable listener) {
        synchronized (lock) {
            listeners.remove(listener);
        }
    }

    public boolean await() throws InterruptedException {
        synchronized (lock) {
            while(!fired)
                lock.wait();

            return true;
        }
    }

    public boolean await(long timeout) throws InterruptedException {
        synchronized (lock) {
            long finalTime = System.currentTimeMillis() + timeout;
            long timeToFinish;

            while(true) {
                timeToFinish = finalTime - System.currentTimeMillis();

                if(fired || timeToFinish<=0)
                    break;

                lock.wait(timeToFinish);
            }

            return fired;
        }
    }

    public void fire() {
        synchronized (lock) {
            if(!fired) {
                this.fired = true;

                lock.notifyAll();

                listeners.forEach(LISTENERS_RUNNER::submit);
            }
        }
    }

    public boolean hasFired() {
        synchronized (lock) {
            return this.fired;
        }
    }

}
