package com.example.nikita.teethhelper.UI.RecordActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.TableContract;
import com.example.nikita.teethhelper.presenters.DoctorDataActivityPresenter;
import com.example.nikita.teethhelper.presenters.PatientDataActivityPresenter;
import com.example.nikita.teethhelper.data.Patient;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class PatientDataActivity extends AppCompatActivity implements TableContract.TableView{
    private PatientDataActivityPresenter mPresenter;
    public Intent data;
    @BindView(R.id.editTextNameOfPatient)
    EditText mEditTextName;
    @BindView(R.id.editTextPassportOfPatient)
    EditText mEditTextPassport;
    @BindView(R.id.editTextAddressOfPatient)
    EditText mEditTextAddress;
    @BindView(R.id.editTextDiseaseOfPatient)
    EditText mEditTextDisease;
    @BindView(R.id.buttonAddOfPatient)
    Button mButtonAdd;
    @OnClick(R.id.buttonAddOfPatient)
    void onClickAdd(){
        mPresenter.checkData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_data);
        ButterKnife.bind(this);
        data = getIntent();
        if(data.getIntExtra("code", -1) != -1) {
            mEditTextName.setText(data.getStringExtra("oldName"));
            mEditTextPassport.setText(data.getStringExtra("oldPassport"));
            mEditTextAddress.setText(data.getStringExtra("oldAddress"));
            mEditTextDisease.setText(data.getStringExtra("oldDisease"));
            mButtonAdd.setText(getResources().getString(R.string.edit));
        }
        mPresenter = new PatientDataActivityPresenter(this);
    }

    @Override
    public void showError(String result){
        Log.d("ERROR: ", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }

    public Patient getPatient(){
        String name = mEditTextName.getText().toString();
        String passport = mEditTextPassport.getText().toString();
        String address = mEditTextAddress.getText().toString();
        String disease = mEditTextDisease.getText().toString();
        Patient patient = new Patient(name, passport, address, disease);
        return patient;
    }
}
