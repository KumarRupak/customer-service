/*;==========================================
; Title:  End Points for customer
; Author: Rupak Kumar
; Date:   23 Sep 2021
;==========================================*/

package demo.wsc.beta.controller.customer


import demo.wsc.beta.algorithms.utility.Validator
import demo.wsc.beta.enums.AuthRole
import demo.wsc.beta.exceptions.WSCExceptionInvalidDetails
import demo.wsc.beta.exceptions.WSCExceptionInvalidUser
import demo.wsc.beta.model.transport.*
import demo.wsc.beta.repository.CreditRepository
import demo.wsc.beta.service.authentication.ServiceAuthenticationProvider
import demo.wsc.beta.service.customer.ServiceCustomerProvider
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws


@RestController
@RequestMapping("api/customer")
class CustomerAccountEndPoint {

    @Autowired
    private lateinit var serviceCustomer: ServiceCustomerProvider

    @Autowired
    private lateinit var serviceAuth: ServiceAuthenticationProvider

    @Autowired
    private lateinit var repoCredit: CreditRepository





    /**
     * End point for register customer
     *
     * @param 'customer details-JSON'
     * @return - Response enitity
     */

    @PostMapping(
        "/account/register",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun addCustomer(@RequestBody accountDetails: OpenAccount, response: HttpServletResponse): ResponseEntity<Any> {
        //----------
        if (accountDetails.accountNumber.toString().length == 12 && accountDetails.panId!!.length == 10 && Validator.isValidEmailID(
                accountDetails.email!!
            ) == true
        ) {
            val status = serviceCustomer.addCustomer(accountDetails)
            return if (!status.equals(null)) {
                ResponseEntity(status, HttpStatus.OK)
            } else {
                throw WSCExceptionInvalidDetails()
            }
        } else {
            throw WSCExceptionInvalidDetails()
        }
        //---------------------
    }


    /**
     * End point for set the transaction limit for customer personal account
     *
     * @param 'details-JSON'
     * @return - Response enitity
     */
    @PutMapping("account/usage", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun setTransactionLimit(
        @RequestBody limit: SetTransactionLimit,
        token:String?
    ): ResponseEntity<Any> {

        if (!token.equals(null) && Validator.getLength(limit.customerId) == 6) {
            val body = Jwts.parser().setSigningKey(limit.customerId.toString()).parseClaimsJws(token).body

            if (body.values.toList()[0].equals(limit.customerId.toString()) && serviceAuth.getUserLevel(limit.customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //---------
                if (Validator.getLength(limit.customerId) == 6 && Validator.getLength(limit.mpin) == 4 && limit.transactionLimit > 5000) {
                    val status = serviceCustomer.setTransactionLimit(limit)
                    if (status == true) {
                        return ResponseEntity(status, HttpStatus.OK)
                    } else {
                        return ResponseEntity(status, HttpStatus.FORBIDDEN)
                    }
                } else {
                    throw WSCExceptionInvalidDetails()
                }
                //--------
            }
        }
        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for add the amount to personal account from credit card
     *
     * @param 'customer details-JSON'
     * @return - Response enitity
     */
    @PutMapping(
        "/account/transfer",
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun fundTransfer(
        @RequestBody transfer: FundTransfer,
        token:String?
    ): ResponseEntity<Any> {
        println(transfer.cardNumber+" "+transfer.installmentPeriod+" "+transfer.amount+" "+transfer.cardPin)
        val customerId = repoCredit.findById(transfer.cardNumber).get().customerId
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //-------
                return  if (transfer.cardNumber.length == 16 && transfer.amount > 0 && transfer.installmentPeriod > 0 && Validator.getLength(
                        transfer.cardPin
                    ) == 4
                ) {
                    ResponseEntity(serviceCustomer.fundTransfer(transfer), HttpStatus.OK)
                } else {
                    ResponseEntity(serviceCustomer.fundTransfer(transfer), HttpStatus.NO_CONTENT)
                }
                //--------
            }
        }
        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for pay the emi for credit card
     *
     * @param 'customer details-JSON'
     * @return - Response enitity
     */
    @PutMapping("/account/payemi", consumes = [MediaType.APPLICATION_JSON_VALUE], produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun payEmi(
        @RequestBody transfer: PayEmi,
        token:String?
    ): ResponseEntity<Any> {
        val customerId = repoCredit.findById(transfer.cardNumber).get().customerId.or(0)
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //----------
                if (transfer.cardNumber.length == 16 && Validator.getLength(transfer.mpin) == 4 && transfer.installment > 0) {
                    return ResponseEntity(serviceCustomer.payEmi(transfer), HttpStatus.OK)
                } else {
                    return ResponseEntity(serviceCustomer.payEmi(transfer), HttpStatus.FORBIDDEN)
                }
                //--------
            }
        }
        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for show the transactions details
     *
     * @param 'Pan-Id'
     * @return - Response enitity
     */
    @GetMapping("/account/transaction/{customerId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun transactionHistory(
        @PathVariable("customerId") customerId: Int,
        token:String?
    ): ResponseEntity<Any> {
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //------
                  return  ResponseEntity(serviceCustomer.transactionHistory(customerId), HttpStatus.OK)
                //------
            }
        }
        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for show the customer details
     *
     * @param 'customerId'
     * @return - Response enitity
     */
    @GetMapping("/account/{customerId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun getProfile(
        @PathVariable("customerId") customerId: Int,
        token:String?
    ): ResponseEntity<Any> {
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //------
                if (customerId.toString().length == 6) {
                    val credit = serviceCustomer.getProfile(customerId)
                    if (credit.customerId > 0) {
                        return ResponseEntity(credit, HttpStatus.OK)
                    } else {
                        throw WSCExceptionInvalidDetails()
                    }
                } else {
                    throw WSCExceptionInvalidDetails()
                }
                //------
            } else {
                throw WSCExceptionInvalidUser()
            }
        }
        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for enable auto pay for emi bills
     *
     * @param 'customer details-JSON'
     * @return - Response enitity
     */
    @PatchMapping("/account/enableautopay", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class, WSCExceptionInvalidUser::class)
    fun enable(
        @RequestBody cardDetails: PayAutoEmi,
        token:String?
    ): ResponseEntity<Any> {
        if (repoCredit.findById(cardDetails.cardNumber!!).isPresent) {

            val customerId = repoCredit.findById(cardDetails.cardNumber!!).get().customerId
            if (!token.equals(null) && cardDetails.cardNumber!!.length == 16) {
                val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
                if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                    ==AuthRole.CUSTOMER.toString()
                ) {

                    //------
                    if (cardDetails.cardNumber?.length == 16 && Validator.getLength(cardDetails.mPin) == 4) {
                        val status = serviceCustomer.enableAutoPay(cardDetails)
                        if (status == true)
                            return ResponseEntity(true, HttpStatus.OK)
                        else
                            throw WSCExceptionInvalidDetails()
                    } else {
                        throw WSCExceptionInvalidDetails()
                    }
                } else {
                    throw WSCExceptionInvalidUser()
                }
                //------
            } else {
                throw WSCExceptionInvalidUser()
            }
        }
        throw WSCExceptionInvalidUser()
    }


    /**
     * End point for disable auto pay
     *
     * @param 'card Number'
     * @return - Response enitity
     */
    @PatchMapping("/account/disableautopay/{cardNumber}")
    @Throws(
        ExpiredJwtException::class,
        SignatureException::class,
        WSCExceptionInvalidUser::class,
        NoSuchElementException::class
    )
    fun disableAutoPay(
        @PathVariable("cardNumber") cardNumber: String,
        token:String?
    ): ResponseEntity<Any> {

        val customerId = repoCredit.findById(cardNumber).get().customerId
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {

                //----
                if (cardNumber.length == 16) {
                    val status = serviceCustomer.disableAutoPay(cardNumber)
                    if (status == true)
                        return ResponseEntity(true, HttpStatus.OK)
                    else
                        return ResponseEntity(false, HttpStatus.NO_CONTENT)
                } else {
                    throw WSCExceptionInvalidDetails()
                }
                //------
            } else {
                throw WSCExceptionInvalidUser()
            }
        }
        throw WSCExceptionInvalidUser()
    }


}