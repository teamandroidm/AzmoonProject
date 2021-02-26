package com.example.azmoonproject.Model;

import java.util.Date;

public class Factors {
    private int factorId;
    private int validateTime;
    private int price;
    private int userId;
    private byte termId;
    private String termName;
    private String fainallyDate;
    public Factors() {
    }

    public Factors(int factorId, int validateTime, String finallyDate, int price, int userId, byte termId, String termName) {
        this.factorId = factorId;
        this.validateTime = validateTime;
        this.fainallyDate = finallyDate;
        this.price = price;
        this.userId = userId;
        this.termId = termId;
        this.termName = termName;
    }

    public int getFactorId() {
        return factorId;
    }

    public void setFactorId(int factorId) {
        this.factorId = factorId;
    }

    public int getValidateTime() {
        return validateTime;
    }

    public void setValidateTime(int validateTime) {
        this.validateTime = validateTime;
    }

    public String getFinallyDate() {
        return fainallyDate;
    }

    public void setFinallyDate(String finallyDate) {
        this.fainallyDate = finallyDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte getTermId() {
        return termId;
    }

    public void setTermId(byte termId) {
        this.termId = termId;
    }


    public String getTermName() {
        return termName;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }
}
