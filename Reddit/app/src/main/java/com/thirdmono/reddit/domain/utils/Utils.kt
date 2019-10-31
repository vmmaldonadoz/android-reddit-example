package com.thirdmono.reddit.domain.utils

import android.content.Context
import android.net.ConnectivityManager
import com.google.gson.Gson
import com.thirdmono.reddit.data.entity.Listing
import com.thirdmono.reddit.data.entity.Thing

object Utils {

    fun hasNetwork(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = cm.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    fun toString(`object`: Any): String {
        val gson = Gson()
        return gson.toJson(`object`)
    }

    fun valueOf(json: String): Thing {
        val gson = Gson()
        return gson.fromJson(json, Thing::class.java)
    }

    fun valueOfListing(json: String): Listing {
        val gson = Gson()
        return gson.fromJson(json, Listing::class.java)
    }

}
