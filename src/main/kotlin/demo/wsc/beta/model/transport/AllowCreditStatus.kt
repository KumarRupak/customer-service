/*;==========================================
; Title:  DTO class for Allow Credit Status
; Author: Rupak Kumar
; Date:   22 Sep 2021
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
open class AllowCreditStatus(var status: String = "failed", var amount: Long = 0) {
}