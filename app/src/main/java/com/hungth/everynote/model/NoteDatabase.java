package com.hungth.everynote.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 4/2/2018.
 */

public class NoteDatabase {
    private String DATABASE_NAME = "db_note";

    private Context context;
    private SQLiteDatabase db;
    private NoteDatabase.DPHelper dpHelper;

    public NoteDatabase(Context context) {
        this.context = context;
    }

    public NoteDatabase open() throws SQLException {
        dpHelper = new NoteDatabase.DPHelper(context);
        db = dpHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dpHelper.close();
    }

    public boolean isFirst() {
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM NOTE;", null);
        } catch (Exception e) {

        }

        if (cursor.getCount() == 0) {
            return true;
        }

        return false;
    }

    class DPHelper extends SQLiteOpenHelper {


        public DPHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE NOTE (title VARCHAR(50), " +
                        "content VARCHAR(20), color INTERGER, colorText INTERGER, id INTERGER, alarm VARCHAR(50));");

            } catch (Exception e) {

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS NOTE;");
        }
    }

    public long insertNote (String title, String content, int color, int colorText, int id, String alarm)
    {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("title", title);
        values.put("content", content);
        values.put("color", color);
        values.put("colorText", colorText);
        values.put("alarm", alarm);

        try {
            result = db.insert("NOTE", null, values);
        } catch (Exception e) {

        }
        return result;
    }

    public Cursor getAllNoteFromDtb() {
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM NOTE ORDER BY title ASC;", null);
        try {

        } catch (Exception e) {

        }
        return cursor;
    }

    public boolean deleteNote(int id) {
        try {
            db.execSQL("DELETE FROM NOTE WHERE id = '" + id + "';");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateNote(int id, String newTitle, String newContent,
                                int newColorBackground, int newColorText) {
        db.execSQL("UPDATE NOTE SET title = '" + newTitle + "', content = '" + newContent + "'," +
                " color = '" + newColorBackground + "', colorText = '" + newColorText + "' WHERE id = '" + id + "';");
        return true;
    }

    public boolean updateTimeAlarm(int id, String newTimeAlarm) {
        db.execSQL("UPDATE NOTE SET timeAlarm = '" + newTimeAlarm + "' WHERE id = '" + id + "';");
        return true;
    }
}
