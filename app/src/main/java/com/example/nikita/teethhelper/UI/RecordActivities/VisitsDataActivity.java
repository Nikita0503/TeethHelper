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
import com.example.nikita.teethhelper.data.Visit;

import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class VisitsDataActivity extends AppCompatActivity {
    public Intent data;

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
    @BindView(R.id.buttonAddOfVisit)
    Button buttonAdd;
    @OnClick(R.id.buttonAddOfVisit)
    void onClickAdd(){
        VisitsDataActivityPresenter presenter = new VisitsDataActivityPresenter(this);
        presenter.checkData();
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
                editTextDataDay.setText(date.nextToken());
                editTextDataMonth.setText(date.nextToken());
                editTextDataYear.setText(date.nextToken());
            }
            buttonAdd.setText("edit");
        }
        VisitsDataActivityPresenter presenter = new VisitsDataActivityPresenter(this);
        presenter.fetchDataForAdapters();
    }

    public void setAdaptersByData(ArrayList<String> patientNames, ArrayList<String> doctorNames){
        ArrayAdapter<String> spinnerPatientAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, patientNames);
        spinnerPatientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPatients.setAdapter(spinnerPatientAdapter);
        ArrayAdapter<String> spinnerServicesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, doctorNames);
        spinnerServicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(spinnerServicesAdapter);
        //spinnerServices.setSelection(1); через цикл узнать номер имени с списке и поставить этот номер в спиннер
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

    public void showError(String result){
        Log.d("444", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }
}
