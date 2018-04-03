package org.denis.sample.java.jmh.collection.set;

import java.util.concurrent.ThreadLocalRandom;

public class BasePrePopulatedLongSetBenchmark extends AbstractLongSetBenchmark {

    @Override
    public void populateState() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        long limit = state.input.length * 3 / 4;
        for (long ignored : state.input) {
            long value = random.nextLong(limit) << 7; // For consistency with the input's shift
            state.addToAll(value);
        }

        limit = state.input.length * 3 / 2;
        for (int i = 0; i < state.input.length; i++) {
            state.input[i] = random.nextLong(limit) << 7;  // Shift to avoid Long.valueOf() optimization on boxing
        }
    }
}
