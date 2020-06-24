package edu.glut.tini.data.dao;

import android.content.Context;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.glut.tini.data.AppDatabase;
import edu.glut.tini.data.entity.User;
import edu.glut.tini.ui.MainActivity;

import static org.junit.Assert.*;

/**
 * @Author Ardien
 * @Date 6/24/2020 4:07 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/
@RunWith(AndroidJUnit4.class)
public class UserDaoTest {
    private static final String TAG = "UserDaoTest";
    Context appContext;
    UserDao userDao;
    @Before
    public void add() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        User user = new User("name","12345");
        userDao =  AppDatabase.getInstance(appContext).getUserDao();
        userDao.add(user);
    }

    @Test
    public void selectUserByUsername() {
        User user = userDao.selectUserByUsername("name");
        Log.d(TAG, "selectUserByUsername: "+user);
        Assert.assertEquals(user.getUsername(),"name");
    }

    @After
    public void delete() {

    }
}