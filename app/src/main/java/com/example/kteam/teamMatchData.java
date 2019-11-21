package com.example.kteam;

public class teamMatchData {
    String myTeam;
    String opposingteam;

    public teamMatchData(String myTeam, String opposingteam) {
        this.myTeam = myTeam;
        this.opposingteam = opposingteam;
    }

    public String getMyTeam() {
        return myTeam;
    }

    public void setMyTeam(String myTeam) {
        this.myTeam = myTeam;
    }

    public String getOpposingteam() {
        return opposingteam;
    }

    public void setOpposingteam(String opposingteam) {
        this.opposingteam = opposingteam;
    }
}
