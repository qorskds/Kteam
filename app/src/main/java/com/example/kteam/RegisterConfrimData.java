package com.example.kteam;

public class RegisterConfrimData {
    String name;
    String text;
    String uid;

    public RegisterConfrimData(String name, String text) {
        this.name = name;
        this.text = text;
    }

    public RegisterConfrimData(String name, String text, String uid) {
        this.name = name;
        this.text = text;
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
