package org.denis.sample.java.jmh.collection.map;

import com.carrotsearch.hppc.LongObjectHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import org.agrona.collections.Long2ObjectHashMap;
import org.apache.mahout.math.map.OpenLongObjectHashMap;
import org.denis.sample.java.jmh.collection.common.AbstractBenchmarkState;

import java.util.HashMap;
import java.util.Map;

public class LongMapState extends AbstractBenchmarkState {

    public LongObjectHashMap<Boolean> hppc = new LongObjectHashMap<>();

    public Long2ObjectHashMap<Boolean> agrona = new Long2ObjectHashMap<>();

    public OpenLongObjectHashMap<Boolean> mahout = new OpenLongObjectHashMap<>();

    public Map<Long, Boolean> jdk = new HashMap<>();

    public Long2ObjectOpenHashMap<Boolean> fastutil = new Long2ObjectOpenHashMap<>();

    public org.eclipse.collections.impl.map.mutable.primitive.LongObjectHashMap<Boolean> eclipse
            = new org.eclipse.collections.impl.map.mutable.primitive.LongObjectHashMap<>();

    @Override
    protected void doReset() {
        hppc = new LongObjectHashMap<>();
        agrona = new Long2ObjectHashMap<>();
        mahout = new OpenLongObjectHashMap<>();
        jdk = new HashMap<>();
        fastutil = new Long2ObjectOpenHashMap<>();
        eclipse = new org.eclipse.collections.impl.map.mutable.primitive.LongObjectHashMap<>();
    }

    @Override
    public void addToAll(long key) {
        hppc.put(key, Boolean.TRUE);
        fastutil.put(key, Boolean.TRUE);
        eclipse.put(key, Boolean.TRUE);
        agrona.put(key, Boolean.TRUE);
        mahout.put(key, Boolean.TRUE);
        jdk.put(key, Boolean.TRUE);
    }
}
