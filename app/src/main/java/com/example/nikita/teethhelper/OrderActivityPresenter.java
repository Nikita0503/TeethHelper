package com.example.nikita.teethhelper;

import android.content.Intent;
import android.util.Log;

import com.example.nikita.teethhelper.UI.DateActivity;
import com.example.nikita.teethhelper.UI.ListActivity;
import com.example.nikita.teethhelper.data.Doctor;
import com.example.nikita.teethhelper.data.Patient;
import com.example.nikita.teethhelper.data.Render;
import com.example.nikita.teethhelper.data.Visit;
import com.example.nikita.teethhelper.getters.DoctorsGetter;
import com.example.nikita.teethhelper.getters.PatientsGetter;
import com.example.nikita.teethhelper.getters.RendersGetter;
import com.example.nikita.teethhelper.getters.VisitsGetter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Created by Nikita on 21.05.2018.
 */

public class OrderActivityPresenter {
    OrderActivity orderActivity;

    public OrderActivityPresenter(OrderActivity orderActivity){
        this.orderActivity = orderActivity;
    }

    public void writeToFile(String typeOFOrder){
        PDFWriter pdfWriter = new PDFWriter(orderActivity.getApplicationContext());
        switch (typeOFOrder){
            case "doctors":
                DoctorsGetter doctorsGetter = new DoctorsGetter(orderActivity.getApplicationContext());
                ArrayList<Doctor> doctors = doctorsGetter.getDoctors();
                pdfWriter.writeDoctors(getArrayListByDoctors(doctors));
                break;
            case "patients":
                PatientsGetter patientsGetter = new PatientsGetter(orderActivity.getApplicationContext());
                ArrayList<Patient> patients = patientsGetter.getPatients();
                pdfWriter.writePatients(getArrayListByPatients(patients));
                break;
            case "renders":
                RendersGetter rendersGetter = new RendersGetter(orderActivity.getApplicationContext());
                ArrayList<Render> renders = rendersGetter.getRenders();
                pdfWriter.writeRenders(getArrayListByRenders(renders));
                break;
            case "visits for period":
                VisitsGetter visitsGetter = new VisitsGetter(orderActivity.getApplicationContext());
                ArrayList<Visit> visitsBeforeSelection = visitsGetter.getVisits();
                ArrayList<Visit> visitsAfterSelection = getVisitsSelectionByDates(visitsBeforeSelection , orderActivity.date.getStringExtra("dateAfter"), orderActivity.date.getStringExtra("dateBefore"));
                pdfWriter.writeVisits(getArrayListByVisits(visitsAfterSelection));
                break;
            case "visits statistic for period":
                VisitsGetter visitsGetter2 = new VisitsGetter(orderActivity.getApplicationContext());
                ArrayList<Visit> visitsBeforeSelection2 = visitsGetter2.getVisits();
                ArrayList<Visit> visitsAfterSelection2 = getVisitsSelectionByDates(visitsBeforeSelection2 , orderActivity.date.getStringExtra("dateAfter"), orderActivity.date.getStringExtra("dateBefore"));
                pdfWriter.writeVisitsStatistic(getVisitsStatistic(visitsAfterSelection2));
                break;
        }
    }

