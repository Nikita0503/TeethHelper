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
import com.example.nikita.teethhelper.data.Service;
import com.example.nikita.teethhelper.data.defaultObject;
import com.example.nikita.teethhelper.defaultTable;

import java.util.ArrayList;

/**
 * Created by Nikita on 15.04.2018.
 */

public class ServicesTable implements defaultTable {
    String[] tagNames;

    DBHepler dbHepler;
    SQLiteDatabase db;
    ListActivity listActivity;
    ListPresenter listPresenter;

    public ServicesTable(Context context){//context
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public ServicesTable(ListActivity listActivity, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.listActivity = listActivity;
        dbHepler = new DBHepler(listActivity);
        db = dbHepler.getWritableDatabase();
    }

    public ArrayList<Service> getServices(){
        ArrayList<Service> services = new ArrayList<Service>();
        Cursor c = db.query("service", null, null, null, null, null, null);
        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {
            // определяем номера столбцов по имени в выборке
            int patientColIndex = c.getColumnIndex("patient");
            int doctorColIndex = c.getColumnIndex("doctor");
            int dateColIndex = c.getColumnIndex("date");
            int costColIndex = c.getColumnIndex("cost");
            int manipulationColIndex = c.getColumnIndex("manipulation");
            do {
                services.add(new Service(c.getString(manipulationColIndex), c.getString(patientColIndex), c.getString(doctorColIndex), c.getFloat(costColIndex), c.getString(dateColIndex)));
                // получаем значения по номерам столбцов и пишем все в лог
                Log.d("LOG",
                        "patient = "
                                + c.getString(patientColIndex) + ", doctor = "
                                + c.getString(doctorColIndex) + ", date = "
                                + c.getString(dateColIndex) + ", cost = "
                                + c.getString(costColIndex) + ", manipulation = "
                                + c.getString(manipulationColIndex));
                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false -
                // выходим из цикла
            } while (c.moveToNext());
        } else
            Log.d("LOG", "0 rows");
        c.close();
        return services;
    }

    public ArrayList<String> getManipulations(){
        ArrayList<String> names = new ArrayList<String>();
        Cursor c = db.query("service", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("manipulation");
            do {
                names.add(c.getString(nameColIndex));
            } while (c.moveToNext());
        }
        return names;
    }

    @Override
    public void fetchData() {
        ArrayList<Service> services = getServices();
        tagNames = listActivity.getResources().getStringArray(R.array.serviceTagNames);
        listPresenter.prepareDataByServices(services, tagNames);
    }

    @Override
    public void addRow(defaultObject defaultObject) {
        Service service = (Service) defaultObject;
        Log.d("Добавление", service.manipulation + service.patient + service.doctor + service.cost + service.date);
        ContentValues cv = new ContentValues();
        cv.put("manipulation", service.manipulation);
        cv.put("patient", service.patient);
        cv.put("doctor", service.doctor);
        cv.put("cost", service.cost);
        cv.put("date", service.date);
        // вставляем запись и получаем ее ID
        long rowID = db.insert("service", null, cv);
        listActivity.showResult("new service was added successful!");
    }

    public ArrayList<String> getNames(){
        ArrayList<String> names = new ArrayList<String>();
        Cursor c = db.query("service", null, null, null, null, null, null);
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("manipulation");
            do {
                names.add(c.getString(nameColIndex));
            } while (c.moveToNext());
        }
        return names;
    }

    @Override
    public boolean deleteRow(defaultObject defaultObject) {
        Service service = (Service) defaultObject;
        String manipulation = service.manipulation;
        String patient = service.patient;
        String doctor = service.doctor;
        String cost = String.valueOf(service.cost);
        String date = service.date;
        //String specialization = patient.specialization;
        //String experience = String.valueOf(doctor.experience);
        //String berth = doctor.berth;
        String[] values = new String[]{manipulation, patient, doctor, date};
        db.delete("service","manipulation=? and patient=? and doctor=? and date=?", values);//через listPresenter
        listPresenter.updateDataByTableId(3);
        listActivity.showResult("service was deleted successfully!");//ЗАСУНУТЬ ЭТО К ПРЕЗЕНТЕРУ!!!!
        return true;
    }

    @Override
    public boolean updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject) {
        Log.d("YADOSHEL!!!!!!!!!!!!", "JEJEJEJ");
        Service oldService = (Service) oldDefaultObject;
        Service newService = (Service) newDefaultObject;
        ContentValues cv = new ContentValues();
        cv.put("manipulation", newService.manipulation);
        cv.put("patient", newService.patient);
        cv.put("doctor", newService.doctor);
        cv.put("cost", newService.cost);
        cv.put("date", newService.date);

        String patient = oldService.patient;
        String doctor = oldService.doctor;
        String date = oldService.date;
        String cost = String .valueOf(oldService.cost);
        String manipulation = oldService.manipulation;
        Log.d("123", newService.patient + " " + newService.doctor + " " + newService.date + " " + newService.cost + " " + newService.manipulation);
        Log.d("123", oldService.patient + " " + oldService.doctor + " " + oldService.date + " " + oldService.cost + " " + oldService.manipulation);
        String[] values = new String[]{manipulation, patient, doctor, date};
        int updCount = db.update("service", cv, "manipulation=? and patient=? and doctor=? and date=?", values);
        Log.d("asd", updCount+"!!");
        listPresenter.updateDataByTableId(3);
        listActivity.showResult("service was updated successful!");
        return true;
    }
}
