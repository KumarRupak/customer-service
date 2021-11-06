/*;==========================================
; Title:  DTO class for GeneratePin details
; Author: Rupak Kumar
; Date:   17 Sep 2021
;==========================================*/

package demo.wsc.beta.model.transport


/*;==========================================
; Title:  Customer Services Provider
; Author: Rupak Kumar
; Date:   18 Sep 2021
;==========================================*/

open class GeneratePin(val cardNumber: String, val otp: Int, val pin: Int) {
}