/*;==========================================
; Title:  DTO class for Other Organisation Access the Rest Services
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/
package demo.wsc.beta.model

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
open class WSCServices {
    @Id
    var organisationToken: String? = null
    var organisationEmail: String? = null
    var organisationName: String? = null
    var accountFlag = 0
}