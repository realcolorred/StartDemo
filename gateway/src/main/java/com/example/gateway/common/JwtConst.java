package com.example.gateway.common;

/**
 * Created by new on 2020/8/15.
 */
public class JwtConst {

    public static final String TOKEN     = "token";
    public static final String ISSUER    = "example";
    public static final String USER_INFO = "userInfo";
    public static final String REFRESH   = "refresh";

    public static final long DEFAULT_MAX_AGE     = 3 * 60 * 60 * 1000;
    public static final long DEFAULT_REFRESH_AGE = DEFAULT_MAX_AGE / 10;
}
