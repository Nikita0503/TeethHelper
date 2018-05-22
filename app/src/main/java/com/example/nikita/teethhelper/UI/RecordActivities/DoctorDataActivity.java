package com.example.nikita.teethhelper.UI.RecordActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.data.Doctor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class DoctorDataActivity extends AppCompatActivity {
    public Intent data;

    @BindView(R.id.editTextNameOfDoctor)
    EditText editTextName;
    @BindView(R.id.editTextPassportOfDoctor)
    EditText editTextPassport;
    @BindView(R.id.editTextAddressOfDoctor)
    EditText editTextAddress;
    @BindView(R.id.editTextSpecializationOfDoctor)
    EditText editTextSpecialization;
    @BindView(R.id.editTextExperienceOfDoctor)
    EditText editTextExperience;
    @BindView(R.id.editTextBerthOfDoctor)
    EditText editTextBerth;
    @BindView(R.id.buttonAddOfDoctor)
    Button buttonAdd;
    @OnClick(R.id.buttonAddOfDoctor)
    void onClickAdd(){
        DoctorDataActivityPresenter presenter = new DoctorDataActivityPresenter(this);
        presenter.checkData();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_data);
        ButterKnife.bind(this);
        data = getIntent();
        if(data.getIntExtra("code", -1) != -1){
            editTextName.setText(data.getStringExtra("oldName"));
            editTextPassport.setText(data.getStringExtra("oldPassport"));
            editTextAddress.setText(data.getStringExtra("oldAddress"));
            editTextSpecialization.setText(data.getStringExtra("oldSpecialization"));
            editTextExperience.setText(String.valueOf(data.getIntExtra("oldExperience", -1)));
            editTextBerth.setText(data.getStringExtra("oldBerth"));
            buttonAdd.setText("edit");
        }
    }

    public Doctor getDoctor(){
        String name = editTextName.getText().toString();
        String passport = editTextPassport.getText().toString();
        String address = editTextAddress.getText().toString();
        String specialization = editTextSpecialization.getText().toString();
        int experience = -1;
        if(editTextExperience.getText().length()!=0) {
            experience = Integer.parseInt(editTextExperience.getText().toString());
        }
        String berth = editTextBerth.getText().toString();
        Doctor doctor = new Doctor(name, passport, address, specialization, experience, berth);
        return doctor;
    }

    public void showError(String result){
        Log.d("ERROR: ", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }
}
