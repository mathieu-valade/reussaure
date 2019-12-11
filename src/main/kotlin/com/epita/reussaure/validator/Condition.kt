package com.epita.reussaure.validator

enum class Condition(val predicate: (Any?) -> Boolean) {
    IS_NOT_NULL(Predicate.isNotNull);

    fun <OBJECT_TYPE> validate(value: OBJECT_TYPE): Boolean {
        return predicate(value)
    }
}