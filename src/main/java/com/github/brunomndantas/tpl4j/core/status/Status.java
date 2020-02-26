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



    public final Event scheduledEvent = new Event();
    public final Event runningEvent = new Event();
    public final Event waitingForChildrenEvent = new Event();
    public final Event cancelledEvent = new Event();
    public final Event failedEvent = new Event();
    public final Event succeededEvent = new Event();
    public final Event finishedEvent = new Event();

    private State state = State.CREATED;
    public synchronized State getState() { return this.state; }

    private String taskId;
    public synchronized String getTaskId() { return this.taskId; }



    public Status(String taskId) {
        this.taskId = taskId;
    }



    public synchronized void declareSchedule() {
        this.state = State.SCHEDULED;
        this.scheduledEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Schedule state!");
    }

    public synchronized void declareRun() {
        this.state = State.RUNNING;
        this.runningEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Run state!");
    }

    public synchronized void declareWaitChildren() {
        this.state = State.WAITING_CHILDREN;
        this.waitingForChildrenEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Wait Children state!");
    }

    public synchronized void declareCancel() {
        this.state = State.CANCELED;
        this.cancelledEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Cancel state!");

        this.declareFinish();
    }

    public synchronized void declareFail() {
        this.state = State.FAILED;
        this.failedEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Fail state!");

        this.declareFinish();
    }

    public synchronized void declareSuccess() {
        this.state = State.SUCCEEDED;
        this.succeededEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Success state!");

        this.declareFinish();
    }

    public synchronized void declareFinish() {
        this.finishedEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Finish state!");
    }

}
