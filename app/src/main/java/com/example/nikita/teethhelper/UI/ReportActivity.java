package com.example.nikita.teethhelper.UI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nikita.teethhelper.Contract;
import com.example.nikita.teethhelper.R;
import com.example.nikita.teethhelper.UI.DateActivity;
import com.example.nikita.teethhelper.presenters.ListPresenter;
import com.example.nikita.teethhelper.presenters.ReportActivityPresenter;

import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class ReportActivity extends AppCompatActivity implements Contract.View {
    public static final int REQUEST_CODE_VISITS_FOR_PERIOD = 1;
    public static final int REQUEST_CODE_VISITS_STATISTIC = 2;
    public Intent date;
    private ReportActivityPresenter mReportActivityPresenter;

    @OnClick(R.id.buttonRendersOrder)
    void onClickRendersOrder() {
        mReportActivityPresenter.writeToFile("renders");
    }

    @OnClick(R.id.buttonDoctorsOrder)
    void onClickDoctorsOrder() {
        mReportActivityPresenter.writeToFile("doctors");
    }

    @OnClick(R.id.buttonPatientsOrder)
    void onClickPatientsOrder() {
        mReportActivityPresenter.writeToFile("patients");
    }

    @OnClick(R.id.buttonVisitsOrder)
    void onClickVisitsOrder() {
        Intent intent = new Intent(getApplicationContext(), DateActivity.class);
        startActivityForResult(intent, 1);
    }
    @OnClick(R.id.buttonVisitsStatisticOrder)
    void onClickVisitsStatisticOrder() {
        Intent intent = new Intent(getApplicationContext(), DateActivity.class);
        startActivityForResult(intent, 2);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
    }

    @Override
    public void onStart(){
        super.onStart();
        mReportActivityPresenter = new ReportActivityPresenter(this);
        mReportActivityPresenter.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == RESULT_OK) {
            date = intent;
            switch (requestCode) {
                case REQUEST_CODE_VISITS_FOR_PERIOD:
                    mReportActivityPresenter.writeToFile("visits for period");
                case REQUEST_CODE_VISITS_STATISTIC:
                    mReportActivityPresenter.writeToFile("visits statistic for period");
            }
        }
    }

    @Override
    public void showMessage(String result){
        Log.d("ERROR: ", result);
        Toasty.error(getApplicationContext(), result, Toast.LENGTH_SHORT, true).show();
    }

    @Override
    public void onStop(){
        super.onStop();
        mReportActivityPresenter.onStop();
    }
}
