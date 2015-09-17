package com.jude.fishing.model.bean;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class Account extends PersonDetail implements Serializable{
    private String phone;
    private String rongToken;

    public Account(int userId, String userName, String userFace, String userSign, String skill, int age, String background, String phone, String rongToken) {
        super(userId, userName, userFace, userSign, skill, age, background);
        this.phone = phone;
        this.rongToken = rongToken;
    }

    public String getPhone() {

        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRongToken() {
        return rongToken;
    }

    public void setRongToken(String rongToken) {
        this.rongToken = rongToken;
    }
}
