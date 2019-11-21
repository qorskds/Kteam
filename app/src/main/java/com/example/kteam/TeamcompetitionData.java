package com.example.kteam;

public class TeamcompetitionData {
    private String name;
    private String area;
    private String location;
    private String comment;
    private String time;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public TeamcompetitionData(String name, String area, String location, String comment, String time) {
        this.name = name;
        this.area = area;
        this.location = location;
        this.comment = comment;
        this.time = time;
    }

    public TeamcompetitionData(String name, String area, String location, String comment) {
        this.name = name;
        this.area = area;
        this.location = location;
        this.comment = comment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
