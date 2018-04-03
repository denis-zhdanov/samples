package org.denis.sample.java.jmh.collection.common;

public abstract class AbstractBenchmarkState {

    public final long[] input = new long[5000];

    public int index;

    public void reset() {
        index = 0;
        doReset();
    }

    protected abstract void doReset();

    protected abstract void addToAll(long value);
}
