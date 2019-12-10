package com.epita.reussaure.core

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.util.*


typealias Handler = () -> Unit
typealias Handlers = HashMap<Method, ArrayList<Handler>>

class BeanInterceptor: InvocationHandler {
    val beforeHandlers: Handlers = hashMapOf()
    val aroundHandlers: Handlers = hashMapOf()
    val afterHandlers: Handlers = hashMapOf()


    private fun handle(method: Method, handlers: Handlers) {
        for (handler in handlers.getOrDefault(method, arrayListOf())) {
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

    override fun invoke(proxy: Any, method: Method, args: Array<out Any>?): Any {
        handleBefore(method)
        handleAround(method)
        val result = method.invoke(proxy, *(args ?: arrayOf()))
        handleAround(method)
        handleAfter(method)
        return result
    }
}