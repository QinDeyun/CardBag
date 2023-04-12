package com.example.activitylifecycle_205801.entity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.activitylifecycle_205801.util.Conduct_bitmap;

public class Card_bank {
        String number;
        String issuer;
        String organization;
        String type;
        String fronturl;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFronturl() {
        return fronturl;
    }

    public void setFronturl(String fronturl) {
        this.fronturl = fronturl;
    }

    @Override
    public String toString() {
        return "Card_bank{" +
                "number='" + number + '\'' +
                ", issuer='" + issuer + '\'' +
                ", organization='" + organization + '\'' +
                ", type='" + type + '\'' +
                ", fronturl='" + fronturl + '\'' +
                '}';
    }


    public Card_bank() {

    }

    public Card_bank(String number, String issuer, String organization, String type, String fronturl) {
        this.number = number;
        this.issuer = issuer;
        this.organization = organization;
        this.type = type;
        this.fronturl = fronturl;
    }

    //将对象插入数据库
    public static void insertToDatabase(SQLiteDatabase db, String tableName, Card_bank card_bank){
        ContentValues values = new ContentValues();
        values.put("number", card_bank.getNumber());
        values.put("issuer", card_bank.getIssuer());
        values.put("organization", card_bank.getOrganization());
        values.put("type", card_bank.getType());
        values.put("fronturl", card_bank.getFronturl());
        db.insert(tableName, null, values);
    }
    //根据number删除记录
    public static void deleteFromDatabase(SQLiteDatabase db, String tableName, String number) {
        String selection = "number = ?";
        String[] selectionArgs = {number};
        db.delete(tableName, selection, selectionArgs);
    }
    //查询所有记录
    public static Card_bank[] getAllRecordsFromDatabase(SQLiteDatabase db, String tableName) {
        String[] projection = { "number", "issuer", "organization", "type", "fronturl" };
        Cursor cursor = db.query(tableName, projection, null, null, null, null, null);

        int numRows = cursor.getCount();
        Card_bank[] result = new Card_bank[numRows];

        int i = 0;
        while (cursor.moveToNext()) {
            String number = cursor.getString(cursor.getColumnIndexOrThrow("number"));
            String issuer = cursor.getString(cursor.getColumnIndexOrThrow("issuer"));
            String organization = cursor.getString(cursor.getColumnIndexOrThrow("organization"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String fronturl = cursor.getString(cursor.getColumnIndexOrThrow("fronturl"));
            result[i] = new Card_bank(number, issuer, organization, type, fronturl);
            i++;
        }

        cursor.close();
        return result;
    }
    //根据number查询
    public static Card_bank getRecordByNumber(SQLiteDatabase db, String tableName, String number) {
        String[] projection = { "number", "issuer", "organization", "type", "fronturl" };
        String selection = "number = ?";
        String[] selectionArgs = { number };
        Cursor cursor = db.query(tableName, projection, selection, selectionArgs, null, null, null);

        Card_bank result = null;
        if (cursor.moveToFirst()) {
            String issuer = cursor.getString(cursor.getColumnIndexOrThrow("issuer"));
            String organization = cursor.getString(cursor.getColumnIndexOrThrow("organization"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            String fronturl = cursor.getString(cursor.getColumnIndexOrThrow("fronturl"));
            result = new Card_bank(number, issuer, organization, type, fronturl);
        }

        cursor.close();
        return result;
    }


    //更新
    public static void updateToDatabase(SQLiteDatabase db, String tableName, Card_bank card_bank) {
        ContentValues values = new ContentValues();
        values.put("issuer", card_bank.getIssuer());
        values.put("organization", card_bank.getOrganization());
        values.put("type", card_bank.getType());
        values.put("fronturl", card_bank.getFronturl());

        String selection = "number = ?";
        String[] selectionArgs = { card_bank.getNumber() };

        db.update(tableName, values, selection, selectionArgs);
    }



    //删除卡片信息，和图片
    public static void delCard(SQLiteDatabase db, String tableName, Card_bank card){
        deleteFromDatabase(db,tableName,card.number);
        Conduct_bitmap.deleteFileFromInternalStorage(card.fronturl);


    }

}
