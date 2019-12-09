package com.epita.reussaure.core

interface Provider<BEAN_TYPE> {
    fun provideOptional() : BEAN_TYPE? {
        return provide()
    }

    fun provide() : BEAN_TYPE

    fun provideForClass() : Class<BEAN_TYPE>
}