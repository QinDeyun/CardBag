package com.example.activitylifecycle_205801.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "myDatabase.db";
    private static final int DATABASE_VERSION = 1;
    String  IdcardTableCreat= "CREATE TABLE card_id (\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,\n" +
            "    name VARCHAR(50) NOT NULL,\n" +
            "    sex VARCHAR(10) NOT NULL,\n" +
            "    nation VARCHAR(20) NOT NULL,\n" +
            "    birthday VARCHAR(20) NOT NULL,\n" +
            "    address VARCHAR(100) NOT NULL,\n" +
            "    idnum VARCHAR(50) UNIQUE  NOT NULL,\n" +
            "    authority VARCHAR(50) NOT NULL,\n" +
            "    validdate VARCHAR(50) NOT NULL,\n" +
            "    firsturl VARCHAR(100) NOT NULL,\n" +
            "    secondurl VARCHAR(100) NOT NULL\n" +
            ");";
    String  BankcardTableCreat= "CREATE TABLE card_bank (\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    number TEXT UNIQUE,\n" +
            "    issuer TEXT,\n" +
            "    organization TEXT,\n" +
            "    type TEXT,\n" +
            "    fronturl TEXT\n" +
            ");";
    String  schoolcardTableCreat= "CREATE TABLE card_student (\n" +
            "    id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
            "    sname TEXT,\n" +
            "    snum TEXT UNIQUE,\n" +
            "    sclass TEXT,\n" +
            "    scollege TEXT,\n" +
            "    fronturl TEXT\n" +
            ");";


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(this.BankcardTableCreat);
        sqLiteDatabase.execSQL(this.IdcardTableCreat);
        sqLiteDatabase.execSQL(this.schoolcardTableCreat);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}
