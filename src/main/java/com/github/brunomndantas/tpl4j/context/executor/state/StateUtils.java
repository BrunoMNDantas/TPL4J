package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.IContext;

import java.util.Collection;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class StateUtils {

    public static Collection<IContext<?>> getAttachedChildrenContext(IContext<?> context) {
        if(context.getOptions().rejectChildren())
            return new LinkedList<>();

        return context.getChildrenContexts()
                .stream()
                .filter(ctx -> ctx.getOptions().attachToParent())
                .collect(Collectors.toList());
    }

}
