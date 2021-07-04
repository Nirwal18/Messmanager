package com.nirwal.messmanager.models;

public class Group {
    private String groupId;
    private String ownerUuid;
    private String owner;
    private String title;
    private String description;

    public Group(String groupId, String ownerUuid, String owner, String title, String description) {
        this.groupId = groupId;
        this.ownerUuid = ownerUuid;
        this.owner = owner;
        this.title = title;
        this.description = description;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getOwnerUuid() {
        return ownerUuid;
    }

    public void setOwnerUuid(String ownerUuid) {
        this.ownerUuid = ownerUuid;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
