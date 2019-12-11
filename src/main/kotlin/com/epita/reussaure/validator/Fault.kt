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

    fun <OBJECT_TYPE> softCheck(value: OBJECT_TYPE): Boolean {
        return predicate(value)
    }


    fun <OBJECT_TYPE> validate(value: OBJECT_TYPE) {
        if (predicate(value)) {
            throw forValue()
        }
    }

    fun <OBJECT_TYPE> validate(value: OBJECT_TYPE, field: String) {
        if (predicate(value)) {
            throw forField(field)
        }
    }

    fun <OBJECT_TYPE> validate(vararg valueFields: Pair<OBJECT_TYPE, String>) {
        valueFields.forEach {
            validate(it.first, it.second)
        }
    }
}