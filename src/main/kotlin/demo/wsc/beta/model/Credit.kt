/*;==========================================
; Title:  DTO class for Customer Credit Details
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
import java.time.LocalDate
import java.util.*

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
open class Credit {
    @Id
    var cardNumber: String? = null
    @JsonIgnore
    var customerId = 0
    var branchId=0
    var cardLimit: Long? = null
    var cardType: String? = null
    var cardSpend: Long? = null
    var instalmentAmount = 0.0
    var cardPendingInstalment = 0
    var cardPaidInstalment = 0

    @JsonIgnore
    var instalamentDate: Date? = null
    var instalamentDateShowUser: String? = null
    @JsonIgnore
    var cardPin = 0
    var cardFlag = 0
    var interestRate = 0.0
    @JsonIgnore
    var instalmentPeriod: List<Int>? = null

    @JsonIgnore
    var creditRecivedDate: LocalDate? = null
    var creditReciveDateShowUser: String? = null
}