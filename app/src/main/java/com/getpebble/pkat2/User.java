package com.getpebble.pkat2;

import java.io.Serializable;

/**
 * Created by mdislam on 4/17/16.
 */
public class User implements Serializable {

    private double calorie;
    private double distance;
    private String name;
    private int step_count;


    public User(double calorie, double distance, String name, int step_count) {
        this.calorie = calorie;
        this.distance = distance;
        this.name = name;
        this.step_count = step_count;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStep_count() {
        return step_count;
    }

    public void setStep_count(int step_count) {
        this.step_count = step_count;
    }
}
