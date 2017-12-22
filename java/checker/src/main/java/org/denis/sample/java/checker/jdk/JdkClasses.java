package org.denis.sample.java.checker.jdk;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.File;

public class JdkClasses {
    public static void main(String[] args) {
        File[] files = new File("/xxx/yyy").listFiles();
        // Checker doesn't detect this
        for (File file : files) {
            process(file);
        }
        // Checker detects this:
        // samples/java/checker/src/main/java/org/denis/sample/java/checker/jdk/JdkClasses.java:16: error: [argument.type.incompatible] incompatible types in argument.
        //        process(System.currentTimeMillis() > 1 ? null : new File("xxx"));
        //                                               ^
        //  found   : @Initialized @Nullable File
        //  required: @Initialized @NonNull File
        process(System.currentTimeMillis() > 1 ? null : new File("xxx"));
    }

    private static void process(@NonNull File file) {
        System.out.println(file.getName());
    }

    static void log(Object x) {
        System.out.println(x.toString());
    }
    static void foo() {
        log(null);
    }
}
