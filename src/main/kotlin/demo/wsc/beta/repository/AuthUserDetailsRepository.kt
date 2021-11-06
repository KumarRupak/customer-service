/*; Title:  Entity  for Authenticate App User Details
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.repository

import demo.wsc.beta.model.AuthUserDetails
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthUserDetailsRepository : MongoRepository<AuthUserDetails, Int> {
}