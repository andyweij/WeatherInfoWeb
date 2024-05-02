package com.eland.weatherInfo.formbean;

import java.util.Date;

public class WeatherInfoForm {
    private String city;
    private Date date;
    private int high;
    private int low;
    private String aqi;
    private int fl;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public int getLow() {
        return low;
    }

    public void setLow(int low) {
        this.low = low;
    }

    public String getAqi() {
        return aqi;
    }

    public void setAqi(String aqi) {
        this.aqi = aqi;
    }

    public int getFl() {
        return fl;
    }

    public void setFl(int fl) {
        this.fl = fl;
    }

    @Override
    public String toString() {
        return "weatherInfoForm{" +
                "city='" + city + '\'' +
                ", date=" + date +
                ", high=" + high +
                ", low=" + low +
                ", aqi='" + aqi + '\'' +
                ", fl=" + fl +
                '}';
    }
}
