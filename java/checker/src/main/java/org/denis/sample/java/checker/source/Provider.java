package org.denis.sample.java.checker.source;

public class Provider {

    public static Object get() {
        return System.currentTimeMillis() > 1 ? null : new Object();
    }
}
