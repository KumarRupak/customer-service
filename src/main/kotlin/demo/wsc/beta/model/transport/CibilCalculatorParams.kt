/*;==========================================
; Title:  DTO class for calculate cibil score details
; Author: Rupak Kumar
; Date:   20 Sep 2021
;==========================================*/
package demo.wsc.beta.model.transport

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDate

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
open class CibilCalculatorParams {
    var cardPaidInstalment = 0
    var creditRecivedDate: LocalDate? = null
    var cardLimit: Long = 0
    var cardSpend: Long = 0
    var cardEligibility = 0
    var multipleCards = 0

    constructor() {}
    constructor(
        cardPaidInstalment: Int,
        creditRecivedDate: LocalDate?,
        cardLimit: Long?,
        cardSpend: Long?,
        cardEligibility: Int,
        multipleCards: Int
    ) {
        this.cardPaidInstalment = cardPaidInstalment
        if (creditRecivedDate != null) {
            this.creditRecivedDate = creditRecivedDate
        }
        if (cardLimit != null) {
            this.cardLimit = cardLimit
        }
        if (cardSpend != null) {
            this.cardSpend = cardSpend
        }
        this.cardEligibility = cardEligibility
        this.multipleCards = multipleCards
    }
}