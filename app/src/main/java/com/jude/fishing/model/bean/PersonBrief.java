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
    private int relation;//0未关注，1已关注

    public int getRelation() {
        return relation;
    }

    public PersonBrief(String avatar, int id, String name, int relation, String sign) {
        this.avatar = avatar;
        this.id = id;
        this.name = name;
        this.relation = relation;
        this.sign = sign;
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
