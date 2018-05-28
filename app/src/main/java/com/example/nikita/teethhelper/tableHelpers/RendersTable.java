package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHelper;
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
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 16.04.2018.
 */

public class RendersTable implements defaultTable {
    private String[] mTagNames;
    private Context mContext;
    private DBHelper dbHepler;
    private SQLiteDatabase mDb;
    private ListPresenter mListPresenter;

    public RendersTable(Context context){
        dbHepler = new DBHelper(context);
        mDb = dbHepler.getWritableDatabase();
    }

    public RendersTable(Context context, ListPresenter listPresenter){
        this.mListPresenter = listPresenter;
        this.mContext = context;
        dbHepler = new DBHelper(context);
        mDb = dbHepler.getWritableDatabase();
    }

    @Override
    public void fetchData() {
        Disposable disposable = getRenders.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Render>>() {
                    @Override
                    public void onSuccess(ArrayList<Render> renders) {
                        mTagNames = mContext.getResources().getStringArray(R.array.renderTagNames);
                        mListPresenter.prepareDataByRenders(renders, mTagNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
                    }
                });
        mListPresenter.disposables.add(disposable);
    }

    public DisposableObserver<defaultObject> addRow() {
        return new DisposableObserver<defaultObject>() {

            @Override
            public void onNext(defaultObject defaultObject) {
                Render render = (Render) defaultObject;
                ContentValues cv = new ContentValues();
                cv.put("service", render.service);
                cv.put("patient", render.patient);
                cv.put("doctor", render.doctor);
                cv.put("sum", render.sum);
                cv.put("date", render.date);
                long rowID = mDb.insert("renders", null, cv);
                Log.d("ADDED: ", "id = " + rowID);
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.addedRender));
                mListPresenter.updateDataByTableId(0);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject> deleteRow() {
        return new DisposableObserver<defaultObject>() {
            @Override
            public void onNext(defaultObject defaultObject) {
                Render render = (Render) defaultObject;
                String service = render.service;
                String patient = render.patient;
                String doctor = render.doctor;
                String sum = String.valueOf(render.sum);
                String date = render.date;
                String[] values = new String[]{service, patient, doctor, date};
                Log.d("DEL", sum);
                int dltCount = mDb.delete("renders","service=? and patient=? and doctor=? and date=?", values);
                Log.d("DELETED: ", "deleted " + dltCount + " rows");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.deletedRender));
                mListPresenter.updateDataByTableId(0);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject[]> updateRow() {
        return new DisposableObserver<defaultObject[]>() {
            @Override
            public void onNext(defaultObject[] defaultObject) {
                Render oldRender = (Render) defaultObject[0];
                Render newRender = (Render) defaultObject[1];
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
                int updCount = mDb.update("renders", cv, "service=? and patient=? and doctor=? and date=?", values);
                Log.d("UPDATED", "updated " + updCount + " rows ");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.updatedRender));
                mListPresenter.updateDataByTableId(0);
            }
        };
    }

    public Single<ArrayList<Render>> getRenders = Single.create(new SingleOnSubscribe<ArrayList<Render>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<Render>> e) throws Exception {
            ArrayList<Render> renders = new ArrayList<Render>();
            Cursor c = mDb.query("renders", null, null, null, null, null, null);
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