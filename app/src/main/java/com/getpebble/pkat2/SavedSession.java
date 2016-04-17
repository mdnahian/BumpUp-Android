package com.getpebble.pkat2;

import java.io.Serializable;

/**
 * Created by mdislam on 4/16/16.
 */
public class SavedSession implements Serializable {

    private String fname;
    private String lname;
    private String email;
    private String capital_one_id;


    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCapital_one_id() {
        return capital_one_id;
    }

    public void setCapital_one_id(String capital_one_id) {
        this.capital_one_id = capital_one_id;
    }
}
