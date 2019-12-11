package com.epita.reussaure.core

import com.epita.reussaure.annotation.NotNull

interface Aspect<BEAN_TYPE : Any> {
    @NotNull
    fun proxify(@NotNull provider: Provider<BEAN_TYPE>, @NotNull bean: BEAN_TYPE): BEAN_TYPE
}