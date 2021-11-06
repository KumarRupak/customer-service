/*;==========================================
; Title:  DTO class for Convertion Of Prediction Details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/
package demo.wsc.beta.model.transport

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Data

@Data
@AllArgsConstructor
open class CustomerPredctionsTransport {
    var customerId = 0
    var gender //male(1)-female(0)-others(2)
            : String? = null
    var income: Long? = null
    var age: Int? = null
    var cardType //silver(1)-gold(2)-diamond (3) others(4)
            : String? = null
    var profession //government sector(1)-private sector(2)-Business(3)-Student(4)-Farmer(5)
            : String? = null
    var cibilScore: Int? = null

    @JsonIgnore
    var defaulter //yes(1)-no(0)
            = "no"
    var maritalStatus //married(1) - unmarried(0)
            : String? = null
}