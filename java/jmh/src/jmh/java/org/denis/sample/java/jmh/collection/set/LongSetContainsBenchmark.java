package org.denis.sample.java.jmh.collection.set;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class LongSetContainsBenchmark extends BasePrePopulatedLongSetBenchmark {

    @Benchmark
    public void hppc(Blackhole blackhole) {
        blackhole.consume(state.hppc.contains(state.input[state.index]));
    }

    @Benchmark
    public void fastutil(Blackhole blackhole) {
        blackhole.consume(state.fastutil.contains(state.input[state.index]));
    }

    @Benchmark
    public void eclipse(Blackhole blackhole) {
        blackhole.consume(state.eclipse.contains(state.input[state.index]));
    }

    @Benchmark
    public void agrona(Blackhole blackhole) {
        blackhole.consume(state.agrona.contains(state.input[state.index]));
    }

    @Benchmark
    public void mahout(Blackhole blackhole) {
        blackhole.consume(state.mahout.contains(state.input[state.index]));
    }

    @Benchmark
    public void jdk(Blackhole blackhole) {
        blackhole.consume(state.jdk.contains(state.input[state.index]));
    }
}
