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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Status {

    private final Logger LOGGER = LogManager.getLogger(Status.class);



    private final Object lock = new Object();

    public final Event scheduledEvent = new Event();
    public final Event runningEvent = new Event();
    public final Event waitingForChildrenEvent = new Event();
    public final Event cancelledEvent = new Event();
    public final Event failedEvent = new Event();
    public final Event succeededEvent = new Event();
    public final Event finishedEvent = new Event();

    private State state = State.CREATED;
    public State getValue() { synchronized (lock) { return this.state; } }

    private String taskId;
    public String getTaskId() { synchronized (lock) { return this.taskId; } }



    public Status(String taskId) {
        this.taskId = taskId;
    }



    public void declareSchedule() {
        synchronized (lock) {
            this.state = State.SCHEDULED;
            scheduledEvent.fire();
            LOGGER.info("Task:" + taskId + " declared Schedule Status!");
        }
    }

    public void declareRun() {
        synchronized (lock) {
            this.state = State.RUNNING;
            runningEvent.fire();
            LOGGER.info("Task:" + taskId + " declared Run Status!");
        }
    }

    public void declareWaitChildren() {
        synchronized (lock) {
            this.state = State.WAITING_CHILDREN;
            waitingForChildrenEvent.fire();
            LOGGER.info("Task:" + taskId + " declared Wait Children Status!");
        }
    }

    public void declareCancel() {
        synchronized (lock) {
            this.state = State.CANCELED;
            cancelledEvent.fire();
            LOGGER.info("Task:" + taskId + " declared Cancel Status!");
        }

        declareFinish();
    }

    public void declareFail() {
        synchronized (lock) {
            this.state = State.FAILED;
            failedEvent.fire();
            LOGGER.info("Task:" + taskId + " declared Fail Status!");
        }

        declareFinish();
    }

    public void declareSuccess() {
        synchronized (lock) {
            this.state = State.SUCCEEDED;
            succeededEvent.fire();
            LOGGER.info("Task:" + taskId + " declared Success Status!");
        }

        declareFinish();
    }

    public void declareFinish() {
        synchronized (lock) {
            finishedEvent.fire();
            LOGGER.info("Task:" + taskId + " declared Finish Status!");
        }
    }

}
