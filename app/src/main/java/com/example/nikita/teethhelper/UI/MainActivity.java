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
        if(editTextName.getText().length()==0) {
            Toasty.error(getApplicationContext(), "User not found!", Toast.LENGTH_SHORT, true).show();
            Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_shake_teeth);
            imageViewTeeth.startAnimation(translateAnimation);
            Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_cloud);
            imageViewTeeth.startAnimation(translateAnimation);
            imageViewTeethCloud.setVisibility(View.VISIBLE);
            imageViewTeethCloud.startAnimation(scaleAnimation);
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
