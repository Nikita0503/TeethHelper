package com.example.nikita.teethhelper.getters;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHepler;
import com.example.nikita.teethhelper.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.ListActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.PatientDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.ServiceDataActivity;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.defaultObject;
import com.example.nikita.teethhelper.defaultGetter;

import java.util.ArrayList;

/**
 * Created by Nikita on 15.04.2018.
 */

public class PatientsGetter implements defaultGetter {
    String[] tagNames;

    DBHepler dbHepler;
    SQLiteDatabase db;
    ListActivity listActivity;
    ListPresenter listPresenter;

    public PatientsGetter(Context context){//context
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public PatientsGetter(ListActivity listActivity, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.listActivity = listActivity;
        dbHepler = new DBHepler(listActivity);
        db = dbHepler.getWritableDatabase();
    }

    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<String>();
        Cursor c = db.query("patients", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            do {
                names.add(c.getString(nameColIndex));
            } while (c.moveToNext());
        }
        return names;
    }

    public ArrayList<Patient> getPatients(){
        ArrayList<Patient> patients = new ArrayList<Patient>();
        Cursor c = db.query("patients", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int idColIndex = c.getColumnIndex("code");
            int nameColIndex = c.getColumnIndex("name");
            int passportColIndex = c.getColumnIndex("passport");
            int addressColIndex = c.getColumnIndex("address");
            int diseaseColIndex = c.getColumnIndex("disease");
            do {
                patients.add(new Patient(c.getInt(idColIndex),  c.getString(nameColIndex), c.getString(passportColIndex), c.getString(addressColIndex), c.getString(diseaseColIndex)));
                Log.d("LOG",
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(nameColIndex) + ", passport = "
                                + c.getString(passportColIndex) + ", address = "
                                + c.getString(addressColIndex) + ", disease = "
                                + c.getString(diseaseColIndex));
            } while (c.moveToNext());
        } else
            Log.d("LOG", "0 rows");
        c.close();
        return patients;
    }

    @Override
    public void fetchData(){
        tagNames = listActivity.getResources().getStringArray(R.array.patientTagNames);
        ArrayList<Patient> patients = getPatients();
        listPresenter.prepareDataByPatients(patients, tagNames);
    }

    @Override
    public void addRow(defaultObject defaultObject) {
        Patient patient = (Patient) defaultObject;
        Log.d("123", patient.name);
        ContentValues cv = new ContentValues();
        cv.put("name", patient.name);
        cv.put("passport", patient.passport);
        cv.put("address", patient.address);
        cv.put("disease", patient.disease);
        // вставляем запись и получаем ее ID
        long rowID = db.insert("patients", null, cv);
        listActivity.showResult("new patient was added successful!");
    }

    @Override
    public boolean deleteRow(defaultObject defaultObject) {
        Patient patient = (Patient) defaultObject;
        String code = String.valueOf(patient.code);
        String name = patient.name;
        String passport = patient.passport;
        String address = patient.address;
        String disease = patient.disease;
        //String specialization = patient.specialization;
        //String experience = String.valueOf(doctor.experience);
        //String berth = doctor.berth;
        String[] values = new String[]{code, name, passport, address, disease};
        db.delete("patients","code=? and name=? and passport=? and address=? and disease=?", values);//через listPresenter
        listPresenter.updateDataByTableId(2);
        listActivity.showResult("patient was deleted successfully!");//ЗАСУНУТЬ ЭТО К ПРЕЗЕНТЕРУ!!!!
        return true;
    }

    @Override
    public boolean updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject) {
        Patient oldPatient = (Patient) oldDefaultObject;
        Patient newPatient = (Patient) newDefaultObject;
        ContentValues cv = new ContentValues();
        cv.put("code", oldPatient.code);
        cv.put("name", newPatient.name);
        cv.put("passport", newPatient.passport);
        cv.put("address", newPatient.address);
        cv.put("disease", newPatient.disease);

        String code = String.valueOf(oldPatient.code);
        String name = oldPatient.name;
        String passport = oldPatient.passport;
        String address = oldPatient.address;
        String disease = oldPatient.disease;
        Log.d("123", oldPatient.code + " " + oldPatient.name + " " + oldPatient.passport + " " + oldPatient.address + " " + oldPatient.disease);
        Log.d("123", newPatient.code + " " + newPatient.name + " " + newPatient.passport + " " + newPatient.address + " " + newPatient.disease);
        String[] values = new String[]{code, name, passport, address, disease};
        int updCount = db.update("patients", cv, "code=? and name=? and passport=? and address=? and disease=?", values);
        Log.d("asd", updCount+"!!");
        listPresenter.updateDataByTableId(2);
        listActivity.showResult("patient was updated!");
        return true;
    }
}
