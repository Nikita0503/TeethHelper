package com.example.nikita.teethhelper.UI.RecordActivities;

import android.app.Activity;
import android.util.Log;

import com.example.nikita.teethhelper.UI.RecordActivities.PatientDataActivity;
import com.example.nikita.teethhelper.UI.RecordActivities.ServiceDataActivity;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.Service;
import com.example.nikita.teethhelper.defaultPresenter;
import com.example.nikita.teethhelper.getters.DoctorsGetter;
import com.example.nikita.teethhelper.getters.PatientsGetter;

import java.util.ArrayList;
import java.util.StringTokenizer;

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
            sendResult("Invalid patient");
            return;
        }
        if (service.doctor.trim().length() <= 0){
            sendResult("Invalid doctor");
            return;
        }
        if (service.manipulation.trim().length() <= 0){
            sendResult("Invalid manipulation");
            return;
        }
        if (service.cost < 0){
            sendResult("Invalid cost");
            return;
        }
        if (service.date.trim().length() <= 0) {
            sendResult("Invalid data");
            return;
        }
        if(!checkCorrectDate(service.date)){
            return;
        }
        Log.d("233", "OKAY");
        dataActivity.data.putExtra("patient", service.patient);
        dataActivity.data.putExtra("doctor", service.doctor);
        dataActivity.data.putExtra("manipulation", service.manipulation);
        dataActivity.data.putExtra("cost", service.cost);
        dataActivity.data.putExtra("date", service.date);
        dataActivity.setResult(Activity.RESULT_OK, dataActivity.data);
        dataActivity.finish();
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

    public void fetchDataForAdapters(){
        PatientsGetter patientsGetter = new PatientsGetter(dataActivity.getApplicationContext());
        ArrayList<String> patientNames = patientsGetter.getNames();
        DoctorsGetter doctorsGetter = new DoctorsGetter(dataActivity.getApplicationContext());
        ArrayList<String> doctorNames = doctorsGetter.getNames();
        dataActivity.setAdaptersByData(patientNames, doctorNames);
    }

    @Override
    public void sendResult(String result)  {
        dataActivity.showError(result);
    }
}
