package org.denis.sample.java.jmh.collection.list;

import org.openjdk.jmh.annotations.Benchmark;

import java.util.concurrent.ThreadLocalRandom;

public class LongListAddBenchmark extends AbstractLongListBenchmark {

    @Override
    public void populateState() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        long limit = state.input.length;
        for (long ignored : state.input) {
            long value = random.nextLong(limit) << 7; // Shift to avoid Long.valueOf() optimization on boxing
            state.addToAll(value);
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
