package com.example.nevcpms.util;

public class CommonData {

    private static CommonData instance;

    private String phoneNum = "" ;

    public CommonData() {
    }

    public static CommonData getInstance() {
        if (instance == null) {
            instance = new CommonData();
        }
        return instance;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
}
