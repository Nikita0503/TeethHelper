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

    @BindView(R.id.buttonDoctorsOrder)
    Button buttonDoctorsOrder;
    @OnClick(R.id.buttonDoctorsOrder)
    void onClickDoctorsOrder() {
        ReportActivityPresenter reportActivityPresenter = new ReportActivityPresenter(this);
        reportActivityPresenter.writeToFile("doctors");
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
        ReportActivityPresenter orderActivityPresenter = new ReportActivityPresenter(this);
        orderActivityPresenter.writeToFile("patients");
    }
    @OnClick(R.id.buttonRendersOrder)
    void onClickRendersOrder() {
        ReportActivityPresenter orderActivityPresenter = new ReportActivityPresenter(this);
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
