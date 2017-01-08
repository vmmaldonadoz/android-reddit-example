package com.thirdmono.reddit.data.entity;

import com.google.gson.annotations.SerializedName;

public class Reddit {

    @SerializedName("kind")
    private String kind;
    @SerializedName("data")
    private Listing listing;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

}
