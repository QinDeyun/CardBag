package com.example.activitylifecycle_205801.entity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Card_ID {
    String name;
    String sex;
    String nation;
    String birthday;
    String address;
    String idnum;
    String authority;
    String validdate;
    String firsturl;
    String secondurl;

    public Card_ID(String name, String sex, String nation, String birthday, String address, String idnum, String authority, String validdate, String firsturl, String secondurl) {
        this.name = name;
        this.sex = sex;
        this.nation = nation;
        this.birthday = birthday;
        this.address = address;
        this.idnum = idnum;
        this.authority = authority;
        this.validdate = validdate;
        this.firsturl = firsturl;
        this.secondurl = secondurl;
    }

    public Card_ID() {
    }

    @Override
    public String toString() {
        return "Card_ID{" +
                "name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", nation='" + nation + '\'' +
                ", birthday='" + birthday + '\'' +
                ", address='" + address + '\'' +
                ", idnum='" + idnum + '\'' +
                ", authority='" + authority + '\'' +
                ", validdate='" + validdate + '\'' +
                ", firsturl='" + firsturl + '\'' +
                ", secondurl='" + secondurl + '\'' +
                '}';
    }

    public String getFirsturl() {
        return firsturl;
    }

    public void setFirsturl(String firsturl) {
        this.firsturl = firsturl;
    }

    public String getSecondurl() {
        return secondurl;
    }

    public void setSecondurl(String secondurl) {
        this.secondurl = secondurl;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdnum() {
        return idnum;
    }

    public void setIdnum(String idnum) {
        this.idnum = idnum;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public String getValiddate() {
        return validdate;
    }

    public void setValiddate(String validdate) {
        this.validdate = validdate;
    }



    //对象插入数据库
    public static void insertToDatabase( SQLiteDatabase db,String tableName,Card_ID card_id){
        ContentValues values = new ContentValues();
        values.put("name", card_id.getName());
        values.put("sex", card_id.getSex());
        values.put("nation", card_id.getNation());
        values.put("birthday", card_id.getBirthday());
        values.put("address", card_id.getAddress());
        values.put("idnum", card_id.getIdnum());
        values.put("authority", card_id.getAuthority());
        values.put("validdate", card_id.getValiddate());
        values.put("firsturl", card_id.getFirsturl());
        values.put("secondurl", card_id.getSecondurl());
        db.insert(tableName, null, values);
    }
    //按身份证号从数据库删除
    public static void deleteFromDatabase(SQLiteDatabase db, String tableName, String idnum) {
        String whereClause = "idnum=?";
        String[] whereArgs = { idnum };
        db.delete(tableName, whereClause, whereArgs);
    }
    //查询所有返回对象数组
    @SuppressLint("Range")//忽略报错
    public static Card_ID[] queryAllFromDatabase(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT * FROM " + tableName, null);
        ArrayList<Card_ID> cardList = new ArrayList<>();
        while (cursor.moveToNext()) {
            Card_ID card = new Card_ID();
            card.setName(cursor.getString(cursor.getColumnIndex("name")));
            card.setSex(cursor.getString(cursor.getColumnIndex("sex")));
            card.setNation(cursor.getString(cursor.getColumnIndex("nation")));
            card.setBirthday(cursor.getString(cursor.getColumnIndex("birthday")));
            card.setAddress(cursor.getString(cursor.getColumnIndex("address")));
            card.setIdnum(cursor.getString(cursor.getColumnIndex("idnum")));
            card.setAuthority(cursor.getString(cursor.getColumnIndex("authority")));
            card.setValiddate(cursor.getString(cursor.getColumnIndex("validdate")));
            card.setFirsturl(cursor.getString(cursor.getColumnIndex("firsturl")));
            card.setSecondurl(cursor.getString(cursor.getColumnIndex("secondurl")));
            cardList.add(card);
        }
        cursor.close();
        return cardList.toArray(new Card_ID[0]);
    }

    //根据idnum查询
    @SuppressLint("Range")//忽略报错
    public static   Card_ID queryCardByIdnum(SQLiteDatabase db, String tableName, String idnum){
        Card_ID card = null;
        Cursor cursor = db.query(tableName, null, "idnum=?", new String[]{idnum}, null, null, null);
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String sex = cursor.getString(cursor.getColumnIndex("sex"));
            String nation = cursor.getString(cursor.getColumnIndex("nation"));
            String birthday = cursor.getString(cursor.getColumnIndex("birthday"));
            String address = cursor.getString(cursor.getColumnIndex("address"));
            String authority = cursor.getString(cursor.getColumnIndex("authority"));
            String validDate = cursor.getString(cursor.getColumnIndex("validdate"));
            String firstUrl = cursor.getString(cursor.getColumnIndex("firsturl"));
            String secondUrl = cursor.getString(cursor.getColumnIndex("secondurl"));

            card = new Card_ID(name, sex, nation, birthday, address, idnum, authority, validDate, firstUrl, secondUrl);
        }
        cursor.close();
        return card;
    }

    //修改数据库
    public static void updateToDatabase(SQLiteDatabase db, String tableName, Card_ID card) {
        ContentValues values = new ContentValues();
        values.put("name", card.getName());
        values.put("sex", card.getSex());
        values.put("nation", card.getNation());
        values.put("birthday", card.getBirthday());
        values.put("address", card.getAddress());
        values.put("idnum", card.getIdnum());
        values.put("authority", card.getAuthority());
        values.put("validdate", card.getValiddate());
        values.put("firsturl", card.getFirsturl());
        values.put("secondurl", card.getSecondurl());
        String whereClause = "idnum=?";
        String[] whereArgs = {card.getIdnum()};
        db.update(tableName, values, whereClause, whereArgs);
    }




}
