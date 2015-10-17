package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class PersonDetail extends PersonBrief implements Serializable {
    private String skill;
    private int age;
    @SerializedName("bg")
    private String background;
    private String address;
    private List<Seed> seeds;
    private int blogCount;
    private String cared;
    private String fans;
    private int gender;


    public List<Seed> getSeeds() {
        return seeds;
    }

    public String getAddress() {
        return address;
    }

    public int getBlogCount() {
        return blogCount;
    }

    public String getCared() {
        return cared;
    }

    public String getFans() {
        return fans;
    }

    public PersonDetail(String avatar, int id, String name, boolean relation, String sign, String skill, int age, String background, String address, List<Seed> seeds, int blogCount, String cared, String fans) {
        super(avatar, id, name, relation, sign);
        this.skill = skill;
        this.age = age;
        this.background = background;
        this.address = address;
        this.seeds = seeds;
        this.blogCount = blogCount;
        this.cared = cared;
        this.fans = fans;
    }

    public void setBlogCount(int blogCount) {
        this.blogCount = blogCount;
    }

    public void setCared(String cared) {
        this.cared = cared;
    }

    public void setFans(String fans) {
        this.fans = fans;
    }

    public void setSeeds(List<Seed> seeds) {
        this.seeds = seeds;
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

    public void setSkill(String skill) {
        this.skill = skill;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
