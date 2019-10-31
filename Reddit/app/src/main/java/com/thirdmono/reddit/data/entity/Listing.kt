package com.thirdmono.reddit.data.entity

import com.google.gson.annotations.SerializedName

data class Listing (
    @SerializedName("modhash")
    private val modhash: String? = null,
    @SerializedName("children")
    private val things: List<Thing>? = null,
    @SerializedName("after")
    private val after: String? = null,
    @SerializedName("before")
    private val before: Any? = null
)