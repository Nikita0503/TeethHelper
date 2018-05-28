package com.example.nikita.teethhelper.presenters;

import android.app.Activity;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.TableContract;
import com.example.nikita.teethhelper.UI.RecordActivities.DoctorDataActivity;
import com.example.nikita.teethhelper.data.Doctor;

/**
 * Created by Nikita on 24.04.2018.
 */

public class DoctorDataActivityPresenter implements TableContract.TablePresenter {
    private DoctorDataActivity mDataActivity;

    public DoctorDataActivityPresenter(DoctorDataActivity dataActivity){
        this.mDataActivity = dataActivity;
    }

    @Override
    public void checkData() {
        Doctor doctor = mDataActivity.getDoctor();
        if (doctor.name.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidName));
            return;
        }
        if (doctor.passport.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidPassport));
            return;
        }
        if (doctor.address.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidAddress));
            return;
        }
        if (doctor.specialization.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidSpecialization));
            return;
        }
        if (doctor.experience < 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidExperience));
            return;
        }
        if (doctor.berth.trim().length() <= 0){
            sendError(mDataActivity.getResources().getString(R.string.invalidBerth));
            return;
        }
        mDataActivity.data.putExtra("name", doctor.name);
        mDataActivity.data.putExtra("passport", doctor.passport);
        mDataActivity.data.putExtra("address", doctor.address);
        mDataActivity.data.putExtra("specialization", doctor.specialization);
        mDataActivity.data.putExtra("experience", doctor.experience);
        mDataActivity.data.putExtra("berth", doctor.berth);
        mDataActivity.setResult(Activity.RESULT_OK, mDataActivity.data);
        mDataActivity.finish();
    }

    @Override
    public void sendError(String result) {
        mDataActivity.showError(result);
    }
}
