package com.epita.reussaure

import com.epita.reussaure.core.Reussaure
import com.epita.reussaure.provider.Prototype
import com.epita.reussaure.test.TestService
import com.epita.reussaure.test.TestServiceBlipImpl
import com.epita.reussaure.test.TestServiceImpl
import org.junit.Assert
import org.junit.Test
import java.util.function.Supplier
import kotlin.reflect.jvm.javaMethod

class BasicReussaureTest {

    @Test
    fun TestSimpleRessaure() {
        val reussaure = Reussaure {
            provider(Prototype(TestServiceBlipImpl::class.java, Supplier { TestServiceBlipImpl() }))
            provider(Prototype(TestServiceImpl::class.java, Supplier { TestServiceImpl() }))
        }

        Assert.assertEquals(reussaure.instanceOf(TestServiceBlipImpl::class.java).pong(), "blip")
        Assert.assertEquals(reussaure.instanceOf(TestServiceImpl::class.java).pong(), "pong")
    }

    @Test
    fun TestInstanceOfInterface() {
        val reussaure = Reussaure {
            provider(Prototype(TestService::class.java, Supplier { TestServiceImpl() }))
        }

        Assert.assertTrue(reussaure.instanceOf(TestService::class.java) is TestServiceImpl)
    }

    @Test
    fun TestBeforeAspect() {
        var beforeHasBeenCalled = false
        val reussaure = Reussaure {
            provider(Prototype(TestService::class.java, Supplier { TestServiceBlipImpl() })
            ) {
                before(TestService::pong.javaMethod) { _, _, _ ->
                    println("BEFORE")
                    beforeHasBeenCalled = true
                }
            }
        }
        reussaure.instanceOf(TestService::class.java).pong()
        Assert.assertTrue(beforeHasBeenCalled)
    }

    @Test
    fun TestAfterAspect() {
        var beforeHasBeenCalled = false
        val reussaure = Reussaure {
            provider(Prototype(TestService::class.java, Supplier { TestServiceBlipImpl() })
            ) {
                after(TestService::pong.javaMethod) { _, _, _ ->
                    println("BEFORE")
                    beforeHasBeenCalled = true
                }
            }
        }
        reussaure.instanceOf(TestService::class.java).pong()
        Assert.assertTrue(beforeHasBeenCalled)
    }

}
