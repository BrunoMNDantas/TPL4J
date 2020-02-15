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
package com.github.brunomndantas.tpl4j.task.core.cancel;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class CancellationToken {

    private final Logger LOGGER = LogManager.getLogger(CancellationToken.class);



    private String id;
    public String getId() { return this.id; }

    private AtomicBoolean cancelRequested = new AtomicBoolean();



    public CancellationToken() {
        this.id = UUID.randomUUID().toString();
    }



    public void cancel() {
        LOGGER.info("Cancellation Token with id:" + id + " received cancel request!");
        cancelRequested.set(true);
    }

    public boolean hasCancelRequest() {
        return cancelRequested.get();
    }

    public CancelledException abort() {
        LOGGER.info("Cancellation Token wth id:" + id + " received abort request!");
        return new CancelledException();
    }

}
