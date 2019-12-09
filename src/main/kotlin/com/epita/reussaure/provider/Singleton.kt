package com.epita.reussaure.provider

import com.epita.reussaure.core.Provider
import java.util.function.Supplier

class Singleton<BEAN_TYPE>(var initializer: Supplier<BEAN_TYPE>) : Provider<BEAN_TYPE> {

    private var value : BEAN_TYPE? = null

    override fun provide(): BEAN_TYPE {
        if (value == null) {
            value = initializer.get()
        }
        return value!!
    }

    override fun provideForClass(): Class<BEAN_TYPE> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}