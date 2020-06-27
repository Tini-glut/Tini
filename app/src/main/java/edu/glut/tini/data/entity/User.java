package edu.glut.tini.data.entity;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {

    public User() {
    }

    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
}
