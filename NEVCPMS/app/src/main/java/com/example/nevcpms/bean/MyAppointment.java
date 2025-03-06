package com.example.nevcpms.bean;

public class MyAppointment {
    String phone;
    int pile_id;
    String starTime;
    String endTime;
    int isAppointment;//1为被对应的phone的用户预约，0为未预约

    public MyAppointment(String phone, int pile_id, String starTime, String endTime, int isAppointment) {
        this.phone = phone;
        this.pile_id = pile_id;
        this.starTime = starTime;
        this.endTime = endTime;
        this.isAppointment = isAppointment;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getPile_id() {
        return pile_id;
    }

    public void setPile_id(int pile_id) {
        this.pile_id = pile_id;
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

    public int getIsAppointment() {
        return isAppointment;
    }

    public void setIsAppointment(int isAppointment) {
        this.isAppointment = isAppointment;
    }

    @Override
    public String toString() {
        return "MyAppointment{" +
                "phone='" + phone + '\'' +
                ", pile_id=" + pile_id +
                ", starTime='" + starTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", isAppointment=" + isAppointment +
                '}';
    }
}


