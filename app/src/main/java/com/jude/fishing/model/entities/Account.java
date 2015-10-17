package com.jude.fishing.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class Account extends PersonDetail implements Serializable{
    private String phone;
    private String token;
    private String rongToken;

    public Account(String avatar, int id, String name, boolean relation, String sign, String skill, int age, String background, String address, List<Seed> seeds, int blogCount, String cared, String fans, String phone, String token, String rongToken) {
        super(avatar, id, name, relation, sign, skill, age, background, address, seeds, blogCount, cared, fans);
        this.phone = phone;
        this.token = token;
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
