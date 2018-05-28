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
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.presenters.RenderDataActivityPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class DoctorDataActivity extends AppCompatActivity implements TableContract.TableView{
    private DoctorDataActivityPresenter mPresenter;
    public Intent data;
    @BindView(R.id.editTextNameOfDoctor)
    EditText mEditTextName;
    @BindView(R.id.editTextPassportOfDoctor)
    EditText mEditTextPassport;
    @BindView(R.id.editTextAddressOfDoctor)
    EditText mEditTextAddress;
    @BindView(R.id.editTextSpecializationOfDoctor)
    EditText mEditTextSpecialization;
    @BindView(R.id.editTextExperienceOfDoctor)
    EditText mEditTextExperience;
    @BindView(R.id.editTextBerthOfDoctor)
    EditText mEditTextBerth;
    @BindView(R.id.buttonAddOfDoctor)
    Button mButtonAdd;
    @OnClick(R.id.buttonAddOfDoctor)
    void onClickAdd(){
        mPresenter.checkData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_data);
        ButterKnife.bind(this);
        data = getIntent();
        if(data.getIntExtra("code", -1) != -1){
            mEditTextName.setText(data.getStringExtra("oldName"));
            mEditTextPassport.setText(data.getStringExtra("oldPassport"));
            mEditTextAddress.setText(data.getStringExtra("oldAddress"));
            mEditTextSpecialization.setText(data.getStringExtra("oldSpecialization"));
            mEditTextExperience.setText(String.valueOf(data.getIntExtra("oldExperience", -1)));
            mEditTextBerth.setText(data.getStringExtra("oldBerth"));
            mButtonAdd.setText(getResources().getString(R.string.edit));
        }
        mPresenter = new DoctorDataActivityPresenter(this);
    }

    @Override
    public void showError(String result){
        Log.d("ERROR: ", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }

    public Doctor getDoctor(){
        String name = mEditTextName.getText().toString();
        String passport = mEditTextPassport.getText().toString();
        String address = mEditTextAddress.getText().toString();
        String specialization = mEditTextSpecialization.getText().toString();
        int experience = -1;
        if(mEditTextExperience.getText().length()!=0) {
            experience = Integer.parseInt(mEditTextExperience.getText().toString());
        }
        String berth = mEditTextBerth.getText().toString();
        Doctor doctor = new Doctor(name, passport, address, specialization, experience, berth);
        return doctor;
    }


}
