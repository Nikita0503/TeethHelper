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
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.getters.DoctorsGetter;
import com.example.nikita.teethhelper.getters.PatientsGetter;
import com.example.nikita.teethhelper.getters.ServicesGetter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Nikita on 19.05.2018.
 */

public class VisitFragment extends Fragment {
    @BindView(R.id.editTextDataDayOfVisits)
    EditText editTextDataDay;
    @BindView(R.id.editTextDataMonthOfVisits)
    EditText editTextDataMonth;
    @BindView(R.id.editTextDataYearOfVisits)
    EditText editTextDataYear;
    @BindView(R.id.spinnerPatientOfVisit)
    Spinner spinnerPatients;
    @BindView(R.id.spinnerServiceOfVisit)
    Spinner spinnerServices;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visit, null);
        ButterKnife.bind(this, v);
        setAdapters();
        return v;
    }

    private void setAdapters(){
        ServicesGetter servicesGetter = new ServicesGetter(getActivity().getApplicationContext());
        ArrayList<String> servicesNames = servicesGetter.getNames();
        ArrayAdapter<String> spinnerServicesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, servicesNames);
        spinnerServicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(spinnerServicesAdapter);

        PatientsGetter patientsGetter = new PatientsGetter(getActivity().getApplicationContext());
        ArrayList<String> patientNames = patientsGetter.getNames();
        ArrayAdapter<String> spinnerPatientAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, patientNames);
        spinnerPatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatients.setAdapter(spinnerPatientAdapter);
    }

    public Visit getVisit(){
        String patient ="";
        try {
            patient = spinnerPatients.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
        String service = "";
        try {
            service = spinnerServices.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
        String date = "";
        if(editTextDataDay.getText().length()!=0
                && editTextDataMonth.length()!=0
                && editTextDataYear.length()!=0){
            date = editTextDataDay.getText().toString()+"."
                    +editTextDataMonth.getText().toString()+"."
                    +editTextDataYear.getText().toString();
        }
        Visit visit = new Visit(patient, date, service);
        return visit;
    }
}
