/*; Title:  Entity  for Authenticate Customer Details
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.repository

import demo.wsc.beta.model.AuthCustomer
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthCustomerRepository : MongoRepository<AuthCustomer, Int>