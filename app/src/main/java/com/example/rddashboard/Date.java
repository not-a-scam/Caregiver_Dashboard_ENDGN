package com.example.rddashboard;

import java.util.ArrayList;

public class Date {
    private String steps, heartRate;
    private ArrayList<Activities> activityTrack;

    public Date() {
    }

    public Date(String steps, String heartRate, ArrayList<Activities> activityTrack) {
        this.steps = steps;
        this.heartRate = heartRate;
        this.activityTrack = activityTrack;
    }

    public Date(String steps, String heartRate) {
        this.steps = steps;
        this.heartRate = heartRate;
    }

    public String getSteps() {
        return steps;
    }

    public String getHeartRate() {
        return heartRate;
    }

    public ArrayList<Activities> getActivityTrack() {
        return activityTrack;
    }

    public void setActivityTrack(ArrayList<Activities> activityTrack) {
        this.activityTrack = activityTrack;
    }
}
