package com.example.nevcpms.bean;

import androidx.annotation.NonNull;

public class Collection {
    String phone;
    int station_id;
    int isCollected;//1为被对应phone的用户收藏，0为未被收藏

    public Collection(String phone, int station_id, int isCollected) {
        this.phone = phone;
        this.station_id = station_id;
        this.isCollected = isCollected;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getStation_id() {
        return station_id;
    }

    public void setStation_id(int station_id) {
        this.station_id = station_id;
    }

    public int getIsCollected() {
        return isCollected;
    }

    public void setIsCollected(int isCollected) {
        this.isCollected = isCollected;
    }



    @NonNull
    @Override
    public String toString() {
        return "Collection{" +
                "phone='" + phone + '\'' +
                ", station_id=" + station_id +
                ", isCollected=" + isCollected +
                '}';
    }
}
