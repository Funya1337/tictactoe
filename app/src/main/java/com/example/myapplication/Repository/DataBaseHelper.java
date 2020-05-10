package com.example.myapplication.Repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    private static final String DATABASE_NAME = "DataBaseHelper";

    private static final String PHOTO_TABLE = "photo_table";
    private static final String PHOTO_COLUMN1 = "ID";
    private static final String PHOTO_COLUMN2 = "photo";

    private static final String WINNERS_TABLE = "winners_table";
    private static final String WINNERS_COLUMN1 = "ID";
    private static final String WINNERS_COLUMN2 = "winner";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String table1 = "CREATE TABLE " + PHOTO_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PHOTO_COLUMN2 +" TEXT)";
        String table2 = "CREATE TABLE " + WINNERS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                WINNERS_COLUMN2 +" TEXT)";
        db.execSQL(table1);
        db.execSQL(table2);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PHOTO_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + WINNERS_TABLE);
        onCreate(db);
    }

    public boolean addData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(PHOTO_COLUMN2, item);

        Log.d(TAG, "addData: Adding " + item + " to " + PHOTO_TABLE);

        long result = db.insert(PHOTO_TABLE, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addWinnerData(String item) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(WINNERS_COLUMN2, item);

        Log.d(TAG, "addData: Adding " + item + " to " + WINNERS_TABLE);

        long result = db.insert(WINNERS_TABLE, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Cursor getWinnerData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + WINNERS_TABLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + PHOTO_TABLE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemID(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + PHOTO_COLUMN1 + " FROM " + PHOTO_TABLE +
                " WHERE " + PHOTO_COLUMN2 + " = '" + name + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void updateName(String newName, int id, String oldName){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + PHOTO_TABLE + " SET " + PHOTO_COLUMN2 +
                " = '" + newName + "' WHERE " + PHOTO_COLUMN1 + " = '" + id + "'" +
                " AND " + PHOTO_COLUMN2 + " = '" + oldName + "'";
        Log.d(TAG, "updateName: query: " + query);
        Log.d(TAG, "updateName: Setting name to " + newName);
        db.execSQL(query);
    }

    public void deleteName(int id, String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + PHOTO_TABLE + " WHERE "
                + PHOTO_COLUMN1 + " = '" + id + "'" +
                " AND " + PHOTO_COLUMN2 + " = '" + name + "'";
        Log.d(TAG, "deleteName: query: " + query);
        Log.d(TAG, "deleteName: Deleting " + name + " from database.");
        db.execSQL(query);
    }
}
