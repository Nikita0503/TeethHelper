package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHelper;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Service;
import com.example.nikita.teethhelper.data.defaultObject;

import java.util.ArrayList;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 15.04.2018.
 */

public class ServicesTable implements defaultTable {
    public static final int SERVICES_TABLE_ID = 3;
    private String[] mTagNames;
    private DBHelper mDbHepler;
    private SQLiteDatabase mDb;
    private Context mContext;
    private ListPresenter mListPresenter;

    public ServicesTable(Context context){
        mDbHepler = new DBHelper(context);
        mDb = mDbHepler.getWritableDatabase();
    }

    public ServicesTable(Context context, ListPresenter listPresenter){
        this.mListPresenter = listPresenter;
        this.mContext = context;
        mDbHepler = new DBHelper(context);
        mDb = mDbHepler.getWritableDatabase();
    }
    public DisposableObserver<defaultObject> addRow() {
        return new DisposableObserver<defaultObject>() {
            @Override
            public void onNext(defaultObject defaultObject) {
                Service service = (Service) defaultObject;
                Log.d("Добавление", service.manipulation + service.patient + service.doctor + service.cost + service.date);
                ContentValues cv = new ContentValues();
                cv.put("manipulation", service.manipulation);
                cv.put("patient", service.patient);
                cv.put("doctor", service.doctor);
                cv.put("cost", service.cost);
                cv.put("date", service.date);
                long rowID = mDb.insert("service", null, cv);
                Log.d("ADDED: ", "id = " + rowID);
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.addedService));
                mListPresenter.updateDataByTableId(SERVICES_TABLE_ID);
            }
        };
    }
    @Override
    public void fetchData() {
        Disposable disposable = getServices.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Service>>() {
                    @Override
                    public void onSuccess(ArrayList<Service> services) {
                        mTagNames = mContext.getResources().getStringArray(R.array.serviceTagNames);
                        mListPresenter.prepareDataByServices(services, mTagNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
                    }
                });
        mListPresenter.disposables.add(disposable);
    }

    @Override
    public DisposableObserver<defaultObject> deleteRow() {
        return new DisposableObserver<defaultObject>() {
            @Override
            public void onNext(defaultObject defaultObject) {
                Service service = (Service) defaultObject;
                String manipulation = service.manipulation;
                String patient = service.patient;
                String doctor = service.doctor;
                String cost = String.valueOf(service.cost);
                String date = service.date;
                String[] values = new String[]{manipulation, patient, doctor, date};
                long dltCount = mDb.delete("service","manipulation=? and patient=? and doctor=? and date=?", values);
                Log.d("DELETED: ", "deleted " + dltCount + " rows");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.deletedService));
                mListPresenter.updateDataByTableId(SERVICES_TABLE_ID);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject[]> updateRow() {
        return new DisposableObserver<defaultObject[]>() {
            @Override
            public void onNext(defaultObject[] defaultObject) {
                Service oldService = (Service) defaultObject[0];
                Service newService = (Service) defaultObject[1];
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
                Log.d("OLD DATA: ", newService.patient + " " + newService.doctor + " " + newService.date + " " + newService.cost + " " + newService.manipulation);
                Log.d("NEW DATA: ", oldService.patient + " " + oldService.doctor + " " + oldService.date + " " + oldService.cost + " " + oldService.manipulation);
                String[] values = new String[]{manipulation, patient, doctor, date};
                int updCount = mDb.update("service", cv, "manipulation=? and patient=? and doctor=? and date=?", values);
                Log.d("UPDATED: ", "updated " + updCount + " rows ");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.updatedService));
                mListPresenter.updateDataByTableId(SERVICES_TABLE_ID);
            }
        };
    }


    public Single<ArrayList<Service>> getServices = Single.create(new SingleOnSubscribe<ArrayList<Service>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<Service>> e) throws Exception {
            ArrayList<Service> services = new ArrayList<Service>();
            Cursor c = mDb.query("service", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int patientColIndex = c.getColumnIndex("patient");
                int doctorColIndex = c.getColumnIndex("doctor");
                int dateColIndex = c.getColumnIndex("date");
                int costColIndex = c.getColumnIndex("cost");
                int manipulationColIndex = c.getColumnIndex("manipulation");
                do {
                    services.add(new Service(c.getString(manipulationColIndex), c.getString(patientColIndex), c.getString(doctorColIndex), c.getFloat(costColIndex), c.getString(dateColIndex)));
                    Log.d("SERVICE: ",
                            "patient = "
                                    + c.getString(patientColIndex) + ", doctor = "
                                    + c.getString(doctorColIndex) + ", date = "
                                    + c.getString(dateColIndex) + ", cost = "
                                    + c.getString(costColIndex) + ", manipulation = "
                                    + c.getString(manipulationColIndex));
                } while (c.moveToNext());
            } else
                Log.d("SERVICE:", "0 rows");
            c.close();
            e.onSuccess(services);
        }
    });

    public Single<ArrayList<String>> getManipulations = Single.create(new SingleOnSubscribe<ArrayList<String>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<String>> e) throws Exception {
            ArrayList<String> manipulations = new ArrayList<String>();
            Cursor c = mDb.query("service", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int nameColIndex = c.getColumnIndex("manipulation");
                do {
                    manipulations.add(c.getString(nameColIndex));
                } while (c.moveToNext());
            }
            e.onSuccess(manipulations);
        }
    });

}
