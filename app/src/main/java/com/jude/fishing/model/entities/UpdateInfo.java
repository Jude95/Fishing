package com.jude.fishing.model.entities;

import com.google.gson.annotations.SerializedName;

/**
 * Created by zhuchenxi on 15/10/20.
 */
public class UpdateInfo {
    @SerializedName("name")
    private String versionName;
    @SerializedName("code")
    private int versionCode;
    private String info;
    private String url;
    private long time;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }
}
