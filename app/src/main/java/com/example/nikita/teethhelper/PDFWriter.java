package com.example.nikita.teethhelper;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

/**
 * Created by Nikita on 21.05.2018.
 */

public class PDFWriter {

    Context context;

    public PDFWriter(Context context){
        this.context = context;
    }

    public void writeRenders(ArrayList<String[]> text){
        PdfDocument document = new PdfDocument();
        Paint redPaint = new Paint();
        redPaint.setColor(Color.BLUE);
        redPaint.setTextSize(28);
        int x = 100;
        int y = 40;
        for(int i = 0; i < text.size(); i++){
            PdfDocument.PageInfo pageInfo =
                    new PdfDocument.PageInfo.Builder(800, 1200, i+1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Drawable d = context.getResources().getDrawable(R.drawable.ic_doctor_order);
            d.setBounds(100, 100, 300,400);
            d.draw(canvas);
            Drawable p = context.getResources().getDrawable(R.drawable.ic_patient_order);
            p.setBounds(500, 100, 700,400);
            p.draw(canvas);
            for(int j = 0; j < text.get(0).length; j++) {
                canvas.drawText(text.get(i)[j], x, (j+1)*y+800, redPaint);
            }
            document.finishPage(page);
        }
        String targetPdf = "/sdcard/Renders.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toasty.success(context, context.getResources().getString(R.string.done), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toasty.error(context, context.getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
        document.close();
    }

    public void writeDoctors(ArrayList<String[]> text){
        PdfDocument document = new PdfDocument();
        Paint redPaint = new Paint();
        redPaint.setColor(Color.RED);
        redPaint.setTextSize(28);
        int x = 100;
        int y = 40;
        for(int i = 0; i < text.size(); i++){
            PdfDocument.PageInfo pageInfo =
                    new PdfDocument.PageInfo.Builder(800, 1200, i+1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Drawable d = context.getResources().getDrawable(R.drawable.ic_doctor_order);
            d.setBounds(100, 100, 300,400);
            d.draw(canvas);
            for(int j = 0; j < text.get(0).length; j++) {
                canvas.drawText(text.get(i)[j], x, (j+1)*y+800, redPaint);
            }
            document.finishPage(page);
        }
        String targetPdf = "/sdcard/Doctors.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toasty.success(context, context.getResources().getString(R.string.done), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toasty.error(context, context.getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
        document.close();
    }

    public void writePatients(ArrayList<String[]> text){
        PdfDocument document = new PdfDocument();
        Paint redPaint = new Paint();
        redPaint.setColor(Color.parseColor("#AA66CC"));
        redPaint.setTextSize(28);
        int x = 100;
        int y = 40;
        for(int i = 0; i < text.size(); i++){
            PdfDocument.PageInfo pageInfo =
                    new PdfDocument.PageInfo.Builder(800, 1200, i+1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Drawable p = context.getResources().getDrawable(R.drawable.ic_patient_order);
            p.setBounds(100, 100, 300,400);
            p.draw(canvas);
            for(int j = 0; j < text.get(0).length; j++) {
                canvas.drawText(text.get(i)[j], x, (j+1)*y+800, redPaint);
            }
            document.finishPage(page);
        }
        String targetPdf = "/sdcard/Patients.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toasty.success(context, context.getResources().getString(R.string.done), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toasty.error(context, context.getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
        document.close();
    }

    public void writeVisits(ArrayList<String[]> text){
        PdfDocument document = new PdfDocument();
        Paint redPaint = new Paint();
        redPaint.setColor(Color.parseColor("#AA66CC"));
        redPaint.setTextSize(28);
        int x = 100;
        int y = 40;
        for(int i = 0; i < text.size(); i++){
            PdfDocument.PageInfo pageInfo =
                    new PdfDocument.PageInfo.Builder(800, 1200, i+1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Drawable p = context.getResources().getDrawable(R.drawable.ic_patient_order);
            p.setBounds(100, 100, 300,400);
            p.draw(canvas);
            for(int j = 0; j < text.get(0).length; j++) {
                canvas.drawText(text.get(i)[j], x, (j+1)*y+800, redPaint);
            }
            document.finishPage(page);
        }
        String targetPdf = "/sdcard/Visits.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toasty.success(context, context.getResources().getString(R.string.done), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toasty.error(context, context.getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
        document.close();
    }

    public void writeVisitsStatistic(ArrayList<String> text){
        PdfDocument document = new PdfDocument();
        Paint redPaint = new Paint();
        redPaint.setColor(Color.parseColor("#AA66CC"));
        redPaint.setTextSize(28);
        int x = 100;
        int y = 40;
        int rowNumber = 0;
        int pageNumber = 1;
        PdfDocument.PageInfo pageInfo =
                new PdfDocument.PageInfo.Builder(800, 1200, pageNumber).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        for(int i = 0; i < text.size(); i++){
            canvas.drawText(text.get(i), x, (i+1)*y+100, redPaint);
            rowNumber++;
            if(rowNumber==15) {
                document.finishPage(page);
                pageNumber++;
                pageInfo = new PdfDocument.PageInfo.Builder(800, 1200, pageNumber).create();
                page = document.startPage(pageInfo);
                rowNumber = 0;

            }
        }
        document.finishPage(page);
        String targetPdf = "/sdcard/VisitsStatistic.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            Toasty.success(context, context.getResources().getString(R.string.done), Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toasty.error(context, context.getResources().getString(R.string.error), Toast.LENGTH_SHORT).show();
        }
        document.close();
    }
}
