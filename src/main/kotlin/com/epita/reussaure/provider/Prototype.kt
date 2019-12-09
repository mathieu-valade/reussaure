package com.epita.reussaure.provider

import com.epita.reussaure.core.Provider
import java.beans.beancontext.BeanContext
import java.util.function.Supplier

class Prototype<BEAN_TYPE>: Provider<BEAN_TYPE> {


    private lateinit var initializer: Supplier<BEAN_TYPE>

    constructor(initializer: Supplier<BEAN_TYPE>) {
        this.initializer = initializer
    }

    override fun provide(): BEAN_TYPE {
        return initializer.get()
    }

    override fun provideForClass(): Class<BEAN_TYPE> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}