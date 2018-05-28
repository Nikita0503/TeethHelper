package com.example.nikita.teethhelper.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.Toast;

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
    EditText mEditTextDataDayAfter;
    @BindView(R.id.editTextDataMonthAfter)
    EditText mEditTextDataMonthAfter;
    @BindView(R.id.editTextDataYearAfter)
    EditText mEditTextDataYearAfter;
    @BindView(R.id.editTextDataDayBefore)
    EditText mEditTextDataDayBefore;
    @BindView(R.id.editTextDataMonthBefore)
    EditText mEditTextDataMonthBefore;
    @BindView(R.id.editTextDataYearBefore)
    EditText mEditTextDataYearBefore;
    @OnClick(R.id.buttonGetDates)
    void onClick() {
        Intent data = getIntent();
        String dateAfter = "";
        if(mEditTextDataDayAfter.getText().length()!=0
                && mEditTextDataMonthAfter.length()!=0
                && mEditTextDataYearAfter.length()!=0){
            if(Integer.parseInt(mEditTextDataMonthAfter.getText().toString()) > 0
                    && Integer.parseInt(mEditTextDataMonthAfter.getText().toString()) <= 12) {
                if(Integer.parseInt(mEditTextDataDayAfter.getText().toString()) > 0
                        && Integer.parseInt(mEditTextDataDayAfter.getText().toString()) <= 31) {
                    dateAfter = mEditTextDataDayAfter.getText().toString() + "."
                            + mEditTextDataMonthAfter.getText().toString() + "."
                            + mEditTextDataYearAfter.getText().toString();
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
        if(mEditTextDataDayBefore.getText().length()!=0
                && mEditTextDataMonthBefore.length()!=0
                && mEditTextDataYearBefore.length()!=0){
            if(Integer.parseInt(mEditTextDataMonthBefore.getText().toString()) > 0
                    && Integer.parseInt(mEditTextDataMonthBefore.getText().toString()) <= 12) {
                if(Integer.parseInt(mEditTextDataDayBefore.getText().toString()) > 0
                        && Integer.parseInt(mEditTextDataDayBefore.getText().toString()) <= 31) {
                            dateBefore = mEditTextDataDayBefore.getText().toString() + "."
                            + mEditTextDataMonthBefore.getText().toString() + "."
                            + mEditTextDataYearBefore.getText().toString();
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
