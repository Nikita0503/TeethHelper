package com.example.nikita.teethhelper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.example.nikita.teethhelper.UI.DateActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ReportActivity extends AppCompatActivity {

    public Intent date;

    @OnClick(R.id.buttonRendersOrder)
    void onClickRendersOrder() {
        ReportActivityPresenter orderActivityPresenter = new ReportActivityPresenter(this);
        orderActivityPresenter.writeToFile("renders");
    }

    @OnClick(R.id.buttonDoctorsOrder)
    void onClickDoctorsOrder() {
        ReportActivityPresenter reportActivityPresenter = new ReportActivityPresenter(this);
        reportActivityPresenter.writeToFile("doctors");
    }

    @OnClick(R.id.buttonPatientsOrder)
    void onClickPatientsOrder() {
        ReportActivityPresenter orderActivityPresenter = new ReportActivityPresenter(this);
        orderActivityPresenter.writeToFile("patients");
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
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    date = intent;
                    ReportActivityPresenter reportActivityPresenter = new ReportActivityPresenter(this);
                    reportActivityPresenter.writeToFile("visits for period");
                case 2:
                    date = intent;
                    ReportActivityPresenter reportActivityPresenter2 = new ReportActivityPresenter(this);
                    reportActivityPresenter2.writeToFile("visits statistic for period");
            }
        }
    }
}
