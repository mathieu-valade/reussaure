package com.epita.reussaure.core

import com.epita.reussaure.annotation.Mutate
import com.epita.reussaure.annotation.NotNull
import com.epita.reussaure.validator.Fault
import java.util.*


class Reussaure(init: Reussaure.() -> Unit = {}) {

    private val scopeDeque: Deque<Scope> = ArrayDeque()
    private val scopeStack: ScopeStack = object : ScopeStack {
        override fun getScopeStack(): Deque<Scope> {
            return scopeDeque
        }
    }

    init {
        scopeStack.pushScope(Scope())
        init.invoke(this)
    }

    @NotNull
    fun <BEAN_TYPE : Any> instanceOf(expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        val provider = scopeStack.getProvider(expectedClass)

        return provider.provide()
    }

    @Mutate
    fun <BEAN_TYPE : Any> provider(@NotNull provider: Provider<BEAN_TYPE>) {
        Fault.NULL.validate(provider)
        scopeStack.addProvider(provider)
    }

    @Mutate
    fun <BEAN_TYPE : Any> provider(@NotNull provider: Provider<BEAN_TYPE>, @NotNull block: Provider<BEAN_TYPE>.() -> Unit) {
        Fault.NULL.validate(Pair(provider, "provider"), Pair(block, "block"))
        block.invoke(provider)
        scopeStack.addProvider(provider)
    }

}