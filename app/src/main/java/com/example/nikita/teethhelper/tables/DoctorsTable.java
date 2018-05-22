package com.example.nikita.teethhelper.tables;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHepler;
import com.example.nikita.teethhelper.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.ListActivity;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.defaultObject;
import com.example.nikita.teethhelper.defaultTable;

import java.util.ArrayList;

/**
 * Created by Nikita on 10.04.2018.
 */

public class DoctorsTable implements defaultTable {
    String[] tagNames;

    DBHepler dbHepler;
    SQLiteDatabase db;
    ListActivity listActivity;
    ListPresenter listPresenter;

    public DoctorsTable(Context context){
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public DoctorsTable(ListActivity listActivity, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.listActivity = listActivity;
        dbHepler = new DBHepler(listActivity);
        db = dbHepler.getWritableDatabase();
    }

    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<String>();
        Cursor c = db.query("doctors", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            do {
                names.add(c.getString(nameColIndex));
            } while (c.moveToNext());
        }
        return names;
    }

    public ArrayList<Doctor> getDoctors(){
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        Cursor c = db.query("doctors", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("code");
            int nameColIndex = c.getColumnIndex("name");
            int passportColIndex = c.getColumnIndex("passport");
            int addressColIndex = c.getColumnIndex("address");
            int specializationColIndex = c.getColumnIndex("specialization");
            int experienceColIndex = c.getColumnIndex("experience");
            int berthColIndex = c.getColumnIndex("berth");
            do {
                doctors.add(new Doctor(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(passportColIndex), c.getString(addressColIndex), c.getString(specializationColIndex), c.getInt(experienceColIndex), c.getString(berthColIndex)));
                Log.d("LOG",
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(nameColIndex) + ", passport = "
                                + c.getString(passportColIndex) + ", address = "
                                + c.getString(addressColIndex) + ", specialization = "
                                + c.getString(specializationColIndex) + ", experience = "
                                + c.getString(experienceColIndex) + ", berth = "
                                + c.getString(berthColIndex));
            } while (c.moveToNext());
        } else
            Log.d("LOG", "0 rows");
        c.close();
        return doctors;
    }

    @Override
    public void fetchData() { //формирование адаптеров можно делать в View??
        ArrayList doctors = getDoctors();
        tagNames = listActivity.getResources().getStringArray(R.array.doctorTagNames);

        listPresenter.prepareDataByDoctors(doctors, tagNames);
    }

    @Override  //ПОФИКСИТЬ ХЕРНЮ С ЧИСЛОВЫМИ ЗНАЧЕНИЯМИ ИЗ ЕДИТТЕКСТОВ У ДОКТОРА
    public void addRow(defaultObject defaultObject) {
        Doctor doctor = (Doctor) defaultObject;
        Log.d("123", doctor.name);
        ContentValues cv = new ContentValues();
        cv.put("name", doctor.name);
        cv.put("passport", doctor.passport);
        cv.put("address", doctor.address);
        cv.put("specialization", doctor.specialization);
        cv.put("experience", doctor.experience);
        cv.put("berth", doctor.berth);
        // вставляем запись и получаем ее ID
        long rowID = db.insert("doctors", null, cv);
        listActivity.showResult("new doctor was added successful!");
    }

    @Override
    public boolean deleteRow(defaultObject defaultObject) {  //ПОФИКСИТЬ ХЕРНЮ С ЧИСЛОВЫМИ ЗНАЧЕНИЯМИ ИЗ ЕДИТТЕКСТОВ У ДОКТОРА
        Doctor doctor = (Doctor) defaultObject;
        String code = String.valueOf(doctor.code);
        String name = doctor.name;
        String passport = doctor.passport;
        String address = doctor.address;
        String specialization = doctor.specialization;
        String experience = String.valueOf(doctor.experience);
        String berth = doctor.berth;
        String[] values = new String[]{code, name, passport, address, specialization, experience, berth};
        db.delete("doctors","code=? and name=? and passport=? and address = ? and specialization=? and experience=? and berth=?", values);//через listPresenter
        listPresenter.updateDataByTableId(1);
        listActivity.showResult("doctor was deleted successfully!");
        return true;
    }

    @Override
    public boolean updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject) {
        Doctor oldDoctor = (Doctor) oldDefaultObject;
        Doctor newDoctor = (Doctor) newDefaultObject;
        ContentValues cv = new ContentValues();
        cv.put("code", oldDoctor.code);
        cv.put("name", newDoctor.name);
        cv.put("passport", newDoctor.passport);
        cv.put("address", newDoctor.address);
        cv.put("specialization", newDoctor.specialization);
        cv.put("experience", newDoctor.experience);
        cv.put("berth", newDoctor.berth);

        String code = String.valueOf(oldDoctor.code);
        String name = oldDoctor.name;
        String passport = oldDoctor.passport;
        String address = oldDoctor.address;
        String specialization = oldDoctor.specialization;
        String experience = String.valueOf(oldDoctor.experience);
        String berth = oldDoctor.berth;
        Log.d("123", oldDoctor.code + " " + oldDoctor.name + " " + oldDoctor.passport + " " + oldDoctor.address + " " + oldDoctor.specialization + " " + oldDoctor.experience + " " + oldDoctor.berth);
        Log.d("123", newDoctor.code + " " + newDoctor.name + " " + newDoctor.passport + " " + newDoctor.address + " " + newDoctor.specialization + " " + newDoctor.experience + " " + newDoctor.berth);
        String[] values = new String[]{code, name, passport, address, specialization, experience, berth};
        int updCount = db.update("doctors", cv, "code=? and name=? and passport=? and address=? and specialization=? and experience=? and berth=?", values);
        Log.d("asd", updCount+"!!");
        listPresenter.updateDataByTableId(1);
        listActivity.showResult("doctor was updated!");
        return true;
    }


}
