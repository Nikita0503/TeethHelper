package com.example.nikita.teethhelper.presenters;

import android.app.Activity;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.RecordActivities.PatientDataActivity;
import com.example.nikita.teethhelper.data.Patient;

/**
 * Created by Nikita on 07.05.2018.
 */

public class PatientDataActivityPresenter implements defaultPresenter {
    PatientDataActivity dataActivity;

    public PatientDataActivityPresenter(PatientDataActivity dataActivity){
        this.dataActivity = dataActivity;
    }
    @Override
    public void checkData() {
        Patient patient = dataActivity.getPatient();
        if (patient.name.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidName));
            return;
        }
        if (patient.passport.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidPassport));
            return;
        }
        if (patient.address.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidAddress));
            return;
        }
        if (patient.disease.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidDisease));
            return;
        }
        dataActivity.data.putExtra("name", patient.name);
        dataActivity.data.putExtra("passport", patient.passport);
        dataActivity.data.putExtra("address", patient.address);
        dataActivity.data.putExtra("disease", patient.disease);
        dataActivity.setResult(Activity.RESULT_OK, dataActivity.data);
        dataActivity.finish();
    }

    @Override
    public void sendResult(String result) {
        dataActivity.showError(result);
    }
}
