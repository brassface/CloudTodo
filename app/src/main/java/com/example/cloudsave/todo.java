package com.example.cloudsave;

import cn.bmob.v3.BmobObject;

public class todo extends BmobObject {
    private String from;
    private String to;
    private String title;
    private String place;
    private String month;
    private String day;
    private String hour;
    private String minute;
    todo() {}
    todo(String from,String to,String title,String place,String month,String day,String hour,String minute){
        this.from=from;
        this.to=to;
        this.title=title;
        this.place=place;
        this.month=month;
        this.day=day;
        this.hour=hour;
        this.minute=minute;
    }
    public String getFrom() {
        return from;
    }
    public void setFrom(String from) {
        this.from = from;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getMonth() {
        return month;
    }
    public void setMonth(String month) {
        this.month = month;
    }
    public String getDay() {
        return day;
    }
    public void setDay(String day) {
        this.day = day;
    }
    public String getHour() {
        return hour;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public String getMinute() {
        return minute;
    }
    public void setMinute(String minute) {
        this.minute = minute;
    }
    public String getPlace() {
        return place;
    }
    public void setPlace(String place) {
        this.place = place;
    }
}
