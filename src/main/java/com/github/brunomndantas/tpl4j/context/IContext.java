package com.github.brunomndantas.tpl4j.context;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.ICancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.core.status.IStatus;

import java.util.Collection;

public interface IContext<T> {

    String getTaskId();

    IAction<T> getAction();

    ICancellationToken getCancellationToken();

    IScheduler getScheduler();

    IOptions getOptions();

    IStatus getStatus();


    Context<?> getParentContext();

    void setParentContext(Context<?> parentContext);


    Collection<Context<?>> getChildrenContexts();

    void setChildrenContexts(Collection<Context<?>> childrenContexts);


    long getCreatorThreadId();

    void setCreatorThreadId(long creatorThreadId);


    long getExecutorThreadId();

    void setExecutorThreadId(long executorThreadId);


    T getResultValue();

    void setResultValue(T resultValue);


    Exception getResultException();

    void setResultException(Exception resultException);


    boolean hasChild(Context<?> context);

    void addChild(Context<?> context);

}
