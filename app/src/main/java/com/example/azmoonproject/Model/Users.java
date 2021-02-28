package com.example.azmoonproject.Model;

public class Users {
    private int userId;
    private String name;
    private String family;
    private byte fieldId;

    public Users() {
    }

    public Users(int userId, String name, String family, byte fieldId) {
        this.userId = userId;
        this.name = name;
        this.family = family;
        this.fieldId = fieldId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }


    public byte getFieldId() {
        return fieldId;
    }

    public void setFieldId(byte fieldId) {
        this.fieldId = fieldId;
    }
}
