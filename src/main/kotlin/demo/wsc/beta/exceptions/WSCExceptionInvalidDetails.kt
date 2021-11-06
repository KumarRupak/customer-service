/*;==========================================
; Title:  Exception class for Invalid Details Provided by user
; Author: Rupak Kumar
; Date:   10 Oct 2021
;==========================================*/

package demo.wsc.beta.exceptions

import java.lang.Exception

open class WSCExceptionInvalidDetails : Exception() {
    override var message = "Invalid Details"
    override fun toString(): String {
        return "WSCExceptionInvalidDetails{" +
                "message='" + message + '\'' +
                '}'
    }
}