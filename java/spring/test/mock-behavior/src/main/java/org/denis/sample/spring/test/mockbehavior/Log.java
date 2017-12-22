package org.denis.sample.spring.test.mockbehavior;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;

public class Log {

    @NotNull
    private final String id;

    private final List<String> records = new ArrayList<>();

    public Log(@NotNull String id, @NotNull String record) {
        this(id, singletonList(record));
    }

    public Log(@NotNull String id, @NotNull List<String> records) {
        this.id = id;
        this.records.addAll(records);
    }

    @NotNull
    public String getId() {
        return id;
    }

    @NotNull
    public List<String> getRecords() {
        return records;
    }

    @NotNull
    public Log append(@NotNull Log log) {
        List<String> records = new ArrayList<>(this.records);
        records.addAll(log.records);
        return new Log(id, records);
    }
}
