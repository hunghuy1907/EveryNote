package com.hungth.everynote.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BankDatabase {
    private String DATABASE_NAME = "db_bank";

    private Context context;
    private SQLiteDatabase db;
    private BankDatabase.DPHelper dpHelper;

    public BankDatabase(Context context) {
        this.context = context;
    }

    public BankDatabase open() throws SQLException {
        dpHelper = new BankDatabase.DPHelper(context);
        db = dpHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dpHelper.close();
    }

    public boolean isFirst() {
        Cursor cursor = null;

        try {
            cursor = db.rawQuery("SELECT * FROM BANK;", null);
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
                db.execSQL("CREATE TABLE BANK ( id INTERGER  , position VARCHAR(50), " +
                        "nameBank VARCHAR(20), accountNumber VARCHAR(20), nameAccount VARCHAR(50), noteBank VARCHAR(100));");

            } catch (Exception e) {

            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS BANK;");
        }
    }

    public long insertBank (String position, String nameBank, String accountNumber, String nameAccount,
            String noteBank, int id) {
        long result = 0;
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("position", position);
        values.put("nameBank", nameBank);
        values.put("accountNumber", accountNumber);
        values.put("nameAccount", nameAccount);
        values.put("noteBank", noteBank);

        try {
            result = db.insert("BANK", null, values);
        } catch (Exception e) {

        }
        return result;
    }

    public Cursor getAllBankFromDtb() {
        Cursor cursor = null;
        cursor = db.rawQuery("SELECT * FROM BANK ORDER BY title ASC;", null);
        try {

        } catch (Exception e) {

        }
        return cursor;
    }

    public boolean deleteBank(int id) {
        try {
            db.execSQL("DELETE FROM BANK WHERE id = '" + id + "';");
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public boolean updateBank(int id, String position, String nameBank,
                              String accountNumber, String nameAccount, String noteBank) {
        db.execSQL("UPDATE BANK SET position = '" + position + "', nameBank = '" + nameBank + "'," +
                " accountNumber = '" + accountNumber + "', nameAccount = '" + nameAccount + "', noteBank = '"+ noteBank +
                "' WHERE id = '" + id + "';");
        return true;
    }
}
