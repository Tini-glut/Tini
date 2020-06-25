package edu.glut.tini.utils;

/**
 * @Author Ardien
 * @Date 6/22/2020 10:40 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
public class StringUtils {
    public static boolean isValidUsername(String username) {
        return username.matches("[a-zA-Z0-9_]*");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^[0-9]\\w{3,20}$");
    }
}
