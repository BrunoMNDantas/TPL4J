package com.github.brunomndantas.tpl4j.context.executor.state;

import com.github.brunomndantas.tpl4j.context.Context;
import com.github.brunomndantas.tpl4j.context.IContext;
import com.github.brunomndantas.tpl4j.core.options.Option;
import com.github.brunomndantas.tpl4j.core.options.Options;
import com.github.brunomndantas.tpl4j.core.status.Status;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class StateUtilsTest {

    @Test
    public void getAttachedChildrenContextTest() {
        IContext<?> parentContext = new Context<>("", (ct)->"", null, null, new Options(new LinkedList<>()), new Status(""), null, new LinkedList<>(), 0, 0, null, null);
        IContext<?> childContextA = new Context<>("", (ct)->"", null, null, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), parentContext, null, 0, 0, null, null);
        IContext<?> childContextB = new Context<>("", (ct)->"", null, null, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), parentContext, null, 0, 0, null, null);
        IContext<?> childContextC = new Context<>("", (ct)->"", null, null, new Options(new LinkedList<>()), new Status(""), parentContext, null, 0, 0, null, null);

        parentContext.addChild(childContextA);
        parentContext.addChild(childContextB);
        parentContext.addChild(childContextC);

        Collection<IContext<?>> children = StateUtils.getAttachedChildrenContext(parentContext);

        assertEquals(2, children.size());
        assertTrue(children.contains(childContextA));
        assertTrue(children.contains(childContextB));
        assertFalse(children.contains(childContextC));
    }

    @Test
    public void getAttachedChildrenContextWithRejectOptionTest() {
        IContext<?> parentContext = new Context<>("", (ct)->"", null, null, new Options(Arrays.asList(Option.REJECT_CHILDREN)), new Status(""), null, new LinkedList<>(), 0, 0, null, null);
        IContext<?> childContextA = new Context<>("", (ct)->"", null, null, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), parentContext, null, 0, 0, null, null);
        IContext<?> childContextB = new Context<>("", (ct)->"", null, null, new Options(Arrays.asList(Option.ATTACH_TO_PARENT)), new Status(""), parentContext, null, 0, 0, null, null);
        IContext<?> childContextC = new Context<>("", (ct)->"", null, null, new Options(new LinkedList<>()), new Status(""), parentContext, null, 0, 0, null, null);

        parentContext.addChild(childContextA);
        parentContext.addChild(childContextB);
        parentContext.addChild(childContextC);

        Collection<IContext<?>> children = StateUtils.getAttachedChildrenContext(parentContext);

        assertTrue(children.isEmpty());
    }

}
