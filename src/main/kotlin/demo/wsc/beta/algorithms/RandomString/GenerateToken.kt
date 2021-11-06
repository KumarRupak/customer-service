/*;==========================================
; Title:  Algorithm for generate random token
; Author: Rupak Kumar
; Date:   7 Oct 2021
;==========================================*/

package demo.wsc.beta.algorithms.RandomString

import java.lang.StringBuilder
import java.util.*

/**
 * Generate a random token
 *
 * @param 'NA'
 * @return - A random string token
 */

open class GenerateToken {
    // length of the random string.
    companion object{
     fun  getToken():String {
            val token = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
            val salt = StringBuilder()
            val rnd = Random()
            while (salt.length < 18) { // length of the random string.
                val index = (rnd.nextFloat() * token.length).toInt()
                salt.append(token[index])
            }
            return salt.toString()
        }
    }

}