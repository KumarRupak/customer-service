/*;==========================================
; Title:  k nearest neighbor algorithm for credit prediction
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/

package demo.wsc.beta.algorithms.ML

import demo.wsc.beta.model.CustomerPredctions

/**
 * Predict the eligibility for the credit based on the k-nearest neighbors  Algorithm
 *
 * @param 'testData' - Test data provided by the customer
 * @param  'trainData' - Train data based on the previous predictions
 * @return - prediction defaulter or not
 */


open class Knn {
    companion object {
        @JvmStatic
         fun predict(
            testData: CustomerPredctions,
            trainData: List<CustomerPredctions>,
            k: Int = 3
        ): Int {
            var sd: Double
            var one  = 0
            var zero  = 0
            var count  = 0

            val mapData: MutableMap<Int, Double> = mutableMapOf()
            val mapDefault: MutableMap<Int, Int> = mutableMapOf()

            trainData.stream().forEach {

                sd = ((testData.gender - it.gender) * (testData.gender - it.gender) +
                        (testData.age - it.age) * (testData.age - it.age) +
                        (testData.cardType - it.cardType) * (testData.cardType - it.cardType) +
                        (testData.income - it.income) * (testData.income - it.income) +
                        (testData.profession - it.profession) * (testData.profession - it.profession) +
                        (testData.cibilScore - it.cibilScore) * (testData.cibilScore - it.cibilScore) +
                        (testData.maritalStatus - it.maritalStatus) * (testData.maritalStatus - it.maritalStatus))
                    .toDouble()

                mapData.put(it.customerId, Math.sqrt(sd))
                mapDefault.put(it.customerId, it.defaulter)
            }
            val result = mapData.toList().sortedBy { (_, value) -> value }.toMap()
            result.forEach { key ->

                if (count < k) {
                    if (mapDefault.get(key.key) == 1) one++ else zero++
                    count++
                }
            }
            if (one >= zero) return 1 else return 0
        }
    }
}