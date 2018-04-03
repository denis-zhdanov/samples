package org.denis.sample.java.jmh.collection.map;

import org.openjdk.jmh.annotations.Benchmark;

public class LongMapRemoveBenchmark extends BasePrePopulatedLongMapBenchmark {

    @Benchmark
    public void hppc() {
        state.hppc.remove(state.input[state.index]);
    }

    @Benchmark
    public void fastutil() {
        state.fastutil.remove(state.input[state.index]);
    }

    @Benchmark
    public void eclipse() {
        state.eclipse.remove(state.input[state.index]);
    }

    @Benchmark
    public void agrona() {
        state.agrona.remove(state.input[state.index]);
    }

    @Benchmark
    public void mahout() {
        state.mahout.removeKey(state.input[state.index]);
    }

    @Benchmark
    public void jdk() {
        state.jdk.remove(state.input[state.index]);
    }
}
