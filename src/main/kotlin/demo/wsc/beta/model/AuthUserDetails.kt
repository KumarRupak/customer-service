/*;==========================================
; Title:  DTO class for Auth Users details
; Author: Rupak Kumar
; Date:   22 Sep 2021
;==========================================*/
package demo.wsc.beta.model


import com.fasterxml.jackson.annotation.JsonIgnore
import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
open class AuthUserDetails(
    @Id var customerId: Int,
    var password: String,
    @JsonIgnore var level: String?,
    @JsonIgnore var time: Instant?
) {
}