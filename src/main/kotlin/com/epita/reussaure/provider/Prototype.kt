package com.epita.reussaure.provider

import com.epita.reussaure.core.Aspect
import com.epita.reussaure.core.Provider
import java.util.function.Supplier


class Prototype<BEAN_TYPE : Any>(private var provideClass: Class<BEAN_TYPE>, private var initializer: Supplier<BEAN_TYPE>) : Provider<BEAN_TYPE> {
    override val aspectList: ArrayList<Aspect<BEAN_TYPE>> = arrayListOf()

    override fun provide(): BEAN_TYPE {
        return proxify(this, initializer.get())
    }

    override fun provideForClass(): Class<BEAN_TYPE> {
        return provideClass
    }

}