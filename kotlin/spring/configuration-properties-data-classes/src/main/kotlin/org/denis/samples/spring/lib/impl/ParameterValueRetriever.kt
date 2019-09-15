package org.denis.samples.spring.lib.impl

import org.denis.samples.spring.lib.*
import kotlin.reflect.KClass
import kotlin.reflect.KParameter

class ParameterValueRetriever(val parameter: KParameter) {

    private val name = parameter.name

    val error: String? = if (name == null) {
        "can't extract name for parameter #${parameter.index}"
    } else {
        null
    }

    /**
     * @param prefix        prefix to use for the property lookup, e.g. if prefix is *'my'* then for class
     *                      like `data class MyClass(val counter: Int)` property *'my.counter'* would be checked
     * @param creator       non-primitive types creator
     * @param context       instantiation context
     * @return              `null` as an indication that there is no explicit value for the given argument and it's
     *                      optional/has default value
     */
    fun retrieve(prefix: String, creator: KotlinCreator, context: Context): Result<Any?, String>? {
        if (name == null) {
            throw IllegalStateException("Can't retrieve a value of parameter $parameter for path '$prefix' "
                                        + "- the parameter doesn't expose its name")
        }

        val klass = parameter.type.classifier as? KClass<*> ?: return Result.failure(
                "type '${parameter.type}' for argument '$name' for path '$prefix' "
                + "is not a ${KClass::class.qualifiedName}"
        )

        val propertyName = context.getRegularPropertyName(prefix, name)
        if (context.isSimpleType(klass)) {
            return retrieveSimpleValue(propertyName, klass, context)
        }

        if (context.isCollection(klass)) {
            return retrieveCollection(klass, propertyName, creator, context)
        }

        return Result.success(creator.create(propertyName, parameter.type, context))
    }

    private fun retrieveSimpleValue(
            propertyName: String,
            klass: KClass<*>,
            context: Context
    ): Result<Any?, String>? {
        val rawValue = context.getPropertyValue(propertyName)
        return if (rawValue == null) {
            when {
                parameter.type.isMarkedNullable -> Result.success<Any?, String>(null)
                parameter.isOptional -> null
                else -> Result.failure("no value for non-nullable parameter '$propertyName'")
            }
        } else {
            Result.success(context.convertIfNecessary(rawValue, klass))
        }
    }

    private fun retrieveCollection(
            collectionClass: KClass<*>,
            propertyName: String,
            creator: KotlinCreator,
            context: Context
    ) : Result<Any?, String>? {
        val typeArguments = parameter.type.arguments
        if (typeArguments.size != 1) {
            throw IllegalArgumentException(
                    "Failed retrieving value of a '${parameter.type}' property for path '$propertyName' - expected "
                    + "to find a single type argument, but found ${typeArguments.size}: $typeArguments"
            )
        }

        val type = typeArguments[0].type ?: return Result.failure(
                "can't derive collection type for property '$propertyName' of type ${parameter.type}"
        )
        val typeClass = type.classifier as? KClass<*> ?: return Result.failure(
                "can't derive type parameter class for property '$propertyName' of type ${parameter.type}"
        )

        var i = 0
        val parameters = context.createCollection(collectionClass)
        while (true) {
            val collectionElementPropertyName = context.getCollectionElementPropertyName(propertyName, i)
            i++
            if (context.isSimpleType(typeClass)) {
                val rawValue = context.getPropertyValue(collectionElementPropertyName) ?: break
                parameters.add(context.convertIfNecessary(rawValue, typeClass))
                continue
            } else if (context.isCollection(typeClass)) {
                val r = retrieveCollection(typeClass, collectionElementPropertyName, creator, context)
                if (r != null && r.success) {
                    parameters.add(r)
                } else {
                    return Result.failure("can't create a collection for property "
                                          + "$collectionElementPropertyName - ${r?.failureValue}")
                }
            } else {
                try {
                    context.withTolerateEmptyCollection(false) {
                        val element = creator.create<Any>(collectionElementPropertyName, type, context)
                        parameters.add(element)
                    }
                } catch (e: Exception) {
                    break
                }
            }
        }

        return if (parameters.isEmpty() && !context.tolerateEmptyCollection) {
            Result.failure("no collection elements are found and context doesn't tolerate empty collections")
        } else {
            Result.success(parameters)
        }
    }

    override fun toString(): String {
        return "$parameter value retriever"
    }
}