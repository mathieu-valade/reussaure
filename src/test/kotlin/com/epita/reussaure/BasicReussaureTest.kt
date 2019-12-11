package com.epita.reussaure

import com.epita.reussaure.core.Reussaure
import com.epita.reussaure.provider.Prototype
import com.epita.reussaure.provider.Singleton
import com.epita.reussaure.test.TestService
import com.epita.reussaure.test.TestServiceBlipImpl
import com.epita.reussaure.test.TestServiceImpl
import com.epita.reussaure.test.aspect.AspectService
import com.epita.reussaure.test.aspect.AspectServiceImpl
import org.junit.Test
import java.util.function.Supplier
import kotlin.reflect.jvm.javaMethod
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BasicReussaureTest {

    @Test
    fun TestSimpleRessaure() {
        val reussaure = Reussaure {
            provider(Prototype(TestServiceBlipImpl::class.java, Supplier { TestServiceBlipImpl() }))
            provider(Prototype(TestServiceImpl::class.java, Supplier { TestServiceImpl() }))
        }

        assertEquals(reussaure.instanceOf(TestServiceBlipImpl::class.java).pong(), "blip")
        assertEquals(reussaure.instanceOf(TestServiceImpl::class.java).pong(), "pong")
    }

    @Test
    fun TestInstanceOfInterface() {
        val reussaure = Reussaure {
            provider(Prototype(TestService::class.java, Supplier { TestServiceImpl() }))
        }

        assert(reussaure.instanceOf(TestService::class.java) is TestServiceImpl)
    }

    @Test
    fun TestBeforeAspect() {

        val valueList = ArrayList<String>()
        val reussaure = Reussaure {
            provider(Prototype(AspectService::class.java, Supplier { AspectServiceImpl(valueList) })
            ) {
                before(TestService::pong.javaMethod) { _, _, _ ->
                    valueList.add("before")
                }
            }
        }
        reussaure.instanceOf(AspectService::class.java).addValue("method")

        assertEquals(valueList.count(), 2)
        assertEquals(valueList[0], "before")
        assertEquals(valueList[1], "method")
    }

    @Test
    fun TestAfterAspect() {
        val valueList = ArrayList<String>()
        val reussaure = Reussaure {
            provider(Prototype(AspectService::class.java, Supplier { AspectServiceImpl(valueList) })
            ) {
                after(TestService::pong.javaMethod) { _, _, _ ->
                    valueList.add("after")
                }
            }
        }
        reussaure.instanceOf(AspectService::class.java).addValue("method")

        assertEquals(valueList.count(), 2)
        assertEquals(valueList[0], "method")
        assertEquals(valueList[1], "after")
    }

    @Test
    fun TestSingletonProvider() {
        val reussaure = Reussaure {
            provider(Singleton(TestService::class.java, Supplier { TestServiceImpl() }))
        }

        val instance1 = reussaure.instanceOf(TestService::class.java)
        val instance2 = reussaure.instanceOf(TestService::class.java)

        assertEquals(instance1, instance2)
    }

    @Test
    fun TestPrototypeProvider() {
        val reussaure = Reussaure {
            provider(Prototype(TestService::class.java, Supplier { TestServiceImpl() }))
        }

        val instance1 = reussaure.instanceOf(TestService::class.java)
        val instance2 = reussaure.instanceOf(TestService::class.java)

        assertNotEquals(instance1, instance2)
    }
}
