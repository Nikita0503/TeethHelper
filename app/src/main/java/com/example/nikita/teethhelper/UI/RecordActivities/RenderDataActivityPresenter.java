package com.example.nikita.teethhelper.UI.RecordActivities;

import android.app.Activity;
import android.util.Log;

import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.defaultPresenter;
import com.example.nikita.teethhelper.tables.DoctorsTable;
import com.example.nikita.teethhelper.tables.PatientsTable;
import com.example.nikita.teethhelper.tables.ServicesTable;

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
        Log.d("233", "OKAY");
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
        ArrayList<String> serviceNames = servicesTable.getNames();
        PatientsTable patientsTable = new PatientsTable(dataActivity.getApplicationContext());
        ArrayList<String> patientNames = patientsTable.getNames();
        DoctorsTable doctorsTable = new DoctorsTable(dataActivity.getApplicationContext());
        ArrayList<String> doctorNames = doctorsTable.getNames();
        dataActivity.setAdaptersByData(serviceNames, patientNames, doctorNames);
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

    @Override
    public void sendResult(String result) {
        dataActivity.showError(result);
    }
}
