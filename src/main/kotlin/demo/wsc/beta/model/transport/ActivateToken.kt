/*;==========================================
; Title:  DTO class for Activate token details provided by admin
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
open class ActivateToken{
    var branchId: Int=0
    var organisationEmail: String?=null
}