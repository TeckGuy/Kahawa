package com.tecksolke.kahawa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by techguy on 2/12/18.
 */

public class TemporaryDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Kahawa.db";
    private static final String TABLE_NAME = "Users_table";

    public static final String COL_1 = "ID";
    private static final String COL_2 = "USERNAME";
    private static final String COL_3 = "USERID";
    private static final String COL_4 = "REGION";
    private static final String COL_5 = "COLLECTION";
    private static final String COL_6 = "ESTATE";
    private static final String COL_7 = "COOP";


    public TemporaryDB(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " ( ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT,USERID TEXT,REGION TEXT,COLLECTION TEXT,ESTATE TEXT,COOP TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }


    //insert data
    public boolean insertData(String name, String userID, String region, String collection, String estate, String coop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, userID);
        contentValues.put(COL_4, region);
        contentValues.put(COL_5, collection);
        contentValues.put(COL_6, estate);
        contentValues.put(COL_7, coop);
        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //read data
    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return res;
    }

    //update data
    public boolean updateData(String name, String userID, String region, String collection, String estate, String coop) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, userID);
        contentValues.put(COL_4, region);
        contentValues.put(COL_5, collection);
        contentValues.put(COL_6, estate);
        contentValues.put(COL_7, coop);
        long result = db.update(TABLE_NAME, contentValues, "USERID =?", new String[]{userID});
        db.close();

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    //delete specific data in database
    public Integer deleteData(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int i = db.delete(TABLE_NAME, "ID=?", new String[]{id});
        return i;
    }

    //delete all data
    boolean deleteAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + DATABASE_NAME);
//        db.execSQL("delete from "+TABLE_NAME);
//        db.delete(TABLE_NAME, null, null);
        return false;
    }
}
