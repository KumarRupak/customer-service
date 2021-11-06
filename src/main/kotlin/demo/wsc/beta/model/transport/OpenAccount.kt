/*;==========================================
; Title:  DTO class for Open Customer Account
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/

package demo.wsc.beta.model.transport


import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.mongodb.core.mapping.Document

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
 open class OpenAccount{
    var accountNumber: Long? = null
    var ifscCode: String? = null
    var balance: Long? = null
    var name: String? = null
    var email: String? = null
    @JsonIgnore
    var cibilScore: Int = 0
    var panId: String? = null
    //var cardEligibility: Int? = null,
    //var cardType: String? = null,
    var password: String? = null

}