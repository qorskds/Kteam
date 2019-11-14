package com.example.kteam;

import android.widget.Spinner;

public class findplayerData {
    private String name;
    private String bulid;
    private String height;
    private String postion;
    private String character;
    private String location;


    public findplayerData(String name, String bulid, String height, String postion, String character, String location) {
        this.name = name;
        this.bulid = bulid;
        this.height = height;
        this.postion = postion;
        this.character = character;
        this.location = location;
    }

    public findplayerData(String name, String bulid, String height, String postion, String character) {
        this.name = name;
        this.bulid = bulid;
        this.height = height;
        this.postion = postion;
        this.character = character;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBulid() {
        return bulid;
    }

    public void setBulid(String bulid) {
        this.bulid = bulid;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getPostion() {
        return postion;
    }

    public void setPostion(String postion) {
        this.postion = postion;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
