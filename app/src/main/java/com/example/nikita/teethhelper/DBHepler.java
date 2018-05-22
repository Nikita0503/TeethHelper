package com.example.nikita.teethhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Patient;

import java.util.List;

/**
 * Created by Nikita on 27.03.2018.
 */

public class DBHepler extends SQLiteOpenHelper {
    public DBHepler(Context context) {
        super(context, "Stomatology", null, 1);
    }                                                                   //ПОФИКСИТЬ ХЕРНЮ С ЧИСЛОВЫМИ ЗНАЧЕНИЯМИ ИЗ ЕДИТТЕКСТОВ У ДОКТОРА
    @Override
    public void onCreate (SQLiteDatabase db){
        Log.d("LOG", "--- onCreate database ---");
        // создаем таблицу с полями
        db.execSQL("create table patients ("
                + "code integer primary key autoincrement,"
                + "name text,"
                + "passport text,"
                + "address text,"
                + "disease text" + ");");

        db.execSQL("create table doctors ("
                + "code integer primary key autoincrement,"
                + "name text,"
                + "passport text,"
                + "address text,"
                + "specialization text,"
                + "experience integer,"
                + "berth text"+ ");");

        db.execSQL("create table service ("
                + "patient text,"
                + "doctor text,"
                + "date text,"
                + "cost real,"
                + "manipulation text" + ");");

        db.execSQL("create table renders ("
                + "patient text,"
                + "doctor text,"
                + "sum real,"
                + "date text,"
                + "service text" + ");");

        db.execSQL("create table visits ("
                + "patient text,"
                + "date text,"
                + "service text" + ");");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
