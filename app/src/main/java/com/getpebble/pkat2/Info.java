package com.getpebble.pkat2;

import java.io.Serializable;

/**
 * Created by mdislam on 4/17/16.
 */
public class Info implements Serializable {

    private String duration;
    private String time;

    public Info(String duration, String time) {
        this.duration = duration;
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
