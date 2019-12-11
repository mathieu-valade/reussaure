package com.epita.reussaure.provider

import java.util.function.Supplier

class Singleton<BEAN_TYPE : Any, SUPPLIER_BEAN_TYPE : BEAN_TYPE>(provideClass: Class<BEAN_TYPE>,
                                                                 initializer: Supplier<SUPPLIER_BEAN_TYPE>)
    : AbstractProvider<BEAN_TYPE, SUPPLIER_BEAN_TYPE>(provideClass, initializer) {

    private var value: BEAN_TYPE? = null
    private var isInitialized = false

    override fun provide(): BEAN_TYPE {
        if (!isInitialized) {
            value = proxify(this, initializer.get())
            isInitialized = true
        }
        return value!!
    }

}