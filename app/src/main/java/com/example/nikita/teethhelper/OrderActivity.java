package com.example.nikita.teethhelper;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.nikita.teethhelper.UI.DateActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderActivity extends AppCompatActivity {

    public Intent date;

    @BindView(R.id.buttonDoctorsOrder)
    Button buttonDoctorsOrder;
    @OnClick(R.id.buttonDoctorsOrder)
    void onClickDoctorsOrder() {
        OrderActivityPresenter orderActivityPresenter = new OrderActivityPresenter(this);
        orderActivityPresenter.writeToFile("doctors");
        /*PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(400, 600, 1).create();

        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();

        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setTextSize(16);

        canvas.drawText("gagagagag   agagagaga\nfafafafaf", 50, 50, redPaint);
        canvas.drawText("gagagagag   agagagaga\nfafafafaf", 50, 75, redPaint);
        canvas.drawText("gagagagag   agagagaga\nfafafafaf", 50, 100, redPaint);

        // finish the page
        document.finishPage(page);
        // Create Page 2


        // write the document content
        String targetPdf = "/sdcard/TeethHelper.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(),
                    Toast.LENGTH_LONG).show();
        }

        // close the document
        document.close();*/
    }
    @OnClick(R.id.buttonPatientsOrder)
    void onClickPatientsOrder() {
        OrderActivityPresenter orderActivityPresenter = new OrderActivityPresenter(this);
        orderActivityPresenter.writeToFile("patients");
    }
    @OnClick(R.id.buttonRendersOrder)
    void onClickRendersOrder() {
        OrderActivityPresenter orderActivityPresenter = new OrderActivityPresenter(this);
        orderActivityPresenter.writeToFile("renders");
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
        setContentView(R.layout.activity_order);
        ButterKnife.bind(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    date = intent;
                    OrderActivityPresenter orderActivityPresenter = new OrderActivityPresenter(this);
                    orderActivityPresenter.writeToFile("visits for period");
                case 2:
                    date = intent;
                    OrderActivityPresenter orderActivityPresenter2 = new OrderActivityPresenter(this);
                    orderActivityPresenter2.writeToFile("visits statistic for period");
            }
        }
    }
}
