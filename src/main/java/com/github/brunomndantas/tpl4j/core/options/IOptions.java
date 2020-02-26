package com.github.brunomndantas.tpl4j.core.options;

import java.util.Collection;

public interface IOptions {

    Collection<Option> getOptions();

    boolean contains(Option option);

    boolean rejectChildren();

    boolean attachToParent();

    boolean notCancelable();

}
