/*;==========================================
; Title:  DTO class for Send Transaction Details
; Author: Rupak Kumar
; Date:   16 Sep 2021
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
open class TransactionStatus {

        var transactionStatus: String? = null
            private set
        var transactionAmount: Long? = null
        var transactionReason: String? = null
            private set

        fun setTransactionStatus(transactionStatus: Int) {
            if (transactionStatus == 1) this.transactionStatus = "success" else this.transactionStatus = "failed"
        }

        fun setTransactionReason(cardStatus: Int) {
            if (cardStatus == 0) transactionReason = "inactive" else if (cardStatus == 1) transactionReason =
                "valid" else if (cardStatus == 2) transactionReason =
                "invalid" else if (cardStatus == 3) transactionReason =
                "insufficient" else if (cardStatus == 4) transactionReason =
                "exceed" else if (cardStatus==5) transactionReason=
                "emi Option" else if (cardStatus==6) transactionReason=
                "early" else if (cardStatus==7) transactionReason="Inactive"
        }

        override fun toString(): String {
            return "transactionStatus{" +
                    "transactionStatus='" + transactionStatus + '\'' +
                    ", transactionAmount=" + transactionAmount +
                    ", transactionReason='" + transactionReason + '\'' +
                    '}'
        }

}