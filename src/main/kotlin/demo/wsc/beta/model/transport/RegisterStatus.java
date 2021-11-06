/*; Title:  DTO class for Send Registration Status
; Author: Rupak Kumar
; Date:   4 Oct 2021
;==========================================*/

package demo.wsc.beta.model.transport;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class RegisterStatus {

    private String status;
    private String token;
    private String date;

    public RegisterStatus(int flag) {
        if (flag == 1) {
            this.status = "success";
            this.token = "Access token has been sent to you email";
            this.date = LocalDate.now().toString();
        } else if (flag == 0) {
            this.status = "failed";
            this.token = "Email has been already registered";
            this.date = LocalDate.now().toString();
        }
    }
}
