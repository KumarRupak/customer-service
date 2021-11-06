/*;==========================================
; Title:  DTO class for Transfer of fund details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/
package demo.wsc.beta.model.transport

import com.fasterxml.jackson.annotation.JsonIgnore


open class FundTransfer(
    val cardNumber: String,
    val amount: Long,
    val cardPin: Int,
    val installmentPeriod: Int
) {

}