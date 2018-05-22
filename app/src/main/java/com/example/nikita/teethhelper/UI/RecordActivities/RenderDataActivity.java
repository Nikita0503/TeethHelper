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
import com.example.nikita.teethhelper.presenters.RenderDataActivityPresenter;
import com.example.nikita.teethhelper.data.Render;

import java.util.ArrayList;
import java.util.StringTokenizer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class RenderDataActivity extends AppCompatActivity {
    public Intent data;

    @BindView(R.id.editTextDataDayOfRender)
    EditText editTextDataDay;
    @BindView(R.id.editTextDataMonthOfRender)
    EditText editTextDataMonth;
    @BindView(R.id.editTextDataYearOfRender)
    EditText editTextDataYear;
    @BindView(R.id.editTextSumOfRendrer)
    EditText editTextSum;
    @BindView(R.id.spinnerServiceOfRender)
    Spinner spinnerServices;
    @BindView(R.id.spinnerPatientOfRender)
    Spinner spinnerPatients;
    @BindView(R.id.spinnerDoctorOfRender)
    Spinner spinnerDoctors;
    @BindView(R.id.buttonAddOfRender)
    Button buttonAdd;
    @OnClick(R.id.buttonAddOfRender)
    void onClickAdd(){
        RenderDataActivityPresenter presenter = new RenderDataActivityPresenter(this);
        presenter.checkData();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_render_data);
        ButterKnife.bind(this);
        data = getIntent();
        RenderDataActivityPresenter presenter = new RenderDataActivityPresenter(this);
        presenter.fetchDataForAdapters();
        if(data.getFloatExtra("oldSum", -1) != -1) {
            editTextSum.setText(String.valueOf(data.getFloatExtra("oldSum", -1)));
            StringTokenizer date = new StringTokenizer(data.getStringExtra("oldDate"), ".");
            while(date.hasMoreTokens()) {
                editTextDataDay.setText(date.nextToken());
                editTextDataMonth.setText(date.nextToken());
                editTextDataYear.setText(date.nextToken());
            }
            buttonAdd.setText("edit");

        }
    }

    public Render getRender(){
        String service = "";
        try {
            service = spinnerServices.getSelectedItem().toString();
        }catch (Exception c){
            c.printStackTrace();
        }
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
        float sum = -1;
        if(editTextSum.getText().length()!=0) {
            sum = Float.parseFloat(editTextSum.getText().toString());
        }
        String date = "";
        if(editTextDataDay.getText().length()!=0
                && editTextDataMonth.length()!=0
                && editTextDataYear.length()!=0){
            date = editTextDataDay.getText().toString()+"."
                    +editTextDataMonth.getText().toString()+"."
                    +editTextDataYear.getText().toString();
        }
        Render render = new Render(service, patient, doctor, sum, date);
        return render;
    }

    public void setAdaptersByData(ArrayList<String> serviceNames, ArrayList<String> patientNames, ArrayList<String> doctorNames){
        ArrayAdapter<String> spinnerServicesAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, serviceNames);
        spinnerServicesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerServices.setAdapter(spinnerServicesAdapter);
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
        Log.d("ERROR: ", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }
}
