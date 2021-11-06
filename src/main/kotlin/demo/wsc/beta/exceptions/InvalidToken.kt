/*;==========================================
; Title:  Exception class for Invalid Token
; Author: Rupak Kumar
; Date:   10 Oct 2021
;==========================================*/

package demo.wsc.beta.exceptions

import java.lang.Exception

open class InvalidToken : Exception() {
    override var message = "Invalid Token"
    override fun toString(): String {
        return "InvalidToken{" +
                "message='" + message + '\'' +
                '}'
    }
}