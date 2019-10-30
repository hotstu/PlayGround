package io.github.hotstu.archcomponent.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import static androidx.room.OnConflictStrategy.IGNORE;

/**
 * @author hglf
 * @since 2017/12/28
 */
@Dao
public interface UserDao {
    @Insert(onConflict = IGNORE)
    long addUser(UserEntity user);

    @Query("select * from user where _id = :id")
    LiveData<UserEntity> findUserById(long id);

    @Delete
    int delUser(UserEntity user);

    @Query("select * from user")
    LiveData<List<UserEntity>> queryAllUsers();
}
