package edu.glut.tini.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import edu.glut.tini.data.entity.User;

@Dao
public interface UserDao {
    @Insert
    public void add(User user);

    @Query("SELECT * FROM USER WHERE USERNAME LIKE :username")
    public User selectUserByUsername(String username);

    @Delete
    public int delete(User user);

    @Update
    public void update();

    @Query("SELECT * FROM User")
    public List<User> selectAll();
}
