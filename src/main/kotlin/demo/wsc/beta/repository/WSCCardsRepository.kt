/*; Title:  Entity  for Credit Card details Provided by bank
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.repository

import demo.wsc.beta.model.WSCCards
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface WSCCardsRepository : MongoRepository<WSCCards, String>