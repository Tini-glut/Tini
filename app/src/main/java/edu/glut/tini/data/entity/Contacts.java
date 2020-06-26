package edu.glut.tini.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

/**
 * 通讯录里的好友实体
 *
 * @Author Ardien
 * @Date 6/26/2020 3:36 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Entity
public class Contacts implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long cid;
    private String ContactsFriendUsername;

    public Contacts() {
    }

    public Contacts(String contactsFriendUsername) {
        ContactsFriendUsername = contactsFriendUsername;
    }

    public long getCid() {
        return cid;
    }

    public void setCid(long cid) {
        this.cid = cid;
    }

    public String getContactsFriendUsername() {
        return ContactsFriendUsername;
    }

    public void setContactsFriendUsername(String contactsFriendUsername) {
        ContactsFriendUsername = contactsFriendUsername;
    }
}
