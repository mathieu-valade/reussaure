package com.epita.reussaure.core.aspects

import com.epita.reussaure.core.AbstractAspect
import com.epita.reussaure.core.Provider
import com.epita.reussaure.core.ProvidingAspectConsumer
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class AroundAspect<BEAN_TYPE : Any>(targetMethod: Method, aspect: ProvidingAspectConsumer<BEAN_TYPE>)
    : AbstractAspect<BEAN_TYPE, ProvidingAspectConsumer<BEAN_TYPE>>(targetMethod, aspect) {
    override fun proxify(provider: Provider<BEAN_TYPE>, bean: BEAN_TYPE): BEAN_TYPE {
        return Proxy.newProxyInstance(
                bean.javaClass.classLoader,
                arrayOf(provider.provideForClass())
        ) { obj: Any, method: Method, args: Array<Any>? ->
            val nonNullArgs = args ?: arrayOf()
            if (checkMethod(method)) {
                aspect({ method.invoke(bean, *nonNullArgs) }, bean, method, nonNullArgs)
            } else {
                method.invoke(bean, *nonNullArgs)
            }
        } as BEAN_TYPE
    }
}