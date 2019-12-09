package com.epita.reussaure.core

import com.epita.reussaure.provider.Singleton
import java.util.*


class Reussaure(init: Reussaure.() -> Unit = {}) {
    init {
        init.invoke(this)
    }

    private val providerList: Deque<Scope> = ArrayDeque()
    private val scopeStack: ScopeStack = object : ScopeStack {
        override fun getScopeStack(): Deque<Scope> {
            return providerList
        }
    }

    fun <BEAN_TYPE> instanceOf(expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        TODO()
    }

    fun <EXPECTED_TYPE, REAL_TYPE : EXPECTED_TYPE> provider(expectedClass: Class<EXPECTED_TYPE>,
                                                            provider: Provider<REAL_TYPE>) {

    }

    fun <EXPECTED_TYPE, REAL_TYPE : EXPECTED_TYPE> bean(expectedClass: Class<EXPECTED_TYPE>,
                                                        bean: REAL_TYPE) {
        this.provider(expectedClass, Singleton(expectedClass, bean))
    }

    fun <REAL_BEAN_TYPE> bean(bean: REAL_BEAN_TYPE) {
    }
}