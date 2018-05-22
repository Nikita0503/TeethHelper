package com.example.nikita.teethhelper.presenters;

import android.app.Activity;

import com.example.nikita.teethhelper.UI.RecordActivities.RenderDataActivity;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.tableHelpers.DoctorsTable;
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;
import com.example.nikita.teethhelper.tableHelpers.ServicesTable;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
            sendResult("Invalid service");
            return;
        }
        if (render.patient.trim().length() <= 0){
            sendResult("Invalid patient");
            return;
        }
        if (render.doctor.trim().length() <= 0){
            sendResult("Invalid doctor");
            return;
        }
        if (render.sum < 0){
            sendResult("Invalid sum");
            return;
        }
        if (render.date.trim().length() <= 0) {
            sendResult("Invalid data");
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
        ArrayList<String> serviceManipulations = servicesTable.getManipulations();
        PatientsTable patientsTable = new PatientsTable(dataActivity.getApplicationContext());
        ArrayList<String> patientNames = patientsTable.getNames();
        DoctorsTable doctorsTable = new DoctorsTable(dataActivity.getApplicationContext());
        ArrayList<String> doctorNames = doctorsTable.getNames();
        dataActivity.setAdaptersByData(serviceManipulations, patientNames, doctorNames);
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

    @Override
    public void sendResult(String result) {
        dataActivity.showError(result);
    }
}
