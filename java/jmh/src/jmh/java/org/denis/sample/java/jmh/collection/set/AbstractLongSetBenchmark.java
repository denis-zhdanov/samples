package org.denis.sample.java.jmh.collection.set;

import org.denis.sample.java.jmh.collection.common.AbstractBenchmark;

public abstract class AbstractLongSetBenchmark extends AbstractBenchmark<LongSetState> {

    @Override
    protected LongSetState buildState() {
        return new LongSetState();
    }
}
