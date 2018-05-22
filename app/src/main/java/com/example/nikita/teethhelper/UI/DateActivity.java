package com.example.nikita.teethhelper.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

import com.example.nikita.teethhelper.OrderActivity;
import com.example.nikita.teethhelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

/**
 * Created by Nikita on 22.05.2018.
 */

public class DateActivity extends AppCompatActivity {
    @BindView(R.id.editTextDataDayAfter)
    EditText editTextDataDayAfter;
    @BindView(R.id.editTextDataMonthAfter)
    EditText editTextDataMonthAfter;
    @BindView(R.id.editTextDataYearAfter)
    EditText editTextDataYearAfter;
    @BindView(R.id.editTextDataDayBefore)
    EditText editTextDataDayBefore;
    @BindView(R.id.editTextDataMonthBefore)
    EditText editTextDataMonthBefore;
    @BindView(R.id.editTextDataYearBefore)
    EditText editTextDataYearBefore;
    @OnClick(R.id.buttonGetDates)
    void onClick() {
        Intent data = getIntent();
        String dateAfter = "";
        if(editTextDataDayAfter.getText().length()!=0
                && editTextDataMonthAfter.length()!=0
                && editTextDataYearAfter.length()!=0){
            if(Integer.parseInt(editTextDataMonthAfter.getText().toString()) > 0
                    && Integer.parseInt(editTextDataMonthAfter.getText().toString()) <= 12) {
                if(Integer.parseInt(editTextDataDayAfter.getText().toString()) > 0
                        && Integer.parseInt(editTextDataDayAfter.getText().toString()) <= 31) {
                    dateAfter = editTextDataDayAfter.getText().toString() + "."
                            + editTextDataMonthAfter.getText().toString() + "."
                            + editTextDataYearAfter.getText().toString();
                }else{
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.invalidAfterDate), Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                Toasty.error(getApplicationContext(), getResources().getString(R.string.invalidAfterDate), Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalidAfterDate), Toast.LENGTH_SHORT).show();
            return;
        }
        String dateBefore = "";
        if(editTextDataDayBefore.getText().length()!=0
                && editTextDataMonthBefore.length()!=0
                && editTextDataYearBefore.length()!=0){
            if(Integer.parseInt(editTextDataMonthBefore.getText().toString()) > 0
                    && Integer.parseInt(editTextDataMonthBefore.getText().toString()) <= 12) {
                if(Integer.parseInt(editTextDataDayBefore.getText().toString()) > 0
                        && Integer.parseInt(editTextDataDayBefore.getText().toString()) <= 31) {
                            dateBefore = editTextDataDayBefore.getText().toString() + "."
                            + editTextDataMonthBefore.getText().toString() + "."
                            + editTextDataYearBefore.getText().toString();
                }else{
                    Toasty.error(getApplicationContext(), getResources().getString(R.string.invalidBeforeDate), Toast.LENGTH_SHORT).show();
                    return;
                }
            }else{
                Toasty.error(getApplicationContext(), getResources().getString(R.string.invalidBeforeDate), Toast.LENGTH_SHORT).show();
                return;
            }
        }else {
            Toasty.error(getApplicationContext(), getResources().getString(R.string.invalidBeforeDate), Toast.LENGTH_SHORT).show();
            return;
        }
        data.putExtra("dateAfter", dateAfter);
        data.putExtra("dateBefore", dateBefore);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date);
        ButterKnife.bind(this);
    }

}
