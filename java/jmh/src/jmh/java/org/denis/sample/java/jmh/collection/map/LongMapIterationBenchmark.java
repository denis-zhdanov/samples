package org.denis.sample.java.jmh.collection.map;

import com.carrotsearch.hppc.procedures.LongObjectProcedure;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.infra.Blackhole;

public class LongMapIterationBenchmark extends BasePrePopulatedLongMapBenchmark {

    @Benchmark
    public void hppc(Blackhole blackhole) {
        state.hppc.forEach((LongObjectProcedure<Boolean>) (key, value) -> blackhole.consume(value));
    }

    @Benchmark
    public void fastutil(Blackhole blackhole) {
        state.fastutil.forEach((key, value) -> blackhole.consume(value));
    }

    @Benchmark
    public void eclipse(Blackhole blackhole) {
        state.eclipse.forEachKeyValue(
                (org.eclipse.collections.api.block.procedure.primitive.LongObjectProcedure<Boolean>)
                        (key, value) -> blackhole.consume(value));
    }

    @Benchmark
    public void agrona(Blackhole blackhole) {
        state.agrona.forEach((key, value) -> blackhole.consume(value));
    }

    @Benchmark
    public void mahout(Blackhole blackhole) {
        state.mahout.forEachPair((key, value) -> {
            blackhole.consume(value);
            return true;
        });
    }

    @Benchmark
    public void jdk(Blackhole blackhole) {
        state.jdk.forEach((key, value) -> blackhole.consume(value));
    }
}
