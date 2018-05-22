package com.example.nikita.teethhelper.UI.RecordActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Patient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class PatientDataActivity extends AppCompatActivity {
    public Intent data;

    @BindView(R.id.editTextNameOfPatient)
    EditText editTextName;
    @BindView(R.id.editTextPassportOfPatient)
    EditText editTextPassport;
    @BindView(R.id.editTextAddressOfPatient)
    EditText editTextAddress;
    @BindView(R.id.editTextDiseaseOfPatient)
    EditText editTextDisease;
    @BindView(R.id.buttonAddOfPatient)
    Button buttonAdd;
    @OnClick(R.id.buttonAddOfPatient)
    void onClickAdd(){
        PatientDataActivityPresenter presenter = new PatientDataActivityPresenter(this);
        presenter.checkData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);
        ButterKnife.bind(this);
        data = getIntent();
        if(data.getIntExtra("code", -1) != -1) {
            editTextName.setText(data.getStringExtra("oldName"));
            editTextPassport.setText(data.getStringExtra("oldPassport"));
            editTextAddress.setText(data.getStringExtra("oldAddress"));
            editTextDisease.setText(data.getStringExtra("oldDisease"));
            buttonAdd.setText("edit");
        }
    }

    public Patient getPatient(){  //ПОФИКСИТЬ ХЕРНЮ С ЧИСЛОВЫМИ ЗНАЧЕНИЯМИ ИЗ ЕДИТТЕКСТОВ У ДОКТОРА
        String name = editTextName.getText().toString();
        String passport = editTextPassport.getText().toString();
        String address = editTextAddress.getText().toString();
        String disease = editTextDisease.getText().toString();
        Patient patient = new Patient(name, passport, address, disease);
        return patient;
    }

    public void showError(String result){
        Log.d("444", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }
}
