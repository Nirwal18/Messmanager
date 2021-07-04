package com.nirwal.messmanager.notification;

public class Subscribers {
    public String to;
    public String[] registration_tokens;

    public Subscribers(String to, String[] registration_tokens) {
        this.to=to;
        this.registration_tokens = registration_tokens;

    }

    public String[] getRegistration_tokens() {
        return registration_tokens;
    }

    public void setRegistration_tokens(String[] registration_tokens) {
        this.registration_tokens = registration_tokens;
    }


    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
