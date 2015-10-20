package com.jude.fishing.model.entities;

/**
 * Created by heqiang on 2015/10/20.
 */
public class Contact extends PersonBrief {
    private String contactName;

    public Contact(String avatar, int id, String name, boolean relation, String sign, String contactName) {
        super(avatar, id, name, relation, sign);
        this.contactName = contactName;
    }

    public String getContactName() {
        return contactName;
    }
}
