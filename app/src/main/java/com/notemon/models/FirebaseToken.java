package com.notemon.models;

/**
 * Created by emil on 01.05.17.
 */

public class FirebaseToken {
    private String deviceToken;

    public FirebaseToken(){}

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
}
