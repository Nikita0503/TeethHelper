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
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.data.defaultObject;
import com.example.nikita.teethhelper.defaultTable;

import java.util.ArrayList;

/**
 * Created by Nikita on 16.04.2018.
 */

public class VisitsTable implements defaultTable {
    String[] tagNames;
    DBHepler dbHepler;
    SQLiteDatabase db;
    ListActivity listActivity;
    ListPresenter listPresenter;

    public VisitsTable(Context context){//context
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public VisitsTable(ListActivity listActivity, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.listActivity = listActivity;
        dbHepler = new DBHepler(listActivity);
        db = dbHepler.getWritableDatabase();
    }

    public ArrayList<Visit> getVisits(){
        ArrayList<Visit> visits = new ArrayList<Visit>();
        Cursor c = db.query("visits", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int patientColIndex = c.getColumnIndex("patient");
            int dateColIndex = c.getColumnIndex("date");
            int serviceColIndex = c.getColumnIndex("service");
            do {
                visits.add(new Visit(c.getString(patientColIndex), c.getString(dateColIndex), c.getString(serviceColIndex)));
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("sdf",
                        "patient = "
                                + c.getString(patientColIndex) + ", doctor = "
                                + c.getString(dateColIndex) + ", service = "
                                + c.getString(serviceColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("LOG", "0 rows");
        c.close();
        return visits;
    }

    @Override
    public void fetchData() {
        ArrayList<Visit> visits = getVisits();
        tagNames = listActivity.getResources().getStringArray(R.array.visitTagNames);
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
        // вставляем запись и получаем ее ID
        long rowID = db.insert("visits", null, cv);
        listActivity.showResult("new visit was added successful!"); //через presenter
    }

    @Override
    public boolean deleteRow(defaultObject defaultObject) {
        Visit visit = (Visit) defaultObject;
        String patient = visit.patient;
        String date = visit.date;
        String service = visit.service;
        String[] values = new String[]{patient, date, service};
        db.delete("visits","patient=? and date=? and service=?", values);//через listPresenter
        listPresenter.updateDataByTableId(4);
        listActivity.showResult("visit was deleted successfully!");//ЗАСУНУТЬ ЭТО К ПРЕЗЕНТЕРУ!!!!
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
        Log.d("123", oldVisit.patient + " " + oldVisit.date + " " + oldVisit.service);
        Log.d("123", newVisit.patient + " " + newVisit.date + " " + newVisit.service);
        String[] values = new String[]{patient, date, service};
        int updCount = db.update("visits", cv, "patient=? and date=? and service=?", values);
        Log.d("asd", updCount+"!!");
        listPresenter.updateDataByTableId(4);
        listActivity.showResult("visit was updated!");
        return true;
    }
}
