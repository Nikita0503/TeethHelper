package com.example.nikita.teethhelper.UI.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Service;
import com.example.nikita.teethhelper.tables.DoctorsTable;
import com.example.nikita.teethhelper.tables.PatientsTable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nikita on 19.05.2018.
 */

public class ServiceFragment extends Fragment {
    @BindView(R.id.editTextDataDayOfService)
    EditText editTextDataDay;
    @BindView(R.id.editTextDataMonthOfService)
    EditText editTextDataMonth;
    @BindView(R.id.editTextDataYearOfService)
    EditText editTextDataYear;
    @BindView(R.id.spinnerPatientOfService)
    Spinner spinnerPatients;
    @BindView(R.id.spinnerDoctorOfService)
    Spinner spinnerDoctors;
    @BindView(R.id.editTextManipulationOfService)
    EditText editTextManipulation;
    @BindView(R.id.editTextCostOfService)
    EditText editTextCost;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service, null);
        ButterKnife.bind(this, v);
        setAdapters();
        return v;
    }

    private void setAdapters(){
        PatientsTable patientsTable = new PatientsTable(getActivity().getApplicationContext());
        ArrayList<String> patientNames = patientsTable.getNames();
        ArrayAdapter<String> spinnerPatientAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, patientNames);
        spinnerPatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatients.setAdapter(spinnerPatientAdapter);

        DoctorsTable doctorsTable = new DoctorsTable(getActivity().getApplicationContext());
        ArrayList<String> doctorNames = doctorsTable.getNames();
        ArrayAdapter<String> spinnerDoctorsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, doctorNames);
        spinnerDoctorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctors.setAdapter(spinnerDoctorsAdapter);
    }

    public Service getService(){
        String patient ="";
        try {
            patient = spinnerPatients.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
        String doctor = "";
        try {
            doctor = spinnerDoctors.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
        String manipulation = editTextManipulation.getText().toString();
        String date = "";
        if(editTextDataDay.getText().length()!=0
                && editTextDataMonth.length()!=0
                && editTextDataYear.length()!=0){
            date = editTextDataDay.getText().toString()+"."
                    +editTextDataMonth.getText().toString()+"."
                    +editTextDataYear.getText().toString();
        }
        float cost = -1;
        if(editTextCost.getText().length()!=0) {
            cost = Float.parseFloat(editTextCost.getText().toString());
        }
        Service service = new Service(manipulation, patient, doctor, cost, date);
        return service;
    }


}
