/*;==========================================
; Title:  DTO class for Customer Details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/

package demo.wsc.beta.model;


import demo.wsc.beta.model.transport.OpenAccount;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@NonNull
public class CustomerDetails {

    @Id
    private int customerId;
    private Long accountNumber;
    private String ifscCode;
    private Long balance;
    private String name;
    private String email;
    private int cibilScore;
    private String panId;
    private Integer cardEligibility;
    private String cardType;

    public CustomerDetails(OpenAccount accountDetails) {
        this.accountNumber = accountDetails.getAccountNumber();
        this.ifscCode = accountDetails.getIfscCode();
        this.balance = accountDetails.getBalance();
        this.name = accountDetails.getName();
        this.email = accountDetails.getEmail();
        this.cibilScore = accountDetails.getCibilScore();
        this.panId = accountDetails.getPanId();
        this.cardEligibility = 0;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(int cibilScore) {
        this.cibilScore = cibilScore;
    }

    public String getPanId() {
        return panId;
    }

    public void setPanId(String panId) {
        this.panId = panId;
    }

    public Integer getCardEligibility() {
        return cardEligibility;
    }

    public void setCardEligibility(Integer cardEligibility) {
        this.cardEligibility = cardEligibility;
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }
}
