package com.thirdmono.reddit.data.entity;

import com.google.gson.annotations.SerializedName;

public class Thing {

    @SerializedName("kind")
    private String kind;
    @SerializedName("data")
    private SubReddit data;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public SubReddit getData() {
        return data;
    }

    public void setData(SubReddit data) {
        this.data = data;
    }

}
