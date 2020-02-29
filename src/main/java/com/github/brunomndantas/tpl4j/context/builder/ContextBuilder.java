package com.github.brunomndantas.tpl4j.context.builder;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.manager.IContextManager;
import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.Status;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.LinkedList;

public class ContextBuilder implements IContextBuilder {

    private static final Logger LOGGER = LogManager.getLogger(ContextBuilder.class);



    protected volatile IContextManager contextManager;
    public IContextManager getContextManager() { return this.contextManager; }



    public ContextBuilder(IContextManager contextManager) {
        this.contextManager = contextManager;
    }



    public <T> Context<T> build(String taskId, IAction<T> action, ICancellationToken cancellationToken, IScheduler scheduler, IOptions options) {
        Context<T> context = new Context<>(taskId, action, cancellationToken, scheduler, options, new Status(taskId), null, new LinkedList<>(), 0, 0, null, null);

        this.contextManager.registerContext(context);

        this.contextManager.registerCurrentThreadAsCreatorOfContext(context);

        Context<?> parentContext = this.contextManager.getContextRunningOnCurrentThread();
        if(parentContext != null)
            this.contextManager.registerTaskParenting(parentContext, context);

        LOGGER.info("Context for Task with id:" + taskId + " built!");
        LOGGER.info("Task with id:" + taskId + " monitoring CancellationToken with id:" + cancellationToken.getId() + "!");
        LOGGER.info("Task with id:" + taskId + " associated to Scheduler with id:" + scheduler.getId() + "!");
        LOGGER.info("Task with id:" + taskId + " created with options:[" + optionsToString(options) + "]");

        return context;
    }

    protected String optionsToString(IOptions options) {
        StringBuilder sb = new StringBuilder();

        for(Option option : options.getOptions())
            sb.append(option).append(";");

        String str = sb.toString();
        if(!str.isEmpty())
            str = str.substring(0, str.length()-";".length());

        return str;
    }

}

