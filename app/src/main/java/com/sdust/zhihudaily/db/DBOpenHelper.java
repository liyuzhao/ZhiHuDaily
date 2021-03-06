package com.sdust.zhihudaily.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.sdust.zhihudaily.Constants;
import com.sdust.zhihudaily.util.LogUtils;

public class DBOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "Database";

    private static final int DB_VERSION = Constants.DATABASE_VERSION;
    private static final String DB_NAME = Constants.DATABASE_NAME;

    private static DBOpenHelper sDBOpenHelper;
    /**
     * Creates underlying database table using DAOs.
     */
    private void createAllTables(SQLiteDatabase db, boolean ifNotExists) {
        CacheDao.createTable(db, ifNotExists);
        CollectedDao.createTable(db,ifNotExists);
    }

    /**
     * Drops underlying database table using DAOs.
     */
    private void dropAllTables(SQLiteDatabase db, boolean ifExists) {
        CacheDao.dropTable(db, ifExists);
        CollectedDao.dropTable(db,ifExists);
    }

    public static DBOpenHelper getInstance(Context context) {
        if (sDBOpenHelper == null) {
            synchronized (DBOpenHelper.class) {
                if (sDBOpenHelper == null) {
                    sDBOpenHelper = new DBOpenHelper(context);
                }
            }
        }
        return sDBOpenHelper;
    }

    private DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtils.i(TAG, "Creating tables for DB version " + DB_VERSION);
        createAllTables(db, false);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.i(TAG, "Upgrading DB from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        dropAllTables(db, true);
        onCreate(db);
    }
}

