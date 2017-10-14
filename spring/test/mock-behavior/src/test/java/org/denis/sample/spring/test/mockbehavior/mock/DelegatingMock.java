package org.denis.sample.spring.test.mockbehavior.mock;

import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface DelegatingMock<T> {

    @NotNull
    Collection<Class<?>> getMockedClasses();

    void pushDelegate(@NotNull T delegate);

    void popDelegate();
}