    private ArrayList<String[]> getArrayListByDoctors(ArrayList<Doctor> doctors){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] tagNames = orderActivity.getResources().getStringArray(R.array.doctorTagNames);
        String row[];
        for(int i = 0; i < doctors.size(); i++){
            row = new String[6];
            row[0] = tagNames[1] + " : " + doctors.get(i).name;
            row[1] = tagNames[2] + " : " + doctors.get(i).passport;
            row[2] = tagNames[3] + " : " + doctors.get(i).address;
            row[3] = tagNames[4] + " : " + doctors.get(i).specialization;
            row[4] = tagNames[5] + " : " + doctors.get(i).experience;
            row[5] = tagNames[6] + " : " + doctors.get(i).berth;
            arrayList.add(row);
        }
        return arrayList;
    }

    private ArrayList<String[]> getArrayListByPatients(ArrayList<Patient> patients){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] tagNames = orderActivity.getResources().getStringArray(R.array.patientTagNames);
        String row[];
        for(int i = 0; i < patients.size(); i++){
            row = new String[4];
            row[0] = tagNames[1] + " : " + patients.get(i).name;
            row[1] = tagNames[2] + " : " + patients.get(i).passport;
            row[2] = tagNames[3] + " : " + patients.get(i).address;
            row[3] = tagNames[4] + " : " + patients.get(i).disease;
            arrayList.add(row);
        }
        return arrayList;
    }

    private ArrayList<String[]> getArrayListByRenders(ArrayList<Render> renders){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();
        String[] tagNames = orderActivity.getResources().getStringArray(R.array.renderTagNames);
        String row[];
        for(int i = 0; i < renders.size(); i++){
            row = new String[5];
            row[0] = tagNames[0] + " : " + renders.get(i).service;
            row[1] = tagNames[1] + " : " + renders.get(i).patient;
            row[2] = tagNames[2] + " : " + renders.get(i).doctor;
            row[3] = tagNames[3] + " : " + renders.get(i).sum;
            row[4] = tagNames[4] + " : " + renders.get(i).date;
            arrayList.add(row);
        }
        return arrayList;
    }

    private ArrayList<String[]> getArrayListByVisits(ArrayList<Visit> visits){
        ArrayList<String[]> arrayList = new ArrayList<String[]>();;
        String[] tagNames = orderActivity.getResources().getStringArray(R.array.visitTagNames);
        String row[];
        for(int i = 0; i < visits.size(); i++){
            row = new String[3];
            row[0] = tagNames[0] + " : " + visits.get(i).patient;
            row[1] = tagNames[1] + " : " + visits.get(i).date;
            row[2] = tagNames[2] + " : " + visits.get(i).service;
            arrayList.add(row);
        }
        return arrayList;
    }

    private ArrayList<Visit> getVisitsSelectionByDates(ArrayList<Visit> visits, String startDateString, String endDateString){
        ArrayList<Visit> visitsAfterSelection = new ArrayList<Visit>();
        Date startDate = getDateByStringTokenizer(new StringTokenizer(startDateString, "."));
        Date endDate =  getDateByStringTokenizer(new StringTokenizer(endDateString, "."));
        for(int i = 0; i < visits.size(); i++){
            Date date =  getDateByStringTokenizer(new StringTokenizer(visits.get(i).date, "."));
            if(date.after(startDate) && date.before(endDate)){
                visitsAfterSelection.add(visits.get(i));
            }
        }
        return visitsAfterSelection;
    }

    private ArrayList<String> getVisitsStatistic(ArrayList<Visit> visits){
        ArrayList<String> statistic = new ArrayList<String>();
        HashSet<String> patientsSet = new HashSet<String>();
        int counter;
        for(int i = 0; i < visits.size(); i++){
            patientsSet.add(visits.get(i).patient);
        }
        ArrayList<String> patients = new ArrayList<String>();
        patients.addAll(patientsSet);
        for(int i = 0; i < patients.size(); i++){
            counter = 0;
            for(int j = 0; j < visits.size(); j++){
                if(patients.get(i).equals(visits.get(j).patient)){
                    counter++;
                }
            }
            statistic.add(patients.get(i) + " : " + counter);
            counter = 0;
        }
        return statistic;
    }

    private Date getDateByStringTokenizer(StringTokenizer stringDate){
        Date date = null;
        while(stringDate.hasMoreTokens()) {
            int day = Integer.parseInt(stringDate.nextToken());
            int month = Integer.parseInt(stringDate.nextToken());
            int year = Integer.parseInt(stringDate.nextToken());
            date = new Date(year, month, day);
        }
        return date;
    }


}
