package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHelper;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Patient;
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

public class PatientsTable implements defaultTable {
    public static final int PATIENTS_TABLE_ID = 2;
    private String[] mTagNames;
    private Context mContext;
    private DBHelper dbHepler;
    private SQLiteDatabase mDb;
    private ListPresenter mListPresenter;

    public PatientsTable(Context context){
        dbHepler = new DBHelper(context);
        mDb = dbHepler.getWritableDatabase();
    }

    public PatientsTable(Context context, ListPresenter listPresenter){
        this.mListPresenter = listPresenter;
        this.mContext = context;
        dbHepler = new DBHelper(context);
        mDb = dbHepler.getWritableDatabase();
    }

    @Override
    public void fetchData(){
        Disposable disposable = getPatients.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Patient>>() {
                    @Override
                    public void onSuccess(ArrayList<Patient> patients) {
                        mTagNames = mContext.getResources().getStringArray(R.array.patientTagNames);
                        mListPresenter.prepareDataByPatients(patients, mTagNames);
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
                Patient patient = (Patient) defaultObject;
                ContentValues cv = new ContentValues();
                cv.put("name", patient.name);
                cv.put("passport", patient.passport);
                cv.put("address", patient.address);
                cv.put("disease", patient.disease);
                long rowID = mDb.insert("patients", null, cv);
                Log.d("ADDED: ", "id = " + rowID);
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.addedPatient));
                mListPresenter.updateDataByTableId(PATIENTS_TABLE_ID);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject> deleteRow() {
        return new DisposableObserver<defaultObject>() {
            @Override
            public void onNext(defaultObject defaultObject) {
                Patient patient = (Patient) defaultObject;
                String code = String.valueOf(patient.code);
                String name = patient.name;
                String passport = patient.passport;
                String address = patient.address;
                String disease = patient.disease;
                String[] values = new String[]{code, name, passport, address, disease};
                long dltCount = mDb.delete("patients","code=? and name=? and passport=? and address=? and disease=?", values);
                Log.d("DELETED: ", "deleted " + dltCount + " rows");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.deletedPatient));
                mListPresenter.updateDataByTableId(PATIENTS_TABLE_ID);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject[]> updateRow() {
        return new DisposableObserver<defaultObject[]>() {
            @Override
            public void onNext(defaultObject[] defaultObject) {
                Patient oldPatient = (Patient) defaultObject[0];
                Patient newPatient = (Patient) defaultObject[1];
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
                int updCount = mDb.update("patients", cv, "code=? and name=? and passport=? and address=? and disease=?", values);
                Log.d("UPDATE: ", "updated " + updCount+" rows ");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.updatedPatient));
                mListPresenter.updateDataByTableId(PATIENTS_TABLE_ID);
            }
        };
    }


    public Single<ArrayList<String>> getNames = Single.create(new SingleOnSubscribe<ArrayList<String>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<String>> e) throws Exception {
            ArrayList<String> names = new ArrayList<String>();
            Cursor c = mDb.query("patients", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int nameColIndex = c.getColumnIndex("name");
                do {
                    names.add(c.getString(nameColIndex));
                } while (c.moveToNext());
            }
            c.close();
            e.onSuccess(names);
        }
    });


    public Single<ArrayList<Patient>> getPatients = Single.create(new SingleOnSubscribe<ArrayList<Patient>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<Patient>> e) throws Exception {
            ArrayList<Patient> patients = new ArrayList<Patient>();
            Cursor c = mDb.query("patients", null, null, null, null, null, null);
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
            e.onSuccess(patients);
        }
    });


}
