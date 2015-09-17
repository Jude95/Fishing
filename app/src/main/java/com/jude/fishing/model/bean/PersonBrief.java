package com.jude.fishing.model.bean;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/2/11.
 */

public class PersonBrief implements Serializable{
    private int id;

    private String name;

    private String avatar;

    private String sign;

    public PersonBrief(int userId, String userName, String userFace, String userSign) {
        this.id = userId;
        this.name = userName;
        this.avatar = userFace;
        this.sign = userSign;
    }

    public void clone(PersonBrief person) {
        this.id = person.id;
        this.name = person.name;
        this.avatar = person.avatar;
        this.sign = person.sign;
    }


    public int getUID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getSign() {
        return sign;
    }

}
