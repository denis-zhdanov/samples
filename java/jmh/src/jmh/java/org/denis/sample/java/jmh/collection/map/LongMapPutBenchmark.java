package org.denis.sample.java.jmh.collection.map;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.concurrent.ThreadLocalRandom;

public class LongMapPutBenchmark extends AbstractLongMapBenchmark {

    @Override
    public void populateState() {
        long limit = state.input.length * 3 / 4;
        ThreadLocalRandom random = ThreadLocalRandom.current();
        for (int i = 0; i < state.input.length; i++) {
            state.input[i] = random.nextLong(limit) << 7; // Shift to avoid Long.valueOf() optimization on boxing
        }
    }

    @Benchmark
    public void hppc() {
        state.hppc.put(state.input[state.index], Boolean.TRUE);
    }

    @Benchmark
    public void fastutil() {
        state.fastutil.put(state.input[state.index], Boolean.TRUE);
    }

    @Benchmark
    public void eclipse() {
        state.eclipse.put(state.input[state.index], Boolean.TRUE);
    }

    @Benchmark
    public void agrona() {
        state.agrona.put(state.input[state.index], Boolean.TRUE);
    }

    @Benchmark
    public void mahout() {
        state.mahout.put(state.input[state.index], Boolean.TRUE);
    }

    @Benchmark
    public void jdk() {
        state.jdk.put(state.input[state.index], Boolean.TRUE);
    }
}
