/*;==========================================
; Title:  DTO class for Bank Organisation Details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/
package demo.wsc.beta.model

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
open class WSCOwner {
    @Id
    var branchId = 0
    var adminPin = 0
    var accountNo: Long? = null
    var ifscCode: String? = null
    var amount: Long? = null
    var bankName: String? = null
    var password: String? = null

    @JsonIgnore
    var returnInterest = 0.0
}