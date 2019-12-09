package com.epita.reussaure.core

class Scope {
    val providers: ArrayList<Pair<Class<Any>, Provider<Any>>> = arrayListOf()

    fun <EXPECTED_BEAN_TYPE, REAL_BEAN_TYPE : EXPECTED_BEAN_TYPE> addProvider(expectedClass: Class<EXPECTED_BEAN_TYPE>,
                                                                              provider: Provider<REAL_BEAN_TYPE>) {
        providers.add(Pair(expectedClass as Class<Any>, provider as Provider<Any>))
    }

    fun <BEAN_TYPE> getProvider(providerClass: Class<BEAN_TYPE>): Provider<BEAN_TYPE>? {
        for (providerPair in providers) {
            if (providerPair.first == providerClass) {
                return providerPair.second as Provider<BEAN_TYPE>
            }
        }
        return null
    }

}
