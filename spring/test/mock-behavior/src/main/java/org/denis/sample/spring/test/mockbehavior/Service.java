package org.denis.sample.spring.test.mockbehavior;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class Service {

    @NotNull
    private final Map<String, Log> cache;

    @Autowired
    public Service(@NotNull Map<String, Log> cache) {
        this.cache = cache;
    }

    @NotNull
    public Log store(@NotNull Log log) {
        Log result = log;
        Log cached = cache.get(log.getId());
        if (cached == null) {
            cache.put(log.getId(), log);
        } else {
            cache.put(log.getId(), result = cached.append(log));
        }
        return result;
    }
}
