package com.sadwyn.institute.birthdayreminder.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.File;

public class DBHelper extends SQLiteOpenHelper {

    public static final String PEOPLE_TABLE = "people";
    private static final String CREATE_TABLE_SQL_QUERY = "create table " + PEOPLE_TABLE + " " +
            "(" + "id integer primary key autoincrement," + "firstName text," + "lastName text,"
            + "patronymic text," + "birthDate text," + "phone text" + ");";
    public static final String PEOPLE_DB = "reminderDb";

    public DBHelper(Context context) {
        super(context, PEOPLE_DB, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(DBHelper.class.getSimpleName(), "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL(CREATE_TABLE_SQL_QUERY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean doesDatabaseExist(Context context, String dbName) {
        File dbFile = context.getDatabasePath(dbName);
        return dbFile.exists();
    }
}
