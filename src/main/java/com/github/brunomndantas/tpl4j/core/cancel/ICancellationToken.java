package com.github.brunomndantas.tpl4j.core.cancel;

public interface ICancellationToken {

    String getId();

    void cancel();

    boolean hasCancelRequest();

    void abortIfCancelRequested() throws CancelledException;

}
