package com.example.azmoonproject.Model;

public class Fields {
    private byte feildId;
    private String feildName;
    private String imageName;

    public Fields() {
    }

    public Fields(byte feildId, String feildName, String imageName) {
        this.feildId = feildId;
        this.feildName = feildName;
        this.imageName = imageName;
    }

    public byte getFeildId() {
        return feildId;
    }

    public void setFeildId(byte feildId) {
        this.feildId = feildId;
    }

    public String getFeildName() {
        return feildName;
    }

    public void setFeildName(String feildName) {
        this.feildName = feildName;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
