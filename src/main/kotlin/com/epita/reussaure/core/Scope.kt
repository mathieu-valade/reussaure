package com.epita.reussaure.core

import com.epita.reussaure.annotation.NotNull
import com.epita.reussaure.annotation.Pure
import com.epita.reussaure.validator.Fault

class Scope {
    private val providers: ArrayList<Provider<Any>> = arrayListOf()

    fun <BEAN_TYPE : Any> addProvider(@NotNull provider: Provider<BEAN_TYPE>) {
        Fault.NULL.validate(provider, "provider")
        providers.add(provider as Provider<Any>)
    }

    @NotNull
    @Pure
    fun <@NotNull BEAN_TYPE : Any> getProvider(@NotNull providerClass: Class<BEAN_TYPE>): Provider<BEAN_TYPE>? {
        Fault.NULL.validate(providerClass, "providerClass")

        return providers
                .filter { provider -> providerClass.isAssignableFrom(provider.provideForClass()) }
                .map { provider -> provider as Provider<BEAN_TYPE> }
                .first()
    }

}
