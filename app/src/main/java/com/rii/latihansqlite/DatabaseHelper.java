package com.rii.latihansqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "StudentDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_STUDENTS = "students";
    private static final String KEY_ID = "id";
    private static final String KEY_FIRSTNAME = "name";

    private static final String CREATE_TABLE_STUDENTS = "CREATE TABLE " +
            TABLE_STUDENTS + "(" + KEY_ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_FIRSTNAME + ");";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        Log.d("table", CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE_STUDENTS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS '" + TABLE_STUDENTS + "'");
        onCreate(sqLiteDatabase);
    }

    public long addStudentDetail(String student) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        //membuat isi values
        ContentValues values = new ContentValues();
        values.put(KEY_FIRSTNAME, student);

        //memasukkan isi values kedalam tabel student
        long insert = sqLiteDatabase.insert(TABLE_STUDENTS, null, values);

        return insert;
    }

    @SuppressLint("Range")
    public ArrayList<String> getAllStudentsList() {
        ArrayList<String> studentsArrayList = new ArrayList<String>();
        String name = "";
        String selectQuery = "SELECT * FROM " + TABLE_STUDENTS;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);

        //looping semua data yang ada di table student dan memasukkan kedalam list
        if(cursor.moveToFirst()) {
            do {
                name = cursor.getString(cursor.getColumnIndex(KEY_FIRSTNAME));
                //memasukkan data kedalam list studentArrayList
                studentsArrayList.add(name);
            } while (cursor.moveToNext());
            Log.d("array", studentsArrayList.toString());
        }
        return studentsArrayList;
    }

    public void truncateStudentlist()
    {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_STUDENTS);
    }
}
