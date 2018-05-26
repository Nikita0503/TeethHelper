package com.example.nikita.teethhelper.tableHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.nikita.teethhelper.DBHepler;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.defaultObject;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Single;
import io.reactivex.SingleEmitter;
import io.reactivex.SingleObserver;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 10.04.2018.
 */

public class DoctorsTable implements defaultTable {
    String[] tagNames;
    DBHepler dbHepler;
    SQLiteDatabase db;
    Context context;
    ListPresenter listPresenter;

    public DoctorsTable(Context context){
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    public DoctorsTable(Context context, ListPresenter listPresenter){
        this.listPresenter = listPresenter;
        this.context = context;
        dbHepler = new DBHepler(context);
        db = dbHepler.getWritableDatabase();
    }

    @Override
    public void fetchData() {
        Disposable disposable = getDoctors.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<Doctor>>() {
                    @Override
                    public void onSuccess(ArrayList<Doctor> doctors) {
                        tagNames = context.getResources().getStringArray(R.array.doctorTagNames);
                        listPresenter.prepareDataByDoctors(doctors, tagNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        listPresenter.sendMessage(context.getResources().getString(R.string.error));
                    }
                });
    }

    @Override
    public void addRow(defaultObject defaultObject) {
        Doctor doctor = (Doctor) defaultObject;
        ContentValues cv = new ContentValues();
        cv.put("name", doctor.name);
        cv.put("passport", doctor.passport);
        cv.put("address", doctor.address);
        cv.put("specialization", doctor.specialization);
        cv.put("experience", doctor.experience);
        cv.put("berth", doctor.berth);
        long rowID = db.insert("doctors", null, cv);
        Log.d("ADDED: ", "id = " + rowID);
        listPresenter.sendMessage("new doctor was added successful!");
    }

    @Override
    public void deleteRow(defaultObject defaultObject) {
        Doctor doctor = (Doctor) defaultObject;
        String code = String.valueOf(doctor.code);
        String name = doctor.name;
        String passport = doctor.passport;
        String address = doctor.address;
        String specialization = doctor.specialization;
        String experience = String.valueOf(doctor.experience);
        String berth = doctor.berth;
        String[] values = new String[]{code, name, passport, address, specialization, experience, berth};
        long dltCount = db.delete("doctors","code=? and name=? and passport=? and address = ? and specialization=? and experience=? and berth=?", values);
        Log.d("DELETED: ", "deleted " + dltCount + " rows");
        listPresenter.updateDataByTableId(1);
        listPresenter.sendMessage("doctor was deleted successfully!");
    }

    @Override
    public void updateRow(defaultObject oldDefaultObject, defaultObject newDefaultObject) {
        Doctor oldDoctor = (Doctor) oldDefaultObject;
        Doctor newDoctor = (Doctor) newDefaultObject;
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
        int updCount = db.update("doctors", cv, "code=? and name=? and passport=? and address=? and specialization=? and experience=? and berth=?", values);
        Log.d("UPDATED: ", "updated " + updCount + " rows ");
        listPresenter.updateDataByTableId(1);
        listPresenter.sendMessage("doctor was updated!");
    }

    public Single<ArrayList<String>> getNames = Single.create(new SingleOnSubscribe<ArrayList<String>>() {
        @Override
        public void subscribe(SingleEmitter<ArrayList<String>> e) throws Exception {
            ArrayList<String> names = new ArrayList<String>();
            Cursor c = db.query("doctors", null, null, null, null, null, null);
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
            Cursor c = db.query("doctors", null, null, null, null, null, null);
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

    /*public ArrayList<Doctor> getDoctors(){
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();
        Cursor c = db.query("doctors", null, null, null, null, null, null);
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
        return doctors;
    }*/
}
