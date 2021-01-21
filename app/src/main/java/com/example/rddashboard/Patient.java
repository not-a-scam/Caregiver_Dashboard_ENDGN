package com.example.rddashboard;

public class Patient {
    private String group;
    private String radiotext;
    private String userAge;
    private String userBirthday;
    private String userEmail;
    private String userGender;
    private String userHeight;
    private String userName;
    private String userWeight;
    private String userId;

    public Patient() {
    }

    public Patient(String group, String radiotext, String userAge, String userBirthday,
                   String userEmail, String userGender, String userHeight, String userName,
                   String userWeight) {
        this.group = group;
        this.radiotext = radiotext;
        this.userAge = userAge;
        this.userBirthday = userBirthday;
        this.userEmail = userEmail;
        this.userGender = userGender;
        this.userHeight = userHeight;
        this.userName = userName;
        this.userWeight = userWeight;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getGroup() {
        return group;
    }

    public String getRadiotext() {
        return radiotext;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserBirthday() {
        return userBirthday;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserGender() {
        return userGender;
    }

    public String getUserHeight() {
        return userHeight;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserWeight() {
        return userWeight;
    }

}
