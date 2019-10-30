package io.github.hotstu.archcomponent.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

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
