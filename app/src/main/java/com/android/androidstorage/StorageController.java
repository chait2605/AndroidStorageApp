package com.android.androidstorage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper class to create data storage
 */
public class StorageController {
    public static final String MESSAGE = "Message";
    public static final String TABLE_NAME = "Blog_Table";
    public static final String DATABASE_NAME = "DataStorageAssign.db";
    public static final int DATABASE_VERSION = 4;
    public static final String TABLE_CREATE= "create table Blog_Table (Message text not null);";

    private DataBaseHelper dbHelper;
    private Context context;
    private SQLiteDatabase db;

    public StorageController(Context context) {
        this.context = context;
        dbHelper = new DataBaseHelper(context);
    }

    public StorageController open() {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public long insert(String message) {
        ContentValues content=new ContentValues();
        content.put(MESSAGE, message);
        return db.insertOrThrow(TABLE_NAME, null, content);
    }

    public Cursor retrieve() {
        return db.query(TABLE_NAME, new String[]{MESSAGE}, null, null, null, null, null);
    }

    private static class DataBaseHelper extends SQLiteOpenHelper {
        public DataBaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            try {
                db.execSQL(TABLE_CREATE);
            } catch(SQLiteException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS Blog_Table");
            onCreate(db);
        }
    }

}