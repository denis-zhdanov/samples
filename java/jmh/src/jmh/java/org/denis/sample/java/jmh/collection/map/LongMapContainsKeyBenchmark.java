package org.denis.sample.java.jmh.collection.map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class LongMapContainsKeyBenchmark extends BasePrePopulatedLongMapBenchmark {

    @Benchmark
    public void hppc(Blackhole blackhole) {
        blackhole.consume(state.hppc.containsKey(state.input[state.index]));
    }

    @Benchmark
    public void fastutil(Blackhole blackhole) {
        blackhole.consume(state.fastutil.containsKey(state.input[state.index]));
    }

    @Benchmark
    public void eclipse(Blackhole blackhole) {
        blackhole.consume(state.eclipse.containsKey(state.input[state.index]));
    }

    @Benchmark
    public void agrona(Blackhole blackhole) {
        blackhole.consume(state.agrona.containsKey(state.input[state.index]));
    }

    @Benchmark
    public void mahout(Blackhole blackhole) {
        blackhole.consume(state.mahout.containsKey(state.input[state.index]));
    }

    @Benchmark
    public void jdk(Blackhole blackhole) {
        blackhole.consume(state.jdk.containsKey(state.input[state.index]));
    }
}
