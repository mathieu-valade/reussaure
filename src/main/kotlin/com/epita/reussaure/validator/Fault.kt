package com.epita.reussaure.validator

import com.epita.reussaure.exception.InvalidArgumentException
import com.epita.reussaure.exception.InvalidValueException

enum class Fault(
        val message: String,
        val predicate: (Any?) -> Boolean) {

    NULL("is null", Predicate.isNull);

    fun forField(field: String): InvalidArgumentException {
        return InvalidArgumentException(field, this)
    }

    fun forValue(): InvalidValueException {
        return InvalidValueException(this)
    }

    fun <OBJECT_TYPE> validate(value: OBJECT_TYPE): OBJECT_TYPE {
        if (predicate(value)) {
            throw forValue()
        }
        return value
    }

    fun <OBJECT_TYPE> validate(value: OBJECT_TYPE, field: String): OBJECT_TYPE {
        if (predicate(value)) {
            throw forField(field)
        }
        return value
    }
}