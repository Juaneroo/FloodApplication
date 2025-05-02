package com.flood_web.security;



public class SecurityUtils {

    public static String encrypt(String password) {
        return Integer.toHexString(password.hashCode());
    }

}
