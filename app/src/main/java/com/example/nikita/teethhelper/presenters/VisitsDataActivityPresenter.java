package com.example.nikita.teethhelper.presenters;

import android.app.Activity;
import android.util.Log;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.RecordActivities.VisitsDataActivity;
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;
import com.example.nikita.teethhelper.tableHelpers.ServicesTable;

import java.util.ArrayList;
import java.util.StringTokenizer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 13.05.2018.
 */

public class VisitsDataActivityPresenter implements defaultPresenter {
    VisitsDataActivity dataActivity;

    public VisitsDataActivityPresenter(VisitsDataActivity dataActivity){
        this.dataActivity = dataActivity;
    }
    @Override
    public void checkData() {
        Visit visit = dataActivity.getVisit();
        if (visit.patient.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidPatient));
            return;
        }
        if (visit.service.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidService));
            return;
        }
        if (visit.date.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidDate));
            return;
        }
        if(!checkCorrectDate(visit.date)){
            return;
        }
        dataActivity.data.putExtra("patient", visit.patient);
        dataActivity.data.putExtra("service", visit.service);
        dataActivity.data.putExtra("date", visit.date);
        dataActivity.setResult(Activity.RESULT_OK, dataActivity.data);
        dataActivity.finish();
    }

    public void fetchDataForAdapters(){
        PatientsTable patientsTable = new PatientsTable(dataActivity.getApplicationContext());
        Disposable patientsDisposable =  patientsTable.getNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> doctorNames) {
                        dataActivity.setPatientAdapter(doctorNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        //
                    }
                });
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
    }

    private boolean checkCorrectDate(String str){
        StringTokenizer date = new StringTokenizer(str, ".");
        int day = -1;
        int month = -1;
        int year = -1;
        while(date.hasMoreTokens()) {
            Log.d("123", str);
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
