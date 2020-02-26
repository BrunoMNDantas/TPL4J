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

public class Status implements IStatus {

    private final Logger LOGGER = LogManager.getLogger(Status.class);



    private volatile IEvent scheduledEvent = new Event();
    private volatile IEvent runningEvent = new Event();
    private volatile IEvent waitingForChildrenEvent = new Event();
    private volatile IEvent cancelledEvent = new Event();
    private volatile IEvent failedEvent = new Event();
    private volatile IEvent succeededEvent = new Event();
    private volatile IEvent finishedEvent = new Event();
    private State state = State.CREATED;

    private volatile String taskId;
    public String getTaskId() { return this.taskId; }



    public Status(String taskId) {
        this.taskId = taskId;
    }



    @Override
    public synchronized State getState() {
        return this.state;
    }

    @Override
    public synchronized void setState(State state) {
        LOGGER.trace("Setting state of task with id:" + this.taskId + " to " + state);

        switch (state) {
            case SCHEDULED: this.declareSchedule(); break;
            case RUNNING: this.declareRun(); break;
            case WAITING_CHILDREN: this.declareWaitChildren(); break;
            case SUCCEEDED: this.declareSuccess(); break;
            case CANCELED: this.declareCancel(); break;
            case FAILED: this.declareFail(); break;
            default: throw new RuntimeException("Invalid State:" + state);
        }
    }

    @Override
    public IEvent getScheduledEvent() {
        return this.scheduledEvent;
    }

    @Override
    public IEvent getRunningEvent() {
        return this.runningEvent;
    }

    @Override
    public IEvent getWaitingForChildrenEvent() {
        return this.waitingForChildrenEvent;
    }

    @Override
    public IEvent getCancelledEvent() {
        return this.cancelledEvent;
    }

    @Override
    public IEvent getFailedEvent() {
        return this.failedEvent;
    }

    @Override
    public IEvent getSucceededEvent() {
        return this.succeededEvent;
    }

    @Override
    public IEvent getFinishedEvent() {
        return this.finishedEvent;
    }

    private void declareSchedule() {
        this.state = State.SCHEDULED;
        this.scheduledEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Schedule state!");
    }

    private void declareRun() {
        this.state = State.RUNNING;
        this.runningEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Run state!");
    }

    private void declareWaitChildren() {
        this.state = State.WAITING_CHILDREN;
        this.waitingForChildrenEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Wait Children state!");
    }

    private void declareCancel() {
        this.state = State.CANCELED;
        this.cancelledEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Cancel state!");

        this.declareFinish();
    }

    private void declareFail() {
        this.state = State.FAILED;
        this.failedEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Fail state!");

        this.declareFinish();
    }

    private void declareSuccess() {
        this.state = State.SUCCEEDED;
        this.succeededEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Success state!");

        this.declareFinish();
    }

    private void declareFinish() {
        this.finishedEvent.fire();
        LOGGER.info("Task with id:" + this.taskId + " declared Finish state!");
    }

}
