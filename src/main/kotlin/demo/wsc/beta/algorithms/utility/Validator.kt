/*;==========================================
; Title:  Algorithm for validate email and id
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.algorithms.utility

import java.util.regex.Pattern

/**
 * Validating the email and customer id
 *
 * @param 'value,email'- in string and Integer format
 * @return - A boolean and the length
 */


open class Validator {

    companion object{

    fun getLength(value: Int): Int {
        var c = 0
        var num = value
        while (num > 0) {
            c++
            num = num / 10
        }
        return c
    }


    fun isValidEmailID(email: String): Boolean {
        if (email.equals(null))
            return false
        val regex = "^[\\w!#$%&'+/=?`{|}~^-]+(?:\\.[\\w!#$%&'+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$"
        val pattern = Pattern.compile(regex)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }
    }

}