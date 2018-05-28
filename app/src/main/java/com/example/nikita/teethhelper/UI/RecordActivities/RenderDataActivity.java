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
import com.example.nikita.teethhelper.presenters.RenderDataActivityPresenter;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.presenters.ServiceDataActivityPresenter;

import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class RenderDataActivity extends AppCompatActivity implements TableContract.TableView{
    private RenderDataActivityPresenter mPresenter;
    public Intent data;
    @BindView(R.id.editTextDataDayOfRender)
    EditText mEditTextDataDay;
    @BindView(R.id.editTextDataMonthOfRender)
    EditText mEditTextDataMonth;
    @BindView(R.id.editTextDataYearOfRender)
    EditText mEditTextDataYear;
    @BindView(R.id.editTextSumOfRendrer)
    EditText mEditTextSum;
    @BindView(R.id.spinnerServiceOfRender)
    Spinner mSpinnerServices;
    @BindView(R.id.spinnerPatientOfRender)
    Spinner mSpinnerPatients;
    @BindView(R.id.spinnerDoctorOfRender)
    Spinner mSpinnerDoctors;
    @BindView(R.id.buttonAddOfRender)
    Button mButtonAdd;
    @OnClick(R.id.buttonAddOfRender)
    void onClickAdd(){
        mPresenter.checkData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_data);
        ButterKnife.bind(this);
        data = getIntent();
        if(data.getFloatExtra("oldSum", -1) != -1) {
            mEditTextSum.setText(String.valueOf(data.getFloatExtra("oldSum", -1)));
            StringTokenizer date = new StringTokenizer(data.getStringExtra("oldDate"), ".");
            while(date.hasMoreTokens()) {
                mEditTextDataDay.setText(date.nextToken());
                mEditTextDataMonth.setText(date.nextToken());
                mEditTextDataYear.setText(date.nextToken());
            }
            mButtonAdd.setText(getResources().getString(R.string.edit));
        }
        mPresenter = new RenderDataActivityPresenter(this);
        mPresenter.onStart();
        mPresenter.fetchDataForAdapters();
    }


    @Override
    public void showError(String result){
        Log.d("ERROR: ", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }

    public Render getRender(){
        String service = "";
        try {
            service = mSpinnerServices.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
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
        float sum = -1;
        if(mEditTextSum.getText().length()!=0) {
            sum = Float.parseFloat(mEditTextSum.getText().toString());
        }
        String date = "";
        if(mEditTextDataDay.getText().length()!=0
                && mEditTextDataMonth.length()!=0
                && mEditTextDataYear.length()!=0){
            date = mEditTextDataDay.getText().toString()+"."
                    + mEditTextDataMonth.getText().toString()+"."
                    + mEditTextDataYear.getText().toString();
        }
        Render render = new Render(service, patient, doctor, sum, date);
        return render;
    }

    public void setServiceAdapter(ArrayList<String> serviceNames){
        ArrayAdapter<String> spinnerServicesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, serviceNames);
        spinnerServicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerServices.setAdapter(spinnerServicesAdapter);
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
