/*; Title:  Entity  for Credit card Details
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.repository

import org.springframework.data.mongodb.repository.MongoRepository
import demo.wsc.beta.model.Credit
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CreditRepository : MongoRepository<Credit, String> {
    @Query(value = "{'customerId':?0}", fields = "{'cardPin':0,'customerId':0}")
    fun findAllCards(customerId: Int): List<Credit>
}