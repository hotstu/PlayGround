package io.github.hotstu.archcomponent.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

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
