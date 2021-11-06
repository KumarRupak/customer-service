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
import demo.wsc.beta.model.Credit
import demo.wsc.beta.model.WSCCards
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
import javax.servlet.http.HttpServletResponse
import kotlin.jvm.Throws


@RestController
@RequestMapping("api/customer")
class CustomerEndPoints {

    @Autowired
    private lateinit var serviceCustomer: ServiceCustomerProvider

    @Autowired
    private lateinit var serviceAuth: ServiceAuthenticationProvider

    @Autowired
    private lateinit var repoCredit: CreditRepository



    /**
     * End point for check the elegibility for credit
     *
     * @param 'customer details-JSON'
     * @return - Response enitity
     */
    @PostMapping("/offer/predict", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun creditPredction(
        @RequestBody testData: CustomerPredctionsTransport,
        response: HttpServletResponse,
        token:String?
    ): ResponseEntity<Any> {
        if (!token.equals(null)) {
            val body = Jwts.parser().setSigningKey(testData.customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(testData.customerId.toString()) && serviceAuth.getUserLevel(testData.customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {

                //--------
                val status = serviceCustomer.creditPredction(testData)
                if(status>0)
                    return  ResponseEntity(PredictStatus(testData.cardType!!,"Eligible"),HttpStatus.OK)
                else
                    return ResponseEntity(PredictStatus(testData.cardType!!,"Not Eligible"),HttpStatus.NO_CONTENT)
                //--------

            } else {
                throw WSCExceptionInvalidUser()
            }
        }
        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for show all credit cards
     *
     * @param 'customerId'
     * @return - Response enitity
     */

    @GetMapping("cards/{customerId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun getAllCards(
        @PathVariable customerId: Int,
        token:String?
    ): ResponseEntity<Any> {
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body

            return if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //--------
                val status: List<Credit> = serviceCustomer.getAllCards(customerId)
                 if (!status.isEmpty()) {
                     ResponseEntity(status, HttpStatus.OK)
                } else {
                     throw WSCExceptionInvalidDetails()
                }
                //---------
            } else {
                throw WSCExceptionInvalidUser()
            }
        }

        throw WSCExceptionInvalidUser()
    }


    /**
     * End point for get the credit card details
     *
     * @param 'card Number'
     * @return - Response enitity
     */
    @GetMapping("card/{cardNumber}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun getCardDetails(
        @PathVariable("cardNumber") cardNumber: String,
        token:String?
    ): ResponseEntity<Any> {

        val customerId = repoCredit.findById(cardNumber).get().customerId
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {

                //-----
                if (cardNumber.length == 16) {
                    val credit = serviceCustomer.getCardDetails(cardNumber)
                    if (!credit.equals(null)) {
                        return ResponseEntity(credit, HttpStatus.OK)
                    } else {
                        return ResponseEntity(credit, HttpStatus.FORBIDDEN)
                    }
                } else {
                    throw WSCExceptionInvalidDetails()
                }
                //-----
            }
        }
        throw WSCExceptionInvalidUser()
    }


    /**
     * End point for get the credit card details
     *
     * @param 'card Number'
     * @return - Response enitity
     */
    @GetMapping("card/emi/{cardNumber}")
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun getCardEmi(
        @PathVariable("cardNumber") cardNumber: String,
        token:String?
    ): ResponseEntity<Any> {

        val customerId = repoCredit.findById(cardNumber).get().customerId
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {

                //-----
                if (cardNumber.length == 16) {
                    val emi = serviceCustomer.getCardEmi(cardNumber)
                        return ResponseEntity(emi, HttpStatus.OK)
                } else {
                    throw WSCExceptionInvalidDetails()
                }
                //-----
            }
        }
        throw WSCExceptionInvalidUser()
    }


