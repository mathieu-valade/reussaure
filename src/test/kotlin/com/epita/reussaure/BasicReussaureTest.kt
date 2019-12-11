package com.epita.reussaure

//import com.epita.reussaure.util.*
import com.epita.reussaure.core.Reussaure
import com.epita.reussaure.provider.Prototype
import com.epita.reussaure.test.TestServiceBlipImpl
import com.epita.reussaure.test.TestServiceImpl
import org.junit.Assert
import org.junit.Test
import java.util.function.Supplier

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
}
