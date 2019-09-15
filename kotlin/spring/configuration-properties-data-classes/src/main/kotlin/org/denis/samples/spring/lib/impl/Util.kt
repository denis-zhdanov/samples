package org.denis.samples.spring.lib.impl

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KVisibility

inline fun <T, R> Iterable<T>.mapFirstNotNull(transform: (T) -> R): R? {
    for (item in this) {
        val result = transform(item)
        if (result != null) {
            return result
        }
    }
    return null
}

fun <T : Any> parseConstructors(klass: KClass<T>): Collection<KFunction<T>> {
    return klass.constructors.filter {
        it.visibility == KVisibility.PUBLIC
    }
}