/*;==========================================
; Title:  DTO class for Customer Credit Prediction Details
; Author: Rupak Kumar
; Date:   16 Sep 2021
;==========================================*/

package demo.wsc.beta.model;

import demo.wsc.beta.model.transport.CustomerPredctionsTransport;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;



@Document
@NonNull
@AllArgsConstructor
@NoArgsConstructor
public class CustomerPredctions {

    @Id
    private int customerId;
    private Integer gender;           //male(1)-female(0)-others(2)
    private Long income;
    private Integer age;
    private Integer cardType;         //silver(1)-gold(2)-diamond (3)
    private Integer profession;       //government sector(1)-private sector(2)-Business(3)-Student(4)-Farmer(5)
    private Integer cibilScore;
    private Integer defaulter;        //yes(1)-no(0)
    private Integer maritalStatus;    //married(1) - unmarried(0)


    public CustomerPredctions(CustomerPredctionsTransport data) {
        this.customerId = data.getCustomerId();
        convertGender(data.getGender());
        this.income = data.getIncome();
        this.age = data.getAge();
        convertCardType(data.getCardType());
        convertProfession(data.getProfession());
        this.cibilScore = data.getCibilScore();
        convertDefaulter(data.getDefaulter());
        convertMaritalStatus(data.getMaritalStatus());
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public void setProfession(Integer profession) {
        this.profession = profession;
    }

    public void setDefaulter(Integer defaulter) {
        this.defaulter = defaulter;
    }

    public void setMaritalStatus(Integer maritalStatus) {
        this.maritalStatus = maritalStatus;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Integer getGender() {
        return gender;
    }

    //male(1)-female(0)-others(2)
    public void convertGender(String gender) {
        if (gender.equalsIgnoreCase("male"))
            this.gender = 1;
        else if (gender.equalsIgnoreCase("female"))
            this.gender = 0;
        else if (gender.equalsIgnoreCase("others"))
            this.gender = 2;
    }

    public Long getIncome() {
        return income;
    }

    public void setIncome(Long income) {
        this.income = income;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getCardType() {
        return cardType;
    }

    //silver(1)-gold(2)-diamond (3)
    public void convertCardType(String cardType) {
        if (cardType.equalsIgnoreCase("silver"))
            this.cardType = 1;
        else if (cardType.equalsIgnoreCase("gold"))
            this.cardType = 2;
        else if (cardType.equalsIgnoreCase("diamond"))
            this.cardType = 3;
        else
            this.cardType = 4;
    }

    public Integer getProfession() {
        return profession;
    }

    //government sector(1)-private sector(2)-business(3)-student(4)-farmer(5)
    public void convertProfession(String profession) {
        if (profession.equalsIgnoreCase("government sector"))
            this.profession = 1;
        else if (profession.equalsIgnoreCase("private sector"))
            this.profession = 2;
        else if (profession.equalsIgnoreCase("business"))
            this.profession = 3;
        else if (profession.equalsIgnoreCase("student"))
            this.profession = 4;
        else if (profession.equalsIgnoreCase("farmer"))
            this.profession = 5;
    }

    public Integer getCibilScore() {
        return cibilScore;
    }

    public void setCibilScore(Integer cibilScore) {
        this.cibilScore = cibilScore;
    }

    public Integer getDefaulter() {
        return defaulter;
    }

    //yes(1)-no(0)
    public void convertDefaulter(String defaulter) {
        if (defaulter.equalsIgnoreCase("yes"))
            this.defaulter = 1;
        else if (defaulter.equalsIgnoreCase("no"))
            this.defaulter = 0;
    }

    public Integer getMaritalStatus() {
        return maritalStatus;
    }

    //married(1) - unmarried(0)
    public void convertMaritalStatus(String maritalStatus) {
        if (maritalStatus.equalsIgnoreCase("married"))
            this.maritalStatus = 1;
        else if (maritalStatus.equalsIgnoreCase("unmarried"))
            this.maritalStatus = 0;
    }
}
