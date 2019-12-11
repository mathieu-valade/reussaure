package com.epita.reussaure.provider

import com.epita.reussaure.core.Aspect
import com.epita.reussaure.core.Provider
import java.util.function.Supplier

class Singleton<BEAN_TYPE: Any>: Provider<BEAN_TYPE> {
    override val aspectList: ArrayList<Aspect<BEAN_TYPE>> = arrayListOf()

    private var value : BEAN_TYPE? = null

    private lateinit var initializer: Supplier<BEAN_TYPE>

    private var provideClass: Class<BEAN_TYPE>

    constructor(provideClass: Class<BEAN_TYPE>, value: BEAN_TYPE) {
        this.provideClass = provideClass
        this.value = proxify(this, value)
    }
    
    constructor(provideClass: Class<BEAN_TYPE>, initializer: Supplier<BEAN_TYPE>) {
        this.provideClass = provideClass
        this.initializer = initializer
    }

    override fun provide(): BEAN_TYPE {
        if (value == null) {
            value = proxify(this, initializer.get())
        }
        return value!!
    }

    override fun provideForClass(): Class<BEAN_TYPE> {
        return provideClass
    }

}