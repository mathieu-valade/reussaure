package com.epita.reussaure.core.aspects

import com.epita.reussaure.core.Aspect
import com.epita.reussaure.core.AspectConsumer
import com.epita.reussaure.core.Provider
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class AfterAspect<BEAN_TYPE : Any>(val aspect: AspectConsumer<BEAN_TYPE>) : Aspect<BEAN_TYPE> {
    override fun proxify(provider: Provider<BEAN_TYPE>, bean: BEAN_TYPE): BEAN_TYPE {
        return Proxy.newProxyInstance(
                bean.javaClass.classLoader,
                arrayOf(provider.provideForClass())
        ) { _: Any, method: Method, args: Array<Any>? ->
            val res = method.invoke(bean, *(args ?: arrayOf()))
            aspect(bean, method, args ?: arrayOf())
            res
        } as BEAN_TYPE
    }
}
