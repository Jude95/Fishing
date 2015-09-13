package com.jude.fishing.model.bean;

/**
 * Created by Mr.Jude on 2015/2/11.
 */

public class PersonBrief{
    private int id;

    private String name;

    private String face;

    private String sign;

    public PersonBrief(int userId, String userName, String userFace, String userSign) {
        this.id = userId;
        this.name = userName;
        this.face = userFace;
        this.sign = userSign;
    }

    public void clone(PersonBrief person) {
        this.id = person.id;
        this.name = person.name;
        this.face = person.face;
        this.sign = person.sign;
    }

    public PersonBrief() {
    }

    public int getUID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getFace() {
        return face;
    }

    public String getSign() {
        return sign;
    }

}
