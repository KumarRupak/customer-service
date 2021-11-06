/*;==========================================
; Title:  DTO class for Pay EMI details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/

package demo.wsc.beta.model.transport

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.mongodb.core.mapping.Document

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
open class PayEmi(
    val cardNumber: String,
    val installment: Int,
    val mpin: Int
) {

}