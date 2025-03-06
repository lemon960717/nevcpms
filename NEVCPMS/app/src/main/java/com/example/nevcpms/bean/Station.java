package com.example.nevcpms.bean;


import android.os.Parcel;
import android.os.Parcelable;

public class Station implements Parcelable {
    private int id;
    private String name;
    private int imageId;
    private String district;//区，如武昌区
    private String location;
    private String chargePrice;
    private String parkingPrice;
    private String openTime;
    private String servicePrice;
    private String introduction;
    private String servicePhone;
    private double longitude;
    private double latitude;

    public Station(int id, String name, int imageId, String district, String location, String chargePrice, String parkingPrice, String openTime, String servicePrice, String introduction, String servicePhone, double longitude, double latitude) {
        this.id = id;
        this.name = name;
        this.imageId = imageId;
        this.district = district;
        this.location = location;
        this.chargePrice = chargePrice;
        this.parkingPrice = parkingPrice;
        this.openTime = openTime;
        this.servicePrice = servicePrice;
        this.introduction = introduction;
        this.servicePhone = servicePhone;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    protected Station(Parcel in) {
        id = in.readInt();
        name = in.readString();
        imageId = in.readInt();
        district = in.readString();
        location = in.readString();
        chargePrice = in.readString();
        parkingPrice = in.readString();
        openTime = in.readString();
        servicePrice = in.readString();
        introduction = in.readString();
        servicePhone = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
    }

    public static final Creator<Station> CREATOR = new Creator<Station>() {
        @Override
        public Station createFromParcel(Parcel in) {
            return new Station(in);
        }

        @Override
        public Station[] newArray(int size) {
            return new Station[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(imageId);
        dest.writeString(district);
        dest.writeString(location);
        dest.writeString(chargePrice);
        dest.writeString(parkingPrice);
        dest.writeString(openTime);
        dest.writeString(servicePrice);
        dest.writeString(introduction);
        dest.writeString(servicePhone);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getChargePrice() {
        return chargePrice;
    }

    public void setChargePrice(String chargePrice) {
        this.chargePrice = chargePrice;
    }

    public String getParkingPrice() {
        return parkingPrice;
    }

    public void setParkingPrice(String parkingPrice) {
        this.parkingPrice = parkingPrice;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(String servicePrice) {
        this.servicePrice = servicePrice;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getServicePhone() {
        return servicePhone;
    }

    public void setServicePhone(String servicePhone) {
        this.servicePhone = servicePhone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageId=" + imageId +
                ", district='" + district + '\'' +
                ", location='" + location + '\'' +
                ", chargePrice='" + chargePrice + '\'' +
                ", parkingPrice='" + parkingPrice + '\'' +
                ", openTime='" + openTime + '\'' +
                ", servicePrice='" + servicePrice + '\'' +
                ", introduction='" + introduction + '\'' +
                ", servicePhone='" + servicePhone + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }


}
