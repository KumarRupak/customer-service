/*;==========================================
; Title:  Exception class for Account Deactivated
; Author: Rupak Kumar
; Date:   10 Oct 2021
;==========================================*/

package demo.wsc.beta.exceptions

open class WSCExceptionAccountDeactivated :  Exception() {
    override var message = "Account Deactivated"
    override fun toString(): String {
        return "WSCExceptionAccountDeactivated{" +
                "message='" + message + '\'' +
                '}'
    }
}