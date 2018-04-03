package org.denis.sample.java.jmh.collection.list;

import java.util.concurrent.ThreadLocalRandom;

public class BasePrePopulatedLongListBenchmark extends AbstractLongListBenchmark {

    @Override
    public void populateState() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        long limit = state.input.length;
        for (long ignored : state.input) {
            long value = random.nextLong(limit) << 7; // For consistency with the input's shift
            state.addToAll(value);
        }

        for (int i = 0; i < state.input.length; i++) {
            state.input[i] = random.nextLong(limit) << 7;  // Shift to avoid Long.valueOf() optimization on boxing
        }
    }
}
