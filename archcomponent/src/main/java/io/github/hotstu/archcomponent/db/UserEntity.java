package io.github.hotstu.archcomponent.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "user")
public class UserEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    public int id;
    public String username;

    public UserEntity(int id, String username) {
        this.id = id;
        this.username = username;
    }
}
