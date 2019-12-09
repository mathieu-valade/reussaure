package com.epita.reussaure.core

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

typealias Handlers = Map<Method, List<() -> Unit>>

class BeanInterceptor(
        private val beforeHandlers: Handlers,
        private val aroundHandlers: Handlers,
        private val afterHandlers: Handlers
) : InvocationHandler {

    private fun handle(method: Method, handlers: Handlers) {
        for (handler in handlers.getOrDefault(method, listOf())) {
            handler()
        }
    }

    private fun handleBefore(method: Method) {
        handle(method, beforeHandlers)
    }

    private fun handleAround(method: Method) {
        handle(method, aroundHandlers)
    }

    private fun handleAfter(method: Method) {
        handle(method, afterHandlers)
    }

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>): Any {
        handleBefore(method)
        handleAround(method)
        val result = method.invoke(proxy, args)
        handleAround(method)
        handleAfter(method)
        return result
    }
}