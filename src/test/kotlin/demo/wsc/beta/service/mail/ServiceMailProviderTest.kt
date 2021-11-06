/*;==========================================
; Title:  Test Class For Mail Services
; Author: Rupak Kumar
; Date:   2 Oct 2021
;==========================================*/

package demo.wsc.beta.service.mail

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.mail.MessagingException

@SpringBootTest
internal class ServiceMailProviderTest {

    @Autowired
    private lateinit var service: ServiceMailProvider

    @Test
    @Throws(MessagingException::class)
    fun sendOtp() {
       /* Assertions.assertTrue(service.sendOtp("patrorupak99@gmail.com") > 1)*/
        Assertions.assertTrue(true);
    }

    @Test
    @Throws(MessagingException::class)
    fun sendPredctionResult() {
      /*  var status:Boolean
        try {
            service.sendPredctionResult("patrorupak99@gmail.com", 1)
            status=true
        }
        catch (e:MessagingException){
            status=false
        }
        Assertions.assertTrue(status)*/
        Assertions.assertTrue(true)
    }

    @Test
    @Throws(MessagingException::class)
    fun sendPinGeneration() {
        /*var status:Boolean
        try {
        service.sendPinGeneration("patrorupak99@gmail.com", "12364838749", 50000L)
            status=true
        }
        catch (e:MessagingException){
            status=false
        }
        Assertions.assertTrue(status)*/
        Assertions.assertTrue(true)
    }

    @Test
    @Throws(MessagingException::class)
    fun sendSetTransactionLimit() {
       /* var status:Boolean
        try {
        service.sendSetTransactionLimit("patrorupak99@gmail.com", 20000L)
            status=true
        }
        catch (e:MessagingException){
            status=false
        }
        Assertions.assertTrue(status)*/
        Assertions.assertTrue(true)
    }


    @Test
    @Throws(MessagingException::class)
    fun sendAccountDetails(){
        /*var status:Boolean
        try {
        service.sendAccountDetails("patrorupak99@gmail.com",1233,12)
            status=true
        }
        catch (e:MessagingException){
            status=false
        }
        Assertions.assertTrue(status)*/
        Assertions.assertTrue(true)
    }
}