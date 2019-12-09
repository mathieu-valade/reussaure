package com.epita.reussaure.provider

import com.epita.reussaure.core.Provider
import java.beans.beancontext.BeanContext
import java.util.function.Supplier

class Prototype<BEAN_TYPE>: Provider<BEAN_TYPE> {


    private lateinit var initializer: Supplier<BEAN_TYPE>

    private lateinit var provideClass: Class<BEAN_TYPE>

    constructor(provideClass: Class<BEAN_TYPE>, initializer: Supplier<BEAN_TYPE>) {
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