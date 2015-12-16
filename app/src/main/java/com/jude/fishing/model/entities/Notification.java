package com.jude.fishing.model.entities;

/**
 * Created by zhuchenxi on 15/10/23.
 */
public class Notification {
    public static final int USER_ATTENTION = 300;


    public static final int PLACE_PASS = 200;
    public static final int PLACE_REFUSE = 201;
    public static final int PLACE_ADD = 202;
    public static final int PLACE_COMMENT = 203;

    public static final int BLOG_COMMENT = 100;
    public static final int BLOG_RECOMMEND = 101;
    public static final int BLOG_PRAISE = 102;


    private int id;
    private String msg;
    private String link;
    private int type;
    private long time;
    private boolean read = true;

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
