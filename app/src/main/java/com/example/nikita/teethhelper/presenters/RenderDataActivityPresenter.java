package com.example.nikita.teethhelper.presenters;

import android.app.Activity;

import com.example.nikita.teethhelper.Contract;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.TableContract;
import com.example.nikita.teethhelper.UI.RecordActivities.RenderDataActivity;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.tableHelpers.DoctorsTable;
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;
import com.example.nikita.teethhelper.tableHelpers.ServicesTable;

import java.util.ArrayList;
import java.util.StringTokenizer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 14.05.2018.
 */

public class RenderDataActivityPresenter implements TableContract.TablePresenter, Contract.Presenter {
    private CompositeDisposable mDisposables;
    private RenderDataActivity mDataActivity;

    public RenderDataActivityPresenter(RenderDataActivity dataActivity){
        this.mDataActivity = dataActivity;
    }

    @Override
    public void onStart() {
        mDisposables = new CompositeDisposable();
    }

    @Override
    public void checkData() {
        Render render = mDataActivity.getRender();
        if (render.service.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidService));
            return;
        }
        if (render.patient.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidPatient));
            return;
        }
        if (render.doctor.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidDoctor));
            return;
        }
        if (render.sum < 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidSum));
            return;
        }
        if (render.date.trim().length() <= 0) {
            sendError(mDataActivity.getResources().getString(R.string.invalidDate));
            return;
        }
        if(!checkCorrectDate(render.date)){
            return;
        }
        mDataActivity.data.putExtra("service", render.service);
        mDataActivity.data.putExtra("patient", render.patient);
        mDataActivity.data.putExtra("doctor", render.doctor);
        mDataActivity.data.putExtra("sum", render.sum);
        mDataActivity.data.putExtra("date", render.date);
        mDataActivity.setResult(Activity.RESULT_OK, mDataActivity.data);
        mDataActivity.finish();
    }

    @Override
    public void sendError(String result) {
        mDataActivity.showError(result);
    }

    public void fetchDataForAdapters(){
        ServicesTable servicesTable = new ServicesTable(mDataActivity.getApplicationContext());
        Disposable serviceDisposable =  servicesTable.getManipulations.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> serviceManipulations) {
                        mDataActivity.setServiceAdapter(serviceManipulations);
                    }
                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }
                });
        mDisposables.add(serviceDisposable);
        PatientsTable patientsTable = new PatientsTable(mDataActivity.getApplicationContext());
        Disposable patientsDisposable =  patientsTable.getNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> patientNames) {
                        mDataActivity.setPatientAdapter(patientNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }
                });
        mDisposables.add(patientsDisposable);
        DoctorsTable doctorsTable = new DoctorsTable(mDataActivity.getApplicationContext());
        Disposable doctorsDisposable =  doctorsTable.getNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> doctorNames) {
                        mDataActivity.setDoctorAdapter(doctorNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }
                });
        mDisposables.add(doctorsDisposable);
    }

    private boolean checkCorrectDate(String str){
        StringTokenizer date = new StringTokenizer(str, ".");
        int day = -1;
        int month = -1;
        int year = -1;
        while(date.hasMoreTokens()) {
            day = Integer.parseInt(date.nextToken());
            month = Integer.parseInt(date.nextToken());
            year = Integer.parseInt(date.nextToken());
        }
        if(year < 2018){
            sendError(mDataActivity.getResources().getString(R.string.invalidYear));
            return false;
        }
        if(month <= 0 || month >12){
            sendError(mDataActivity.getResources().getString(R.string.invalidMonth));
            return false;
        }
        int [] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(day <= 0 || day > daysInMonths[month-1]){
            sendError(mDataActivity.getResources().getString(R.string.invalidDay));
            return false;
        }
        return true;
    }

    @Override
    public void onStop() {
        mDisposables.clear();
    }
}
