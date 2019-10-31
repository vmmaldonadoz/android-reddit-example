package com.thirdmono.reddit.data.entity

import com.google.gson.annotations.SerializedName

data class Reddit (

    @SerializedName("kind")
    private val kind: String? = null,
    @SerializedName("data")
    private val listing: Listing? = null

)
