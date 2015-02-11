package com.example.zf_android.trade.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Leo on 2015/2/11.
 */
public class NetworkUtil {

    private static ConnectivityManager cm;

    public static ConnectivityManager getConnectivityManager(Context context) {
        if (cm == null)
            cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm;
    }

    public static boolean isNetworkAvailable(Context context) {
        NetworkInfo networkInfo = getConnectivityManager(context).getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected())
            return true;
        return false;
    }

    public static String getNetworkType(Context context) {
        NetworkInfo networkInfo = getConnectivityManager(context).getActiveNetworkInfo();
        if (null != networkInfo)
            return networkInfo.getTypeName();
        return "";
    }

}
