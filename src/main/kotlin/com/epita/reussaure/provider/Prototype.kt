package com.epita.reussaure.provider

import com.epita.reussaure.core.BeanInterceptor
import com.epita.reussaure.core.Provider
import java.util.function.Supplier


class Prototype<BEAN_TYPE : Any, SUPPLIER_BEAN_TYPE : BEAN_TYPE> : Provider<BEAN_TYPE> {
    override val interceptor: BeanInterceptor = BeanInterceptor()
    private val initializer: Supplier<SUPPLIER_BEAN_TYPE>
    private val provideClass: Class<BEAN_TYPE>

    constructor(provideClass: Class<BEAN_TYPE>, initializer: Supplier<SUPPLIER_BEAN_TYPE>) {
        this.initializer = initializer
        this.provideClass = provideClass
    }

    override fun provide(): BEAN_TYPE {
        return initializer.get()
    }

    override fun provideForClass(): Class<BEAN_TYPE> {
        return provideClass
    }
}