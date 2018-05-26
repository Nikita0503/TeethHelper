package com.example.nikita.teethhelper.presenters;

import android.app.Activity;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.RecordActivities.ServiceDataActivity;
import com.example.nikita.teethhelper.data.Service;
import com.example.nikita.teethhelper.tableHelpers.DoctorsTable;
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;

import java.util.ArrayList;
import java.util.StringTokenizer;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 08.05.2018.
 */

public class ServiceDataActivityPresenter implements defaultPresenter {
    ServiceDataActivity dataActivity;

    public ServiceDataActivityPresenter(ServiceDataActivity dataActivity){
        this.dataActivity = dataActivity;
    }
    @Override
    public void checkData() {
        Service service = dataActivity.getService();
        if (service.patient.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidPatient));
            return;
        }
        if (service.doctor.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidDoctor));
            return;
        }
        if (service.manipulation.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidManipulation));
            return;
        }
        if (service.cost < 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidService));
            return;
        }
        if (service.date.trim().length() <= 0) {
            sendResult(dataActivity.getResources().getString(R.string.invalidDate));
            return;
        }
        if(!checkCorrectDate(service.date)){
            return;
        }
        dataActivity.data.putExtra("patient", service.patient);
        dataActivity.data.putExtra("doctor", service.doctor);
        dataActivity.data.putExtra("manipulation", service.manipulation);
        dataActivity.data.putExtra("cost", service.cost);
        dataActivity.data.putExtra("date", service.date);
        dataActivity.setResult(Activity.RESULT_OK, dataActivity.data);
        dataActivity.finish();
    }

    public void fetchDataForAdapters(){
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
        Disposable doctorsDisposable =  doctorsTable.getNames.subscribeOn(Schedulers.io())
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
    public void sendResult(String result)  {
        dataActivity.showError(result);
    }
}
