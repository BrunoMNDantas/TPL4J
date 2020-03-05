package com.github.brunomndantas.tpl4j.transversal;

import com.github.brunomndantas.tpl4j.core.action.IAction;
import com.github.brunomndantas.tpl4j.core.cancel.CancellationToken;
import com.github.brunomndantas.tpl4j.core.options.IOptions;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.scheduler.DedicatedThreadScheduler;
import com.github.brunomndantas.tpl4j.core.scheduler.IScheduler;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyAction;
import com.github.brunomndantas.tpl4j.task.action.action.IEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.action.action.IVoidAction;
import com.github.brunomndantas.tpl4j.task.action.link.ILinkAction;
import com.github.brunomndantas.tpl4j.task.action.link.ILinkEmptyAction;
import com.github.brunomndantas.tpl4j.task.action.link.ILinkEmptyVoidAction;
import com.github.brunomndantas.tpl4j.task.action.link.ILinkVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelUninterruptibleAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelUninterruptibleVoidAction;
import com.github.brunomndantas.tpl4j.task.helpers.parallel.action.IParallelVoidAction;

import java.util.Arrays;
import java.util.List;

public class TestUtils {

    public static final IAction<String> ACTION = (ct) -> null;
    public static final IEmptyAction<String> EMPTY_ACTION = () -> null;
    public static final IVoidAction VOID_ACTION = (ct) -> {};
    public static final IEmptyVoidAction EMPTY_VOID_ACTION = () -> {};

    public static final ILinkAction<String, String> LINK_ACTION = (pt, ct) -> null;
    public static final ILinkEmptyAction<String> LINK_EMPTY_ACTION = () -> null;
    public static final ILinkVoidAction<String> LINK_VOID_ACTION = (pt, ct) -> {};
    public static final ILinkEmptyVoidAction LINK_EMPTY_VOID_ACTION = () -> {};

    public static final IParallelAction<String,String> PARALLEL_ACTION = (e, ct) -> null;
    public static final IParallelVoidAction<String> PARALLEL_VOID_ACTION = (e, ct) -> {};
    public static final IParallelUninterruptibleAction<String,String> PARALLEL_UNINTERRUPTIBLE_ACTION = (e) -> null;
    public static final IParallelUninterruptibleVoidAction<String> PARALLEL_UNINTERRUPTIBLE_VOID_ACTION = (e) -> {};

    public static final long SLEEP_TIME = 1000;

    public static final String SUCCESS_RESULT = "";
    public static final IAction<String> SUCCESS_ACTION = (ct) -> { Thread.sleep(SLEEP_TIME); return SUCCESS_RESULT; };

    public static final Exception FAIL_RESULT = new Exception();
    public static final IAction<String> FAIL_ACTION = (ct) -> { Thread.sleep(SLEEP_TIME); throw FAIL_RESULT; };

    public static final IAction<String> CANCEL_ACTION = (ct) -> { Thread.sleep(SLEEP_TIME); ct.cancel(); ct.abortIfCancelRequested(); return SUCCESS_RESULT; };

    public static final CancellationToken CANCELLATION_TOKEN = new CancellationToken();
    public static final Option[] OPTIONS_ARRAY = {};
    public static final List<Option> OPTIONS_LIST = Arrays.asList(OPTIONS_ARRAY);
    public static final IOptions OPTIONS = new Options(OPTIONS_LIST);
    public static final IScheduler SCHEDULER = new DedicatedThreadScheduler();

}
