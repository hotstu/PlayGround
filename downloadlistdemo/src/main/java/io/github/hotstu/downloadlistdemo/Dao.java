package io.github.hotstu.downloadlistdemo;

import android.content.Context;

import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.query.QueryBuilder;

public class Dao {
    private static final boolean ENCRYPTED = false;
    private static Dao sInstance;
    private  DaoSession daoSession;

    public static Dao getInstance(Context ctx) {
        if (sInstance == null) {
            synchronized (Dao.class) {
                QueryBuilder.LOG_SQL = true;
                QueryBuilder.LOG_VALUES = true;
                if (sInstance == null) {
                    sInstance = new Dao(ctx.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private Dao(Context ctx) {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(ctx, ENCRYPTED ? "notes-db-encrypted" : "notes-db");
        Database db = ENCRYPTED ? helper.getEncryptedWritableDb("super-secret") : helper.getWritableDb();
        daoSession = new DaoMaster(db).newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
