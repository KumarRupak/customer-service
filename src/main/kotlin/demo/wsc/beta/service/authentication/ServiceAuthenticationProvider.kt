/*;==========================================
; Title:  Service class for Authenticate Users
; Author: Rupak Kumar
; Date:   22 Sep 2021
;==========================================*/

package demo.wsc.beta.service.authentication



import org.springframework.beans.factory.annotation.Autowired
import demo.wsc.beta.exceptions.WSCExceptionInvalidUser
import demo.wsc.beta.repository.AuthUserDetailsRepository
import org.springframework.stereotype.Service



@Service
 class ServiceAuthenticationProvider : ServiceAuthentication {


    @Autowired
    lateinit var repoAuthUser: AuthUserDetailsRepository




    /**
     * Get the user level and his role
     *
     * @param 'customerId'
     * @return - role
     */
    @kotlin.jvm.Throws(WSCExceptionInvalidUser::class)
    override fun getUserLevel(customerId: Int): String {
        if (repoAuthUser.findById(customerId).isPresent)
        return   repoAuthUser.findById(customerId).get().level.toString()
        else
            throw WSCExceptionInvalidUser()
    }




}
