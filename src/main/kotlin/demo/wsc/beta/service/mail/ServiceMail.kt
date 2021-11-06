/*;==========================================
; Title:  Service class for email
; Author: Rupak Kumar
; Date:   17 Sep 2021
;==========================================*/
package demo.wsc.beta.service.mail

import kotlin.Throws
import javax.mail.MessagingException

 interface ServiceMail {
    @Throws(MessagingException::class)
    fun sendOtp(reciver: String?): Int

    @Throws(MessagingException::class)
    fun sendPredctionResult(reciver: String?, predction: Int)

    @Throws(MessagingException::class)
    fun sendPinGeneration(reciver: String?, cardNo: String?, transactionLimit: Long?)

    @Throws(MessagingException::class)
    fun sendSetTransactionLimit(reciver: String?, transactionLimit: Long?)

    @Throws(MessagingException::class)
    fun sendCreditAccount(reciver: String?, amount: Long?, accountNo: Long?)

    @Throws(MessagingException::class)
    fun sendDebitAccount(reciver: String?, amount: Long?, accountNo: Long?)

    @Throws(MessagingException::class)
    fun sendAccountDetails(reciver: String?, customerId: Int, mPIN: Int)

    @Throws(MessagingException::class)
    fun sendCredit(reciver: String?, amount: Long?, cardNo: String?)

    @Throws(MessagingException::class)
    fun sendAlert(reciver: String?, customerId: Int)
}