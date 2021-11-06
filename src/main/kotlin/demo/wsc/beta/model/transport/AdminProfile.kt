/*;==========================================
; Title:  DTO class for admin profile details
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
open class AdminProfile(
    var branchId: Int = 0,
    var ifscCode: String? = null,
    var accountNo: Long? = null,
    var bankName: String? = null,
    var returnInterest: Double = 0.0
) {
}