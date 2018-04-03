package org.denis.sample.java.jmh.collection.list;

import org.denis.sample.java.jmh.collection.common.AbstractBenchmark;

public abstract class AbstractLongListBenchmark extends AbstractBenchmark<LongListState> {

    @Override
    protected LongListState buildState() {
        return new LongListState();
    }
}
