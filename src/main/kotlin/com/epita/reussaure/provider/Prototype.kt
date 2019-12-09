package com.epita.reussaure.provider

import com.epita.reussaure.core.Provider
import java.util.function.Supplier

class Prototype<BEAN_TYPE>(val initializer: Supplier<BEAN_TYPE>) : Provider<BEAN_TYPE> {

    override fun provide(): BEAN_TYPE {
        return initializer.get()
    }

    override fun provideForClass(): Class<BEAN_TYPE> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}