package com.hungth.everynote.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Admin on 3/7/2018.
 */

public class AcountDatabase {
    private String DATABASE_NAME = "db_acount";

    private Context context;
    private SQLiteDatabase db;
    private DPHelper dpHelper;

    public AcountDatabase(Context context) {
        this.context = context;
    }

    public AcountDatabase open() throws SQLException {
        dpHelper = new DPHelper(context);
        db = dpHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dpHelper.close();
    }

    public boolean isFirst() {
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM MANAGER;", null);
        } catch (Exception e) {

        }

        if (cursor.getCount() == 0) {
            return true;
        }

        return false;
    }

    public void changePassword(String newPass) {
        if (isFirst()) {
            db.execSQL("INSERT INTO MANAGER VALUES ('" + newPass + "');");
        } else {
            db.execSQL("UPDATE MANAGER SET password = '" + newPass + "';");
        }
    }

    public boolean checkPassword(String strPass) {
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM MANAGER WHERE password = '" + strPass + "';", null);
        } catch (Exception e) {

        }
        if (cursor.getCount() == 0) {
            return false;
        }
        return true;
    }

    class DPHelper extends SQLiteOpenHelper {


        public DPHelper(Context context) {
            super(context, DATABASE_NAME, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("CREATE TABLE MANAGER ( password VARCHAR(20));");
                db.execSQL("CREATE TABLE ACOUNT (name VARCHAR(50), " +
                        "password VARCHAR(20), image VARCHAR(50));");

            } catch (Exception e) {

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS MANAGER;");
            db.execSQL("DROP TABLE IF EXISTS ACOUNT;");
        }
    }

    public long insertAcount(String image, String name, String password) {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put("image", image);
        values.put("name", name);
        values.put("password", password);

        try {
            result = db.insert("ACOUNT", null, values);
        } catch (Exception e) {

        }
        return result;
    }

    public Cursor getAllAcountFromDtb() {
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM ACOUNT ORDER BY name ASC;", null);
        try {

        } catch (Exception e) {

        }
        return cursor;
    }

    public boolean deleteAcount(String nameAcount) {
        try {
            db.execSQL("DELETE FROM ACOUNT WHERE name = '" + nameAcount + "';");
        } catch (Exception e) {
            return false;
        }
        return true;

    }

    public boolean updateAcount(String oldName, String newName, String newPassword, String newImage) {
        db.execSQL("UPDATE ACOUNT SET name = '" + newName + "', password = '" + newPassword + "', image = '" + newImage + "' WHERE name = '" + oldName + "';");
        return true;
    }
}
