package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHepler;
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.data.defaultObject;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 16.04.2018.
 */

public class RendersTable implements defaultTable {
    String[] tagNames;
    DBHepler dbHepler;
    SQLiteDatabase db;
    Context context;
    ListPresenter listPresenter;

    public RendersTable(Context context){
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public RendersTable(Context context, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.context = context;
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    @Override
    public void fetchData() {
        Disposable disposable = getRenders.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Render>>() {
                    @Override
                    public void onSuccess(ArrayList<Render> renders) {
                        tagNames = context.getResources().getStringArray(R.array.renderTagNames);
                        listPresenter.prepareDataByRenders(renders, tagNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        listPresenter.sendMessage(context.getResources().getString(R.string.error));
                    }
                });
    }

    @Override
    public void addRow(defaultObject defaultObject) {
        Render render = (Render) defaultObject;
        ContentValues cv = new ContentValues();
        cv.put("service", render.service);
        cv.put("patient", render.patient);
        cv.put("doctor", render.doctor);
        cv.put("sum", render.sum);
        cv.put("date", render.date);
        long rowID = db.insert("renders", null, cv);
        Log.d("ADDED: ", "id = " + rowID);
        listPresenter.sendMessage("new render was added successful!");
    }

    @Override
    public void deleteRow(defaultObject defaultObject) {
        Render render = (Render) defaultObject;
        String service = render.service;
        String patient = render.patient;
        String doctor = render.doctor;
        String sum = String.valueOf(render.sum);
        String date = render.date;
        String[] values = new String[]{service, patient, doctor, date};
        Log.d("DEL", sum);
        int dltCount = db.delete("renders","service=? and patient=? and doctor=? and date=?", values);
        Log.d("DELETED: ", "deleted " + dltCount + " rows");
        listPresenter.updateDataByTableId(0);
        listPresenter.sendMessage("render was deleted successfully!");
    }

    @Override
    public void updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject) {
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
        Log.d("OLD DATA", oldRender.service + " " + oldRender.patient + " " + oldRender.service + " " + oldRender.sum + " " + oldRender.date);
        Log.d("OLD DATA", newRender.service + " " + newRender.patient + " " + newRender.service + " " + newRender.sum + " " + newRender.date);
        String[] values = new String[]{service, patient, doctor, date};
        int updCount = db.update("renders", cv, "service=? and patient=? and doctor=? and date=?", values);
        Log.d("UPDATED", "updated " + updCount + " rows ");
        listPresenter.updateDataByTableId(0);
        listPresenter.sendMessage("render was updated!");
    }

    public Single<ArrayList<Render>> getRenders = Single.create(new SingleOnSubscribe<ArrayList<Render>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<Render>> e) throws Exception {
            ArrayList<Render> renders = new ArrayList<Render>();
            Cursor c = db.query("renders", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int patientColIndex = c.getColumnIndex("patient");
                int doctorColIndex = c.getColumnIndex("doctor");
                int sumColIndex = c.getColumnIndex("sum");
                int dateColIndex = c.getColumnIndex("date");
                int serviceColIndex = c.getColumnIndex("service");
                do {
                    renders.add(new Render(c.getString(serviceColIndex), c.getString(patientColIndex), c.getString(doctorColIndex), c.getFloat(sumColIndex), c.getString(dateColIndex)));
                    Log.d("RENDER",
                            "patient = "
                                    + c.getString(patientColIndex) + ", doctor = "
                                    + c.getString(doctorColIndex) + ", sum = "
                                    + c.getString(sumColIndex) + ", date = "
                                    + c.getString(dateColIndex) + ", service = "
                                    + c.getString(serviceColIndex));
                } while (c.moveToNext());
            } else
                Log.d("RENDER", "0 rows");
            c.close();
            e.onSuccess(renders);
        }
    });
}