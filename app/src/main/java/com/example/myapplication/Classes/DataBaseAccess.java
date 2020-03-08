package com.example.myapplication.Classes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DataBaseAccess instance;
    private Cursor c = null;

    private DataBaseAccess(Context context) {
        this.openHelper = new DataBaseOpenHelper(context);
    }

    public static DataBaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DataBaseAccess(context);
        }
        return instance;
    }

    public void open() {
        this.db = openHelper.getWritableDatabase();
    }

    public void close() {
        if (db != null) {
            this.db.close();
        }
    }

    public String getAddress() {
        c = db.rawQuery("select Text from Images where Number = 123", new String[]{});
        StringBuilder buffer = new StringBuilder();
        while(c.moveToNext()) {
            String address = c.getString(0);
            buffer.append("").append(address);
        }
        return buffer.toString();
    }
}
