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
import com.example.nikita.teethhelper.presenters.ServiceDataActivityPresenter;
import com.example.nikita.teethhelper.data.Service;

import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ServiceDataActivity extends AppCompatActivity implements TableContract.TableView {
    private ServiceDataActivityPresenter mPresenter;
    public Intent data;
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
    @BindView(R.id.buttonAddOfService)
    Button mButtonAdd;
    @OnClick(R.id.buttonAddOfService)
    void onClickAdd(){
        mPresenter.checkData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) { //в бд засунуть контекст везде, пофиксить все в спинерах
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_data);
        ButterKnife.bind(this);
        data = getIntent();
        if(data.getFloatExtra("oldCost", -1) != -1) {
            mEditTextManipulation.setText(data.getStringExtra("oldManipulation"));
            mEditTextCost.setText(String.valueOf(data.getFloatExtra("oldCost", 0)));
            StringTokenizer date = new StringTokenizer(data.getStringExtra("oldDate"), ".");
            while(date.hasMoreTokens()) {
                mEditTextDataDay.setText(date.nextToken());
                mEditTextDataMonth.setText(date.nextToken());
                mEditTextDataYear.setText(date.nextToken());
            }
            mButtonAdd.setText(getResources().getString(R.string.edit));
        }
        mPresenter = new ServiceDataActivityPresenter(this);
        mPresenter.onStart();
        mPresenter.fetchDataForAdapters();
    }

    @Override
    public void showError(String result){
        Log.d("ERROR: ", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
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

    public void setPatientAdapter(ArrayList<String> patientNames){
        ArrayAdapter<String> spinnerPatientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, patientNames);
        spinnerPatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPatients.setAdapter(spinnerPatientAdapter);
    }

    public void setDoctorAdapter(ArrayList<String> doctorNames){
        ArrayAdapter<String> spinnerDoctorAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, doctorNames);
        spinnerDoctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerDoctors.setAdapter(spinnerDoctorAdapter);
    }

    @Override
    public void onStop(){
        super.onStop();
        mPresenter.onStop();
    }
}
