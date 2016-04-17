package com.getpebble.pkat2;

import java.io.Serializable;

/**
 * Created by mdislam on 4/17/16.
 */
public class Compare implements Serializable {

    private Info info;
    private User user;

    public Compare(Info info, User user) {
        this.info = info;
        this.user = user;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
