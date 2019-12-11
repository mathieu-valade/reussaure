package com.epita.reussaure.core

import java.lang.reflect.Method
import java.lang.reflect.Proxy

interface Provider<BEAN_TYPE : Any> {
    val interceptor: BeanInterceptor

    private fun collect(method: Method?, block: Handler, handlers: Handlers) {
        if (method == null) {
            return
        }
        if (!handlers.containsKey(method)) {
            handlers[method] = arrayListOf()
        }
        handlers[method]?.add(block)
    }

    fun before(method: Method?, block: Handler) {
        collect(method, block, interceptor.beforeHandlers)
    }
    fun around(method: Method?, block: Handler) {
        collect(method, block, interceptor.aroundHandlers)
    }
    fun after(method: Method?, block: Handler) {
        collect(method, block, interceptor.afterHandlers)
    }

    fun provideOptional() : BEAN_TYPE? {
        return provide()
    }

    fun provide() : BEAN_TYPE

    fun provideForClass() : Class<BEAN_TYPE>

    fun proxify(bean: BEAN_TYPE): BEAN_TYPE {
        return Proxy.newProxyInstance(
                bean.javaClass.classLoader,
                bean.javaClass.interfaces,
                interceptor
        ) as BEAN_TYPE
    }
}