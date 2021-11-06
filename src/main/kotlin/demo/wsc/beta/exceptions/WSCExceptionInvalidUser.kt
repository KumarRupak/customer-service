/*;==========================================
; Title:  Exception class for Invalid User Details
; Author: Rupak Kumar
; Date:   10 Oct 2021
;==========================================*/

package demo.wsc.beta.exceptions

import java.lang.Exception

open class WSCExceptionInvalidUser : Exception() {
    override var message = "Invalid User"
    override fun toString(): String {
        return "WSCExceptionInvalidUser{" +
                "message='" + message + '\'' +
                '}'
    }
}