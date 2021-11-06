/*; Title:  Entity  for Customers Details
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.repository

import demo.wsc.beta.model.CustomerDetails
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CustomerDetailsRepository : MongoRepository<CustomerDetails?, Int?> {
    @Query(value = "{cardEligibility:1}", fields = "{password:0}")
    fun findByCardEligibility(): List<CustomerDetails>

    @Query("{'panId':'?0'}")
    fun findCustomer(panId: String?): Optional<CustomerDetails>

    fun findByAccountNumber(accountNumber:Long):Optional<CustomerDetails>

    fun findByPanId(panId: String?):Optional<CustomerDetails>
}