package com.example.nevcpms.bean;

public class Pile {
    private int stationId;
    private int id;
    private String pliePower;
    private String plieName;
    int isUsed;

    public Pile(int id, int stationId, String pliePower, int isUsed,String plieName) {
        this.id = id;
        this.stationId = stationId;
        this.pliePower = pliePower;
        this.isUsed = isUsed;
        this.plieName = plieName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStationId() {
        return stationId;
    }

    public void setStationId(int stationId) {
        this.stationId = stationId;
    }

    public String getPliePower() {
        return pliePower;
    }

    public void setPliePower(String pliePower) {
        this.pliePower = pliePower;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getPlieName() {
        return plieName;
    }

    public void setPlieName(String plieName) {
        this.plieName = plieName;
    }

    @Override
    public String toString() {
        return "Pile{" +
                "stationId=" + stationId +
                ", id=" + id +
                ", pliePower='" + pliePower + '\'' +
                ", plieName='" + plieName + '\'' +
                ", isUsed=" + isUsed +
                '}';
    }
}
