package com.nirwal.messmanager.models;

public class UserSettings {
    private String uuid;
    private String[] ownGroupList;
    private String[] sharedGroupList;


    public UserSettings(String uuid, String[] ownGroupList, String[] sharedGroupList) {
        this.uuid = uuid;
        this.ownGroupList = ownGroupList;
        this.sharedGroupList = sharedGroupList;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String[] getOwnGroupList() {
        return ownGroupList;
    }

    public void setOwnGroupList(String[] ownGroupList) {
        this.ownGroupList = ownGroupList;
    }

    public String[] getSharedGroupList() {
        return sharedGroupList;
    }

    public void setSharedGroupList(String[] sharedGroupList) {
        this.sharedGroupList = sharedGroupList;
    }
}
