package org.denis.sample.spring.test.mockbehavior.mock;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;

public class DelegatingMockUtil {

    @SuppressWarnings("unchecked")
    @NotNull
    public static <T> T createMock(@NotNull T delegate) {
        Collection<Class<?>> toImplement = new HashSet<>();
        collectInterfaces(delegate.getClass(), toImplement);
        toImplement.add(DelegatingMock.class);
        MyHandler<T> handler = new MyHandler<>(toImplement);
        handler.pushDelegate(delegate);
        return (T) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                                          toImplement.toArray(new Class[toImplement.size()]),
                                          handler);
    }

    @SuppressWarnings("unchecked")
    public static <T> void pushDelegate(@NotNull T delegatingMock, @NotNull T delegate) {
        ((DelegatingMock) delegatingMock).pushDelegate(delegate);
    }

    public static void popDelegate(@NotNull Object delegatingMock) {
        ((DelegatingMock) delegatingMock).popDelegate();
    }

    private static void collectInterfaces(@NotNull Class<?> clazz, @NotNull Collection<Class<?>> holder) {
        for (Class<?> c = clazz; c != null; c = c.getSuperclass()) {
            for (Class<?> intf : c.getInterfaces()) {
                holder.add(intf);
                collectInterfaces(intf, holder);
            }
        }
    }

    @SuppressWarnings("unchecked")
    @NotNull
    public static <T> T rewrite(@NotNull T mock,
                                @NotNull Invocation invocation,
                                @NotNull Function<ArgumentsProvider, Object> mixin)
    {
        if (!(mock instanceof DelegatingMock)) {
            throw new IllegalArgumentException(String.format(
                    "Expected to get an %s instance but got a %s instance: %s",
                    DelegatingMock.class.getSimpleName(), mock.getClass().getSimpleName(), mock
            ));
        }

        DelegatingMock<T> delegate = (DelegatingMock<T>) mock;
        return (T) Proxy.newProxyInstance(
                mock.getClass().getClassLoader(),
                delegate.getMockedClasses().toArray(new Class[delegate.getMockedClasses().size()]),
                (proxy, method, args) -> {
                    try {
                        return invocation.matches(method, args) ? mixin.apply(new ArgumentsProvider(args))
                                                                : method.invoke(mock, args);
                    } catch (InvocationTargetException e) {
                        throw e.getCause() == null ? e : e.getCause();
                    }
                });
    }

    @NotNull
    public static Invocation forMethod(@NotNull String methodName) {
        return new Invocation(methodName, null);
    }

    @NotNull
    public static Invocation forMethod(@NotNull String methodName, @NotNull Class<?> ... argTypes) {
        return new Invocation(methodName, asList(argTypes));
    }

    private static class MyHandler<T> implements InvocationHandler, DelegatingMock<T> {

        private final Stack<T>             delegates     = new Stack<>();
        private final Collection<Class<?>> mockedClasses = new ArrayList<>();

        public MyHandler(@NotNull Collection<Class<?>> mockedClasses) {
            this.mockedClasses.addAll(mockedClasses);
        }

        @NotNull
        @Override
        public Collection<Class<?>> getMockedClasses() {
            return mockedClasses;
        }

        @Override
        public void pushDelegate(@NotNull T delegate) {
            delegates.push(delegate);
        }

        @Override
        public void popDelegate() {
            delegates.pop();
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object callSite = isDelegatingMockMethod(method) ? this : delegates.peek();
            try {
                return method.invoke(callSite, args);
            } catch (InvocationTargetException e) {
                throw e.getCause() == null ? e : e.getCause();
            }
        }

        private boolean isDelegatingMockMethod(@NotNull Method method) {
            return method.getDeclaringClass() == DelegatingMock.class;
        }
    }

    public static class Invocation {

        @NotNull private final String methodName;

        @Nullable private final Map<Class<?>, Integer> argumentTypes;

        Invocation(@NotNull String methodName, @Nullable List<Class<?>> argumentTypes) {
            this.methodName = methodName;
            this.argumentTypes = argumentTypes == null ? null
                                                       : IntStream.range(0, argumentTypes.size())
                                                                  .boxed()
                                                                  .collect(toMap(argumentTypes::get, identity()));
        }

        @SuppressWarnings("SimplifiableIfStatement")
        public boolean matches(@NotNull Method method, @NotNull Object[] args) {
            if (!methodName.matches(method.getName())) {
                return false;
            }
            if (argumentTypes == null) {
                return true;
            }
            return argumentTypes.entrySet().stream().allMatch(e -> e.getKey().isInstance(args[e.getValue()]));
        }
    }

    public static class ArgumentsProvider {

        @NotNull private final Object[] args;

        public ArgumentsProvider(@NotNull Object[] args) {
            this.args = args;
        }

        @SuppressWarnings("unchecked")
        @NotNull
        public <T> T get(int index) {
            return (T) args[index];
        }

        @SuppressWarnings("unchecked")
        @NotNull
        public <T> T get(@NotNull Class<T> targetType) {
            Object candidate = null;
            for (Object arg : args) {
                if (arg == null) {
                    continue;
                }
                if (!targetType.isInstance(arg)) {
                    continue;
                }
                if (candidate == null) {
                    candidate = arg;
                } else {
                    throw new IllegalArgumentException(String.format(
                            "Found more than one %s instance: %s and %s", targetType.getSimpleName(), arg, candidate
                    ));
                }
            }
            if (candidate == null) {
                throw new IllegalArgumentException(String.format(
                        "Can't find a %s instance in the current arguments. Actual types: %s",
                        targetType.getSimpleName(),
                        stream(args).map(a -> a == null ? "<null>" : a.getClass().getSimpleName()).collect(toList())
                ));
            }
            return (T) candidate;
        }
    }
}