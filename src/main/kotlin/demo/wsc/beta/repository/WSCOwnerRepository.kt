/*; Title:  Entity  for Admin details
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.repository

import demo.wsc.beta.model.Credit
import org.springframework.data.mongodb.repository.MongoRepository
import demo.wsc.beta.model.WSCOwner
import org.springframework.data.mongodb.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface WSCOwnerRepository : MongoRepository<WSCOwner, Int> {
}

