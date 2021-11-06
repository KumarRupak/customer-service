/*; Title:  DTO class for Register Organizations
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.model.transport

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@Data
@AllArgsConstructor
@NoArgsConstructor
open class OrganisationRegister(val organisationName: String?, val organisationEmail: String?) {

}