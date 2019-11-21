package com.example.kteam;

public class teamMatchingConfrimData {
    String name;
    String stadium;
    String teamInformation;

    public teamMatchingConfrimData(String name, String stadium, String teamInformation) {
        this.name = name;
        this.stadium = stadium;
        this.teamInformation = teamInformation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStadium() {
        return stadium;
    }

    public void setStadium(String stadium) {
        this.stadium = stadium;
    }

    public String getTeamInformation() {
        return teamInformation;
    }

    public void setTeamInformation(String teamInformation) {
        this.teamInformation = teamInformation;
    }
}
