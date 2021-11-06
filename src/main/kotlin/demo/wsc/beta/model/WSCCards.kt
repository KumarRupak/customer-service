/*;==========================================
; Title:  Data class for Offered Credit Cards details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/
package demo.wsc.beta.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
open class WSCCards {
    @Id
    lateinit var cardType: String
    lateinit var cardOffers: List<String>
    var creditAmount: Long = 0

    //public int cardInstalment;
    var interestRate = 0.0
    lateinit var instalmentPeriod: List<Int>
}