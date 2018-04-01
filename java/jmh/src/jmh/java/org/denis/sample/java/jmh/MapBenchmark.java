package org.denis.sample.java.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Warmup(iterations = 1)
@Measurement(iterations = 3)
@State(Scope.Group)
public class MapBenchmark {

    public static final String PARAM_SYNCHRONIZED_MAP    = "SynchronizedMap";
    public static final String PARAM_CONCURRENT_HASH_MAP = "ConcurrentHashMap";

    public static final String GROUP_NAME = "MyGroup";

    public static final int THREADS_NUMBER = 4;

    @Param({PARAM_SYNCHRONIZED_MAP, PARAM_CONCURRENT_HASH_MAP})
    public volatile String implementation;

    @State(Scope.Thread)
    public static class RandomWrapper {

        public int value;

        @Setup(Level.Invocation)
        public void nextValue() {
            value = ThreadLocalRandom.current().nextInt(1000);
        }
    }

    public volatile Map<Integer, Integer> map;

    @Setup(Level.Trial)
    public void setUp() {
        switch (implementation) {
            case PARAM_SYNCHRONIZED_MAP:
                map = Collections.synchronizedMap(new HashMap<>());
                break;
            case PARAM_CONCURRENT_HASH_MAP:
                map = new ConcurrentHashMap<>();
                break;
            default:
                throw new UnsupportedOperationException("Unsupported implementation " + implementation);
        }
    }

    @Benchmark
    @Group(GROUP_NAME)
    @GroupThreads(THREADS_NUMBER)
    public void put(RandomWrapper random) {
        map.put(random.value, 1);
    }

    @Benchmark
    @Group(GROUP_NAME)
    @GroupThreads(THREADS_NUMBER)
    public void get(RandomWrapper random) {
        map.get(random.value);
    }

    @Benchmark
    @Group(GROUP_NAME)
    @GroupThreads(THREADS_NUMBER)
    public void remove(RandomWrapper random) {
        map.remove(random.value);
    }
}
