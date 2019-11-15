package com.example.kteam;

public class findteamData {
    private String name;
    private String teamnumber;
    private String location;

    public findteamData(String name, String teamnumber, String location) {
        this.name = name;
        this.teamnumber = teamnumber;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamnumber() {
        return teamnumber;
    }

    public void setTeamnumber(String teamnumber) {
        this.teamnumber = teamnumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}