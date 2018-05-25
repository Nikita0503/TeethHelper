package com.example.nikita.teethhelper.presenters;

import android.app.Activity;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.RecordActivities.DoctorDataActivity;
import com.example.nikita.teethhelper.data.Doctor;

/**
 * Created by Nikita on 24.04.2018.
 */

public class DoctorDataActivityPresenter implements defaultPresenter {
    DoctorDataActivity dataActivity;

    public DoctorDataActivityPresenter(DoctorDataActivity dataActivity){
        this.dataActivity = dataActivity;
    }

    @Override
    public void checkData() {
        Doctor doctor = dataActivity.getDoctor();
        if (doctor.name.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidName));
            return;
        }
        if (doctor.passport.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidPassport));
            return;
        }
        if (doctor.address.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidAddress));
            return;
        }
        if (doctor.specialization.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidSpecialization));
            return;
        }
        if (doctor.experience < 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidExperience));
            return;
        }
        if (doctor.berth.trim().length() <= 0){
            sendResult(dataActivity.getResources().getString(R.string.invalidBerth));
            return;
        }
        dataActivity.data.putExtra("name", doctor.name);
        dataActivity.data.putExtra("passport", doctor.passport);
        dataActivity.data.putExtra("address", doctor.address);
        dataActivity.data.putExtra("specialization", doctor.specialization);
        dataActivity.data.putExtra("experience", doctor.experience);
        dataActivity.data.putExtra("berth", doctor.berth);
        dataActivity.setResult(Activity.RESULT_OK, dataActivity.data);
        dataActivity.finish();
    }

    @Override
    public void sendResult(String result) {
        dataActivity.showError(result);
    }
}
