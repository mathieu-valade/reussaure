package com.epita.reussaure.provider

import com.epita.reussaure.core.Provider
import java.util.function.Supplier

class Prototype<BEAN_TYPE>: Provider<BEAN_TYPE> {

    private lateinit var providesClass: Class<BEAN_TYPE>

    private lateinit var initializer: Supplier<BEAN_TYPE>

    constructor(providesClass: Class<BEAN_TYPE>, initializer: Supplier<BEAN_TYPE>) {
        this.providesClass = providesClass
        this.initializer = initializer
    }

    override fun provide(): BEAN_TYPE? {
        return initializer.get()
    }

    override fun providesForClass(): Class<BEAN_TYPE> {

        return providesClass

    }


}