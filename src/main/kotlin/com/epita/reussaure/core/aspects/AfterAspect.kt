package com.epita.reussaure.core.aspects

import com.epita.reussaure.core.AbstractAspect
import com.epita.reussaure.core.AspectConsumer
import com.epita.reussaure.core.Provider
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class AfterAspect<BEAN_TYPE : Any>(targetMethod: Method, aspect: AspectConsumer<BEAN_TYPE>)
    : AbstractAspect<BEAN_TYPE, AspectConsumer<BEAN_TYPE>>(targetMethod, aspect) {
    override fun proxify(provider: Provider<BEAN_TYPE>, bean: BEAN_TYPE): BEAN_TYPE {
        return Proxy.newProxyInstance(
                bean.javaClass.classLoader,
                arrayOf(provider.provideForClass())
        ) { _: Any, method: Method, args: Array<Any>? ->
            val res = method.invoke(bean, *(args ?: arrayOf()))
            if (checkMethod(method)) {
                aspect(bean, method, args ?: arrayOf())
            }
            res
        } as BEAN_TYPE
    }
}
