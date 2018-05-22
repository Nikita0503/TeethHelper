package com.example.nikita.teethhelper.UI.RecordActivities;

import android.app.Activity;
import android.util.Log;

import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.defaultPresenter;

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
            sendResult("Invalid name");
            return;
        }
        if (doctor.passport.trim().length() <= 0){
            sendResult("Invalid passport");
            return;
        }
        if (doctor.address.trim().length() <= 0){
            sendResult("Invalid address");
            return;
        }
        if (doctor.specialization.trim().length() <= 0){
            sendResult("Invalid specialization");
            return;
        }
        if (doctor.experience < 0){
            sendResult("Invalid experience");
            return;
        }
        if (doctor.berth.trim().length() <= 0){
            sendResult("Invalid berth");
            return;
        }
        Log.d("233", "OKAY");
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
