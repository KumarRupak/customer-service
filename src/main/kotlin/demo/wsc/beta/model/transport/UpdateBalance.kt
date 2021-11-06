/*; Title:  DTO class for Update account balance details
; Author: Rupak Kumar
; Date:   4 Oct 2021
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
open class UpdateBalance(val branchId: Int, val amount: Long) {
}