/*;==========================================
; Title:  Exception class for Insufficient Fund
; Author: Rupak Kumar
; Date:   10 Oct 2021
;==========================================*/

package demo.wsc.beta.exceptions

import java.lang.Exception

open class WSCExceptionInsufficientFund : Exception() {
    override var message = "Insufficient Fund"
    override fun toString(): String {
        return "WSCExceptionInsufficientFund{" +
                "message='" + message + '\'' +
                '}'
    }
}