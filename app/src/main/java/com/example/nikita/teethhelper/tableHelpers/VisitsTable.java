package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHelper;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Visit;
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
 * Created by Nikita on 16.04.2018.
 */

public class VisitsTable implements defaultTable {
    private String[] mTagNames;
    private DBHelper mDbHepler;
    private SQLiteDatabase mDb;
    private Context mContext;
    private ListPresenter mListPresenter;

    public VisitsTable(Context context){
        mDbHepler = new DBHelper(context);
        mDb = mDbHepler.getWritableDatabase();
    }

    public VisitsTable(Context context, ListPresenter listPresenter){
        this.mListPresenter = listPresenter;
        this.mContext = context;
        mDbHepler = new DBHelper(context);
        mDb = mDbHepler.getWritableDatabase();
    }

    @Override
    public void fetchData() {
        Disposable disposable = getVisits.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Visit>>() {
                    @Override
                    public void onSuccess(ArrayList<Visit> visits) {
                        mTagNames = mContext.getResources().getStringArray(R.array.visitTagNames);
                        mListPresenter.prepareDataByVisits(visits, mTagNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
                    }
                });
        mListPresenter.disposables.add(disposable);
    }

    @Override
    public DisposableObserver<defaultObject> addRow() {
        return new DisposableObserver<defaultObject>() {
            @Override
            public void onNext(defaultObject defaultObject) {
                Visit visit = (Visit) defaultObject;
                Log.d("Добавление", visit.patient + visit.date + visit.service);
                ContentValues cv = new ContentValues();
                cv.put("patient", visit.patient);
                cv.put("date", visit.date);
                cv.put("service", visit.service);
                long rowID = mDb.insert("visits", null, cv);
                Log.d("ADDED: ", "id = " + rowID);
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.addedVisit));
                mListPresenter.updateDataByTableId(4);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject> deleteRow() {
        return new DisposableObserver<defaultObject>() {
            @Override
            public void onNext(defaultObject defaultObject) {
                Visit visit = (Visit) defaultObject;
                String patient = visit.patient;
                String date = visit.date;
                String service = visit.service;
                String[] values = new String[]{patient, date, service};
                long dltCount = mDb.delete("visits","patient=? and date=? and service=?", values);
                Log.d("DELETED: ", "deleted " + dltCount + " rows");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.deletedVisit));
                mListPresenter.updateDataByTableId(4);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject[]> updateRow() {
        return new DisposableObserver<defaultObject[]>() {
            @Override
            public void onNext(defaultObject[] defaultObject) {
                Visit oldVisit = (Visit) defaultObject[0];
                Visit newVisit = (Visit) defaultObject[1];
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
                int updCount = mDb.update("visits", cv, "patient=? and date=? and service=?", values);
                Log.d("UPDATED: ", "updated " + updCount + " rows ");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage("Error");
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.updatedVisit));
                mListPresenter.updateDataByTableId(4);
            }
        };
    }


    public Single<ArrayList<Visit>> getVisits = Single.create(new SingleOnSubscribe<ArrayList<Visit>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<Visit>> e) throws Exception {
            ArrayList<Visit> visits = new ArrayList<Visit>();
            Cursor c = mDb.query("visits", null, null, null, null, null, null);
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
            e.onSuccess(visits);
        }
    });

}
