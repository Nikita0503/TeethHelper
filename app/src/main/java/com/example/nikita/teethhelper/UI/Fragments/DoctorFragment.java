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
    EditText editTextName;
    @BindView(R.id.editTextPassportOfDoctor)
    EditText editTextPassport;
    @BindView(R.id.editTextAddressOfDoctor)
    EditText editTextAddress;
    @BindView(R.id.editTextSpecializationOfDoctor)
    EditText editTextSpecialization;
    @BindView(R.id.editTextExperienceOfDoctor)
    EditText editTextExperience;
    @BindView(R.id.editTextBerthOfDoctor)
    EditText editTextBerth;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_doctor, null);
        ButterKnife.bind(this, v);
        return v;
    }

    public Doctor getDoctor(){
        String name = editTextName.getText().toString();
        String passport = editTextPassport.getText().toString();
        String address = editTextAddress.getText().toString();
        String specialization = editTextSpecialization.getText().toString();
        int experience = -1;
        if(editTextExperience.getText().length()!=0) {
            experience = Integer.parseInt(editTextExperience.getText().toString());
        }
        String berth = editTextBerth.getText().toString();
        Doctor doctor = new Doctor(name, passport, address, specialization, experience, berth);
        return doctor;
    }
}
