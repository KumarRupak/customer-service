/*;==========================================
; Title:  Advice Controller for all end-Points
; Author: Rupak Kumar
; Date:   23 Sep 2021
;==========================================*/

package demo.wsc.beta.controller.advisecontroller

import demo.wsc.beta.exceptions.*
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.SignatureException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.NullPointerException
import java.util.NoSuchElementException
import javax.mail.MessagingException

@RestControllerAdvice
class WSCExceptionHandler {

    @ExceptionHandler
    fun InvalidTokenException(exception: ExpiredJwtException): ResponseEntity<Any> {
        return ResponseEntity("unauthorized", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun tokenParserException(exception: SignatureException): ResponseEntity<Any> {
        return ResponseEntity("unauthorized", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun invalidUserException(exception: WSCExceptionInvalidUser): ResponseEntity<Any> {
        return ResponseEntity("Invalid User", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun invalidUserException(exception: NoSuchElementException): ResponseEntity<Any> {
        return ResponseEntity("Invalid User", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun invalidEmailException(exception: MessagingException): ResponseEntity<Any> {
        return ResponseEntity("Invalid Email Address", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun invalidTokenException(exception: InvalidToken): ResponseEntity<Any> {
        return ResponseEntity("Invalid Token", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun invalidDetailsException(exception: WSCExceptionInvalidDetails): ResponseEntity<Any> {
        return ResponseEntity("Invalid Details", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun invalidModeldataException(
        exception: WSCExceptionInvalidModeldata,
        e: NullPointerException
    ): ResponseEntity<Any> {
        return ResponseEntity("Invalid Data", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun insufficientFundException(exception: WSCExceptionInsufficientFund): ResponseEntity<Any> {
        return ResponseEntity("insufficient Fund", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun accountDeactivated(exception: WSCExceptionAccountDeactivated): ResponseEntity<Any> {
        return ResponseEntity("Account Deactivated", HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler
    fun WSCExceptionServiceDown(exception: WSCExceptionServiceDown): ResponseEntity<Any> {
        return ResponseEntity("Service Down", HttpStatus.GATEWAY_TIMEOUT)
    }


}