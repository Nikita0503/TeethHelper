package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHelper;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Doctor;
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
 * Created by Nikita on 10.04.2018.
 */

public class DoctorsTable implements defaultTable {
    public static final int DOCTORS_TABLE_ID = 1;
    private String[] mTagNames;
    private Context mContext;
    private SQLiteDatabase mDb;
    private DBHelper mDbHepler;
    private ListPresenter mListPresenter;

    public DoctorsTable(Context context){
        mDbHepler = new DBHelper(context);
        mDb = mDbHepler.getWritableDatabase();
    }

    public DoctorsTable(Context context, ListPresenter listPresenter){
        this.mListPresenter = listPresenter;
        this.mContext = context;
        mDbHepler = new DBHelper(context);
        mDb = mDbHepler.getWritableDatabase();
    }

    @Override
    public void fetchData() {
        Disposable disposable = getDoctors.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Doctor>>() {
                    @Override
                    public void onSuccess(ArrayList<Doctor> doctors) {
                        mTagNames = mContext.getResources().getStringArray(R.array.doctorTagNames);
                        mListPresenter.prepareDataByDoctors(doctors, mTagNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
                    }
                });
        mListPresenter.disposables.add(disposable);
    }

    public DisposableObserver<defaultObject> addRow(){
        return new DisposableObserver<defaultObject>() {
            @Override
            public void onNext(defaultObject defaultObject) {
                Doctor doctor = (Doctor) defaultObject;
                ContentValues cv = new ContentValues();
                cv.put("name", doctor.name);
                cv.put("passport", doctor.passport);
                cv.put("address", doctor.address);
                cv.put("specialization", doctor.specialization);
                cv.put("experience", doctor.experience);
                cv.put("berth", doctor.berth);
                long rowID = mDb.insert("doctors", null, cv);
                Log.d("ADDED: ", "id = " + rowID);
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.addedDoctor));
                mListPresenter.updateDataByTableId(DOCTORS_TABLE_ID);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject> deleteRow() {
        return new DisposableObserver<defaultObject>() {
            @Override
            public void onNext(defaultObject defaultObject) {
                Doctor doctor = (Doctor) defaultObject;
                String code = String.valueOf(doctor.code);
                String name = doctor.name;
                String passport = doctor.passport;
                String address = doctor.address;
                String specialization = doctor.specialization;
                String experience = String.valueOf(doctor.experience);
                String berth = doctor.berth;
                String[] values = new String[]{code, name, passport, address, specialization, experience, berth};
                long dltCount = mDb.delete("doctors","code=? and name=? and passport=? and address = ? and specialization=? and experience=? and berth=?", values);
                Log.d("DELETED: ", "deleted " + dltCount + " rows");

            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.deletedDoctor));
                mListPresenter.updateDataByTableId(DOCTORS_TABLE_ID);
            }
        };
    }

    @Override
    public DisposableObserver<defaultObject[]> updateRow() {
        return new DisposableObserver<defaultObject[]>() {
            @Override
            public void onNext(defaultObject[] defaultObject) {
                Doctor oldDoctor = (Doctor) defaultObject[0];
                Doctor newDoctor = (Doctor) defaultObject[1];
                ContentValues cv = new ContentValues();
                cv.put("code", oldDoctor.code);
                cv.put("name", newDoctor.name);
                cv.put("passport", newDoctor.passport);
                cv.put("address", newDoctor.address);
                cv.put("specialization", newDoctor.specialization);
                cv.put("experience", newDoctor.experience);
                cv.put("berth", newDoctor.berth);
                String code = String.valueOf(oldDoctor.code);
                String name = oldDoctor.name;
                String passport = oldDoctor.passport;
                String address = oldDoctor.address;
                String specialization = oldDoctor.specialization;
                String experience = String.valueOf(oldDoctor.experience);
                String berth = oldDoctor.berth;
                Log.d("OLD DATA: ", oldDoctor.code + " " + oldDoctor.name + " " + oldDoctor.passport + " " + oldDoctor.address + " " + oldDoctor.specialization + " " + oldDoctor.experience + " " + oldDoctor.berth);
                Log.d("NEW DATA: ", newDoctor.code + " " + newDoctor.name + " " + newDoctor.passport + " " + newDoctor.address + " " + newDoctor.specialization + " " + newDoctor.experience + " " + newDoctor.berth);
                String[] values = new String[]{code, name, passport, address, specialization, experience, berth};
                int updCount = mDb.update("doctors", cv, "code=? and name=? and passport=? and address=? and specialization=? and experience=? and berth=?", values);
                Log.d("UPDATED: ", "updated " + updCount + " rows ");
            }

            @Override
            public void onError(Throwable e) {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.error));
            }

            @Override
            public void onComplete() {
                mListPresenter.sendMessage(mContext.getResources().getString(R.string.updatedDoctor));
                mListPresenter.updateDataByTableId(DOCTORS_TABLE_ID);
            }
        };
    }

    public Single<ArrayList<String>> getNames = Single.create(new SingleOnSubscribe<ArrayList<String>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<String>> e) throws Exception {
            ArrayList<String> names = new ArrayList<String>();
            Cursor c = mDb.query("doctors", null, null, null, null, null, null);
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

    public Single<ArrayList<Doctor>> getDoctors = Single.create(new SingleOnSubscribe<ArrayList<Doctor>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<Doctor>> e) throws Exception {
            ArrayList<Doctor> doctors = new ArrayList<Doctor>();
            Cursor c = mDb.query("doctors", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                int idColIndex = c.getColumnIndex("code");
                int nameColIndex = c.getColumnIndex("name");
                int passportColIndex = c.getColumnIndex("passport");
                int addressColIndex = c.getColumnIndex("address");
                int specializationColIndex = c.getColumnIndex("specialization");
                int experienceColIndex = c.getColumnIndex("experience");
                int berthColIndex = c.getColumnIndex("berth");
                do {
                    doctors.add(new Doctor(c.getInt(idColIndex), c.getString(nameColIndex), c.getString(passportColIndex), c.getString(addressColIndex), c.getString(specializationColIndex), c.getInt(experienceColIndex), c.getString(berthColIndex)));
                    Log.d("DOCTOR: ",
                            "ID = " + c.getInt(idColIndex) + ", name = "
                                    + c.getString(nameColIndex) + ", passport = "
                                    + c.getString(passportColIndex) + ", address = "
                                    + c.getString(addressColIndex) + ", specialization = "
                                    + c.getString(specializationColIndex) + ", experience = "
                                    + c.getString(experienceColIndex) + ", berth = "
                                    + c.getString(berthColIndex));
                } while (c.moveToNext());
            } else
                Log.d("DOCTOR: ", "0 rows");
            c.close();
            e.onSuccess(doctors);
        }
    });
}
