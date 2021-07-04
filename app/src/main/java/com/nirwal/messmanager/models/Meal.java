package com.nirwal.messmanager.models;

public class Meal {
    public boolean breakFastRequired= false;
    public boolean lunchRiceRequired =false;
    public boolean dinnerRiceRequired = false;
    public int lunchRotiCount = 0;
    public int dinnerRotiCount = 0;
    public String requestorName ="";

    public Meal(String requestorName, boolean breakFastRequired, boolean lunchRiceRequired, boolean dinnerRiceRequired, int lunchRotiCount, int dinnerRotiCount){
        this.requestorName = requestorName;
        this.breakFastRequired =breakFastRequired;
        this.lunchRiceRequired = lunchRiceRequired;
        this.dinnerRiceRequired = dinnerRiceRequired;
        this.lunchRotiCount = lunchRotiCount;
        this.dinnerRotiCount = dinnerRotiCount;

    }
}
