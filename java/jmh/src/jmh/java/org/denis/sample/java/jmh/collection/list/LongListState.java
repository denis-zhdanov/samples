package org.denis.sample.java.jmh.collection.list;

import com.carrotsearch.hppc.LongArrayList;
import org.denis.sample.java.jmh.collection.common.AbstractBenchmarkState;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;

import java.util.ArrayList;
import java.util.List;

public class LongListState extends AbstractBenchmarkState {

    public LongArrayList hppc = new LongArrayList();

    public org.agrona.collections.LongArrayList agrona = new org.agrona.collections.LongArrayList();

    public org.apache.mahout.math.list.LongArrayList mahout = new org.apache.mahout.math.list.LongArrayList();

    public List<Long> jdk = new ArrayList<>();

    public it.unimi.dsi.fastutil.longs.LongArrayList fastutil = new it.unimi.dsi.fastutil.longs.LongArrayList();

    public org.eclipse.collections.impl.set.mutable.primitive.LongHashSet eclipse
            = new org.eclipse.collections.impl.set.mutable.primitive.LongHashSet();

    @Override
    protected void doReset() {
        hppc = new LongArrayList();
        agrona = new org.agrona.collections.LongArrayList();
        mahout = new org.apache.mahout.math.list.LongArrayList();
        jdk = new ArrayList<>();
        fastutil = new it.unimi.dsi.fastutil.longs.LongArrayList();
        eclipse = new LongHashSet();
    }

    @Override
    public void addToAll(long value) {
        hppc.add(value);
        fastutil.add(value);
        eclipse.add(value);
        agrona.add(value);
        mahout.add(value);
        jdk.add(value);
    }
}
