/*;==========================================
; Title:  Exception class for Invalid Java Model Object data
; Author: Rupak Kumar
; Date:   10 Oct 2021
;==========================================*/
package demo.wsc.beta.exceptions

import java.lang.Exception

open class WSCExceptionInvalidModeldata : Exception() {
    override var message = "Invalid Model data"
    override fun toString(): String {
        return "WSCExceptionInvalidModeldata{" +
                "message='" + message + '\'' +
                '}'
    }
}