package com.nirwal.messmanager.models;

public class User {
    private String name;
    private String email;
    private String uuid;
    private String phone;
    private String photoUrl;
    private boolean isVerified;


    public User() {
    }

    /***
     *
     * @param name
     * @param email
     * @param uuid
     * @param phone
     * @param photoUrl
     * @param isVerified
     */
    public User(String name, String email, String uuid, String phone, String photoUrl, boolean isVerified) {
        this.name = name;
        this.email = email;
        this.uuid = uuid;
        this.phone = phone;
        this.photoUrl = photoUrl;
        this.isVerified = isVerified;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }
}




