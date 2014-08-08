package com.bigfootsoftwares.notes;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_LAST_REVIEWED = "last_reviewed";
    public static final String COLUMN_TOTAL_REVIEWS = "total_reviews";
    public static final String COLUMN_CONTENT = "content";

    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_NOTES + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_LAST_REVIEWED
            + " text not null);";

    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(" CREATE TABLE " + TABLE_NOTES + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_TITLE + " TEXT NOT NULL, " +
                        COLUMN_LAST_REVIEWED + " TEXT NOT NULL, " +
                        COLUMN_TOTAL_REVIEWS + " INT NOT NULL, " +
                        COLUMN_CONTENT + " TEXT NOT NULL);"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(SQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }

}