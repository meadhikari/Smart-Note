package com.bigfootsoftwares.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;
import java.util.List;

public class NotesDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SQLiteHelper dbHelper;

    private static final String DATABASE_NAME = "notes.db";

    public NotesDataSource(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public long insertNotes(String title, String content)
    {
        this.open();
        ContentValues insertValues = new ContentValues();
        insertValues.put(SQLiteHelper.COLUMN_TITLE, title);
        insertValues.put(SQLiteHelper.COLUMN_CONTENT, content);
        long epoch = System.currentTimeMillis();
        insertValues.put(SQLiteHelper.COLUMN_LAST_REVIEWED, epoch);
        insertValues.put(SQLiteHelper.COLUMN_TOTAL_REVIEWS, 0);
        long val = database.insert(SQLiteHelper.TABLE_NOTES, null, insertValues);
        this.close();
        return val;
    }
    public long deleteOne(String title, String content)
    {
        this.open();
        database.delete(SQLiteHelper.TABLE_NOTES, SQLiteHelper.COLUMN_CONTENT + " = ?", new String[] { String.valueOf(content) });
        this.close();
        return 0;
    }
    public long incrementTotalReviews(String content)
    {
        this.open();
        String sql = "UPDATE " + SQLiteHelper.TABLE_NOTES +
                " SET " + SQLiteHelper.COLUMN_TOTAL_REVIEWS + "=" + SQLiteHelper.COLUMN_TOTAL_REVIEWS + "+1" +
                " WHERE " + SQLiteHelper.COLUMN_CONTENT + " >= '" + content+"'";

        database.execSQL(sql);
        /*database.execSQL("UPDATE " + SQLiteHelper.TABLE_NOTES + " SET "
                + SQLiteHelper.COLUMN_TOTAL_REVIEWS + " = " + SQLiteHelper.COLUMN_TOTAL_REVIEWS + " +1 WHERE "
                + SQLiteHelper.COLUMN_CONTENT + " = " +content);*/
        this.close();
        return 0;

    }
    public long modifyLastSeen(String content)
    {
        this.open();
        String sql = "UPDATE " + SQLiteHelper.TABLE_NOTES +
                " SET " + SQLiteHelper.COLUMN_LAST_REVIEWED + "=" + System.currentTimeMillis()+
                " WHERE " + SQLiteHelper.COLUMN_CONTENT + " >= '" + content+"'";

        database.execSQL(sql);
        /*database.execSQL("UPDATE " + SQLiteHelper.TABLE_NOTES + " SET "
                + SQLiteHelper.COLUMN_TOTAL_REVIEWS + " = " + SQLiteHelper.COLUMN_TOTAL_REVIEWS + " +1 WHERE "
                + SQLiteHelper.COLUMN_CONTENT + " = " +content);*/
        this.close();
        return 0;

    }
    public ArrayList<NoteItems> getAllNotes() {
        this.open();
        ArrayList<NoteItems> items = new ArrayList<NoteItems>();
        Cursor  cursor = database.rawQuery("select * from notes",null);

        if (cursor .moveToFirst()) {

            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TITLE));
                String last_reviewed = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LAST_REVIEWED));
                String total_reviews = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TOTAL_REVIEWS));
                String content  = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_CONTENT));

                NoteItems item = new NoteItems(title,last_reviewed,total_reviews,content);
                items.add(item);
                cursor.moveToNext();
            }
        }
        this.close();
        return items;
    }


    public List<NoteItems> getAllNotesForNotification() {
        this.open();
        List<NoteItems> items = new ArrayList<NoteItems>();
        Cursor  cursor = database.rawQuery("select * from notes",null);

        if (cursor .moveToFirst()) {

            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TITLE));
                String last_reviewed = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_LAST_REVIEWED));
                String total_reviews = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_TOTAL_REVIEWS));
                String content  = cursor.getString(cursor.getColumnIndex(SQLiteHelper.COLUMN_CONTENT));

                NoteItems item = new NoteItems(title,last_reviewed,total_reviews,content);
                long past_epoch = Long.valueOf(item.last_reviewed);
                long current_epoch = System.currentTimeMillis();
                long difference = current_epoch - past_epoch;
                if (notificationRequired(difference,Integer.valueOf(item.total_reviews))) {
                    items.add(item);
                }
                cursor.moveToNext();
            }
        }
        this.close();
        return items;
    }

    public boolean notificationRequired(long difference, int times)
    {
        if (times == 0){
            return true;
        }
        else if (times == 1 && difference >= 60000*5)
        {
            return true;
        }
        else if (times == 2 && difference >=60000*10)
        {
            return true;
        }
        else if (times == 3 && difference >=60000*30)
        {
            return true;
        }
        else if (times == 4 && difference >=60000*60)
        {
            return true;
        }
        else if (times == 5 && difference >=60000*60*2)
        {
            return true;
        }
        else if (times == 6 && difference >=60000*60*4)
        {
            return true;
        }
        else if (times == 7 && difference >=60000*60*8)
        {
            return true;
        }
        else if (times == 8 && difference >=60000*60*12)
        {
            return true;
        }
        else if (times == 9 && difference >=60000*60*24)
        {
            return true;
        }
        else if (times == 10 && difference >=60000*60*24*2)
        {
            return true;
        }
        else if (times == 11 && difference >=60000*60*24*4)
        {
            return true;
        }
        else if (times == 11 && difference >=60000*60*24*8)
        {
            return true;
        }
        else if (times == 11 && difference >=60000*60*24*16)
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}
