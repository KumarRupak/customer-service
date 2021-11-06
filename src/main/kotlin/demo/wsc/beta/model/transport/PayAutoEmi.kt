/*; Title:  DTO class for Setup Auto pay details
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.model.transport

import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
open class PayAutoEmi {
    @Id
    var cardNumber: String? = null
    var mPin: Int = 0

    @JsonIgnore
    var serviceFlag: Int = 0
}