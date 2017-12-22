package org.denis.sample.java.checker.source;

public class Consumer {

    public void check() {
        // Checker detects this:
        // samples/java/checker/src/main/java/org/denis/sample/java/checker/source/Provider.java:6: error: [return.type.incompatible] incompatible types in return.
        //        return System.currentTimeMillis() > 1 ? null : new Object();
        //                                              ^
        //  found   : @Initialized @Nullable Object
        //  required: @Initialized @NonNull Object
        System.out.println(Provider.get().hashCode());
    }
}
