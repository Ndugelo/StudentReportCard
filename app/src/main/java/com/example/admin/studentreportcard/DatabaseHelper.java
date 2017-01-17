package com.example.admin.studentreportcard;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.StaleDataException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import static android.R.attr.version;

/**
 * Created by admin on 2016/11/24.
 */

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "report.db";
    public static final String TABLE_NAME = "report";

    public static final String COL_1 = "id";
    public static final String COL_2 = "name";
    public static final String COL_3 = "surname";
    public static final String COL_4 = "student_number";
    public static final String COL_5 = "subject";
    public static final String COL_6 = "marks";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +
                        "(id integer primary key, name text, surname text,student_number integer, subject text, marks integer)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name, String surname, String number, String subject, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("surname", surname);
        cv.put("Student_number", number);
        cv.put("subject", subject);
        cv.put("marks", marks);

        db.insert(TABLE_NAME, null, cv);
        return true;
    }

    public Cursor getData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where " + COL_1 +" = "+id+"", null);
        return c;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getWritableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }

    public boolean updateData(Integer id, String name,String surname, String number, String subject, String marks){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("name", name);
        cv.put("surname", surname);
        cv.put("Student_number", number);
        cv.put("subject", subject);
        cv.put("marks", marks);

        db.update(TABLE_NAME, cv, "id = ? ", new String[]{Integer.toString(id)});
        return  true;
    }

    public Integer deleteData(Integer id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "id = ? ", new String[] {Integer.toString(id)});
    }

    public ArrayList<String> getAllData(){
        ArrayList<String> arrayList = new ArrayList<String>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from " + TABLE_NAME +"", null);
        c.moveToFirst();

        while (c.isAfterLast() == false){
            arrayList.add(c.getString(c.getColumnIndex(COL_2)));
            c.moveToNext();
        }
        return arrayList;
    }
}
