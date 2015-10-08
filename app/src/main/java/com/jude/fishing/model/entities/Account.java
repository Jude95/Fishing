package com.jude.fishing.model.entities;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class Account extends PersonDetail implements Serializable{
    private String phone;
    private String token;
    private String rongToken;

    public Account(String avatar, int id, String name, int relation, String sign, String skill, int age, String background, String address, Seed[] seeds, int blogCount, int attentionCount, int fansCount, String phone, String rongToken) {
        super(avatar, id, name, relation, sign, skill, age, background, address, seeds, blogCount, attentionCount, fansCount);
        this.phone = phone;
        this.rongToken = rongToken;
    }
    public String getToken() {
        return token;
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
