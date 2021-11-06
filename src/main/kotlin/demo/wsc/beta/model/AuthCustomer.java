/*;==========================================
; Title:  DTO class for Customer Authenticate Setting details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/

package demo.wsc.beta.model;

import java.time.LocalDateTime;

import demo.wsc.beta.algorithms.PasswordEncode.Encoder;
import demo.wsc.beta.model.transport.OpenAccount;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@NonNull
@NoArgsConstructor
public class AuthCustomer {

    @Id
    private int customerId;
    private int accountFlag;
    private Long transactionLimit;
    private LocalDateTime lastLogin;
    private String password;
    private int mPin;


    public AuthCustomer(OpenAccount accountDetails) {
        this.accountFlag = 1;
        this.transactionLimit = 20000L;
        this.password = Encoder.Companion.encode(accountDetails.getPassword());
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(int accountFlag) {
        this.accountFlag = accountFlag;
    }

    public Long getTransactionLimit() {
        return transactionLimit;
    }

    public void setTransactionLimit(Long transactionLimit) {
        this.transactionLimit = transactionLimit;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getMPIN() {
        return mPin;
    }

    public void setMPIN(int mPin) {
        this.mPin = mPin;
    }
}
