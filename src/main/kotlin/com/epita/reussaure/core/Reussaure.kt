package com.epita.reussaure.core

import com.epita.reussaure.provider.Singleton
import com.epita.reussaure.utils.exception.ProviderNotFoundException
import java.util.*


class Reussaure(init: Reussaure.() -> Unit = {}) {

    private val providerDeque: Deque<Scope> = ArrayDeque()
    private val scopeStack: ScopeStack = object : ScopeStack {
        override fun getScopeStack(): Deque<Scope> {
            providerDeque.add(Scope())
            return providerDeque
        }
    }

    init {
        init.invoke(this)
    }

    fun <BEAN_TYPE> instanceOf(expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        val provider = scopeStack.getProvider(expectedClass)
                ?: throw ProviderNotFoundException(expectedClass.toString())

        return provider.provide()
    }

    fun <EXPECTED_BEAN_TYPE : Any, REAL_BEAN_TYPE : EXPECTED_BEAN_TYPE> provider(expectedClass: Class<EXPECTED_BEAN_TYPE>,
                                                                                 provider: Provider<REAL_BEAN_TYPE>) {
        scopeStack.addProvider(expectedClass, provider)
    }

    fun <EXPECTED_BEAN_TYPE : Any, REAL_BEAN_TYPE : EXPECTED_BEAN_TYPE> bean(expectedClass: Class<EXPECTED_BEAN_TYPE>,
                                                                             bean: REAL_BEAN_TYPE) {
        this.provider(expectedClass, Singleton(expectedClass, bean))
    }

    fun <REAL_BEAN_TYPE : Any> bean(bean: REAL_BEAN_TYPE) {
        this.provider(bean.javaClass, Singleton(bean.javaClass, bean))
    }
}