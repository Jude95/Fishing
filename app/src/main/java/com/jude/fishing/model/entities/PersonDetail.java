package com.jude.fishing.model.entities;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Jude on 2015/9/11.
 */
public class PersonDetail extends PersonBrief implements Serializable {
    private String skill;
    private int age;
    private String background;
    private String address;
    private List<Seed> seeds;
    private int blogCount;
    private int attentionCount;
    private int fansCount;


    public List<Seed> getSeeds() {
        return seeds;
    }

    public String getAddress() {
        return address;
    }

    public int getBlogCount() {
        return blogCount;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public PersonDetail(String avatar, int id, String name, int relation, String sign, String skill, int age, String background, String address, List<Seed> seeds, int blogCount, int attentionCount, int fansCount) {
        super(avatar, id, name, relation, sign);
        this.skill = skill;
        this.age = age;
        this.background = background;
        this.address = address;
        this.seeds = seeds;
        this.blogCount = blogCount;
        this.attentionCount = attentionCount;
        this.fansCount = fansCount;
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
