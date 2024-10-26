package com.yudiz.testing.mockito

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.lang.NumberFormatException


@RunWith(MockitoJUnitRunner::class) //or Mockito.initMocks(this)
class ServiceTest {

    @Mock
    lateinit var database: Database

    @InjectMocks
    lateinit var service: Service

    @Test
    fun service_getUserNameReturn() {
        /**
         * executes once when defined
         */
        `when`(database.getUserName(1)).thenReturn("smoke")

        Assert.assertEquals("smoke", service.queryUserName(1))
    }

    @Test
    fun service_getUserNameSpy() {
        val database = spy(Database::class.java)
        val service = Service(database)

        Assert.assertEquals("Smoke", service.queryUserName(1))
    }

    @Test
    fun service_getUserNameAnswer() {
        var counter = 0

        /**
         * executes when the mocked method is called in runtime
         */
        `when`(database.getUserName(1)).thenAnswer {
            /*it.arguments
            it.method*/

            counter++
            "smoke"
        }

        service.queryUserName(1)
        service.queryUserName(1)

        Assert.assertEquals(2, counter)
    }

    @Test
    fun service_testVerify() {
        `when`(database.getUserName(1)).thenReturn("smoke")

        service.queryUserName(1)

        verify(database).getUserName(1)
        /*times(3)
        never()
        atLeast(3)
        atMostOnce()
        ...*/
    }

    @Test
    fun service_testTryCatch() {
        `when`(database.getUserName(1)).thenThrow(NumberFormatException("test"))

        Assert.assertEquals("test", service.queryUserName(1))
    }
    @Test
    fun service_testSequence() {
        service.queryUserNameAndProfession(1)

        /**
         * test passes if the following methods are executed in the defined sequence
         */
        val order = inOrder(database)
        order.verify(database).getUserName(1)
        order.verify(database).getUserProfession(1)
    }

    @Test
    fun service_testReset() {
        `when`(database.getUserName(1)).thenThrow(NumberFormatException("test"))

        Assert.assertEquals("test", service.queryUserName(1))

        reset(database)

        Assert.assertEquals(null, service.queryUserName(1))
    }

    @Test
    fun service_testTimeOut() {
        Thread {
            try {
                Thread.sleep(1000)
                service.queryUserName(1)
            } catch (e: InterruptedException) {
            }
        }.start()

        verify(database, timeout(2000)).getUserName(1)
    }

    @Test
    fun service_testBdd() {
        //given
        BDDMockito.given(database.getUserName(1)).willReturn("test")

        //when
        val output = service.queryUserName(1)

        //then
        Assert.assertEquals("test", output)
    }
}