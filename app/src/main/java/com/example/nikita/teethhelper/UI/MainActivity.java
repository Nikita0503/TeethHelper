package com.example.nikita.teethhelper.UI;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.nikita.teethhelper.DBHepler;
import com.example.nikita.teethhelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.imageViewTeeth)
    ImageView imageViewTeeth;
    @BindView(R.id.imageViewTeethCloud)
    ImageView imageViewTeethCloud;
    @OnClick(R.id.imageViewTeeth)
    void onClick(){
        //final CircleMenuView menu = (CircleMenuView) findViewById(R.id.circle_menu);
        if(editTextName.getText().length()==0) {
            Toasty.error(getApplicationContext(), "User not found!", Toast.LENGTH_SHORT, true).show();
            Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_shake_teeth);
            imageViewTeeth.startAnimation(translateAnimation);
            Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_cloud);
            imageViewTeeth.startAnimation(translateAnimation);
            imageViewTeethCloud.setVisibility(View.VISIBLE);
            imageViewTeethCloud.startAnimation(scaleAnimation);

            DBHepler dbHepler = new DBHepler(this);
            SQLiteDatabase db = dbHepler.getWritableDatabase();
            ContentValues cv = new ContentValues();

            /*VISITS
            cv.put("patient", "somePat");
            cv.put("date", "someDate");
            cv.put("service", "someServ");
            long rowID = db.insert("visits", null, cv);
            Log.d("LOG", "row inserted, ID = " + rowID);
            Cursor c = db.query("visits", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int patientColIndex = c.getColumnIndex("patient");
                int dateColIndex = c.getColumnIndex("date");
                int serviceColIndex = c.getColumnIndex("service");
                do {
                    // получаем значения по номерам столбцов и пишем все в лог
                    Log.d("sdf",
                            "patient = "
                                    + c.getString(patientColIndex) + ", doctor = "
                                    + c.getString(dateColIndex) + ", service = "
                                    + c.getString(serviceColIndex));
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false -
                    // выходим из цикла
                } while (c.moveToNext());
            } else
                Log.d("LOG", "0 rows");
            c.close();
            */

            /*RENDERS
            cv.put("patient", "somePat");
            cv.put("doctor", "someDoc");
            cv.put("sum", 12.5);
            cv.put("date", "someDate");
            cv.put("service", "someServ");

            long rowID = db.insert("renders", null, cv);
            Log.d("LOG", "row inserted, ID = " + rowID);
            Cursor c = db.query("renders", null, null, null, null, null, null);
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int patientColIndex = c.getColumnIndex("patient");
                int doctorColIndex = c.getColumnIndex("doctor");
                int sumColIndex = c.getColumnIndex("sum");
                int dateColIndex = c.getColumnIndex("date");
                int serviceColIndex = c.getColumnIndex("service");
                do {
                    // получаем значения по номерам столбцов и пишем все в лог
                    Log.d("sdf",
                            "patient = "
                                    + c.getString(patientColIndex) + ", doctor = "
                                    + c.getString(doctorColIndex) + ", sum = "
                                    + c.getString(sumColIndex) + ", date = "
                                    + c.getString(dateColIndex) + ", service = "
                                    + c.getString(serviceColIndex));
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false -
                    // выходим из цикла
                } while (c.moveToNext());
            } else
                Log.d("LOG", "0 rows");
            c.close();
            */


            /* SERVICE
            cv.put("patient", "somePat");
            cv.put("doctor", "someDoc");
            cv.put("date", "someDate");
            cv.put("cost", 12.4);
            cv.put("manipulation", "yes");
            long rowID = db.insert("service", null, cv);
            Log.d("LOG", "row inserted, ID = " + rowID);
            Cursor c = db.query("service", null, null, null, null, null, null);
            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int patientColIndex = c.getColumnIndex("patient");
                int doctorColIndex = c.getColumnIndex("doctor");
                int dateColIndex = c.getColumnIndex("date");
                int costColIndex = c.getColumnIndex("cost");
                int manipulationColIndex = c.getColumnIndex("manipulation");
                do {
                    // получаем значения по номерам столбцов и пишем все в лог
                    Log.d("sdf",
                            "patient = "
                                    + c.getString(patientColIndex) + ", doctor = "
                                    + c.getString(doctorColIndex) + ", date = "
                                    + c.getString(dateColIndex) + ", cost = "
                                    + c.getString(costColIndex) + ", manipulation = "
                                    + c.getString(manipulationColIndex));
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false -
                    // выходим из цикла
                } while (c.moveToNext());
            } else
                Log.d("LOG", "0 rows");
            c.close();
            */


            /* DOCTOR
            cv.put("name", "Dog");
            cv.put("passport", "654321");
            cv.put("address", "someAndress222");
            cv.put("specialization", "someSpec");
            cv.put("experience", 123);
            cv.put("berth", "someBerth");
            long rowID = db.insert("doctors", null, cv);
            Log.d("LOG", "row inserted, ID = " + rowID);
            Cursor c = db.query("doctors", null, null, null, null, null, null);
            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int idColIndex = c.getColumnIndex("code");
                int nameColIndex = c.getColumnIndex("name");
                int passportColIndex = c.getColumnIndex("passport");
                int addressColIndex = c.getColumnIndex("address");
                int specializationColIndex = c.getColumnIndex("specialization");
                int experienceColIndex = c.getColumnIndex("experience");
                int berthColIndex = c.getColumnIndex("berth");
                do {
                    // получаем значения по номерам столбцов и пишем все в лог
                    Log.d("sdf",
                            "ID = " + c.getInt(idColIndex) + ", name = "
                                    + c.getString(nameColIndex) + ", passport = "
                                    + c.getString(passportColIndex) + ", address = "
                                    + c.getString(addressColIndex) + ", specialization = "
                                    + c.getString(specializationColIndex) + ", experience = "
                                    + c.getString(experienceColIndex) + ", berth = "
                                    + c.getString(berthColIndex));
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false -
                    // выходим из цикла
                } while (c.moveToNext());
            } else
                Log.d("LOG", "0 rows");
            c.close();
            // READ
            */

            /* PATIENT
            // ADD
            cv.put("name", "Cat");
            cv.put("passport", "123456");
            cv.put("address", "someAndress");
            cv.put("disease", "someIll");
            //long rowID = db.insert("patients", null, cv);
            //Log.d("LOG", "row inserted, ID = " + rowID);
            // ADD

            //UPDATE
            cv.put("name", "sukablyat");
            // обновляем по id
            int updCount = db.update("patients", cv, "code = ?",
                    new String[] { "2" });
            Log.d("LOG", "updated rows count = " + updCount);
            //UPDATE

            // READ
            Cursor c = db.query("patients", null, null, null, null, null, null);
            // ставим позицию курсора на первую строку выборки
            // если в выборке нет строк, вернется false
            if (c.moveToFirst()) {
                // определяем номера столбцов по имени в выборке
                int idColIndex = c.getColumnIndex("code");
                int nameColIndex = c.getColumnIndex("name");
                int passportColIndex = c.getColumnIndex("passport");
                int addressColIndex = c.getColumnIndex("address");
                int diseaseColIndex = c.getColumnIndex("disease");
                do {
                    // получаем значения по номерам столбцов и пишем все в лог
                    Log.d("sdf",
                            "ID = " + c.getInt(idColIndex) + ", name = "
                                    + c.getString(nameColIndex) + ", passport = "
                                    + c.getString(passportColIndex) + ", address = "
                                    + c.getString(addressColIndex) + ", disease = "
                                    + c.getString(diseaseColIndex));
                    // переход на следующую строку
                    // а если следующей нет (текущая - последняя), то false -
                    // выходим из цикла
                } while (c.moveToNext());
            } else
                Log.d("LOG", "0 rows");
            c.close();
            // READ
            */





        }else {
            Toasty.success(getApplicationContext(), "Welcome!", Toast.LENGTH_SHORT, true).show();
            final Animation translateEditTextName = AnimationUtils.loadAnimation(this, R.anim.translate_edittext_name);
            editTextName.startAnimation(translateEditTextName);
            Animation translateEditTextPassword =  AnimationUtils.loadAnimation(this, R.anim.translate_edittext_password);
            editTextPassword.startAnimation(translateEditTextPassword);
            Animation translateImageViewTeethToDown =  AnimationUtils.loadAnimation(this, R.anim.translate_to_down_teeth);
            translateImageViewTeethToDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(MainActivity.this, TablesActivity.class);
                    startActivity(intent);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            imageViewTeeth.startAnimation(translateImageViewTeethToDown);
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageViewTeethCloud.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        imageViewTeethCloud.setVisibility(View.INVISIBLE);
    }
}
