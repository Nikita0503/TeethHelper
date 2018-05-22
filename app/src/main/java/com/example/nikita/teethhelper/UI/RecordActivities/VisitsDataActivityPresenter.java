package com.example.nikita.teethhelper.UI.RecordActivities;

import android.app.Activity;
import android.util.Log;

import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.defaultPresenter;
import com.example.nikita.teethhelper.tables.PatientsTable;
import com.example.nikita.teethhelper.tables.ServicesTable;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
            sendResult("Invalid patient");
            return;
        }
        if (visit.service.trim().length() <= 0){
            sendResult("Invalid service");
            return;
        }
        if (visit.date.trim().length() <= 0){
            sendResult("Invalid date");
            return;
        }
        if(!checkCorrectDate(visit.date)){
            return;
        }
        Log.d("233", "OKAY");
        dataActivity.data.putExtra("patient", visit.patient);
        dataActivity.data.putExtra("service", visit.service);
        dataActivity.data.putExtra("date", visit.date);
        dataActivity.setResult(Activity.RESULT_OK, dataActivity.data);
        dataActivity.finish();
    }

    public void fetchDataForAdapters(){
        PatientsTable patientsTable = new PatientsTable(dataActivity.getApplicationContext());
        ArrayList<String> patientNames = patientsTable.getNames();
        ServicesTable doctorsGetter = new ServicesTable(dataActivity.getApplicationContext());
        ArrayList<String> doctorNames = doctorsGetter.getManipulations();
        dataActivity.setAdaptersByData(patientNames, doctorNames);
    }

    @Override
    public void sendResult(String result) {
        dataActivity.showError(result);
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
            sendResult("Invalid year");
            return false;
        }
        if(month <= 0 || month >12){
            sendResult("Invalid month");
            return false;
        }
        int [] daysInMonths = {31,28,31,30,31,30,31,31,30,31,30,31};
        if(day <= 0 || day > daysInMonths[month-1]){
            sendResult("Invalid day");
            return false;
        }
        return true;
    }
}
