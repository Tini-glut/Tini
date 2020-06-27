package edu.glut.tini.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.glut.tini.data.entity.Contacts;

/**
 * @Author Ardien
 * @Date 6/26/2020 3:40 PM
 * @Email ardien@126.com
 * @Version 1.0
 **/

@Dao
public interface ContactsDao {

    @Insert
    public void add(Contacts... contacts);

    @Delete
    public void delete(Contacts contacts);

    @Update
    public void update(Contacts contacts);

    @Query("SELECT * FROM Contacts WHERE ContactsFriendUsername LIKE '%' || :username || '%'")
    public List<Contacts> selectContactsByUsername(String username);

    @Query("SELECT * FROM Contacts")
    public List<Contacts> selectAll();

    @Query("DELETE FROM Contacts")
    public void deleteAll();


   @Query("INSERT OR REPLACE INTO Contacts ('ContactsFriendUsername') VALUES(:userName)")
    public void insertInto(String userName);
}
