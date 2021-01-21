package com.example.rddashboard;

public class Activities {
    private String activity, cTime, duration, avrHeartRate;

    public Activities() {
    }

    public Activities(String activity, String cTime, String duration, String avrHeartRate) {
        this.activity = activity;
        this.cTime = cTime;
        this.duration = duration;
        this.avrHeartRate = avrHeartRate;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getcTime() {
        return cTime;
    }

    public void setcTime(String cTime) {
        this.cTime = cTime;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getAvrHeartRate() {
        return avrHeartRate;
    }

    public void setAvrHeartRate(String avrHeartRate) {
        this.avrHeartRate = avrHeartRate;
    }
}
