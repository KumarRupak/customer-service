/*;==========================================
; Title:  Service class for Authenticate Users
; Author: Rupak Kumar
; Date:   22 Sep 2021
;==========================================*/

package demo.wsc.beta.service.authentication

import demo.wsc.beta.exceptions.WSCExceptionInvalidUser
import demo.wsc.beta.model.AuthUserDetails
import kotlin.jvm.Throws

interface ServiceAuthentication {

    @Throws(WSCExceptionInvalidUser::class)
    fun getUserLevel(customerId: Int): String

}
