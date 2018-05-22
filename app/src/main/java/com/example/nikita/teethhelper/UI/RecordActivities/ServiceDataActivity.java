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
import com.example.nikita.teethhelper.data.Service;

import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ServiceDataActivity extends AppCompatActivity {
    public Intent data;
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
    @BindView(R.id.buttonAddOfService)
    Button buttonAdd;
    @OnClick(R.id.buttonAddOfService)
    void onClickAdd(){
        ServiceDataActivityPresenter presenter = new ServiceDataActivityPresenter(this);
        presenter.checkData();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) { //в бд засунуть контекст везде, пофиксить все в спинерах
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_data);
        ButterKnife.bind(this);
        data = getIntent();
        if(data.getFloatExtra("oldCost", -1) != -1) {
            editTextManipulation.setText(data.getStringExtra("oldManipulation"));
            editTextCost.setText(String.valueOf(data.getFloatExtra("oldCost", 0)));
            Log.d("#!@#!@#@#!@#", data.getStringExtra("oldManipulation"));
            Log.d("#!@#!@#@#!@#", data.getStringExtra("oldPatient"));
            Log.d("#!@#!@#@#!@#", data.getStringExtra("oldDoctor"));
            Log.d("#!@#!@#@#!@#", data.getFloatExtra("oldCost", 0)+"");
            Log.d("#!@#!@#@#!@#", data.getStringExtra("oldDate"));


            StringTokenizer date = new StringTokenizer(data.getStringExtra("oldDate"), ".");
            while(date.hasMoreTokens()) {
                editTextDataDay.setText(date.nextToken());
                editTextDataMonth.setText(date.nextToken());
                editTextDataYear.setText(date.nextToken());
            }
            buttonAdd.setText("edit");
        }
        ServiceDataActivityPresenter presenter = new ServiceDataActivityPresenter(this);
        presenter.fetchDataForAdapters();
    }

    public void setAdaptersByData(ArrayList<String> patientNames, ArrayList<String> doctorNames){
        ArrayAdapter<String> spinnerPatientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, patientNames);
        spinnerPatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatients.setAdapter(spinnerPatientAdapter);
        ArrayAdapter<String> spinnerDoctorAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, doctorNames);
        spinnerDoctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDoctors.setAdapter(spinnerDoctorAdapter);
    }



    public void showError(String result){
        Log.d("444", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
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

    @Override
    protected void onDestroy(){
        super.onDestroy();
        //ArrayAdapter<String>.flag = false;
    }
}
