package com.epita.reussaure.core

import com.epita.reussaure.core.aspects.AfterAspect
import com.epita.reussaure.core.aspects.AroundAspect
import com.epita.reussaure.core.aspects.BeforeAspect
import java.lang.reflect.Method

interface Provider<BEAN_TYPE : Any> {
    val aspectList: ArrayList<Aspect<BEAN_TYPE>>

    fun before(method: Method?, block: AspectConsumer<BEAN_TYPE>) {
        aspectList.add(BeforeAspect(block))
    }

    fun around(method: Method?, block: ProvidingAspectConsumer<BEAN_TYPE>) {
        aspectList.add(AroundAspect(block))
    }

    fun after(method: Method?, block: AspectConsumer<BEAN_TYPE>) {
        aspectList.add(AfterAspect(block))
    }

    fun provideOptional(): BEAN_TYPE? {
        return provide()
    }

    fun provide(): BEAN_TYPE

    fun provideForClass(): Class<BEAN_TYPE>

    fun proxify(provider: Provider<BEAN_TYPE>, bean: BEAN_TYPE): BEAN_TYPE {
        if (aspectList.isEmpty()) {
            return bean
        }
        if (!provideForClass().isInterface) {
            throw RuntimeException("Not an interface")
        }

        var proxied = bean

        aspectList.forEach { aspect -> proxied = aspect.proxify(this, proxied) }

        return proxied
    }
}