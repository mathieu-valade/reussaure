package com.epita.reussaure.core

import kotlin.collections.ArrayList

class Reussaure(init: Reussaure.() -> Unit = {}) {
    init {
        init.invoke(this)
    }

    private val providerList: List<Provider<Any?>> = ArrayList()


    fun <BEAN_TYPE> instanceOf(expectedClass: Class<BEAN_TYPE>): BEAN_TYPE {
        return providerList
                .filter { provider -> provider.provideForClass().isAssignableFrom(expectedClass) }
                .map { provider -> provider as Provider<BEAN_TYPE>}
                .first()
                .provide()
    }

    fun <BEAN_TYPE> provider(expectedClass: Class<BEAN_TYPE>, provider: Provider<BEAN_TYPE>) {
    }

    fun <BEAN_TYPE> bean(expectedClass: Class<BEAN_TYPE>, bean: BEAN_TYPE) {

    }

    fun <BEAN_TYPE> bean(bean: BEAN_TYPE) {

    }
}