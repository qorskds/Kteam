package com.example.kteam;

public class myproposalData {
    private String teamName;
    private String teamLeader;
    private String staditum;
    private String teamIntroduce;

    public myproposalData (){
    }

    public myproposalData(String teamName, String teamLeader, String staditum, String teamIntroduce) {
        this.teamName = teamName;
        this.teamLeader = teamLeader;
        this.staditum = staditum;
        this.teamIntroduce = teamIntroduce;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamLeader() {
        return teamLeader;
    }

    public void setTeamLeader(String teamLeader) {
        this.teamLeader = teamLeader;
    }

    public String getStaditum() {
        return staditum;
    }

    public void setStaditum(String staditum) {
        this.staditum = staditum;
    }

    public String getTeamIntroduce() {
        return teamIntroduce;
    }

    public void setTeamIntroduce(String teamIntroduce) {
        this.teamIntroduce = teamIntroduce;
    }
}
