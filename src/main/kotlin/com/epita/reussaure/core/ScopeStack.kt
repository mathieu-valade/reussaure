package com.epita.reussaure.core

import com.epita.reussaure.annotation.Mutate
import com.epita.reussaure.annotation.NotNull
import com.epita.reussaure.annotation.Pure
import com.epita.reussaure.exception.InvalidScopePopException
import com.epita.reussaure.validator.Fault
import java.util.*


interface ScopeStack {
    companion object {
        private val MIN_STACK_SIZE = 1
    }

    @NotNull
    @Pure
    fun getScopeStack(): Deque<Scope>

    @Pure
    fun getHead(): Scope {
        return getScopeStack().peek()
    }

    @Mutate
    fun pushScope(@NotNull scope: Scope) {
        Fault.NULL.validate(scope, "scope")
        getScopeStack().push(scope)
    }

    @Mutate
    fun popScope(): Scope {
        if (getScopeStack().size <= MIN_STACK_SIZE) {
            throw InvalidScopePopException()
        }
        return getScopeStack().pop()
    }

    @NotNull
    @Pure
    fun <BEAN_TYPE : Any> getProvider(@NotNull providerClass: Class<BEAN_TYPE>): Provider<BEAN_TYPE>? {
        Fault.NULL.validate(providerClass, "providerClass")
        return getScopeStack()
                .mapNotNull { scope -> scope.getProvider(providerClass) }
                .first()
    }


    fun <EXPECTED_BEAN_TYPE : Any, REAL_BEAN_TYPE : EXPECTED_BEAN_TYPE> addProvider(expectedClass: Class<EXPECTED_BEAN_TYPE>,
                                                                                    provider: Provider<REAL_BEAN_TYPE>) {
        getHead().addProvider(expectedClass, provider)
    }
}