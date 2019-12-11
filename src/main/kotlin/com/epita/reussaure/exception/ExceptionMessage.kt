package com.epita.reussaure.exception

enum class ExceptionMessage
(val message: String) {
    INVALID_SCOPE_POP("Not enough scope left in the stack to pop"),
    BEAN_NOT_FOUND("No bean of type %s was found");
}