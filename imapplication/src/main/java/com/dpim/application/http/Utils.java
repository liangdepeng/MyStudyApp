package com.dpim.application.http;

public class Utils {

    static {
        System.loadLibrary("imlib");
    }

    public static native String getKey();

    public static native String getAppSecret();
}
