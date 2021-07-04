package com.nirwal.messmanager.activities.Intro;

public class IntroItem {
    private int photo;
    private String title;
    private String description;

    public IntroItem(int photo, String title, String description) {
        this.photo = photo;
        this.title = title;
        this.description = description;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
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
