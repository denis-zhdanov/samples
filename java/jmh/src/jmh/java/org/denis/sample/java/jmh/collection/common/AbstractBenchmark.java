package org.denis.sample.java.jmh.collection.common;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SampleTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
@Threads(1)
@Warmup(iterations = 1, time = 5)
@Measurement(iterations = 3, time = 5)
@State(Scope.Benchmark)
public abstract class AbstractBenchmark<S extends AbstractBenchmarkState> {

    public final S state = buildState();

    protected abstract S buildState();

    @Setup(Level.Trial)
    public void setUp() {
        populateState();
    }

    public abstract void populateState();

    @Setup(Level.Invocation)
    public void resetIfNecessary() {
        if (++state.index >= state.input.length) {
            state.reset();
            populateState();
        }
    }
}
