package com.example.nikita.teethhelper.UI.Fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.tableHelpers.DoctorsTable;
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

public class RenderFragment extends Fragment {

    @BindView(R.id.editTextDataDayOfRender)
    EditText editTextDataDay;
    @BindView(R.id.editTextDataMonthOfRender)
    EditText editTextDataMonth;
    @BindView(R.id.editTextDataYearOfRender)
    EditText editTextDataYear;
    @BindView(R.id.editTextSumOfRendrer)
    EditText editTextSum;
    @BindView(R.id.spinnerServiceOfRender)
    Spinner spinnerServices;
    @BindView(R.id.spinnerPatientOfRender)
    Spinner spinnerPatients;
    @BindView(R.id.spinnerDoctorOfRender)
    Spinner spinnerDoctors;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_render, null);
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

    private void setDoctorAdapter(){
        DoctorsTable doctorsTable = new DoctorsTable(getActivity().getApplicationContext());
        Disposable disposable =  doctorsTable.getNames.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<String>>() {
                    @Override
                    public void onSuccess(ArrayList<String> doctorNames) {
                        ArrayAdapter<String> spinnerDoctorsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, doctorNames);
                        spinnerDoctorsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerDoctors.setAdapter(spinnerDoctorsAdapter);
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
        setDoctorAdapter();
    }

    public Render getRender(){
        Log.d("213", "123");
        String service = "";
        try {
            service = spinnerServices.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
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
        float sum = -1;
        if(editTextSum.getText().length()!=0) {
            sum = Float.parseFloat(editTextSum.getText().toString());
        }
        String date = "";
        if(editTextDataDay.getText().length()!=0
                && editTextDataMonth.length()!=0
                && editTextDataYear.length()!=0){
            date = editTextDataDay.getText().toString()+"."
                    +editTextDataMonth.getText().toString()+"."
                    +editTextDataYear.getText().toString();
        }
        Render render = new Render(service, patient, doctor, sum, date);
        return render;
    }
}
