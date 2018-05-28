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
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Nikita on 19.05.2018.
 */

public class VisitFragment extends Fragment {
    private CompositeDisposable mDisposables;
    @BindView(R.id.editTextDataDayOfVisits)
    EditText mEditTextDataDay;
    @BindView(R.id.editTextDataMonthOfVisits)
    EditText mEditTextDataMonth;
    @BindView(R.id.editTextDataYearOfVisits)
    EditText mEditTextDataYear;
    @BindView(R.id.spinnerPatientOfVisit)
    Spinner mSpinnerPatients;
    @BindView(R.id.spinnerServiceOfVisit)
    Spinner mSpinnerServices;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visit, null);
        ButterKnife.bind(this, v);
        mDisposables = new CompositeDisposable();
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
                        mSpinnerServices.setAdapter(spinnerServicesAdapter);
                    }
                    @Override
                    public void onError(Throwable e) {
                        /*ignore*/
                    }
                });
        mDisposables.add(disposable);
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

    private void setAdapters(){
        setServiceAdapter();
        setPatientAdapter();
    }

    public Visit getVisit(){
        String patient ="";
        try {
            patient = mSpinnerPatients.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
        String service = "";
        try {
            service = mSpinnerServices.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
        String date = "";
        if(mEditTextDataDay.getText().length()!=0
                && mEditTextDataMonth.length()!=0
                && mEditTextDataYear.length()!=0){
            date = mEditTextDataDay.getText().toString()+"."
                    + mEditTextDataMonth.getText().toString()+"."
                    + mEditTextDataYear.getText().toString();
        }
        Visit visit = new Visit(patient, date, service);
        return visit;
    }

    public void onStop() {
        super.onStop();
        mDisposables.clear();
    }
}
