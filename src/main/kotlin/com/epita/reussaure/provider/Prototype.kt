package com.epita.reussaure.provider

import com.epita.reussaure.core.BeanInterceptor
import com.epita.reussaure.core.Provider
import java.lang.reflect.Proxy
import java.util.function.Supplier


class Prototype<BEAN_TYPE: Any>: Provider<BEAN_TYPE> {
    override val interceptor: BeanInterceptor = BeanInterceptor()

    private lateinit var initializer: Supplier<BEAN_TYPE>

    private lateinit var provideClass: Class<BEAN_TYPE>

    constructor(provideClass: Class<BEAN_TYPE>, initializer: Supplier<BEAN_TYPE>) {
        this.initializer = initializer
        this.provideClass = provideClass
    }

    override fun provide(): BEAN_TYPE {
        val value = initializer.get()
        return Proxy.newProxyInstance(
                value.javaClass.classLoader,
                value.javaClass.interfaces,
                interceptor
        ) as BEAN_TYPE
    }

    override fun provideForClass(): Class<BEAN_TYPE> {
        return provideClass
    }
}