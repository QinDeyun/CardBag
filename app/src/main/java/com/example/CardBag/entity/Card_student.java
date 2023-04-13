package com.example.CardBag.entity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.CardBag.util.Conduct_bitmap;

public class Card_student {
    String sname;
    String snum;
    String sclass;
    String scollege;
    String fronturl;

    @Override
    public String toString() {
        return "Card_student{" +
                "sname='" + sname + '\'' +
                ", snum='" + snum + '\'' +
                ", sclass='" + sclass + '\'' +
                ", scollege='" + scollege + '\'' +
                ", fronturl='" + fronturl + '\'' +
                '}';
    }





    public Card_student() {
    }

    public Card_student(String sname, String snum, String sclass, String scollege, String fronturl) {
        this.sname = sname;
        this.snum = snum;
        this.sclass = sclass;
        this.scollege = scollege;
        this.fronturl = fronturl;
    }

    public String getFronturl() {
        return fronturl;
    }

    public void setFronturl(String fronturl) {
        this.fronturl = fronturl;
    }
    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSnum() {
        return snum;
    }

    public void setSnum(String snum) {
        this.snum = snum;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getScollege() {
        return scollege;
    }

    public void setScollege(String scollege) {
        this.scollege = scollege;
    }

    //对象插入数据库
    public static void insertToDatabase(SQLiteDatabase db, String tableName, Card_student card_student){
        ContentValues values = new ContentValues();
        values.put("sname", card_student.getSname());
        values.put("snum", card_student.getSnum());
        values.put("sclass", card_student.getSclass());
        values.put("scollege", card_student.getScollege());
        values.put("fronturl", card_student.getFronturl());
        db.insert(tableName, null, values);
    }

    //查询所有,返回对象数组
    @SuppressLint("Range")//忽略报错
    public static Card_student[] queryAllFromDatabase(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.query(tableName, null, null, null, null, null, null);
        Card_student[] cardStudents = new Card_student[cursor.getCount()];
        int index = 0;
        while(cursor.moveToNext()){
            String sname = cursor.getString(cursor.getColumnIndex("sname"));
            String snum = cursor.getString(cursor.getColumnIndex("snum"));
            String sclass = cursor.getString(cursor.getColumnIndex("sclass"));
            String scollege = cursor.getString(cursor.getColumnIndex("scollege"));
            String fronturl = cursor.getString(cursor.getColumnIndex("fronturl"));
            cardStudents[index++] = new Card_student(sname, snum, sclass, scollege, fronturl);
        }
        cursor.close();
        return cardStudents;
    }


    //按学号查询
    @SuppressLint("Range")//忽略报错
    public static Card_student queryCardByIdnum(SQLiteDatabase db, String tableName, String snum){
        Cursor cursor = db.query(tableName, null, "snum=?", new String[]{snum}, null, null, null);
        Card_student cardStudent = null;
        if(cursor.moveToFirst()){
            String sname = cursor.getString(cursor.getColumnIndex("sname"));
            String sclass = cursor.getString(cursor.getColumnIndex("sclass"));
            String scollege = cursor.getString(cursor.getColumnIndex("scollege"));
            String fronturl = cursor.getString(cursor.getColumnIndex("fronturl"));
            cardStudent = new Card_student(sname, snum, sclass, scollege, fronturl);
        }
        cursor.close();
        return cardStudent;
    }


    //按学号删除
    public static void deleteFromDatabase(SQLiteDatabase db, String tableName, String snum) {
        db.delete(tableName, "snum=?", new String[]{snum});
    }
    //按学号更新
    public static void updateDatabase(SQLiteDatabase db, String tableName, Card_student card_student) {
        ContentValues values = new ContentValues();
        values.put("sname", card_student.getSname());
        values.put("sclass", card_student.getSclass());
        values.put("scollege", card_student.getScollege());
        values.put("fronturl", card_student.getFronturl());

        db.update(tableName, values, "snum=?", new String[]{card_student.getSnum()});
    }

    ////删除卡片信息，和图片
    public static void delCard(SQLiteDatabase db, String tableName, Card_student card){
        deleteFromDatabase(db,tableName,card.snum);
        Conduct_bitmap.deleteFileFromInternalStorage(card.fronturl);


    }
}
