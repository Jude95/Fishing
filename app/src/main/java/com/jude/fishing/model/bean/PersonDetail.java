package com.jude.fishing.model.bean;

import java.io.Serializable;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class PersonDetail extends PersonBrief implements Serializable {
    private String skill;
    private int age;
    private String background;
    private String address;

    public String getAddress() {
        return address;
    }

    private int blogCount;
    private int attentionCount;
    private int fansCount;

    public int getBlogCount() {
        return blogCount;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public PersonDetail(int userId, String userName, String userFace, String userSign, String skill, int age, String background) {
        super(userId, userName, userFace, userSign);
        this.skill = skill;
        this.age = age;
        this.background = background;
    }

    public String getSkill() {
        return skill;
    }

    public int getAge() {
        return age;
    }

    public String getBackground() {
        return background;
    }

}
