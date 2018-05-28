package com.example.nikita.teethhelper.UI;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.nikita.teethhelper.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.editTextName)
    EditText mEditTextName;
    @BindView(R.id.editTextPassword)
    EditText mEditTextPassword;
    @BindView(R.id.imageViewTeeth)
    ImageView mImageViewTeeth;
    @BindView(R.id.imageViewTeethCloud)
    ImageView mImageViewTeethCloud;
    @OnClick(R.id.imageViewTeeth)
    void onClick(){
        if(mEditTextName.getText().toString().equals("admin") && mEditTextPassword.getText().toString().equals("admin")) {
            Toasty.success(getApplicationContext(), getResources().getString(R.string.welcome), Toast.LENGTH_SHORT, true).show();
            final Animation translateEditTextName = AnimationUtils.loadAnimation(this, R.anim.translate_edittext_name);
            mEditTextName.startAnimation(translateEditTextName);
            Animation translateEditTextPassword =  AnimationUtils.loadAnimation(this, R.anim.translate_edittext_password);
            mEditTextPassword.startAnimation(translateEditTextPassword);
            Animation translateImageViewTeethToDown =  AnimationUtils.loadAnimation(this, R.anim.translate_to_down_teeth);
            translateImageViewTeethToDown.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    /*ignore*/
                }
                @Override
                public void onAnimationEnd(Animation animation) {
                    Intent intent = new Intent(MainActivity.this, TablesActivity.class);
                    startActivity(intent);
                }
                @Override
                public void onAnimationRepeat(Animation animation) {
                    /*ignore*/
                }
            });
            mImageViewTeeth.startAnimation(translateImageViewTeethToDown);
        }else{
            Toasty.error(getApplicationContext(), getResources().getString(R.string.accessDenied), Toast.LENGTH_SHORT, true).show();
            Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate_shake_teeth);
            mImageViewTeeth.startAnimation(translateAnimation);
            Animation scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_cloud);
            mImageViewTeeth.startAnimation(translateAnimation);
            mImageViewTeethCloud.setVisibility(View.VISIBLE);
            mImageViewTeethCloud.startAnimation(scaleAnimation);
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mImageViewTeethCloud.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mImageViewTeethCloud.setVisibility(View.INVISIBLE);
    }
}
