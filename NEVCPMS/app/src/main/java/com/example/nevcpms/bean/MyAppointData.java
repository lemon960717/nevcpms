package com.example.nevcpms.bean;

public class MyAppointData {
    public  Pile pile;
    String starTime;
    String endTime;

    public MyAppointData(Pile pile, String starTime, String endTime) {
        this.pile = pile;
        this.starTime = starTime;
        this.endTime = endTime;
    }

    public Pile getPile() {
        return pile;
    }

    public void setPile(Pile pile) {
        this.pile = pile;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
