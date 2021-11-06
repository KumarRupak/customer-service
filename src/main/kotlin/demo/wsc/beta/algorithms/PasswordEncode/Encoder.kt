/*;==========================================
; Title:  Md5 Hashing for password encode and decode
; Author: Rupak Kumar
; Date:   5 Oct 2021
;==========================================*/

package demo.wsc.beta.algorithms.PasswordEncode

import java.util.*


/**
 * Encode and Decode the password
 *
 * @param  'password' - Password in string format
 * @return - 'hashing' md5 hashing
 */


open class Encoder {
    companion object {


        fun encode(password: String): String {
            return Base64.getEncoder().encodeToString(password.toByteArray())
        }

        fun decode(password: String): String {
            return String(Base64.getMimeDecoder().decode(password.toByteArray()))
        }
    }
}