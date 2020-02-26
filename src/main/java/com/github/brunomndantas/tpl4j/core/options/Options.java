package com.github.brunomndantas.tpl4j.core.options;

import java.util.Collection;

public class Options {

    private volatile Collection<Option> options;
    public Collection<Option> getOptions() { return this.options; }



    public Options(Collection<Option> options) {
        this.options = options;
    }



    public boolean contains(Option option) {
        return this.options.contains(option);
    }

    public boolean rejectChildren() {
        return this.contains(Option.REJECT_CHILDREN);
    }

    public boolean attachToParent() {
        return this.contains(Option.ATTACH_TO_PARENT);
    }

    public boolean notCancelable() {
        return this.contains(Option.NOT_CANCELABLE);
    }

}
