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
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;
import com.example.nikita.teethhelper.tableHelpers.ServicesTable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

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

    private void setServiceAdapter(){
        ServicesTable servicesTable = new ServicesTable(getActivity().getApplicationContext());
        Disposable disposable =  servicesTable.getManipulations.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> serviceManipulations) {
                        ArrayAdapter<String> spinnerServicesAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, serviceManipulations);
                        spinnerServicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerServices.setAdapter(spinnerServicesAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        //
                    }
                });
    }

    private void setPatientAdapter(){
        PatientsTable patientsTable = new PatientsTable(getActivity().getApplicationContext());
        Disposable disposable =  patientsTable.getNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> patientNames) {
                        ArrayAdapter<String> spinnerPatientAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, patientNames);
                        spinnerPatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerPatients.setAdapter(spinnerPatientAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        //
                    }
                });
    }

    private void setAdapters(){
        setServiceAdapter();
        setPatientAdapter();
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
