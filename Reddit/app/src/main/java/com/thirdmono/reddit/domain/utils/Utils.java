package com.thirdmono.reddit.domain.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.thirdmono.reddit.data.entity.Listing;
import com.thirdmono.reddit.data.entity.Thing;

/**
 * Utilities.
 *
 * @author <a href="mailto:vmmzn20@gmail.com">Victor Maldonado</a>
 * @since 1.0
 */
public class Utils {


    public static boolean hasNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static String toString(Object object) {
        Gson gson = new Gson();
        String json = gson.toJson(object);
        return json;
    }

    public static Thing valueOf(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Thing.class);
    }

    public static Listing valueOfListing(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Listing.class);
    }

}
