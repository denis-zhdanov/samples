package org.denis.sample.java.jmh.collection.set;

import com.carrotsearch.hppc.LongHashSet;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import org.apache.mahout.math.set.OpenLongHashSet;
import org.denis.sample.java.jmh.collection.common.AbstractBenchmarkState;

import java.util.HashSet;
import java.util.Set;

public class LongSetState extends AbstractBenchmarkState {

    public LongHashSet     hppc     = new LongHashSet();
    public LongOpenHashSet fastutil = new LongOpenHashSet();
    public org.eclipse.collections.impl.set.mutable.primitive.LongHashSet eclipse
            = new org.eclipse.collections.impl.set.mutable.primitive.LongHashSet();
    public org.agrona.collections.LongHashSet agrona = new org.agrona.collections.LongHashSet();
    public OpenLongHashSet mahout = new OpenLongHashSet();
    public Set<Long>       jdk      = new HashSet<>();

    @Override
    protected void doReset() {
        hppc = new LongHashSet();
        fastutil = new LongOpenHashSet();
        eclipse = new org.eclipse.collections.impl.set.mutable.primitive.LongHashSet();
        agrona = new org.agrona.collections.LongHashSet();
        mahout = new OpenLongHashSet();
        jdk = new HashSet<>();
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
