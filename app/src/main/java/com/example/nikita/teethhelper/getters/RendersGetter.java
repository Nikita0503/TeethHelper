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
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.data.defaultObject;
import com.example.nikita.teethhelper.defaultGetter;

import java.util.ArrayList;

/**
 * Created by Nikita on 16.04.2018.
 */

public class RendersGetter implements defaultGetter {
    String[] tagNames;

    DBHepler dbHepler;
    SQLiteDatabase db;
    ListActivity listActivity;
    ListPresenter listPresenter;

    public RendersGetter(Context context){//context
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public RendersGetter(ListActivity listActivity, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.listActivity = listActivity;
        dbHepler = new DBHepler(listActivity);
        db = dbHepler.getWritableDatabase();
    }

    public ArrayList<Render> getRenders(){
        ArrayList<Render> renders = new ArrayList<Render>();
        Cursor c = db.query("renders", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int patientColIndex = c.getColumnIndex("patient");
            int doctorColIndex = c.getColumnIndex("doctor");
            int sumColIndex = c.getColumnIndex("sum");
            int dateColIndex = c.getColumnIndex("date");
            int serviceColIndex = c.getColumnIndex("service");
            do {
                renders.add(new Render(c.getString(serviceColIndex), c.getString(patientColIndex), c.getString(doctorColIndex), c.getFloat(sumColIndex), c.getString(dateColIndex)));
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("sdf",
                        "patient = "
                                + c.getString(patientColIndex) + ", doctor = "
                                + c.getString(doctorColIndex) + ", sum = "
                                + c.getString(sumColIndex) + ", date = "
                                + c.getString(dateColIndex) + ", service = "
                                + c.getString(serviceColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("LOG", "0 rows");
        c.close();
        return renders;
    }

    @Override
    public void fetchData() {
        ArrayList<Render> renders = getRenders();
        tagNames = listActivity.getResources().getStringArray(R.array.renderTagNames);
        Cursor c = db.query("renders", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int patientColIndex = c.getColumnIndex("patient");
            int doctorColIndex = c.getColumnIndex("doctor");
            int sumColIndex = c.getColumnIndex("sum");
            int dateColIndex = c.getColumnIndex("date");
            int serviceColIndex = c.getColumnIndex("service");
            do {
                renders.add(new Render(c.getString(serviceColIndex), c.getString(patientColIndex), c.getString(doctorColIndex), c.getFloat(sumColIndex), c.getString(dateColIndex)));
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("sdf",
                        "patient = "
                                + c.getString(patientColIndex) + ", doctor = "
                                + c.getString(doctorColIndex) + ", sum = "
                                + c.getString(sumColIndex) + ", date = "
                                + c.getString(dateColIndex) + ", service = "
                                + c.getString(serviceColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("LOG", "0 rows");
        c.close();
        listPresenter.prepareDataByRenders(renders, tagNames);
    }

    @Override
    public void addRow(defaultObject defaultObject) {
        Render render = (Render) defaultObject;
        Log.d("Добавление", render.patient + render.date + render.service);
        ContentValues cv = new ContentValues();
        cv.put("service", render.service);
        cv.put("patient", render.patient);
        cv.put("doctor", render.doctor);
        cv.put("sum", render.sum);
        cv.put("date", render.date);
        // вставляем запись и получаем ее ID
        long rowID = db.insert("renders", null, cv);
        listActivity.showResult("new render was added successful!"); //через presenter
    }

    @Override
    public boolean deleteRow(defaultObject defaultObject) {
        Render render = (Render) defaultObject;
        String service = render.service;
        String patient = render.patient;
        String doctor = render.doctor;
        String sum = String.valueOf(render.sum);
        String date = render.date;
        //String specialization = patient.specialization;
        //String experience = String.valueOf(doctor.experience);
        //String berth = doctor.berth;
        String[] values = new String[]{service, patient, doctor, date};
        Log.d("DEL", sum);
        int i = db.delete("renders","service=? and patient=? and doctor=? and date=?", values);//через listPresenter
        listPresenter.updateDataByTableId(0);
        listActivity.showResult("render was deleted successfully!");//ЗАСУНУТЬ ЭТО К ПРЕЗЕНТЕРУ!!!!
        return true;
    }

    @Override
    public boolean updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject) {
        Render oldRender = (Render) oldDefaultObject;
        Render newRender = (Render) newDefaultObject;
        ContentValues cv = new ContentValues();
        cv.put("service", newRender.service);
        cv.put("patient", newRender.patient);
        cv.put("doctor", newRender.doctor);
        cv.put("sum", newRender.sum);
        cv.put("date", newRender.date);

        String service = oldRender.service;
        String patient = oldRender.patient;
        String doctor = oldRender.doctor;
        String sum = String.valueOf(oldRender.sum);
        String date = oldRender.date;
        Log.d("123", oldRender.service + " " + oldRender.patient + " " + oldRender.service + " " + oldRender.sum + " " + oldRender.date);
        Log.d("123", newRender.service + " " + newRender.patient + " " + newRender.service + " " + newRender.sum + " " + newRender.date);
        String[] values = new String[]{service, patient, doctor, date};
        int updCount = db.update("renders", cv, "service=? and patient=? and doctor=? and date=?", values);
        Log.d("asd", updCount+"!!");
        listPresenter.updateDataByTableId(0);
        listActivity.showResult("render was updated!");
        return true;
    }
}
