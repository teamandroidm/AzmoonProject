package com.example.azmoonproject.Model;

public class Levels {
    private byte levelCount;
    private byte nineteen;
    private byte wrongNumber;
    private byte correctNumber;
    private int timeTookTest;
    private int termId;
    private int userId;

    public Levels() {
    }

    public Levels(byte levelCount, byte nineteen, byte wrongNumber, byte correctNumber, int timeTookTest, int termId, int userId) {
        this.levelCount = levelCount;
        this.nineteen = nineteen;
        this.wrongNumber = wrongNumber;
        this.correctNumber = correctNumber;
        this.timeTookTest = timeTookTest;
        this.termId = termId;
        this.userId = userId;
    }

    public byte getLevelCount() {
        return levelCount;
    }

    public void setLevelCount(byte levelCount) {
        this.levelCount = levelCount;
    }

    public byte getNineteen() {
        return nineteen;
    }

    public void setNineteen(byte nineteen) {
        this.nineteen = nineteen;
    }

    public byte getWrongNumber() {
        return wrongNumber;
    }

    public void setWrongNumber(byte wrongNumber) {
        this.wrongNumber = wrongNumber;
    }

    public byte getCorrectNumber() {
        return correctNumber;
    }

    public void setCorrectNumber(byte correctNumber) {
        this.correctNumber = correctNumber;
    }

    public int getTimeTookTest() {
        return timeTookTest;
    }

    public void setTimeTookTest(int timeTookTest) {
        this.timeTookTest = timeTookTest;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
