package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHepler;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.defaultObject;

import java.util.ArrayList;

/**
 * Created by Nikita on 15.04.2018.
 */

public class PatientsTable implements defaultTable {
    String[] tagNames;
    DBHepler dbHepler;
    SQLiteDatabase db;
    Context context;
    ListPresenter listPresenter;

    public PatientsTable(Context context){
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public PatientsTable(Context context, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.context = context;
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    @Override
    public void fetchData(){
        tagNames = context.getResources().getStringArray(R.array.patientTagNames);
        ArrayList<Patient> patients = getPatients();
        listPresenter.prepareDataByPatients(patients, tagNames);
    }

    @Override
    public void addRow(defaultObject defaultObject) {
        Patient patient = (Patient) defaultObject;
        ContentValues cv = new ContentValues();
        cv.put("name", patient.name);
        cv.put("passport", patient.passport);
        cv.put("address", patient.address);
        cv.put("disease", patient.disease);
        long rowID = db.insert("patients", null, cv);
        Log.d("ADDED: ", "id = " + rowID);
        listPresenter.sendMessage("new patient was added successful!");
    }

    @Override
    public void deleteRow(defaultObject defaultObject) {
        Patient patient = (Patient) defaultObject;
        String code = String.valueOf(patient.code);
        String name = patient.name;
        String passport = patient.passport;
        String address = patient.address;
        String disease = patient.disease;
        String[] values = new String[]{code, name, passport, address, disease};
        long dltCount = db.delete("patients","code=? and name=? and passport=? and address=? and disease=?", values);
        Log.d("DELETED: ", "deleted " + dltCount + " rows");
        listPresenter.updateDataByTableId(2);
        listPresenter.sendMessage("patient was deleted successfully!");
    }

    @Override
    public void updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject) {
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
        Log.d("OLD DATA: ", oldPatient.code + " " + oldPatient.name + " " + oldPatient.passport + " " + oldPatient.address + " " + oldPatient.disease);
        Log.d("NEW DATA: ", newPatient.code + " " + newPatient.name + " " + newPatient.passport + " " + newPatient.address + " " + newPatient.disease);
        String[] values = new String[]{code, name, passport, address, disease};
        int updCount = db.update("patients", cv, "code=? and name=? and passport=? and address=? and disease=?", values);
        Log.d("UPDATE: ", "updated " + updCount+" rows ");
        listPresenter.updateDataByTableId(2);
        listPresenter.sendMessage("patient was updated!");
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
                Log.d("PATIENT: ",
                        "ID = " + c.getInt(idColIndex) + ", name = "
                                + c.getString(nameColIndex) + ", passport = "
                                + c.getString(passportColIndex) + ", address = "
                                + c.getString(addressColIndex) + ", disease = "
                                + c.getString(diseaseColIndex));
            } while (c.moveToNext());
        } else
            Log.d("PATIENT: ", "0 rows");
        c.close();
        return patients;
    }
}
