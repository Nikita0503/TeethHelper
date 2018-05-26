package com.example.nikita.teethhelper.presenters;

import android.app.Activity;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.RecordActivities.RenderDataActivity;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.tableHelpers.DoctorsTable;
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;
import com.example.nikita.teethhelper.tableHelpers.ServicesTable;

import java.util.ArrayList;
import java.util.StringTokenizer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 14.05.2018.
 */

public class RenderDataActivityPresenter implements defaultPresenter {
    RenderDataActivity dataActivity;

    public RenderDataActivityPresenter(RenderDataActivity dataActivity){
        this.dataActivity = dataActivity;
    }

    @Override
    public void checkData() {
        Render render = dataActivity.getRender();
        if (render.service.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidService));
            return;
        }
        if (render.patient.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidPatient));
            return;
        }
        if (render.doctor.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidDoctor));
            return;
        }
        if (render.sum < 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidSum));
            return;
        }
        if (render.date.trim().length() <= 0) {
            sendResult(dataActivity.getResources().getString(R.string.invalidDate));
            return;
        }
        if(!checkCorrectDate(render.date)){
            return;
        }
        dataActivity.data.putExtra("service", render.service);
        dataActivity.data.putExtra("patient", render.patient);
        dataActivity.data.putExtra("doctor", render.doctor);
        dataActivity.data.putExtra("sum", render.sum);
        dataActivity.data.putExtra("date", render.date);
        dataActivity.setResult(Activity.RESULT_OK, dataActivity.data);
        dataActivity.finish();
    }

    public void fetchDataForAdapters(){
        ServicesTable servicesTable = new ServicesTable(dataActivity.getApplicationContext());
        Disposable serviceDisposable =  servicesTable.getManipulations.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> serviceManipulations) {
                        dataActivity.setServiceAdapter(serviceManipulations);
                    }
                    @Override
                    public void onError(Throwable e) {
                        //
                    }
                });
        PatientsTable patientsTable = new PatientsTable(dataActivity.getApplicationContext());
        Disposable patientsDisposable =  patientsTable.getNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> patientNames) {
                        dataActivity.setPatientAdapter(patientNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        //
                    }
                });
        DoctorsTable doctorsTable = new DoctorsTable(dataActivity.getApplicationContext());
        Disposable disposable =  doctorsTable.getNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> doctorNames) {
                        dataActivity.setDoctorAdapter(doctorNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        //
                    }
                });

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
            sendResult(dataActivity.getResources().getString(R.string.invalidYear));
            return false;
        }
        if(month <= 0 || month >12){
            sendResult(dataActivity.getResources().getString(R.string.invalidMonth));
            return false;
        }
        int [] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(day <= 0 || day > daysInMonths[month-1]){
            sendResult(dataActivity.getResources().getString(R.string.invalidDay));
            return false;
        }
        return true;
    }

    @Override
    public void sendResult(String result) {
        dataActivity.showError(result);
    }
}
