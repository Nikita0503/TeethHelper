package com.example.nikita.teethhelper.presenters;

import android.app.Activity;
import android.util.Log;

import com.example.nikita.teethhelper.Contract;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.TableContract;
import com.example.nikita.teethhelper.UI.RecordActivities.VisitsDataActivity;
import com.example.nikita.teethhelper.data.Visit;
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
 * Created by Nikita on 13.05.2018.
 */

public class VisitsDataActivityPresenter implements TableContract.TablePresenter, Contract.Presenter {
    private CompositeDisposable mDisposables;
    private VisitsDataActivity mDataActivity;

    public VisitsDataActivityPresenter(VisitsDataActivity dataActivity){
        this.mDataActivity = dataActivity;
    }

    @Override
    public void onStart() {
        mDisposables = new CompositeDisposable();
    }

    @Override
    public void checkData() {
        Visit visit = mDataActivity.getVisit();
        if (visit.patient.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidPatient));
            return;
        }
        if (visit.service.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidService));
            return;
        }
        if (visit.date.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidDate));
            return;
        }
        if(!checkCorrectDate(visit.date)){
            return;
        }
        mDataActivity.data.putExtra("patient", visit.patient);
        mDataActivity.data.putExtra("service", visit.service);
        mDataActivity.data.putExtra("date", visit.date);
        mDataActivity.setResult(Activity.RESULT_OK, mDataActivity.data);
        mDataActivity.finish();
    }

    @Override
    public void sendError(String result) {
        mDataActivity.showError(result);
    }

    public void fetchDataForAdapters(){
        PatientsTable patientsTable = new PatientsTable(mDataActivity.getApplicationContext());
        Disposable patientsDisposable =  patientsTable.getNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> doctorNames) {
                        mDataActivity.setPatientAdapter(doctorNames);
                    }
                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }
                });
        mDisposables.add(patientsDisposable);
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
                        //
                    }
                });
        mDisposables.add(serviceDisposable);
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
