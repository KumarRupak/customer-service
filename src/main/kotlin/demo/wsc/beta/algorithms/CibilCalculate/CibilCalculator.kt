/*;==========================================
; Title:  Service for calculate CIBIL score
; Author: Rupak Kumar
; Date:   21 Sep 2021
;==========================================*/

package demo.wsc.beta.algorithms.CibilCalculate

import demo.wsc.beta.model.transport.CibilCalculatorParams
import java.time.LocalDate
import java.time.Period

/*
Score Range 300 - 900
1- Payment History 30%
2- Credit Exposure (Utilization ratio of credit 30%) 25%
3- Credit Type and Duration history  20%
4- Other Factors 25%
*/

/**
 * Calculate the cibil score
 *
 * @param 'CibilCalculatorParams' - object of the CibilCalculatorParams class
 * @return - Cibil score details of the user
 */


open class CibilCalculator {
    companion object {
        @JvmStatic
        fun getScore(params: CibilCalculatorParams): MutableMap<String, String> {
            var precentage = 0
            var score: Int
            val map = mutableMapOf<String, String>()

            //1- Payment History
            val duration = (Period.between(params.creditRecivedDate, LocalDate.now()).months - 1).toLong()
            if (params.cardPaidInstalment >= duration) {
                precentage = precentage + 30
                map.put("1", "Payment History Yes")
            } else {
                map.put("1", "Payment History No")
            }

            //2- Credit Exposure
            val creditUtilize: Long = (params.cardSpend / (params.cardSpend + params.cardLimit) * 100)
            if (creditUtilize <= 30) {
                precentage = precentage + 25
                map.put("2", "Credit Exposure Yes")
            } else {
                map.put("2", "Credit Exposure No")
            }

            //3- Other Factors
            if (params.cardEligibility == 1) {
                precentage = precentage + 20
                map.put("3", "Other Factors Yes")
            } else {
                map.put("3", "Other Factors No")
            }

            //4- Credit Type and Duration history
            if (params.multipleCards > 1) {
                precentage = precentage + 25
                map.put("4", "Credit Type and Duration history Yes")
            } else {
                map.put("4", "Credit Type and Duration history No")
            }

            score = 900 * precentage / 100
            map.put("score", score.toString())
            return map
        }
    }
}