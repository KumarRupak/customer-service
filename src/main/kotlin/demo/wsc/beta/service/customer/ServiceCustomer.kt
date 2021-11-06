/*;==========================================
; Title:  Customer Services
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/
package demo.wsc.beta.service.customer

import kotlin.Throws
import demo.wsc.beta.exceptions.WSCExceptionInvalidModeldata
import demo.wsc.beta.exceptions.WSCExceptionInvalidUser
import javax.mail.MessagingException
import demo.wsc.beta.exceptions.WSCExceptionInvalidDetails
import demo.wsc.beta.model.*
import demo.wsc.beta.model.transport.*
import java.text.ParseException

 interface ServiceCustomer {

    @Throws(WSCExceptionInvalidModeldata::class,MessagingException::class)
    fun addCustomer(accountDetails: OpenAccount): PublishCustomerStatus

    @Throws(WSCExceptionInvalidModeldata::class, WSCExceptionInvalidUser::class, MessagingException::class)
    fun creditPredction(testData: CustomerPredctionsTransport):Int

    fun getAllCards(customerId: Int): List<Credit>

    @Throws(WSCExceptionInvalidDetails::class, MessagingException::class)
    fun generatePin(data: GeneratePin): Boolean

    @Throws(MessagingException::class)
    fun sendOTP(customerId: Int):Int

    @Throws(WSCExceptionInvalidDetails::class)
    fun blockCard(cardNumber: String): Boolean

    @Throws(WSCExceptionInvalidDetails::class, MessagingException::class)
    fun setTransactionLimit(limit: SetTransactionLimit): Boolean

    @Throws(WSCExceptionInvalidDetails::class, MessagingException::class)
    fun fundTransfer(transfer: FundTransfer): TransactionStatus

    @Throws(WSCExceptionInvalidDetails::class, MessagingException::class, ParseException::class)
    fun payEmi(transfer: PayEmi): TransactionStatus

    @Throws(WSCExceptionInvalidUser::class)
    fun transactionHistory(customerId : Int): List<CustomerTransactions>

    @Throws(WSCExceptionInvalidDetails::class)
    fun getCardDetails(cardNumber: String): Credit

    @Throws(WSCExceptionInvalidUser::class)
    fun getProfile(customerId: Int): CustomerDetails

    @Throws(WSCExceptionInvalidUser::class)
    fun enableAutoPay(cardDetails:PayAutoEmi):Boolean

    @Throws(WSCExceptionInvalidUser::class)
    fun disableAutoPay(cardNumber:String):Boolean

    fun getWSCCards():List<WSCCards>

    @Throws(WSCExceptionInvalidDetails::class)
    fun getCibilScore(panId:String): CibilStatus

    @Throws(WSCExceptionInvalidDetails::class)
    fun getCardEmi(cardNumber: String): List<Int>

}