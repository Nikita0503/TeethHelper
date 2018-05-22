package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHepler;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.data.defaultObject;

import java.util.ArrayList;

/**
 * Created by Nikita on 16.04.2018.
 */

public class VisitsTable implements defaultTable {
    String[] tagNames;
    DBHepler dbHepler;
    SQLiteDatabase db;
    Context context;
    ListPresenter listPresenter;

    public VisitsTable(Context context){
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public VisitsTable(Context context, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.context = context;
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    @Override
    public void fetchData() {
        ArrayList<Visit> visits = getVisits();
        tagNames = context.getResources().getStringArray(R.array.visitTagNames);
        listPresenter.prepareDataByVisits(visits, tagNames);
    }

    @Override
    public void addRow(defaultObject defaultObject) {
        Visit visit = (Visit) defaultObject;
        Log.d("Добавление", visit.patient + visit.date + visit.service);
        ContentValues cv = new ContentValues();
        cv.put("patient", visit.patient);
        cv.put("date", visit.date);
        cv.put("service", visit.service);
        long rowID = db.insert("visits", null, cv);
        Log.d("ADDED: ", "id = " + rowID);
        listPresenter.sendMessage("new visit was added successful!"); //через presenter
    }

    @Override
    public boolean deleteRow(defaultObject defaultObject) {
        Visit visit = (Visit) defaultObject;
        String patient = visit.patient;
        String date = visit.date;
        String service = visit.service;
        String[] values = new String[]{patient, date, service};
        long dltCount = db.delete("visits","patient=? and date=? and service=?", values);
        Log.d("DELETED: ", "deleted " + dltCount + " rows");
        listPresenter.updateDataByTableId(4);
        listPresenter.sendMessage("visit was deleted successfully!");
        return true;
    }

    @Override
    public boolean updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject) {
        Visit oldVisit = (Visit) oldDefaultObject;
        Visit newVisit = (Visit) newDefaultObject;
        ContentValues cv = new ContentValues();
        cv.put("patient", newVisit.patient);
        cv.put("date", newVisit.date);
        cv.put("service", newVisit.service);
        String patient = oldVisit.patient;
        String date = oldVisit.date;
        String service = oldVisit.service;
        Log.d("OLD DATE: ", oldVisit.patient + " " + oldVisit.date + " " + oldVisit.service);
        Log.d("NEW DATE: ", newVisit.patient + " " + newVisit.date + " " + newVisit.service);
        String[] values = new String[]{patient, date, service};
        int updCount = db.update("visits", cv, "patient=? and date=? and service=?", values);
        Log.d("UPDATED: ", "updated " + updCount + " rows ");
        listPresenter.updateDataByTableId(4);
        listPresenter.sendMessage("visit was updated!");
        return true;
    }

    public ArrayList<Visit> getVisits(){
        ArrayList<Visit> visits = new ArrayList<Visit>();
        Cursor c = db.query("visits", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int patientColIndex = c.getColumnIndex("patient");
            int dateColIndex = c.getColumnIndex("date");
            int serviceColIndex = c.getColumnIndex("service");
            do {
                visits.add(new Visit(c.getString(patientColIndex), c.getString(dateColIndex), c.getString(serviceColIndex)));
                Log.d("VISIT: ",
                        "patient = "
                                + c.getString(patientColIndex) + ", doctor = "
                                + c.getString(dateColIndex) + ", service = "
                                + c.getString(serviceColIndex));
            } while (c.moveToNext());
        } else
            Log.d("VISIT: ", "0 rows");
        c.close();
        return visits;
    }
}
