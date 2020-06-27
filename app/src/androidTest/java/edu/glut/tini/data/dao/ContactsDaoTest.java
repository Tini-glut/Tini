package edu.glut.tini.data.dao;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import edu.glut.tini.data.AppDatabase;
import edu.glut.tini.data.entity.Contacts;
import edu.glut.tini.data.entity.User;

import static org.junit.Assert.*;

/**
 * @Author Ardien
 * @Date 6/26/2020 3:46 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@RunWith(AndroidJUnit4.class)
public class ContactsDaoTest {

    private static final String TAG = "ContactsDaoTest";
    Context appContext;
    ContactsDao contactsDao;

    @Before
    public void init() {
        appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        contactsDao = AppDatabase.getInstance(appContext).getContactsDao();
    }

    @Test
    public void add() {
        try {
            List<String> usernames = EMClient.getInstance().contactManager().getAllContactsFromServer();
            for (String username : usernames) {
                contactsDao.add(new Contacts(username));
                System.out.println("hhhhhhhh");
            }
        } catch (HyphenateException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void select(){
        List<Contacts> contacts = contactsDao.selectAll();
        System.out.println("aaaaaaaaaa");
        for (Contacts contact : contacts) {
            System.out.println(contact.getContactsFriendUsername());
        }
    }

    @Ignore
    @Test
    public void deleteAll(){
        contactsDao.deleteAll();
        System.out.println("deletaAlldeletaAlldeletaAll");

    }
    @Test
    public void updateContactsSeq(){
//        contactsDao.updateContactsSeq();
        System.out.println("updateContactsSequpdateContactsSeq");

    }


    @Test
    public void selectContactsByUsername() {
        String admin = contactsDao.selectContactsByUsername("admin").get(0).getContactsFriendUsername();
        assertEquals(admin,"admin5");

    }

}