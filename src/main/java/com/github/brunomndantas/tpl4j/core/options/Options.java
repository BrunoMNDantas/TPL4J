package com.github.brunomndantas.tpl4j.core.options;

import java.util.Collection;

public class Options implements IOptions {

    protected volatile Collection<Option> options;



    public Options(Collection<Option> options) {
        this.options = options;
    }



    @Override
    public Collection<Option> getOptions() {
        return this.options;
    }

    @Override
    public boolean contains(Option option) {
        return this.options.contains(option);
    }

    @Override
    public boolean rejectChildren() {
        return this.contains(Option.REJECT_CHILDREN);
    }

    @Override
    public boolean attachToParent() {
        return this.contains(Option.ATTACH_TO_PARENT);
    }

    @Override
    public boolean notCancelable() {
        return this.contains(Option.NOT_CANCELABLE);
    }

}
