package com.epita.reussaure.core

class Scope {
    val providers: ArrayList<Provider<Any>> = arrayListOf()

    fun <BEAN_TYPE> addProvider(provider: Provider<BEAN_TYPE>) {
        providers.add(provider as Provider<Any>)
    }

    fun <BEAN_TYPE> getProvider(providerClass: Class<BEAN_TYPE>): Provider<BEAN_TYPE>? {
        for (provider in providers) {
            if (provider.javaClass == providerClass) {
                return provider as Provider<BEAN_TYPE>
            }
        }
        return null
    }

}
