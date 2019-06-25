package com.example.aroutelogin.interfaces;


import com.example.aroutelogin.BuildConfig;

public final class RoutePath {

    public static final boolean IS_DEBUG = BuildConfig.DEBUG;


    public static final String TAG = "TAG";

    public static final String PATH = "path";

    //存储是否登录的
    public static final String SP_IS_LOGIN = "sp_is_login";

    private static final String BASE_PATH = "/base/path/";

    //登录
    public static final String LOGIN_PATH = BASE_PATH + "login";
    //不需要登录的activity
    public static final String FIRST_PATH = BASE_PATH + "first";
    //登录登录的actvity
    public static final String SECOND_PATH = BASE_PATH + "second";
}
