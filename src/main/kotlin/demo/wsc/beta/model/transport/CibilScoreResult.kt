/*;==========================================
; Title:  DTO class for send cibil score
; Author: Rupak Kumar
; Date:   20 Sep 2021
;==========================================*/
package demo.wsc.beta.model.transport

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.mongodb.core.mapping.Document
import java.util.ArrayList

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
open class CibilScoreResult {


        var score = 0
            get() = field
            set(score) {
                field = score
            }
        val scoreRatio = "300 - 900"
        var parameter1: String? = null
        var parameter2: String? = null
        var parameter3: String? = null
        var parameter4: String? = null
        private var eligibleLoans: MutableList<String>? = null
        fun getEligibleLoans(): List<String>? {
            return eligibleLoans
        }

        fun setEligibleLoans(score: Int) {
            eligibleLoans = ArrayList()
            if (score >= 700) {
                (eligibleLoans as ArrayList<String>).add("Personal Loan")
                (eligibleLoans as ArrayList<String>).add("Car Loan")
                (eligibleLoans as ArrayList<String>).add("Business Loan")
            } else if (score >= 650) {
                (eligibleLoans as ArrayList<String>).add("Home Loan")
                (eligibleLoans as ArrayList<String>).add("Loan against Property")
            } else {
                (eligibleLoans as ArrayList<String>).add("Gold Loan")
            }
        }

        override fun toString(): String {
            return "CibilScoreResult{" +
                    "YourScore=" + score +
                    ", ScoreRatio='" + scoreRatio + '\'' +
                    ", Parameter1='" + parameter1 + '\'' +
                    ", Parameter2='" + parameter2 + '\'' +
                    ", Parameter3='" + parameter3 + '\'' +
                    ", Parameter4='" + parameter4 + '\'' +
                    ", EligibleLoans=" + eligibleLoans +
                    '}'
        }

}