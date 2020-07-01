package edu.glut.tini.data.entity;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    private boolean autoLogin = true;
    private boolean rebPsd = true;
    private boolean darkMode = true;
    private boolean pushNotification = true;

    public boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public boolean isRebPsd() {
        return rebPsd;
    }

    public void setRebPsd(boolean rebPsd) {
        this.rebPsd = rebPsd;
    }

    public boolean isDarkMode() {
        return darkMode;
    }

    public void setDarkMode(boolean darkMode) {
        this.darkMode = darkMode;
    }

    public boolean isPushNotification() {
        return pushNotification;
    }

    public void setPushNotification(boolean pushNotification) {
        this.pushNotification = pushNotification;
    }

    public User() {
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
}
