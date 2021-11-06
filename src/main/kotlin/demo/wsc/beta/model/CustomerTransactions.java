/*;==========================================
; Title:  Data class for All Payment Transaction Details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/

package demo.wsc.beta.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import demo.wsc.beta.algorithms.PasswordEncode.Encoder;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;





@Document
public class CustomerTransactions {

    @Id
    private String transactionId;
    private String panId;
    private String senderName;
    private String senderAccount;
    private String receiverAccount;
    private Long amount;
    @JsonIgnore
    private LocalDateTime transactionDate;
    private String transactionDetails;
    private String interest;
    private String transactionDateShowUser;

    public String getTransactionId() {
        return Encoder.Companion.decode(transactionId);
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = Encoder.Companion.encode(transactionId);
    }

    public String getPanId() {
        return Encoder.Companion.decode(panId);
    }

    public void setPanId(String panId) {
        this.panId = Encoder.Companion.encode(panId);
    }

    public String getSenderName() {
        return Encoder.Companion.decode(senderName);
    }

    public void setSenderName(String senderName) {
        this.senderName = Encoder.Companion.encode(senderName);
    }

    public String getSenderAccount() {
        return Encoder.Companion.decode(senderAccount);
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = Encoder.Companion.encode(senderAccount);
    }

    public String getReceiverAccount() {
        return Encoder.Companion.decode(receiverAccount);
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = Encoder.Companion.encode(receiverAccount);
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    public String getTransactionDetails() {
        return Encoder.Companion.decode(transactionDetails);
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = Encoder.Companion.encode(transactionDetails);
    }

    public String getInterest() {
        return Encoder.Companion.decode(interest);
    }

    public void setInterest(String interest) {
        this.interest = Encoder.Companion.encode(interest);
    }

    public String getTransactionDateShowUser() {
        return Encoder.Companion.decode(transactionDateShowUser);
    }

    public void setTransactionDateShowUser(String transactionDateShowUser) {
        this.transactionDateShowUser = Encoder.Companion.encode(transactionDateShowUser);
    }
}
