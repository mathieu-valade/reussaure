package com.epita.reussaure.provider

import com.epita.reussaure.core.BeanInterceptor
import com.epita.reussaure.core.Provider
import java.lang.reflect.Proxy
import java.util.function.Supplier

class Singleton<BEAN_TYPE: Any>: Provider<BEAN_TYPE> {
    override val interceptor: BeanInterceptor = BeanInterceptor()

    private var value : BEAN_TYPE? = null

    private lateinit var initializer: Supplier<BEAN_TYPE>

    private lateinit var provideClass: Class<BEAN_TYPE>

    constructor(provideClass: Class<BEAN_TYPE>, value: BEAN_TYPE) {
        this.value = proxify(value)
        this.provideClass = provideClass
    }
    
    constructor(provideClass: Class<BEAN_TYPE>, initializer: Supplier<BEAN_TYPE>) {
        this.initializer = initializer
        this.provideClass = provideClass
    }

    override fun provide(): BEAN_TYPE {
        if (value == null) {
            value = proxify(initializer.get())
        }
        return value!!
    }

    override fun provideForClass(): Class<BEAN_TYPE> {
        return provideClass
    }

}