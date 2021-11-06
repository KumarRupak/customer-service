/*;==========================================
; @Title:  Service for all User authentication
; @Author: Rupak Kumar
; @Date:   11 Oct 2021
;==========================================*/


package demo.wsc.beta.algorithms.authconfig

import demo.wsc.beta.algorithms.PasswordEncode.Encoder
import demo.wsc.beta.repository.AuthCustomerRepository
import demo.wsc.beta.repository.CustomerDetailsRepository
import demo.wsc.beta.repository.WSCOwnerRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service


//return 0- User Inactive
//return 1- User Authorised
//return 2- User UnAuthorised
//return 3- User Locked

@Service
class AuthUser {

    @Autowired
    lateinit var repoCus: CustomerDetailsRepository

    var customerId: Int? = 0
    var password: String? = null
    var level: String = "NA"

    var map = mutableMapOf<String, Int>()

    fun setCustomerId(customerId: Int) {
        this.customerId = customerId
    }

    fun setPasswd(password: String) {
        this.password = password
    }

    /**
     * Authenticate the user based on the user details
     *
     * @param 'cusomerId' - ID provided by the customer
     * @param 'password' - Password of the user
     * @return - User level and his account status
     */


    fun authenticate(
        repoAuthCus: AuthCustomerRepository,
        repoAuthAdmin: WSCOwnerRepository
    ): MutableMap<String, Int> {

        if (repoAuthCus.findById(customerId!!).isPresent)
            this.level = "CUSTOMER"
        else if (repoAuthAdmin.findById(customerId!!).isPresent)
            this.level = "ADMIN"



        if (level.uppercase().equals("CUSTOMER")) {
            if (!repoAuthCus.findById(customerId!!).isEmpty) {

                if (repoAuthCus.findById(customerId!!).get().accountFlag < 4) {
                    if (Encoder.decode(repoAuthCus.findById(customerId!!).get().password).equals(password)) {
                        map.put(this.level, 1)
                        return map
                    } else {
                        val auth=repoAuthCus.findById(customerId!!).get()
                        auth.accountFlag=auth.accountFlag+1
                        repoAuthCus.save(auth)
                        map.put(this.level, 2)
                        return map
                    }

                } else {
                    map.put(this.level, 3)
                    return map
                }
            } else {
                map.put(this.level, 0)
                return map
            }
        } else if (level.uppercase().equals("ADMIN")) {

            if (!repoAuthAdmin.findById(customerId!!).isEmpty) {

                if (Encoder.decode(repoAuthAdmin.findById(customerId!!).get().password!!).equals(password)) {
                    map.put(this.level, 1)
                    return map
                } else {
                    map.put(this.level, 2)
                    return map
                }
            } else {
                map.put(this.level, 3)
                return map
            }
        }
        map.put(this.level, 0)
        return map
    }

}
