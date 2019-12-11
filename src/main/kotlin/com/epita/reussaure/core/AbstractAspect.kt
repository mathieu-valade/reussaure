package com.epita.reussaure.core

import java.lang.reflect.Method

abstract class AbstractAspect<BEAN_TYPE : Any, ASPECT_TYPE>(
        val targetMethod: Method,
        val aspect: ASPECT_TYPE) : Aspect<BEAN_TYPE> {

    protected fun checkMethod(invokedMethod: Method): Boolean {
        return targetMethod.name == invokedMethod.name
    }
}