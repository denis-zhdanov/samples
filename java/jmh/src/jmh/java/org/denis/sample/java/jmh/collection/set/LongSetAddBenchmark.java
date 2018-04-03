package org.denis.sample.java.jmh.collection.set;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.concurrent.ThreadLocalRandom;

public class LongSetAddBenchmark extends AbstractLongSetBenchmark {

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
        state.hppc.add(state.input[state.index]);
    }

    @Benchmark
    public void fastutil() {
        state.fastutil.add(state.input[state.index]);
    }

    @Benchmark
    public void eclipse() {
        state.eclipse.add(state.input[state.index]);
    }

    @Benchmark
    public void agrona() {
        state.agrona.add(state.input[state.index]);
    }

    @Benchmark
    public void mahout() {
        state.mahout.add(state.input[state.index]);
    }

    @Benchmark
    public void jdk() {
        state.jdk.add(state.input[state.index]);
    }
}
