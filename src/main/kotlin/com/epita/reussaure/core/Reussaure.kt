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

//    fun scope(init: Scope.() -> Unit = {}) {
//        scopeStack.pushScope(Scope().apply(init))
//    }

    fun <BEAN_TYPE : Any> instanceOf(expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        val provider = scopeStack.getProvider(expectedClass)

        return provider.provide()
    }

    @Mutate
    fun <BEAN_TYPE : Any> provider(@NotNull provider: Provider<BEAN_TYPE>) {
        Fault.NULL.validate(provider)
        scopeStack.addProvider(provider)
    }

//    fun <EXPECTED_BEAN_TYPE : Any, REAL_BEAN_TYPE : EXPECTED_BEAN_TYPE> bean(
//            expectedClass: Class<EXPECTED_BEAN_TYPE>,
//            bean: REAL_BEAN_TYPE,
//            block: Provider<REAL_BEAN_TYPE>.() -> Unit
//    ) {
//
//        val provider = Singleton(bean.javaClass, bean)
//        block.invoke(provider)
//        this.provider(expectedClass, provider)
//    }
}