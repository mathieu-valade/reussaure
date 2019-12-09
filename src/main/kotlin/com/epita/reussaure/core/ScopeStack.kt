package com.epita.reussaure.core

import java.util.*


interface ScopeStack {
    fun getScopeStack(): Deque<Scope>

    fun getHead(): Scope {
        return getScopeStack().peek()
    }

    fun pushScope(scope: Scope) {
        getScopeStack().push(scope)
    }

    fun popScope(): Scope {
        return getScopeStack().pop()
    }

    fun <BEAN_TYPE> getProvider(providerClass: Class<BEAN_TYPE>): Provider<BEAN_TYPE>? {
        for (scope in getScopeStack().reversed()) {
            val provider = scope.getProvider(providerClass)
            if (provider != null) {
                return provider
            }
        }
        return null
    }

    fun <BEAN_TYPE> addProvider(provider: Provider<BEAN_TYPE>) {
        getHead().addProvider(provider)
    }
}