package edu.glut.tini.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.hyphenate.chat.EMClient;

import org.jetbrains.annotations.NotNull;
import edu.glut.tini.data.dao.ContactsDao;
import edu.glut.tini.data.entity.Contacts;

/**
 * 实体类填写至 "entities = {}"里,，并提供一个抽象的获取Dao的方法
 * */
@Database(entities = {Contacts.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance = null;
    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = buildDataBase(context);
                }
            }
        }
        return instance;
    }

    @NotNull
    private static AppDatabase buildDataBase(Context context) {
        String currentUser = EMClient.getInstance().getCurrentUser();
        return Room.databaseBuilder(context,AppDatabase.class,currentUser+"appdb")
                .build();
    }

    public abstract ContactsDao getContactsDao();
}
