/*; Title:  Entity  for Customers Credit Predictions Details
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.repository

import org.springframework.data.mongodb.repository.MongoRepository
import demo.wsc.beta.model.CustomerPredctions
import org.springframework.stereotype.Repository

@Repository
interface CustomerPredictionsRepository : MongoRepository<CustomerPredctions, Int>