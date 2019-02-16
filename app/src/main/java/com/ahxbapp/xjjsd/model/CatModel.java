package com.ahxbapp.xjjsd.model;

import java.io.Serializable;

/**
 * Created by admin on 2019/1/25.
 */

public class CatModel implements Serializable{

    int LoanId;
    int TermId;
    String token;
    int Identifier;
    double Interest;
    double Userfee;
    double Applyfee;

    public int getLoanId() {
        return LoanId;
    }

    public void setLoanId(int loanId) {
        LoanId = loanId;
    }

    public int getTermId() {
        return TermId;
    }

    public void setTermId(int termId) {
        TermId = termId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getIdentifier() {
        return Identifier;
    }

    public void setIdentifier(int identifier) {
        Identifier = identifier;
    }

    public double getInterest() {
        return Interest;
    }

    public void setInterest(double interest) {
        Interest = interest;
    }

    public double getUserfee() {
        return Userfee;
    }

    public void setUserfee(double userfee) {
        Userfee = userfee;
    }

    public double getApplyfee() {
        return Applyfee;
    }

    public void setApplyfee(double applyfee) {
        Applyfee = applyfee;
    }
}
