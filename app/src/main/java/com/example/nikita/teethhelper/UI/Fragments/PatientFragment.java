package com.example.nikita.teethhelper.UI.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Patient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Nikita on 19.05.2018.
 */

public class PatientFragment extends Fragment {
    @BindView(R.id.editTextNameOfPatient)
    EditText mEditTextName;
    @BindView(R.id.editTextPassportOfPatient)
    EditText mEditTextPassport;
    @BindView(R.id.editTextAddressOfPatient)
    EditText mEditTextAddress;
    @BindView(R.id.editTextDiseaseOfPatient)
    EditText mEditTextDisease;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_patient, null);
        ButterKnife.bind(this, v);
        return v;
    }

    public Patient getPatient(){
        String name = mEditTextName.getText().toString();
        String passport = mEditTextPassport.getText().toString();
        String address = mEditTextAddress.getText().toString();
        String disease = mEditTextDisease.getText().toString();
        Patient patient = new Patient(name, passport, address, disease);
        return patient;
    }
}
