package org.denis.sample.java.jmh.collection.list;

import com.carrotsearch.hppc.procedures.LongProcedure;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

import java.util.function.LongConsumer;

public class LongListIterationBenchmark extends BasePrePopulatedLongListBenchmark {

    @Benchmark
    public void hppc(Blackhole blackhole) {
        state.hppc.forEach((LongProcedure) blackhole::consume);
    }

    @Benchmark
    public void fastutil(Blackhole blackhole) {
        state.fastutil.forEach((LongConsumer) blackhole::consume);
    }

    @Benchmark
    public void eclipse(Blackhole blackhole) {
        state.eclipse.forEach((org.eclipse.collections.api.block.procedure.primitive.LongProcedure) blackhole::consume);
    }

    @Benchmark
    public void agrona(Blackhole blackhole) {
        state.agrona.forEach(blackhole::consume);
    }

    @Benchmark
    public void mahout(Blackhole blackhole) {
        state.mahout.forEach(element -> {
            blackhole.consume(element);
            return true;
        });
    }

    @Benchmark
    public void jdk(Blackhole blackhole) {
        state.jdk.forEach(blackhole::consume);
    }
}
