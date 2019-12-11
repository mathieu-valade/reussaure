package com.epita.reussaure.core

import com.epita.reussaure.annotation.Mutate
import com.epita.reussaure.annotation.NotNull
import com.epita.reussaure.annotation.Nullable
import com.epita.reussaure.core.aspects.AfterAspect
import com.epita.reussaure.core.aspects.AroundAspect
import com.epita.reussaure.core.aspects.BeforeAspect
import com.epita.reussaure.validator.Condition
import com.epita.reussaure.validator.Fault
import java.lang.reflect.Method

interface Provider<BEAN_TYPE : Any> {
    val aspectList: ArrayList<Aspect<BEAN_TYPE>>

    @Mutate
    fun before(@Nullable method: Method?, @NotNull block: AspectConsumer<BEAN_TYPE>) {
        Fault.NULL.validate(block, "block")
        if (Condition.IS_NOT_NULL.validate(method)) {
            aspectList.add(BeforeAspect(method as Method, block))
        }
    }

    @Mutate
    fun around(@Nullable method: Method?, @NotNull block: ProvidingAspectConsumer<BEAN_TYPE>) {
        Fault.NULL.validate(block, "block")
        if (Condition.IS_NOT_NULL.validate(method)) {
            aspectList.add(AroundAspect(method as Method, block))
        }
    }

    @Mutate
    fun after(@Nullable method: Method?, @NotNull block: AspectConsumer<BEAN_TYPE>) {
        Fault.NULL.validate(block, "block")
        if (Condition.IS_NOT_NULL.validate(method)) {
            aspectList.add(AfterAspect(method as Method, block))
        }
    }

    fun provideOptional(): BEAN_TYPE? {
        return provide()
    }

    fun provide(): BEAN_TYPE

    fun provideForClass(): Class<BEAN_TYPE>

    fun proxify(provider: Provider<BEAN_TYPE>, bean: BEAN_TYPE): BEAN_TYPE {
        Fault.NULL.validate(Pair(provider, "provider"), Pair(bean, "bean"))

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