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
}