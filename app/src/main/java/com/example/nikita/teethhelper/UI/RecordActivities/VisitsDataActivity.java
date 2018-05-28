package com.example.nikita.teethhelper.UI.RecordActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.TableContract;
import com.example.nikita.teethhelper.presenters.ReportActivityPresenter;
import com.example.nikita.teethhelper.presenters.VisitsDataActivityPresenter;
import com.example.nikita.teethhelper.data.Visit;

import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class VisitsDataActivity extends AppCompatActivity  implements TableContract.TableView {
    public Intent data;
    private VisitsDataActivityPresenter mPresenter;
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
    @BindView(R.id.buttonAddOfVisit)
    Button mButtonAdd;
    @OnClick(R.id.buttonAddOfVisit)
    void onClickAdd(){
        mPresenter.checkData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visits_data);
        ButterKnife.bind(this);
        data = getIntent();
        if(data.getStringExtra("oldDate") != null) {
            StringTokenizer date = new StringTokenizer(data.getStringExtra("oldDate"), ".");
            while(date.hasMoreTokens()) {
                mEditTextDataDay.setText(date.nextToken());
                mEditTextDataMonth.setText(date.nextToken());
                mEditTextDataYear.setText(date.nextToken());
            }
            mButtonAdd.setText(getResources().getString(R.string.edit));
        }
        mPresenter = new VisitsDataActivityPresenter(this);
        mPresenter.onStart();
        mPresenter.fetchDataForAdapters();
    }

    @Override
    public void showError(String result){
        Log.d("ERROR: ", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
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

    public void setPatientAdapter(ArrayList<String> patientNames){
        ArrayAdapter<String> spinnerPatientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, patientNames);
        spinnerPatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPatients.setAdapter(spinnerPatientAdapter);
    }

    public void setServiceAdapter(ArrayList<String> serviceNames){
        ArrayAdapter<String> spinnerServicesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, serviceNames);
        spinnerServicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerServices.setAdapter(spinnerServicesAdapter);
    }

    @Override
    public void onStop(){
        super.onStop();
        mPresenter.onStop();
    }
}
