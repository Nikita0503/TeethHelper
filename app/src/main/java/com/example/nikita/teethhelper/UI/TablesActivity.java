package com.example.nikita.teethhelper.UI;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.nikita.teethhelper.R;
import com.ramotion.circlemenu.CircleMenuView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TablesActivity extends AppCompatActivity {
    @BindView(R.id.buttonCreateRequest)
    Button buttonCreateRequest;
    @BindView(R.id.buttonCreateOrder)
    Button getButtonCreateOrder;
    @BindView(R.id.circle_menu)
    CircleMenuView menu;
    @OnClick(R.id.buttonCreateRequest)
    void onClickRequest() {
        Intent intent = new Intent(getApplicationContext(), RequestActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.buttonCreateOrder)
    void onClickOrder() {
        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        ButterKnife.bind(this);
        menu.setEventListener(new CircleMenuView.EventListener() {
            @Override
            public void onMenuOpenAnimationStart(@NonNull CircleMenuView view) {

            }

            @Override
            public void onMenuOpenAnimationEnd(@NonNull CircleMenuView view) {

            }

            @Override
            public void onMenuCloseAnimationStart(@NonNull CircleMenuView view) {

            }

            @Override
            public void onMenuCloseAnimationEnd(@NonNull CircleMenuView view) {

            }

            @Override
            public void onButtonClickAnimationStart(@NonNull CircleMenuView view, int index) {

            }

            @Override
            public void onButtonClickAnimationEnd(@NonNull CircleMenuView view, int index) {
                        Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                        intent.putExtra("tableId", index);
                        startActivity(intent);
            }
        });
        Animation alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_menu);
        menu.startAnimation(alphaAnimation);
    }
}
