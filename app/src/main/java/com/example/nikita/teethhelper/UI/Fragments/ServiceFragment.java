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
import com.example.nikita.teethhelper.tableHelpers.DoctorsTable;
import com.example.nikita.teethhelper.tableHelpers.PatientsTable;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 19.05.2018.
 */

public class ServiceFragment extends Fragment {
    private CompositeDisposable mDisposables;
    @BindView(R.id.editTextDataDayOfService)
    EditText mEditTextDataDay;
    @BindView(R.id.editTextDataMonthOfService)
    EditText mEditTextDataMonth;
    @BindView(R.id.editTextDataYearOfService)
    EditText mEditTextDataYear;
    @BindView(R.id.spinnerPatientOfService)
    Spinner mSpinnerPatients;
    @BindView(R.id.spinnerDoctorOfService)
    Spinner mSpinnerDoctors;
    @BindView(R.id.editTextManipulationOfService)
    EditText mEditTextManipulation;
    @BindView(R.id.editTextCostOfService)
    EditText mEditTextCost;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_service, null);
        ButterKnife.bind(this, v);
        mDisposables = new CompositeDisposable();
        setAdapters();
        return v;
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
                        mSpinnerPatients.setAdapter(spinnerPatientAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }
                });
        mDisposables.add(disposable);
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
                        mSpinnerDoctors.setAdapter(spinnerDoctorsAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }
                });
        mDisposables.add(disposable);
    }

    private void setAdapters(){
        setPatientAdapter();
        setDoctorAdapter();
    }

    public Service getService(){
        String patient ="";
        try {
            patient = mSpinnerPatients.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
        String doctor = "";
        try {
            doctor = mSpinnerDoctors.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
        String manipulation = mEditTextManipulation.getText().toString();
        String date = "";
        if(mEditTextDataDay.getText().length()!=0
                && mEditTextDataMonth.length()!=0
                && mEditTextDataYear.length()!=0){
            date = mEditTextDataDay.getText().toString()+"."
                    + mEditTextDataMonth.getText().toString()+"."
                    + mEditTextDataYear.getText().toString();
        }
        float cost = -1;
        if(mEditTextCost.getText().length()!=0) {
            cost = Float.parseFloat(mEditTextCost.getText().toString());
        }
        Service service = new Service(manipulation, patient, doctor, cost, date);
        return service;
    }

    public void onStop() {
        super.onStop();
        mDisposables.clear();
    }
}
