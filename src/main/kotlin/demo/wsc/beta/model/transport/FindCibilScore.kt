/*;==========================================
; Title:  DTO class for Find cibil score details
; Author: Rupak Kumar
; Date:   20 Sep 2021
;==========================================*/
package demo.wsc.beta.model.transport

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import java.time.LocalDate

@Data
@AllArgsConstructor
@NoArgsConstructor
open class FindCibilScore(var pandId: String) {

}