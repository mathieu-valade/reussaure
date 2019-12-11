package com.epita.reussaure.core.aspects

import com.epita.reussaure.core.Aspect
import com.epita.reussaure.core.Provider
import com.epita.reussaure.core.ProvidingAspectConsumer
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class AroundAspect<BEAN_TYPE : Any>(val aspect: ProvidingAspectConsumer<BEAN_TYPE>) : Aspect<BEAN_TYPE> {
    override fun proxify(provider: Provider<BEAN_TYPE>, bean: BEAN_TYPE): BEAN_TYPE {
        return Proxy.newProxyInstance(
                bean.javaClass.classLoader,
                arrayOf(provider.provideForClass())
        ) { obj: Any, method: Method, args: Array<Any>? ->
            val nonNullArgs = args ?: arrayOf()
            aspect({ method.invoke(bean, method, nonNullArgs) }, bean, method, nonNullArgs)
        } as BEAN_TYPE
    }
}