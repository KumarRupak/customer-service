/*; Title:  Entity  for Transaction Details
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.repository

import demo.wsc.beta.model.CustomerTransactions
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface CustomerTransactionsRepository : MongoRepository<CustomerTransactions, Int> {
    @Query("{'panId':'?0'}")
    fun findAllTransactions(panId: String?): List<CustomerTransactions>
}