    /**
     * End point for generate the pin for credit card
     *
     * @param 'Datils-JSON'
     * @return - Response enitity
     */
    @PutMapping("card/generatepin", consumes = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun generatePin(
        @RequestBody data: GeneratePin,
        token:String?
    ): ResponseEntity<Any> {
        val customerId = repoCredit.findById(data.cardNumber).get().customerId
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //----------
                if (data.cardNumber.length == 16 && Validator.getLength(data.pin) == 4 && Validator.getLength(data.otp) == 4) {
                    val status = serviceCustomer.generatePin(data)
                    if (status == true) {
                        return ResponseEntity(status, HttpStatus.OK)
                    } else {
                        return ResponseEntity(status, HttpStatus.FORBIDDEN)
                    }
                } else {
                    throw WSCExceptionInvalidDetails()
                }
            }
            //----------
        }
        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for send otp to customer
     *
     * @param 'cistomerId'
     * @return - Response enitity
     */
    @PutMapping("otp/{customerId}")
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun sendOtp(
        @PathVariable customerId: Int,
        token:String?
    ): ResponseEntity<Any> {

        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body

            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {

                //--------
                if (Validator.getLength(customerId) == 6) {
                    val status = serviceCustomer.sendOTP(customerId)
                    if (status > 0) {

                        return ResponseEntity(status, HttpStatus.OK)
                    } else {
                        return ResponseEntity(status, HttpStatus.FORBIDDEN)
                    }
                } else {
                    throw WSCExceptionInvalidDetails()
                }
                //--------
            } else {
                throw WSCExceptionInvalidUser()
            }
        }
        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for block the credit card
     *
     * @param 'card Number'
     * @return - Response enitity
     */
    @PatchMapping("card/block/{cardNumber}")
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun blockCard(
        @PathVariable cardNumber: String,
        token:String?
    ): ResponseEntity<Any> {
        println(token)
        val customerId = repoCredit.findById(cardNumber).get().customerId
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body
            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //-------
                if (cardNumber.length == 16) {
                    val status = serviceCustomer.blockCard(cardNumber)
                    if (status == true) {
                        return ResponseEntity(status, HttpStatus.OK)
                    } else {
                        return ResponseEntity(status, HttpStatus.FORBIDDEN)
                    }
                } else {
                    throw WSCExceptionInvalidDetails()
                }
                //------
            }
        }
        throw WSCExceptionInvalidUser()
    }


    /**
     * End point for get all credit card offers provided by the bank
     *
     * @param 'customerId'
     * @return - Response enitity
     */
    @GetMapping("/offer/{customerId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(ExpiredJwtException::class, SignatureException::class)
    fun getWSCCards(
        @PathVariable customerId: Int,
        token:String?
    ): ResponseEntity<Any> {
        if (!token.equals(null) && Validator.getLength(customerId) == 6) {
            val body = Jwts.parser().setSigningKey(customerId.toString()).parseClaimsJws(token).body

            if (body.values.toList()[0].equals(customerId.toString()) && serviceAuth.getUserLevel(customerId)
                ==AuthRole.CUSTOMER.toString()
            ) {
                //--------
                val status: List<WSCCards> = serviceCustomer.getWSCCards()
                return if (!status.isEmpty()) {
                     ResponseEntity(status, HttpStatus.OK)
                } else {
                    throw WSCExceptionInvalidDetails()
                }
                //---------
            } else {
                throw WSCExceptionInvalidUser()
            }
        }

        throw WSCExceptionInvalidUser()
    }

    /**
     * End point for calculate the cibil score
     *
     * @param 'panId'
     * @return - Response enitity
     */
    @GetMapping("/cibil/{panId}", produces = [MediaType.APPLICATION_JSON_VALUE])
    @Throws(WSCExceptionInvalidDetails::class)
    fun getCibilScore(
    @PathVariable("panId") panId: String
    ): ResponseEntity<Any> {
        if (panId.length == 10) {
            return ResponseEntity(serviceCustomer.getCibilScore(panId), HttpStatus.OK)
        } else {
            throw WSCExceptionInvalidUser()
        }
    }

}