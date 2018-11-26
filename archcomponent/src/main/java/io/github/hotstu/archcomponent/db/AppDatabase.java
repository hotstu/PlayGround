/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.hotstu.archcomponent.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import io.github.hotstu.archcomponent.BuildConfig;

@Database(entities = {UserEntity.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public abstract UserDao UserModel();


    public static AppDatabase getInMemoryDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    if (BuildConfig.DEBUG) {
                        INSTANCE = Room.inMemoryDatabaseBuilder(context.getApplicationContext(), AppDatabase.class)
                                .allowMainThreadQueries()
                                .build();
                    } else {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                AppDatabase.class, "passkeeper.db")
                                .allowMainThreadQueries()
                                .build();
                    }
                }
            }

        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }
}