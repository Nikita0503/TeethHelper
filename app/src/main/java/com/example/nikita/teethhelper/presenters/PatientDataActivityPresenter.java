package com.example.nikita.teethhelper.presenters;

import android.app.Activity;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.TableContract;
import com.example.nikita.teethhelper.UI.RecordActivities.PatientDataActivity;
import com.example.nikita.teethhelper.data.Patient;

/**
 * Created by Nikita on 07.05.2018.
 */

public class PatientDataActivityPresenter implements TableContract.TablePresenter {
    private PatientDataActivity mDataActivity;

    public PatientDataActivityPresenter(PatientDataActivity dataActivity){
        this.mDataActivity = dataActivity;
    }

    @Override
    public void checkData() {
        Patient patient = mDataActivity.getPatient();
        if (patient.name.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidName));
            return;
        }
        if (patient.passport.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidPassport));
            return;
        }
        if (patient.address.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidAddress));
            return;
        }
        if (patient.disease.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidDisease));
            return;
        }
        mDataActivity.data.putExtra("name", patient.name);
        mDataActivity.data.putExtra("passport", patient.passport);
        mDataActivity.data.putExtra("address", patient.address);
        mDataActivity.data.putExtra("disease", patient.disease);
        mDataActivity.setResult(Activity.RESULT_OK, mDataActivity.data);
        mDataActivity.finish();
    }

    @Override
    public void sendError(String result) {
        mDataActivity.showError(result);
    }
}
