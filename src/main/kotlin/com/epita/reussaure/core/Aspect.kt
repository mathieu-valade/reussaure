package com.epita.reussaure.core

interface Aspect<BEAN_TYPE : Any> {
    fun proxify(provider: Provider<BEAN_TYPE>, bean: BEAN_TYPE): BEAN_TYPE
}