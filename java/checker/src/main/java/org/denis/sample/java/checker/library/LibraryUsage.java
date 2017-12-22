package org.denis.sample.java.checker.library;

import org.apache.commons.lang3.StringUtils;

public class LibraryUsage {

    public void check() {
        // Checker incorrectly reports this as StringUtils.capitalize() handles null arguments:
        // samples/java/checker/src/main/java/org/denis/sample/java/checker/library/LibraryUsage.java:8: error: [argument.type.incompatible] incompatible types in argument.
        //        String s = StringUtils.capitalize(System.currentTimeMillis() % 2 == 0 ? null : "xx");
        //                                                                              ^
        //  found   : @Initialized @Nullable String
        //  required: @Initialized @NonNull String
        String s = StringUtils.capitalize(System.currentTimeMillis() % 2 == 0 ? null : "xx");
        if (s != null) {
            System.out.println(s.hashCode());
        }
    }
}
