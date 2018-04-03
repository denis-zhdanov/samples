package org.denis.sample.java.jmh.collection.map;

import org.denis.sample.java.jmh.collection.common.AbstractBenchmark;

public abstract class AbstractLongMapBenchmark extends AbstractBenchmark<LongMapState> {

    @Override
    protected LongMapState buildState() {
        return new LongMapState();
    }
}
