package com.github.brunomndantas.tpl4j.core.options;

import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

import static org.junit.Assert.*;

public class OptionsTest {


    @Test
    public void constructorTest() {
        Collection<Option> ops = new LinkedList<>();
        Options options = new Options(ops);
        assertSame(ops, options.getOptions());
    }

    @Test
    public void getOptionsTest() {
        Collection<Option> ops = new LinkedList<>();
        Options options = new Options(ops);
        assertSame(ops, options.getOptions());
    }

    @Test
    public void containsTest() {
        Collection<Option> ops = Arrays.asList(Option.REJECT_CHILDREN, Option.ATTACH_TO_PARENT);
        Options options = new Options(ops);

        assertTrue(options.contains(Option.REJECT_CHILDREN));
        assertTrue(options.contains(Option.ATTACH_TO_PARENT));
        assertFalse(options.contains(Option.NOT_CANCELABLE));
    }

    @Test
    public void rejectChildrenTest() {
        Collection<Option> ops = new LinkedList<>();
        Options options = new Options(ops);

        assertFalse(options.rejectChildren());

        ops = Arrays.asList(Option.REJECT_CHILDREN);
        options = new Options(ops);

        assertTrue(options.rejectChildren());
    }

    @Test
    public void attachToParentTest() {
        Collection<Option> ops = new LinkedList<>();
        Options options = new Options(ops);

        assertFalse(options.attachToParent());

        ops = Arrays.asList(Option.ATTACH_TO_PARENT);
        options = new Options(ops);

        assertTrue(options.attachToParent());
    }

    @Test
    public void notCancelableTest() {
        Collection<Option> ops = new LinkedList<>();
        Options options = new Options(ops);

        assertFalse(options.notCancelable());

        ops = Arrays.asList(Option.NOT_CANCELABLE);
        options = new Options(ops);

        assertTrue(options.notCancelable());
    }

    @Test
    public void notPropagateCancellationTest() {
        Collection<Option> ops = new LinkedList<>();
        Options options = new Options(ops);

        assertFalse(options.notPropagateCancellation());

        ops = Arrays.asList(Option.NOT_PROPAGATE_CANCELLATION);
        options = new Options(ops);

        assertTrue(options.notPropagateCancellation());
    }

    @Test
    public void notPropagateFailureTest() {
        Collection<Option> ops = new LinkedList<>();
        Options options = new Options(ops);

        assertFalse(options.notPropagateFailure());

        ops = Arrays.asList(Option.NOT_PROPAGATE_FAILURE);
        options = new Options(ops);

        assertTrue(options.notPropagateFailure());
    }

}
