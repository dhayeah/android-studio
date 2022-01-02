package com.example.database1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DataBaseHelper extends SQLiteOpenHelper {

    public static final String dbname="productDb5.db";
    public static final String tablename="sampledatabase";
    public static final String col1="productId";
    public static final String col2="productName";
    public static final String col3="productBrand";
    public static final String col4="Description";
    public static final String col5="productPrice";


    public DataBaseHelper(@Nullable Context context) {
        super(context, dbname, null, 1);
        SQLiteDatabase db =this.getWritableDatabase();
    }

    public DataBaseHelper(@Nullable Context context,String new_dbname) {
        super(context, new_dbname, null, 1);
        SQLiteDatabase db =this.getWritableDatabase();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+tablename+" (PRODUCTID TEXT PRIMARY KEY ,PRODUCTNAME TEXT ,PRODUCTBRAND TEXT , DESCRIPTION TEXT ,PRODUCTPRICE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+tablename);
        onCreate(db);
    }
    public boolean insertData(String id,String name,String brand,String  desp,String price){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col1,id);
        contentValues.put(col2,name);
        contentValues.put(col3,brand);
        contentValues.put(col4,desp);
        contentValues.put(col5,price);
        long result = db.insert(tablename,null,contentValues);
        return result != -1;
    }
    public Cursor getAlldata(){
        SQLiteDatabase db =this.getWritableDatabase();
        return db.rawQuery("select * from "+tablename ,null);
    }
//
//    public Cursor getdata(String id){
//        SQLiteDatabase db =this.getWritableDatabase();
//        return db.rawQuery("select * from "+tablename +" where PRODUCTBRAND = '"+id+"'",null);
//    }

    //    public Cursor getdata(String id){
//        SQLiteDatabase db =this.getWritableDatabase();
//        return db.rawQuery("select * from "+tablename+" where PRODUCTID =  id",null);
//    }
    public void updatedata(String id,String new_price){
        SQLiteDatabase db =this.getWritableDatabase();
        String query="UPDATE "+tablename+" SET PRODUCTPRICE = "+new_price+" where PRODUCTID = '"+id+"'";
        db.execSQL(query);
    }
    public void deleterow(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM "+tablename+" WHERE PRODUCTID = '"+id+"'");
    }
}
