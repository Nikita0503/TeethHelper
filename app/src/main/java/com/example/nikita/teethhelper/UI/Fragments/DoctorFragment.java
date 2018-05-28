package com.example.nikita.teethhelper.UI.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Doctor;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nikita on 19.05.2018.
 */

public class DoctorFragment extends Fragment {
    @BindView(R.id.editTextNameOfDoctor)
    EditText mEditTextName;
    @BindView(R.id.editTextPassportOfDoctor)
    EditText mEditTextPassport;
    @BindView(R.id.editTextAddressOfDoctor)
    EditText mEditTextAddress;
    @BindView(R.id.editTextSpecializationOfDoctor)
    EditText mEditTextSpecialization;
    @BindView(R.id.editTextExperienceOfDoctor)
    EditText mEditTextExperience;
    @BindView(R.id.editTextBerthOfDoctor)
    EditText mEditTextBerth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor, null);
        ButterKnife.bind(this, v);
        return v;
    }

    public Doctor getDoctor(){
        String name = mEditTextName.getText().toString();
        String passport = mEditTextPassport.getText().toString();
        String address = mEditTextAddress.getText().toString();
        String specialization = mEditTextSpecialization.getText().toString();
        int experience = -1;
        if(mEditTextExperience.getText().length()!=0) {
            experience = Integer.parseInt(mEditTextExperience.getText().toString());
        }
        String berth = mEditTextBerth.getText().toString();
        Doctor doctor = new Doctor(name, passport, address, specialization, experience, berth);
        return doctor;
    }
}
